package gov.nist.healthcare.cds.service.validation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.util.Assert;

import gov.nist.healthcare.cds.domain.Manufacturer;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineGroup;
import gov.nist.healthcare.cds.domain.VaccineMapping;
import gov.nist.healthcare.cds.domain.wrapper.VaccineRef;
import gov.nist.healthcare.cds.domain.Product;
import gov.nist.healthcare.cds.repositories.VaccineMappingRepository;
import gov.nist.healthcare.cds.service.VaccineMatcherService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes=gov.nist.healthcare.cds.service.validation.ContextConfiguration.class)
public class VaccineMatcherServiceTest {
	
	@Autowired
	private VaccineMappingRepository repo;
	
	@Autowired
	private VaccineMatcherService vaccineMatcher;
	
	public VaccineMapping mapping(String cvx, String mvx, List<VaccineGroup> groups){
		VaccineMapping vm = new VaccineMapping();
		vm.setGroup(false);
		if(groups != null)
		vm.setGroups(new HashSet<VaccineGroup>(groups));
		Vaccine generic = vx(cvx);
		vm.setVx(generic);
		vm.setProducts(new HashSet<Product>(Arrays.asList(px(generic,mvx))));
		return vm;
	}
	
	
	public Vaccine vx(String cvx){
		Vaccine vx = new Vaccine();
		vx.setCvx(cvx);
		return vx;
	}
	
	public Product px(Vaccine vx, String mvx){
		Product p = new Product();
		p.setMx(mx(mvx));
		p.setVx(vx);
		return p;
	}
	
	public Manufacturer mx(String mvx){
		Manufacturer mx = new Manufacturer();
		mx.setMvx(mvx);
		return mx;
	}
	
	public VaccineGroup gx(String cvx){
		VaccineGroup gx = new VaccineGroup();
		gx.setCvx(cvx);
		return gx;
	}
	
	@Before
	public void prepare(){
		Mockito.when(repo.findMapping("c")).thenReturn(mapping("c","m",Arrays.asList(gx("a"),gx("b"))));
		Mockito.when(repo.findMapping("x")).thenReturn(mapping("x","n",Arrays.asList(gx("a"),gx("b"))));
		Mockito.when(repo.findMapping("y")).thenReturn(mapping("y","m",Arrays.asList(gx("b"),gx("a"))));
		Mockito.when(repo.findMapping("z")).thenReturn(mapping("z","n",Arrays.asList(gx("a"))));
		Mockito.when(repo.findMapping("t")).thenReturn(mapping("t","n",Arrays.asList(gx("a"),gx("none"))));
		Mockito.when(repo.findMapping("s")).thenReturn(mapping("s","n",Arrays.asList(gx("a"),gx("b"),gx("none"))));
		Mockito.when(repo.findMapping("s")).thenReturn(mapping("e","f",null));
	}
	
	@Test
	public void straightCompareProductTrue(){
		Vaccine v = vx("c");
		Product p = px(v,"m");
		
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("c","m"), p));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("c","none"), p));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("none","m"), p));
	}
	
	
	
	@Test
	public void straightCompareVaccine(){
		Vaccine v = vx("c");
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("c",""), v));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("none",""), v));
	}
	
	@Test
	public void straightCompareProductAndVaccine(){
		Vaccine v = vx("c");
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("c","m"), v));
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("c","none"), v));
		
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("none","m"), v));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("none","none"), v));
	}
	
	@Test
	public void straightCompareVaccineAndProduct(){
		Vaccine v = vx("c");
		Product p = px(v,"m");
		
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("c",""), p));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("none",""), p));
	}
	
	@Test
	public void groupAsRefVaccine(){
		Vaccine v = vx("c");
		
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("a",""), v));
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("b",""), v));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("none",""), v));
	}
	
	@Test
	public void groupAsRefProduct(){
		Vaccine v = vx("c");
		Product p = px(v,"m");
		
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("a",""), p));
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("b",""), p));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("none",""), p));
	}
	
	@Test
	public void siblingAsRefVaccine(){
		Vaccine v = vx("c");
		Product p = px(v,"m");
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("x",""), p));
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("y",""), p));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("z",""), p));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("t",""), p));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("s",""), p));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("e",""), p));
	}
	
	@Test
	public void siblingAsRefProduct(){
		Vaccine v = vx("c");
		
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("x",""), v));
		Assert.isTrue(vaccineMatcher.match(new VaccineRef("y",""), v));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("z",""), v));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("t",""), v));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("s",""), v));
		Assert.isTrue(!vaccineMatcher.match(new VaccineRef("e",""), v));
	}
	

}

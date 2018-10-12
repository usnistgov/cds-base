package gov.nist.healthcare.cds.service.vaccine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import gov.nist.healthcare.cds.domain.EqTuple;
import gov.nist.healthcare.cds.domain.VaccineMatcherCodeNode;
import gov.nist.healthcare.cds.service.domain.matcher.CVXType;
import gov.nist.healthcare.cds.service.domain.matcher.GroupType;
import gov.nist.healthcare.cds.service.domain.matcher.VaccineMatcherType;


public class VaccineMatcherConfiguration {
	
	private VaccineMatcherType xmlConfiguration;
	private List<EqTuple> eqTuples;
	
	public VaccineMatcherConfiguration(InputStream xml) throws JAXBException {
		initTuples();
	}
	
	public VaccineMatcherConfiguration() throws JAXBException {
		initTuples();
	}
	
	private void initTuples(){
		eqTuples = new ArrayList<EqTuple>();
		EqTuple tuple = new EqTuple("DTAP/TD/TDAP");
		tuple.addNode(new VaccineMatcherCodeNode("139"));
		tuple.addNode(new VaccineMatcherCodeNode("107"));
		tuple.addNode(new VaccineMatcherCodeNode("115"));
		eqTuples.add(tuple);
		tuple = new EqTuple("PNEUMO");
		tuple.addNode(new VaccineMatcherCodeNode("152"));
		tuple.addNode(new VaccineMatcherCodeNode("33"));
		tuple.addNode(new VaccineMatcherCodeNode("109"));
		eqTuples.add(tuple);
	}
	
	private VaccineMatcherCodeNode getVMCNode(Object node){
		if(node instanceof GroupType){
			return new VaccineMatcherCodeNode(((GroupType) node).getCode());
		}
		else if(node instanceof CVXType){
			return new VaccineMatcherCodeNode(((CVXType) node).getCode()); 
		}
		return null;
	}
	
	private EqTuple tupleFor(VaccineMatcherCodeNode node){
		
		for(EqTuple tuple : eqTuples){
			if(tuple.contains(node)){
				return tuple;
			}
		}
		
		EqTuple tuple = new EqTuple(node.getCvx());
		tuple.addNode(node);
		eqTuples.add(tuple);
		return tuple;
	}
	
	public EqTuple tupleFor(String cvx){
		for(EqTuple tuple : eqTuples){
			if(tuple.contains(cvx)){
				return tuple;
			}
		}
		return null;
	}
	
	private void addNodes(EqTuple tuple, List<Object> mapping){
		for(Object node : mapping){
			VaccineMatcherCodeNode n = this.getVMCNode(node);
			if(!tuple.contains(n)){
				tuple.addNode(n);
			}
		}
	}
	
	public boolean checkGroups(){
		return xmlConfiguration.isMatchSameGroup();
	}

}

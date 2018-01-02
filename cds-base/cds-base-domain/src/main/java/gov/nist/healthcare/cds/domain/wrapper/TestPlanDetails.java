package gov.nist.healthcare.cds.domain.wrapper;

public class TestPlanDetails {

	private String name;
	private String id;
	private int nb;
	
	
	public TestPlanDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TestPlanDetails(String name, String id, int nb) {
		super();
		this.name = name;
		this.id = id;
		this.nb = nb;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNb() {
		return nb;
	}
	public void setNb(int nb) {
		this.nb = nb;
	}
	
	
}

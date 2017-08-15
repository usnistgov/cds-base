package gov.nist.healthcare.cds.domain;

public class VaccineMatcherCodeNode {
	
	private String cvx;
	
	public VaccineMatcherCodeNode() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public VaccineMatcherCodeNode(String cvx) {
		super();
		this.cvx = cvx;
	}

	public String getCvx() {
		return cvx;
	}

	public void setCvx(String cvx) {
		this.cvx = cvx;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cvx == null) ? 0 : cvx.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VaccineMatcherCodeNode other = (VaccineMatcherCodeNode) obj;
		if (cvx == null) {
			if (other.cvx != null)
				return false;
		} else if (!cvx.equals(other.cvx))
			return false;
		return true;
	}

}

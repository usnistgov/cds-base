package gov.nist.healthcare.cds.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EqTuple {
	
	private String eq;
	private List<VaccineMatcherCodeNode> vmcnl;

	public EqTuple(String id) {
		super();
		eq = id;
		vmcnl = new ArrayList<VaccineMatcherCodeNode>();
	}

	public boolean addNode(VaccineMatcherCodeNode e) {
		return vmcnl.add(e);
	}

	public boolean contains(String cvx) {
		return vmcnl.contains(new VaccineMatcherCodeNode(cvx));
	}

	public boolean contains(VaccineMatcherCodeNode o) {
		return vmcnl.contains(o);
	}

	public String getEq() {
		return eq;
	}

	public void setEq(String eq) {
		this.eq = eq;
	}
	
	
	
	
	
}

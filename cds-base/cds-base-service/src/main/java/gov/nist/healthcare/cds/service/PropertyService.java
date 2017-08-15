package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.Entity;
import gov.nist.healthcare.cds.domain.SoftwareConfig;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.domain.TestCaseGroup;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.wrapper.Report;
import gov.nist.healthcare.cds.enumeration.EntityAccess;

public interface PropertyService {

	public TestCase tcBelongsTo(String tp, String user, EntityAccess accessType);
	public TestPlan tpBelongsTo(String tc, String user, EntityAccess accessType);
	public TestCaseGroup tgBelongsTo(String tg, String user, EntityAccess accessType);
	public Report reportBelongsTo(String report, String user);
	public SoftwareConfig configBelongsTo(String config, String user);
	public <T extends Entity> T belongsTo(String id, String user, Class<T> type, EntityAccess accessType);
	
}

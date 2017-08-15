package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.exception.ShareException;

public interface TPShareService {

	public void share(String tpId, String userId, String shareWith) throws ShareException;
	public void unshare(String tpId, String userId, String shareWith) throws ShareException;
	public void makePublic(String tpId, String userId, boolean toggle) throws ShareException;
	
}

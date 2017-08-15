package gov.nist.healthcare.cds.service.impl.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.nist.healthcare.cds.auth.service.AccountService;
import gov.nist.healthcare.cds.domain.TestPlan;
import gov.nist.healthcare.cds.domain.exception.ShareException;
import gov.nist.healthcare.cds.enumeration.EntityAccess;
import gov.nist.healthcare.cds.repositories.TestPlanRepository;
import gov.nist.healthcare.cds.service.PropertyService;
import gov.nist.healthcare.cds.service.TPShareService;

@Service
public class SharingService implements TPShareService {
	
	@Autowired
	private TestPlanRepository tpRepo;
	@Autowired
	private PropertyService ledger;
	@Autowired
	private AccountService accountService;

	@Override
	public void share(String tpId, String userId, String shareWith) throws ShareException {
		TestPlan tp = preCondition(tpId, userId, shareWith);
		if(!tp.getViewers().contains(shareWith)){
			tp.getViewers().add(shareWith);
			tpRepo.save(tp);
		}
	}

	@Override
	public void unshare(String tpId, String userId, String shareWith) throws ShareException {
		TestPlan tp = preCondition(tpId, userId, shareWith);
		if(tp.getViewers().contains(shareWith)){
			tp.getViewers().remove(shareWith);
			tpRepo.save(tp);
		}
	}
	
	public TestPlan preCondition(String tpId, String userId, String shareWith) throws ShareException{
		TestPlan tp = ledger.tpBelongsTo(tpId, userId, EntityAccess.W);
		
		if(tp == null)
			throw new ShareException("tp-not-found",tpId);
		if(accountService.getAccountByUsername(shareWith) == null)
			throw new ShareException("user-not-found",shareWith);
		if(userId.equals(shareWith))
			throw new ShareException("self", userId);
		
		return tp;
	}

	@Override
	public void makePublic(String tpId, String userId, boolean bool) throws ShareException {
		TestPlan tp = ledger.tpBelongsTo(tpId, userId, EntityAccess.W);
		if(tp == null)
			throw new ShareException("tp-not-found",tpId);
		if(!bool && tp.isPublic()){
			tp.setPublic(false);
			tpRepo.save(tp);
		}
		else if(bool && !tp.isPublic()){
			tp.setPublic(true);
			tpRepo.save(tp);
		}
		else {
			throw new ShareException("invalid-action", userId);
		}
		
	}

}

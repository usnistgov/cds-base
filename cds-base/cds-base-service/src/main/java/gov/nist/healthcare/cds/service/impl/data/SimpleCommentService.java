package gov.nist.healthcare.cds.service.impl.data;

import org.springframework.beans.factory.annotation.Autowired;

import gov.nist.healthcare.cds.domain.CommentHolder;
import gov.nist.healthcare.cds.domain.TestCase;
import gov.nist.healthcare.cds.enumeration.EntityAccess;
import gov.nist.healthcare.cds.repositories.CommentRepository;
import gov.nist.healthcare.cds.service.CommentService;
import gov.nist.healthcare.cds.service.PropertyService;

public class SimpleCommentService implements CommentService {
	
	@Autowired
	private CommentRepository repo;
	
	@Autowired
	private PropertyService ledger;

	@Override
	public String getCommentForTestCase(String testId, String user) {
		TestCase tc = this.ledger.tcBelongsTo(testId, user, EntityAccess.W);
		if(tc != null){
			CommentHolder comment = this.repo.findOne(testId);
			if(comment != null){
				return comment.getComment();
			}
			else {
				return "";
			}
		}
		else {
			return "";
		}
	}

	@Override
	public void setCommentForTestCase(String testId, String user, String comment) {
		TestCase tc = this.ledger.tcBelongsTo(testId, user, EntityAccess.W);
		if(tc != null){
			CommentHolder commentHolder = new CommentHolder();
			commentHolder.setId(testId);
			commentHolder.setComment(comment);
			this.repo.save(commentHolder);
		}
	}

	
	
}

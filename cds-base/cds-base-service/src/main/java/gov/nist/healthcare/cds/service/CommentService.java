package gov.nist.healthcare.cds.service;

public interface CommentService {
	
	public String getCommentForTestCase(String testId, String user);
	public void setCommentForTestCase(String testId, String user, String comment);

}

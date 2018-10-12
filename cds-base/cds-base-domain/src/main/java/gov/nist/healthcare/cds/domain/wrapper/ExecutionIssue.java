package gov.nist.healthcare.cds.domain.wrapper;

import gov.nist.healthcare.cds.enumeration.IssueCategory;
import gov.nist.healthcare.cds.enumeration.IssueLevel;

public class ExecutionIssue {
	
	IssueLevel level;
	IssueCategory category;
	String issueId;
	String message;
	
	public ExecutionIssue(IssueLevel level, IssueCategory category, String issueId, String message) {
		super();
		this.level = level;
		this.category = category;
		this.issueId = issueId;
		this.message = message;
	}

	public ExecutionIssue() {
		super();
	}

	public IssueLevel getLevel() {
		return level;
	}

	public void setLevel(IssueLevel level) {
		this.level = level;
	}

	public IssueCategory getCategory() {
		return category;
	}

	public void setCategory(IssueCategory category) {
		this.category = category;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

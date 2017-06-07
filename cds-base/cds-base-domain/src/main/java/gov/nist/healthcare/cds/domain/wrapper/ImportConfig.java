package gov.nist.healthcare.cds.domain.wrapper;

import org.apache.commons.lang.builder.ToStringBuilder;

public class ImportConfig {
	private int position;
	private boolean all;
	private String lines;
	private boolean ignore;
	private boolean ovGroup;
	private String groupId;
	
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getLines() {
		return lines;
	}
	public void setLines(String lines) {
		this.lines = lines;
	}
	public boolean isIgnore() {
		return ignore;
	}
	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}
	public boolean isAll() {
		return all;
	}
	public void setAll(boolean all) {
		this.all = all;
	}
	
	public boolean isOvGroup() {
		return ovGroup;
	}
	public void setOvGroup(boolean ovGroup) {
		this.ovGroup = ovGroup;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	@Override
	public String toString() 
	{ 
	    return ToStringBuilder.reflectionToString(this); 
	}

}

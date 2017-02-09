package gov.nist.healthcare.cds.domain.wrapper;

public class CDCImportConfig {
	private boolean all;
	private int from;
	private int to;
	private boolean ignore;
	
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
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
	

}

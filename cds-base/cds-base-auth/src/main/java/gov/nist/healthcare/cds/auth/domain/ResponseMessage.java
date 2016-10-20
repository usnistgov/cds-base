package gov.nist.healthcare.cds.auth.domain;

public class ResponseMessage {
    public enum Type {
        success, warn, danger, info;
    }
 
    private final Type type;
    private final String text;
    private final String resourceId;
    private final String manualHandle;
    private   boolean skip;

    public ResponseMessage(Type type, String text, String resourceId, String manualHandle) {
        this.type = type;
        this.text = text;
        this.resourceId = resourceId;
        this.manualHandle = manualHandle;
    }
    

    public ResponseMessage(Type type, String text, String resourceId) {
        this.type = type;
        this.text = text;
        this.resourceId = resourceId;
        this.manualHandle = "false";
    }
  
    public ResponseMessage(Type type, String text, String resourceId,boolean skip) {
        this.type = type;
        this.text = text;
        this.resourceId = resourceId;
        this.manualHandle = "false";
        this.skip = skip;
    }

    public ResponseMessage(Type type, String text) {
        this.type = type;
        this.text = text;
        this.resourceId = null;
        this.manualHandle = "false";
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }

    public String getResourceId() {
        return resourceId;
    }
  
    public String getManualHandle() {
        return manualHandle;
    }


	public boolean isSkip() {
		return skip;
	}


	public void setSkip(boolean skip) {
		this.skip = skip;
	}
    
    
    
}
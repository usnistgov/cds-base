package gov.nist.healthcare.cds.auth.domain;

public class ShortAccount {

    private String id;
    private String username;
    private String email;
    private boolean pending;
    private String accountType;
    private String fullName;
    private String organization;

    public ShortAccount(Account account){
        this.id = account.getId();
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.pending = account.isPending();
        this.accountType = account.getAccountType();
        this.fullName = account.getFullName();
        this.organization = account.getOrganization();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}

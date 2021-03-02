package gov.nist.healthcare.cds.service.domain;

import com.google.common.base.Strings;
import gov.nist.healthcare.cds.domain.Vaccine;
import gov.nist.healthcare.cds.domain.VaccineGroup;

public class CvxCodeReMap {
    private String cvx;
    private String details;
    private boolean group;
    private String groupName;

    public CvxCodeReMap(String cvx, String details, boolean group, String groupName) {
        assert !Strings.isNullOrEmpty(cvx) && !Strings.isNullOrEmpty(details) && (!group || !Strings.isNullOrEmpty(details));
        this.cvx = cvx;
        this.details = details;
        this.group = group;
        this.groupName = groupName;
    }

    public Vaccine getVaccine() {
        Vaccine vaccine = new Vaccine();
        vaccine.setCvx(cvx);
        vaccine.setDetails(details);
        return vaccine;
    }

    public VaccineGroup getGroup() {
        VaccineGroup vaccine = new VaccineGroup();
        vaccine.setCvx(cvx);
        vaccine.setName(groupName);
        return vaccine;
    }

    public String getCvx() {
        return cvx;
    }

    public void setCvx(String cvx) {
        this.cvx = cvx;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}

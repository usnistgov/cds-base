package gov.nist.healthcare.cds.service;

import gov.nist.healthcare.cds.domain.UserMetadata;

public interface UserMetadataService {

    UserMetadata getByUsername(String username);
    void updateLastApiCall(String username);
    void updateTestCasesNumber(String username);
    void updateExecutions(String username, int nb);

}

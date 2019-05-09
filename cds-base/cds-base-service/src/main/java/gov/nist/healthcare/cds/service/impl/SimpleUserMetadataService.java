package gov.nist.healthcare.cds.service.impl;

import gov.nist.healthcare.cds.domain.UserMetadata;
import gov.nist.healthcare.cds.repositories.TestCaseRepository;
import gov.nist.healthcare.cds.repositories.UserMetadataRepository;
import gov.nist.healthcare.cds.service.UserMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SimpleUserMetadataService implements UserMetadataService {

    @Autowired
    private TestCaseRepository tcRepository;
    @Autowired
    private UserMetadataRepository userMetadataRepository;

    @Override
    public UserMetadata getByUsername(String username) {
        return this.userMetadataRepository.findOne(username);
    }

    @Override
    public void updateLastApiCall(String username) {
        Date today = new Date();
        if(this.userMetadataRepository.exists(username)) {
            UserMetadata um = this.userMetadataRepository.findOne(username);
            um.setLastApiCall(today);
            this.userMetadataRepository.save(um);
        } else {
            UserMetadata um = new UserMetadata();
            um.setUsername(username);
            um.setLastApiCall(today);
            this.userMetadataRepository.save(um);
        }
    }

    @Override
    public void updateTestCasesNumber(String username) {
        long nbTestCases = this.tcRepository.countByUser(username);
        if(this.userMetadataRepository.exists(username)) {
            UserMetadata um = this.userMetadataRepository.findOne(username);
            um.setTestCases((int) nbTestCases);
            this.userMetadataRepository.save(um);
        } else {
            UserMetadata um = new UserMetadata();
            um.setUsername(username);
            um.setTestCases((int) nbTestCases);
            this.userMetadataRepository.save(um);
        }
    }

    @Override
    public void updateExecutions(String username, int nb) {
        if(this.userMetadataRepository.exists(username)) {
            UserMetadata um = this.userMetadataRepository.findOne(username);
            um.setExecutions(um.getExecutions() + nb);
            this.userMetadataRepository.save(um);
        } else {
            UserMetadata um = new UserMetadata();
            um.setUsername(username);
            um.setExecutions(nb);
            this.userMetadataRepository.save(um);
        }
    }
}

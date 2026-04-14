package com.final_project.versioncontrolservice.repo;
import com.final_project.versioncontrolservice.model.InvitationDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends MongoRepository<InvitationDocument, ObjectId> {

    long countByRepoOwnerAndRepoNameAndInvitedUserAndStatus(
            String repoOwner, String repoName, String invitedUser, String status);

    List<InvitationDocument> findByInvitedUserAndStatus(String invitedUser, String status);
}

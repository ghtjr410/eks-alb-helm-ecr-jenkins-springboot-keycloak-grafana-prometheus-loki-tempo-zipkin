package com.ghtjr.userprofile.repository;

import com.ghtjr.userprofile.model.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserProfileRepository extends MongoRepository<UserProfile, String> {
}

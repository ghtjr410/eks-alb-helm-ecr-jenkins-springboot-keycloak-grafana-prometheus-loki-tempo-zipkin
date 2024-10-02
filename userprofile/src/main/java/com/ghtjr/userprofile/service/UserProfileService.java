package com.ghtjr.userprofile.service;

import com.ghtjr.userprofile.dto.UserProfileRequest;
import com.ghtjr.userprofile.dto.UserProfileResponse;
import com.ghtjr.userprofile.model.UserProfile;
import com.ghtjr.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileResponse createOrUpdateUserProfile(String uuid, UserProfileRequest userProfileRequest) {
        UserProfile userProfile = userProfileRepository.findById(uuid)
                .orElse(new UserProfile());

        userProfile.setUuid(uuid);
        userProfile.setBio(userProfileRequest.bio());
        userProfile.setBlogTitle(userProfileRequest.blogTitle());

        UserProfile savedUserProfile = userProfileRepository.save(userProfile);

        return new UserProfileResponse(
                savedUserProfile.getUuid(),
                savedUserProfile.getBio(),
                savedUserProfile.getBlogTitle()
        );
    }
}

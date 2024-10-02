package com.ghtjr.userprofile.Controller;

import com.ghtjr.userprofile.dto.UserProfileRequest;
import com.ghtjr.userprofile.dto.UserProfileResponse;
import com.ghtjr.userprofile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/user-profile/{uuid}")
@RequiredArgsConstructor
public class Controller {

    private final UserProfileService userProfileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileResponse createUserProfile(@PathVariable String uuid,
                                                 @RequestBody UserProfileRequest userProfileRequest){
        return userProfileService.createOrUpdateUserProfile(uuid, userProfileRequest);
    }
}

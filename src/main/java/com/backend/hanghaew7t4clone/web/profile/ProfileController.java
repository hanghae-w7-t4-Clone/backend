package com.backend.hanghaew7t4clone.web.profile;

import com.backend.hanghaew7t4clone.domain.profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {
   private final ProfileService profileService;

   @GetMapping("/profiles/{nickname}")
   public ResponseEntity<?> getProfile(@PathVariable String nickname) {
      return profileService.getProfile(nickname);
   }

   @GetMapping("/profiles/{nickname}/cards")
   public ResponseEntity<?> getProfileCards(@PathVariable String nickname) {
      return profileService.getProfileCards(nickname);
   }

   @GetMapping("/recent-users")
   public ResponseEntity<?> getRecentMembers() {
      return profileService.getRecentMembers();
   }
}

package com.backend.hanghaew7t4clone.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileController {
   private final ProfileService profileService;
   @RequestMapping(value = "/profiles/{nickname}", method = RequestMethod.GET)
   public ResponseEntity<?> getProfile(@PathVariable String nickname) {
      return profileService.getProfile(nickname);
   }
   @RequestMapping(value = "/profiles/{nickname}/cards", method = RequestMethod.GET)
   public ResponseEntity<?> getProfileCards(@PathVariable String nickname) {
      return profileService.getProfileCards(nickname);
   }
}

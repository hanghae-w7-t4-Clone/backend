package com.backend.hanghaew7t4clone.profile;

import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.card.CardRepository;
import com.backend.hanghaew7t4clone.exception.MemberNotFoundException;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.member.MemberRepository;
import com.backend.hanghaew7t4clone.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
   private final MemberRepository memberRepository;
   private final CardRepository cardRepository;
   public ResponseEntity<?> getProfile(String nickname) {
      Member member = getMember(nickname);
      List<Card> card = cardRepository.findAllByMember(member);
      ProfileResponseDto profileResponseDto=ProfileResponseDto.builder()
              .id(member.getId())
              .nickname(member.getNickname())
              .name(member.getName())
//                 .content(member.getContent)
//                 .profilePhoto(member.getprofilePhoto)
              .cardCount(card.size())
              .build();
      return new ResponseEntity<>(Message.success(profileResponseDto), HttpStatus.OK);
   }



   public ResponseEntity<?> getProfileCards(String nickname) {
      Member member=getMember(nickname);
      List<Card> cardList = cardRepository.findAllByMember(member);
      List<ProfileCardsResponseDto> profileCardsResponseDtoList=new ArrayList<>();
      for (Card card : cardList) {
                  profileCardsResponseDtoList.add(ProfileCardsResponseDto.builder()
                 .id(card.getId())
                 .imgUrlList(card.getImgUrlList())
                 .likeCount(card.getLikeCount())
                 .commentCount(card.getCommentCount())/// .commentCount(card.getComment().size)
                 .photoCount(card.getImgUrlList().size())
                 .build()
);
      }
      return new ResponseEntity<>(Message.success(profileCardsResponseDtoList), HttpStatus.OK);
   }
   private Member getMember(String nickname) {
      Member member=isPresentMember(nickname);
      if (member==null){
         throw new MemberNotFoundException();
      }
      ;
      return member;
   }
   @Transactional(readOnly = true)
   public Member isPresentMember(String nickname) {
      Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
      return optionalMember.orElse(null);
   }
}
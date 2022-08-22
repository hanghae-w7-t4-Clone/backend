package com.backend.hanghaew7t4clone.card;

import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.likes.Likes;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.shared.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card extends Timestamped {

   @Column
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 생성을 데이터베이스에 위임
   private Long id;

   @Column
   String nickname;

   @Column
   @ElementCollection(targetClass = String.class)
   private List<String> imgUrlList;

   @Column
   private int likeCount;

   @Column(nullable = false)
   private String content;

   @Column
   private int commentCount;

   @Column
   private String place;

   @ManyToOne
   @JoinColumn(name = "member_id")
   @JsonIgnore
   private Member member;

   @Column
   @OneToMany(mappedBy = "card",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Comment> commentListDto;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "card", cascade = CascadeType.ALL)
   private List<Likes> likesList;

   @Builder
   public Card(String nickname, List<String> imgUrlList,int likeCount, String content,int commentCount,String place, Member member) {
      this.nickname=nickname;
      this.imgUrlList=imgUrlList;
      this.likeCount=likeCount;
      this.content=content;
      this.commentCount=commentCount;
      this.place=place;
      this.member=member;
   }
   public void setMember(Member member) {
      this.member = member;
   }

   public void update(CardRequestDto postRequestDto) {
      this.content = postRequestDto.getContent();
      this.imgUrlList=postRequestDto.getImgUrlList();
      this.place= postRequestDto.getPlace();
   }

   public void updateLikes(int likes) {
      this.likeCount= likes;
   }
   public void discountLikes(Likes likes) {
      this.likesList.remove(likes);
   }

}

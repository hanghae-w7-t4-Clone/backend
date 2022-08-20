package com.backend.hanghaew7t4clone.card;

import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.likes.Likes;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.shared.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
   private Set<Likes> likes = new HashSet<>();

   public void setMember(Member member) {
      this.member = member;
   }

   public void update(CardRequestDto postRequestDto) {
      this.content = postRequestDto.getContent();
   }


   public void discountLikes(Likes likes) {
      this.likes.remove(likes);
   }

}

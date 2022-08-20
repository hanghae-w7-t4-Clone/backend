package com.backend.hanghaew7t4clone.card;

import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.member.Member;
import com.backend.hanghaew7t4clone.shared.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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
   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Comment> commentListDto;

   //  @JsonIgnore
//  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true) // 단방향
//  // cascade = CascadeType.ALL , 현 Entity의 변경에 대해 관계를 맺은 Entity도 변경 전략을 결정합니다.
//  // fetch = FetchType.LAZY, 관계된 Entity의 정보를 LAZY는 실제로 요청하는 순간 가져오는겁니다.
//  // orphanRemoval = true, 관계 Entity에서 변경이 일어난 경우 DB 변경을 같이 할지 결정합니다.
//  private List<Comment> comments;
   @Builder
   public Card(String nickname, List<String> imgUrlList,int likeCount, String content,int commentCount, Member member) {
              this.nickname=nickname;
              this.imgUrlList=imgUrlList;
              this.likeCount=likeCount;
              this.content=content;
              this.commentCount=commentCount;
              this.member=member;
   }

   public void setMember(Member member) {
      this.member = member;
   }

   public void update(CardRequestDto postRequestDto) {
      this.content = postRequestDto.getContent();
   }


   public void updateLikes(int likes) {
      this.likeCount = likes;
   }

}

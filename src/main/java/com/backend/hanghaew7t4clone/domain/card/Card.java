package com.backend.hanghaew7t4clone.domain.card;

import com.backend.hanghaew7t4clone.domain.comment.Comment;
import com.backend.hanghaew7t4clone.domain.likes.Likes;
import com.backend.hanghaew7t4clone.domain.member.Member;
import com.backend.hanghaew7t4clone.global.shared.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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
   private Set<String> imgUrlList;

   @Column
   private Integer likeCount;

   @Column(nullable = false)
   private String content;

   @Column
   private Integer commentCount;

   @Column
   private String place;

   @ManyToOne
   @JoinColumn(name = "member_id")
   private Member member;

   @Column
   @BatchSize(size = 10)
   @OneToMany(mappedBy = "card",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
   private List<Comment> commentListDto;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "card", cascade = CascadeType.ALL)
   private List<Likes> likesList;

   @Builder
   public Card(String nickname, Set<String> imgUrlList,int likeCount, String content,int commentCount,String place, Member member) {
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

   public void update(CardRequestDto requestDto) {
      if(requestDto.getContent()!=null)this.content = requestDto.getContent();
      if(requestDto.getImgUrlList()!=null)this.imgUrlList=requestDto.getImgUrlList();
      if(requestDto.getPlace()!=null) this.place= requestDto.getPlace();
   }
   public void updateComment(int commentCount){
      this.commentCount=commentCount;
   }

   public void updateLikes(int likes) {
      this.likeCount= likes;
   }
   public void discountLikes(Likes likes) {
      this.likesList.remove(likes);
   }

}

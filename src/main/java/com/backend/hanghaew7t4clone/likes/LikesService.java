package com.backend.hanghaew7t4clone.likes;


import com.backend.hanghaew7t4clone.card.Card;
import com.backend.hanghaew7t4clone.card.CardRepository;
import com.backend.hanghaew7t4clone.card.CardResponseDto;
import com.backend.hanghaew7t4clone.card.CardService;
import com.backend.hanghaew7t4clone.comment.Comment;
import com.backend.hanghaew7t4clone.comment.CommentRepository;
import com.backend.hanghaew7t4clone.comment.CommentResponseDto;
import com.backend.hanghaew7t4clone.dto.ResponseDto;
import com.backend.hanghaew7t4clone.jwt.TokenProvider;
import com.backend.hanghaew7t4clone.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {

private final LikesRepository LikesRepository;
private final TokenProvider tokenProvider;
private final CardService cardService;
private final CardRepository cardRepository;
private final CommentRepository commentRepository;


    @Transactional
    public ResponseDto<?> pushCardLikes(Long cardId, HttpServletRequest request) {

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);

        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Card card = isPresentCard(cardId);
       if (card==null){ return ResponseDto.fail("CARD_NOT_FOUND", "게시글이 존재하지 않습니다.");
       }

        Optional<CardLikes> ByCardAndMember = cardLikesRepository.findByCardAndMember(card, member);

        ByCardAndMember.ifPresentOrElse(
                // 좋아요 있을경우 삭제
                cardLikes -> {
                    cardLikesRepository.delete(cardLikes);
                    card.discountLikes(cardLikes);
                },
                // 좋아요가 없을 경우 좋아요 추가
                () -> {
                    CardLikes cardLikes = CardLikes.builder()
                            .card(card)
                            .member(member)
                            .build();
                    cardLikesRepository.save(cardLikes);
                });
        return ResponseDto.success(true);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Card isPresentCard(Long id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        return optionalCard.orElse(null);
    }


    @Transactional
    public List<CardResponseDto> getAlLikesByMember(Member member){
       List<com.backend.hanghaew7t4clone.Likess.Likes> LikesList = LikesRepository.findLikesByMember(member);
       List<CardResponseDto> cardResponseDtoList = new ArrayList<>();
        List<String> url = ;
        for(Likess Likess : LikesList) {
            List<com.backend.hanghaew7t4clone.Likess.Likes> cardLikesListByCard = cardLikesRepository.findAllByCard(cardLikes.getCard());
            int LikesCount= cardLikesListByCard.size();
            cardResponseDtoList.add(
                    CardResponseDto.builder()
                            .nickname(cardLikes.getCard().getMember().getNickname())
                            .modifiedAt(cardLikes.getCard().getModifiedAt())
                            .createdAt(cardLikes.getCard().getCreatedAt())
                            .content(cardLikes.getCard().getContent())
                            .id(cardLikes.getCard().getId())
                            .imgUrlList(urlList)
                            .LikesCount(LikesCount)
                            .build()
            );
        }
        return cardResponseDtoList;
    }


    @Transactional
    public ResponseDto<?>pushCommentLikes (Long id, HttpServletRequest request) {

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);

        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        Comment comment =isPresentComment(id);
        if (comment==null){ return ResponseDto.fail("COMMENT_NOT_FOUND", "댓글이 존재하지 않습니다.");
        }


        Optional<CommentLikes> ByCommentAndMember = LikesRepository.findByCommentAndMember(comment, member);
        ByCommentAndMember.ifPresentOrElse(
                commentLikes -> {
                    commentLikesRepository.delete(commentLikes);
                    comment.discountLikes(commentLikes);
                },
                () ->{
                    CommentLikes commentLikes = CommentLikes.builder()
                            .comment(comment)
                            .member(member)
                            .build();

                    commentLikesRepository.save(commentLikes);
                }
        );
        return ResponseDto.success(true);
    }
    @Transactional(readOnly = true)
    public Comment isPresentComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        return optionalComment.orElse(null);
    }


    @Transactional
    public List<CommentResponseDto> getAllCommentLikessByMember(Member member){
        List<CommentLikes> commentLikesList = commentLikesRepository.findCommentLikessByMember(member);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(CommentLikes commentLikes : commentLikesList) {
            List<CommentLikes> commentLikesListByCard = commentLikesRepository.findByComment(commentLikes.getComment());
            int LikesCount= commentLikesListByCard.size();
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .nickname(commentLikes.getComment().getMember().getNickname())
                            .content(commentLikes.getComment().getContent())
                            .id(commentLikes.getComment().getId())
                            .build()
            );
        }
        return commentResponseDtoList;
    }

//    public ResponseDto<?>pushReCommentLikes (Long id, HttpServletRequest request){
//        log.info("1");
//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//        if (null == request.getHeader("Authorization")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }
//
//        Member member = validateMember(request);
//
//        if (null == member) {
//            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
//        }
//
//        ReComment reComment =isPresentReComment(id);
//        if(reComment==null){
//            return ResponseDto.fail("RE_COMMENT_NOT_FOUND",
//                    "댓글이 존재하지 않습니다.");}
//
//        Optional<ReCommentLikes> ByReCommentAndMember = reCommentLikesRepository.findByReCommentAndMember(reComment, member);
//        ByReCommentAndMember.ifPresentOrElse(
//                reCommentLikes -> {
//                    reCommentLikesRepository.delete(reCommentLikes);
//                    reComment.discountLikes(reCommentLikes);
//                    log.info("3");
//                },
//
//                () ->{
//                    ReCommentLikes reCommentLikes = ReCommentLikes.builder()
//                            .member(member)
//                            .reComment(reComment)
//                            .build();
//                    reCommentLikesRepository.save(reCommentLikes);
//                    log.info("4");
//                }
//        );
//        return ResponseDto.success(true);
//    }
//
//    @Transactional
//    public List<ReCommentAllResponseDto> getAllRecommentLikessByMember(Member member){
//        List<ReCommentLikes> reCommentLikesList = reCommentLikesRepository.findReCommentLikessByMember(member);
//        List<ReCommentAllResponseDto> reCommentAllResponseDtoList = new ArrayList<>();
//        for(ReCommentLikes reCommentLikes : reCommentLikesList) {
//            List<ReCommentLikes> cardLikesListByCard = reCommentLikesRepository.findByReComment(reCommentLikes.getReComment());
//            int LikesCount= cardLikesListByCard.size();
//            reCommentAllResponseDtoList.add(
//                    ReCommentAllResponseDto.builder()
//                            .author(reCommentLikes.getReComment().getMember().getNickname())
//                            .modifiedAt(reCommentLikes.getReComment().getModifiedAt())
//                            .createdAt(reCommentLikes.getReComment().getCreatedAt())
//                            .reContent(reCommentLikes.getReComment().getReContent())
//                            .id(reCommentLikes.getReComment().getId())
//                            .reLikesCount(LikesCount)
//                            .build()
//            );
//        }
//        return reCommentAllResponseDtoList;
//    }
//    @Transactional(readOnly = true)
//    public ReComment isPresentReComment(Long id) {
//        log.info("뾰잉");
//        Optional<ReComment> optionalReComment = reCommentRepository.findById(id);
//        return optionalReComment.orElse(null);
//    }
}

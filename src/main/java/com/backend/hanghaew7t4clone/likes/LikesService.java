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
public class LikeService {

private final CardLikeRepository cardLikeRepository;
private final TokenProvider tokenProvider;
private final CardService cardService;
private final CardRepository cardRepository;
private final CommentLikeRepository commentLikeRepository;
private final CommentRepository commentRepository;


    @Transactional
    public ResponseDto<?> pushCardLike(Long cardId, HttpServletRequest request) {

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

        Optional<CardLike> ByCardAndMember = cardLikeRepository.findByCardAndMember(card, member);

        ByCardAndMember.ifPresentOrElse(
                // 좋아요 있을경우 삭제
                cardLike -> {
                    cardLikeRepository.delete(cardLike);
                    card.discountLike(cardLike);
                },
                // 좋아요가 없을 경우 좋아요 추가
                () -> {
                    CardLike cardLike = CardLike.builder()
                            .card(card)
                            .member(member)
                            .build();
                    cardLikeRepository.save(cardLike);
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
    public List<CardResponseDto> getAllCardLikesByMember(Member member){
       List<CardLike> cardLikeList = cardLikeRepository.findCardLikesByMember(member);
       List<CardResponseDto> cardResponseDtoList = new ArrayList<>();
        List<String> url = ;
        for(CardLike cardLike : cardLikeList) {
            List<CardLike> cardLikeListByCard = cardLikeRepository.findAllByCard(cardLike.getCard());
            int likeCount= cardLikeListByCard.size();
            cardResponseDtoList.add(
                    CardResponseDto.builder()
                            .nickname(cardLike.getCard().getMember().getNickname())
                            .modifiedAt(cardLike.getCard().getModifiedAt())
                            .createdAt(cardLike.getCard().getCreatedAt())
                            .content(cardLike.getCard().getContent())
                            .id(cardLike.getCard().getId())
                            .imgUrlList(urlList)
                            .likeCount(likeCount)
                            .build()
            );
        }
        return cardResponseDtoList;
    }


    @Transactional
    public ResponseDto<?>pushCommentLike (Long id, HttpServletRequest request) {

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


        Optional<CommentLike> ByCommentAndMember = commentLikeRepository.findByCommentAndMember(comment, member);
        ByCommentAndMember.ifPresentOrElse(
                commentLike -> {
                    commentLikeRepository.delete(commentLike);
                    comment.discountLike(commentLike);
                },
                () ->{
                    CommentLike commentLike = CommentLike.builder()
                            .comment(comment)
                            .member(member)
                            .build();

                    commentLikeRepository.save(commentLike);
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
    public List<CommentResponseDto> getAllCommentLikesByMember(Member member){
        List<CommentLike> commentLikeList = commentLikeRepository.findCommentLikesByMember(member);
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for(CommentLike commentLike : commentLikeList) {
            List<CommentLike> commentLikeListByCard = commentLikeRepository.findByComment(commentLike.getComment());
            int likeCount= commentLikeListByCard.size();
            commentResponseDtoList.add(
                    CommentResponseDto.builder()
                            .nickname(commentLike.getComment().getMember().getNickname())
                            .content(commentLike.getComment().getContent())
                            .id(commentLike.getComment().getId())
                            .build()
            );
        }
        return commentResponseDtoList;
    }

//    public ResponseDto<?>pushReCommentLike (Long id, HttpServletRequest request){
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
//        Optional<ReCommentLike> ByReCommentAndMember = reCommentLikeRepository.findByReCommentAndMember(reComment, member);
//        ByReCommentAndMember.ifPresentOrElse(
//                reCommentLike -> {
//                    reCommentLikeRepository.delete(reCommentLike);
//                    reComment.discountLike(reCommentLike);
//                    log.info("3");
//                },
//
//                () ->{
//                    ReCommentLike reCommentLike = ReCommentLike.builder()
//                            .member(member)
//                            .reComment(reComment)
//                            .build();
//                    reCommentLikeRepository.save(reCommentLike);
//                    log.info("4");
//                }
//        );
//        return ResponseDto.success(true);
//    }
//
//    @Transactional
//    public List<ReCommentAllResponseDto> getAllRecommentLikesByMember(Member member){
//        List<ReCommentLike> reCommentLikeList = reCommentLikeRepository.findReCommentLikesByMember(member);
//        List<ReCommentAllResponseDto> reCommentAllResponseDtoList = new ArrayList<>();
//        for(ReCommentLike reCommentLike : reCommentLikeList) {
//            List<ReCommentLike> cardLikeListByCard = reCommentLikeRepository.findByReComment(reCommentLike.getReComment());
//            int likeCount= cardLikeListByCard.size();
//            reCommentAllResponseDtoList.add(
//                    ReCommentAllResponseDto.builder()
//                            .author(reCommentLike.getReComment().getMember().getNickname())
//                            .modifiedAt(reCommentLike.getReComment().getModifiedAt())
//                            .createdAt(reCommentLike.getReComment().getCreatedAt())
//                            .reContent(reCommentLike.getReComment().getReContent())
//                            .id(reCommentLike.getReComment().getId())
//                            .reLikeCount(likeCount)
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

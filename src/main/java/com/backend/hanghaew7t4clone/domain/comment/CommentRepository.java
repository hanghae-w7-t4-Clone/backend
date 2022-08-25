package com.backend.hanghaew7t4clone.domain.comment;

import com.backend.hanghaew7t4clone.domain.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCardOrderByCreatedAtDesc(Card card);

    }

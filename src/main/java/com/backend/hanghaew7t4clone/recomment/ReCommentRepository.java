package com.backend.hanghaew7t4clone.recomment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

    List<ReComment> findAllById(Long commentId);


}

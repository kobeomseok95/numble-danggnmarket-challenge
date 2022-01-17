package com.danggn.challenge.comment.domain.repository;

import com.danggn.challenge.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c join fetch c.product where c.id = :commentId")
    Optional<Comment> findWithProductById(@Param("commentId")Long commentId);
}

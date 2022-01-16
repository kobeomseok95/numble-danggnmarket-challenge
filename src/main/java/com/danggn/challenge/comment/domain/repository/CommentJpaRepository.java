package com.danggn.challenge.comment.domain.repository;

import com.danggn.challenge.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}

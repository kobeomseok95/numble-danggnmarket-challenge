package com.danggn.challenge.comment.domain.repository;

import com.danggn.challenge.comment.domain.repository.dto.CommentsQuerydslDto;

import java.util.Optional;

public interface CommentQuerydslRepository {

    Optional<CommentsQuerydslDto> findCommentsByProductId(Long productId);
}

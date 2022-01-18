package com.danggn.challenge.comment.domain.repository;

import com.danggn.challenge.comment.domain.repository.dto.CommentsQuerydslDto;
import com.danggn.challenge.comment.domain.repository.dto.QCommentsQuerydslDto;
import com.danggn.challenge.comment.domain.repository.dto.QCommentsQuerydslDto_CommentDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.danggn.challenge.comment.domain.QComment.comment;
import static com.danggn.challenge.member.domain.QMember.member;
import static com.danggn.challenge.product.domain.QProduct.product;

@Repository
@RequiredArgsConstructor
public class CommentQuerydslRepositoryImpl implements CommentQuerydslRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<CommentsQuerydslDto> findCommentsByProductId(Long productId) {
        CommentsQuerydslDto commentsProduct = queryFactory.select(new QCommentsQuerydslDto(
                product.id,
                product.thumbnailImageUrl,
                product.name,
                product.productTradeStatus.stringValue(),
                product.price))
                .from(product)
                .where(product.id.eq(productId))
                .fetchOne();
        if (commentsProduct == null) {
            return Optional.empty();
        }

        List<CommentsQuerydslDto.CommentDto> comments = queryFactory.select(new QCommentsQuerydslDto_CommentDto(
                member.id,
                member.nickname,
                member.profileImageUrl,
                comment.createdDate,
                comment.contents))
                .from(comment)
                .join(comment.member, member)
                .where(comment.product.id.eq(productId))
                .orderBy(comment.createdDate.desc())
                .fetch();

        if (!comments.isEmpty()) {
            commentsProduct.setComments(comments);
        }
        return Optional.of(commentsProduct);
    }
}

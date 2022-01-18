package com.danggn.challenge.comment.domain.repository;

import com.danggn.challenge.comment.domain.repository.dto.CommentsQuerydslDto;
import com.danggn.challenge.comment.domain.repository.dto.QCommentsQuerydslDto;
import com.danggn.challenge.comment.domain.repository.dto.QCommentsQuerydslDto_CommentDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

import static com.danggn.challenge.comment.domain.QComment.comment;
import static com.danggn.challenge.member.domain.QMember.member;
import static com.danggn.challenge.product.domain.QProduct.product;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class CommentQuerydslRepositoryImpl implements CommentQuerydslRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<CommentsQuerydslDto> findCommentsByProductId(Long productId) {
        Map<Long, CommentsQuerydslDto> transform = queryFactory
                .from(comment)
                .join(comment.member, member)
                .join(comment.product, product)
                .where(product.id.eq(productId))
                .orderBy(comment.createdDate.desc())
                .transform(groupBy(product.id).as(new QCommentsQuerydslDto(
                        product.id,
                        product.thumbnailImageUrl,
                        product.name,
                        product.productTradeStatus.stringValue(),
                        product.price,
                        list(new QCommentsQuerydslDto_CommentDto(
                                member.id,
                                member.profileImageUrl,
                                comment.createdDate,
                                comment.contents
                        )))));

        if (!transform.containsKey(productId)) {
            return Optional.empty();
        }
        return Optional.of(transform.get(productId));
    }
}

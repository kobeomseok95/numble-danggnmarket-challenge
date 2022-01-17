package com.danggn.challenge.product.domain.repository;

import com.danggn.challenge.common.util.QuerydslUtil;
import com.danggn.challenge.product.domain.repository.vo.*;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.danggn.challenge.member.domain.QMember.member;
import static com.danggn.challenge.product.domain.QLike.like;
import static com.danggn.challenge.product.domain.QProduct.product;
import static com.danggn.challenge.product.domain.QProductImage.productImage;
import static com.querydsl.core.group.GroupBy.groupBy;

@Repository
@RequiredArgsConstructor
class ProductQuerydslRepositoryImpl implements ProductQuerydslRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ProductsQuerydslDto> findProducts(Pageable pageable) {
        List<ProductsQuerydslDto> contents = queryFactory
                .select(getProductsQuery())
                .from(product)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(product.lastModifiedDate.desc())
                .fetch();

        boolean hasNext = QuerydslUtil.hasNextContents(contents, pageable.getPageSize());
        return new SliceImpl<>(contents, pageable, hasNext);
    }

    private QProductsQuerydslDto getProductsQuery() {
        return new QProductsQuerydslDto(
                product.id,
                product.thumbnailImageUrl,
                product.name,
                product.productTradeStatus.stringValue(),
                product.price,
                product.commentsCount.longValue(),
                product.likesCount.longValue());
    }

    @Override
    public Optional<ProductDetailQuerydslDto> findProductDetail(Long productId) {
        Map<Long, ProductDetailQuerydslDto> transformMap = findProductDetailQuery(productId);
        if (!QuerydslUtil.containsKey(transformMap, productId)) {
            return Optional.empty();
        }

        ProductDetailQuerydslDto productDetailDto = transformMap.get(productId);
        List<MemberSaleProductsQuerydslDto> memberSaleProductDto = queryFactory
                .select(new QMemberSaleProductsQuerydslDto(
                        product.member.id,
                        product.id,
                        product.thumbnailImageUrl,
                        product.name,
                        product.price
                )).from(product)
                .where(product.member.id.eq(productDetailDto.getMemberId()))
                .fetch();
        productDetailDto.setMemberSaleProduct(memberSaleProductDto);
        return Optional.of(productDetailDto);
    }

    private Map<Long, ProductDetailQuerydslDto> findProductDetailQuery(Long productId) {
        return queryFactory
                .from(product)
                .join(product.member, member)
                .join(product.productImages.values, productImage)
                .where(product.id.eq(productId))
                .transform(groupBy(product.id).as(new QProductDetailQuerydslDto(
                        GroupBy.list(productImage.url),
                        member.id,
                        product.id,
                        member.nickname,
                        product.name,
                        product.price,
                        product.productCategory.stringValue(),
                        product.createdDate,
                        product.mainText)
                ));
    }

    @Override
    public Slice<ProductsQuerydslDto> findLikeProducts(Long memberId, Pageable pageable) {
        List<ProductsQuerydslDto> contents = queryFactory
                .select(getProductsQuery())
                .from(like)
                .join(like.product, product)
                .where(like.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(like.createdDate.desc())
                .fetch();

        boolean hasNext = QuerydslUtil.hasNextContents(contents, pageable.getPageSize());
        return new SliceImpl<>(contents, pageable, hasNext);
    }

    @Override
    public Slice<ProductsQuerydslDto> findByMemberId(Long memberId, Pageable pageable) {
        List<ProductsQuerydslDto> contents = queryFactory
                .select(getProductsQuery())
                .from(product)
                .where(product.member.id.eq(memberId))
                .groupBy(product.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(product.lastModifiedDate.desc())
                .fetch();

        boolean hasNext = QuerydslUtil.hasNextContents(contents, pageable.getPageSize());
        return new SliceImpl<>(contents, pageable, hasNext);
    }
}

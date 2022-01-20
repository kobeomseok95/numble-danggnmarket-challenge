package com.danggn.challenge.product.domain.repository;

import com.danggn.challenge.product.domain.ProductTradeStatus;
import com.danggn.challenge.product.domain.repository.dto.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.danggn.challenge.member.domain.QMember.member;
import static com.danggn.challenge.product.domain.QLike.like;
import static com.danggn.challenge.product.domain.QProduct.product;
import static com.danggn.challenge.product.domain.QProductImage.productImage;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
class ProductQuerydslRepositoryImpl implements ProductQuerydslRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductsQuerydslDto> findProducts(Pageable pageable) {
        return queryFactory
                .select(getProductsQuery())
                .from(product)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(product.lastModifiedDate.desc())
                .fetch();
    }

    private QProductsQuerydslDto getProductsQuery() {
        return new QProductsQuerydslDto(
                product.id,
                product.member.id,
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
        if (!transformMap.containsKey(productId)) {
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
                .leftJoin(product.productImages.values, productImage)
                .where(product.id.eq(productId))
                .transform(groupBy(product.id).as(new QProductDetailQuerydslDto(
                        list(productImage.url),
                        member.id,
                        member.profileImageUrl,
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
    public List<ProductsQuerydslDto> findLikeProducts(Long memberId, Pageable pageable) {
        return queryFactory
                .select(getProductsQuery())
                .from(like)
                .join(like.product, product)
                .where(like.member.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(like.createdDate.desc())
                .fetch();
    }

    @Override
    public List<ProductsQuerydslDto> findByMemberIdStatus(Long memberId, String productStatusName, Pageable pageable) {
        return queryFactory
                .select(getProductsQuery())
                .from(product)
                .where(product.member.id.eq(memberId)
                        .and(getStatus(productStatusName)))
                .groupBy(product.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(product.lastModifiedDate.desc())
                .fetch();
    }

    private BooleanExpression getStatus(String productStatusName) {
        switch (productStatusName) {
            case "SALE":
                return product.productTradeStatus.eq(ProductTradeStatus.SALE);
            case "SOLD_OUT":
                return product.productTradeStatus.eq(ProductTradeStatus.SOLD_OUT);
            default:
                return null;
        }
    }

    @Override
    public Optional<UpdateProductInfoQuerydslDto> findWithImageUrlById(Long productId) {
        Map<Long, UpdateProductInfoQuerydslDto> transform = queryFactory.from(product)
                .leftJoin(product.productImages.values, productImage)
                .where(product.id.eq(productId))
                .transform(groupBy(product.id).as(new QUpdateProductInfoQuerydslDto(
                        product.id,
                        list(productImage.url),
                        product.name,
                        product.productCategory.stringValue(),
                        product.price,
                        product.mainText
                )));
        if (!transform.containsKey(productId)) {
            return Optional.empty();
        }
        return Optional.of(transform.get(productId));
    }
}

package com.danggn.challenge.product.application;

import com.danggn.challenge.common.util.SliceUtil;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductInfoRequestVo;
import com.danggn.challenge.product.application.response.ProductDetailResponseVo;
import com.danggn.challenge.product.application.response.ProductsResponseVo;
import com.danggn.challenge.product.domain.*;
import com.danggn.challenge.product.domain.repository.dto.ProductDetailQuerydslDto;
import com.danggn.challenge.product.domain.repository.dto.ProductsQuerydslDto;
import com.danggn.challenge.product.domain.repository.dto.UpdateProductInfoQuerydslDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class ProductApplicationAssembler {

    static Product toProductEntity(CreateProductRequestVo vo, Member member, List<String> thumbnailUrl) {
        return Product.builder()
                .member(member)
                .name(vo.getName())
                .productCategory(ProductCategory.valueOf(vo.getCategory()))
                .price(vo.getPrice())
                .mainText(vo.getMainText())
                .productTradeStatus(ProductTradeStatus.SALE)
                .likes(Likes.builder().build())
                .likesCount(0)
                .thumbnailImageUrl(thumbnailUrl.isEmpty() ? null : thumbnailUrl.get(0))
                .build();
    }

    static List<ProductImage> toProductImageEntity(Product product, List<String> urls) {
        return urls.stream()
                .map(url -> ProductImage.builder()
                        .product(product)
                        .url(url)
                        .build())
                .collect(Collectors.toList());
    }

    static Like toLikeEntity(Product product, Member member) {
        return Like.builder()
                .product(product)
                .member(member)
                .build();
    }

    static Product toProductEntity(UpdateProductInfoRequestVo vo) {
        return Product.builder()
                .name(vo.getName())
                .productCategory(ProductCategory.valueOf(vo.getCategory()))
                .price(vo.getPrice())
                .mainText(vo.getMainText())
                .build();
    }

    static Slice<ProductsResponseVo> toProductsResponseVoSlice(List<ProductsQuerydslDto> dtos, Pageable pageable) {
        List<ProductsResponseVo> convertContents = dtos.stream()
                .map(product -> ProductsResponseVo.builder()
                        .productId(product.getProductId())
                        .memberId(product.getMemberId())
                        .thumbnailImageUrl(product.getThumbnailImageUrl())
                        .productTradeStatus(product.getProductTradeStatus())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .commentCount(product.getCommentCount())
                        .likeCount(product.getLikeCount())
                        .build())
                .collect(Collectors.toList());

        boolean hasNext = SliceUtil.hasNextContents(convertContents, pageable.getPageSize());
        return new SliceImpl<>(convertContents, pageable, hasNext);
    }

    static ProductDetailResponseVo toProductDetailResponseVo(ProductDetailQuerydslDto dto) {
        return ProductDetailResponseVo.builder()
                .imageUrls(dto.getImageUrls())
                .memberId(dto.getMemberId())
                .memberProfileUrl(dto.getMemberProfileUrl())
                .productId(dto.getProductId())
                .memberNickname(dto.getMemberNickname())
                .productName(dto.getProductName())
                .price(dto.getPrice())
                .category(ProductCategory.valueOf(dto.getCategory()).getValue())
                .createdDate(dto.getCreatedDate())
                .contents(dto.getContents())
                .memberSaleProducts(dto.getMemberSaleProducts()
                        .stream()
                        .map(membersProduct -> ProductDetailResponseVo.MemberSaleProductsVo.builder()
                                .memberId(membersProduct.getMemberId())
                                .productId(membersProduct.getProductId())
                                .thumbnailUrl(membersProduct.getThumbnailUrl())
                                .productName(membersProduct.getProductName())
                                .price(membersProduct.getPrice())
                        .build())
                        .collect(Collectors.toList())
                ).build();
    }

    public static UpdateProductInfoRequestVo toUpdateProductInfoResponseVo(UpdateProductInfoQuerydslDto dto) {
        return UpdateProductInfoRequestVo.builder()
                .productId(dto.getProductId())
                .imageUrls(dto.getImageUrls())
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .mainText(dto.getMainText())
                .build();
    }
}

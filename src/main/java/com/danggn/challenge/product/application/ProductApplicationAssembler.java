package com.danggn.challenge.product.application;

import com.danggn.challenge.common.util.SliceUtil;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductInfoRequestVo;
import com.danggn.challenge.product.application.response.ProductsResponseVo;
import com.danggn.challenge.product.application.response.ProductDetailResponseVo;
import com.danggn.challenge.product.domain.*;
import com.danggn.challenge.product.domain.repository.dto.ProductsQuerydslDto;
import com.danggn.challenge.product.domain.repository.dto.ProductDetailQuerydslDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class ProductApplicationAssembler {

    static Product toProductEntity(CreateProductRequestVo createProductRequestVo, Member member, List<String> thumbnailUrl) {
        return Product.builder()
                .member(member)
                .name(createProductRequestVo.getName())
                .productCategory(ProductCategory.valueOf(createProductRequestVo.getCategory()))
                .price(createProductRequestVo.getPrice())
                .mainText(createProductRequestVo.getMainText())
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

    static Product toProductEntity(UpdateProductInfoRequestVo updateProductInfoRequestVo) {
        return Product.builder()
                .name(updateProductInfoRequestVo.getName())
                .productCategory(ProductCategory.valueOf(updateProductInfoRequestVo.getCategory()))
                .price(updateProductInfoRequestVo.getPrice())
                .mainText(updateProductInfoRequestVo.getMainText())
                .build();
    }

    static Slice<ProductsResponseVo> toProductsResponseVoSlice(List<ProductsQuerydslDto> products, Pageable pageable) {
        List<ProductsResponseVo> convertContents = products.stream()
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

    static ProductDetailResponseVo toProductDetailResponseVo(ProductDetailQuerydslDto productDetailDto) {
        return ProductDetailResponseVo.builder()
                .imageUrls(productDetailDto.getImageUrls())
                .memberId(productDetailDto.getMemberId())
                .memberProfileUrl(productDetailDto.getMemberProfileUrl())
                .productId(productDetailDto.getProductId())
                .memberNickname(productDetailDto.getMemberNickname())
                .productName(productDetailDto.getProductName())
                .price(productDetailDto.getPrice())
                .category(ProductCategory.valueOf(productDetailDto.getCategory()).getValue())
                .createdDate(productDetailDto.getCreatedDate())
                .contents(productDetailDto.getContents())
                .memberSaleProducts(productDetailDto.getMemberSaleProducts()
                        .stream()
                        .map(dto -> ProductDetailResponseVo.MemberSaleProductsVo.builder()
                                .memberId(dto.getMemberId())
                                .productId(dto.getProductId())
                                .thumbnailUrl(dto.getThumbnailUrl())
                                .productName(dto.getProductName())
                                .price(dto.getPrice())
                        .build())
                        .collect(Collectors.toList())
                ).build();
    }
}

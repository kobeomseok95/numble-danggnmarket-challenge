package com.danggn.challenge.product.application;

import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductInfoRequestVo;
import com.danggn.challenge.product.application.response.MainProductsResponseVo;
import com.danggn.challenge.product.application.response.ProductDetailResponseVo;
import com.danggn.challenge.product.domain.*;
import com.danggn.challenge.product.domain.repository.vo.ProductsQuerydslDto;
import com.danggn.challenge.product.domain.repository.vo.ProductDetailQuerydslDto;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class ProductApplicationAssembler {

    static Product toProductEntity(CreateProductRequestVo createProductRequestVo, Member member, String thumbnailUrl) {
        return Product.builder()
                .member(member)
                .name(createProductRequestVo.getName())
                .productCategory(ProductCategory.valueOf(createProductRequestVo.getCategory()))
                .price(createProductRequestVo.getPrice())
                .mainText(createProductRequestVo.getMainText())
                .productTradeStatus(ProductTradeStatus.SALE)
                .likes(Likes.builder().build())
                .likesCount(0)
                .thumbnailImageUrl(thumbnailUrl)
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

    static Slice<MainProductsResponseVo> toMainProductsResponseVoSlice(Slice<ProductsQuerydslDto> products) {
        List<MainProductsResponseVo> convertContents = products.getContent().stream()
                .map(content -> MainProductsResponseVo.builder()
                        .productId(content.getProductId())
                        .thumbnailImageUrl(content.getThumbnailImageUrl())
                        .productName(content.getProductName())
                        .price(content.getPrice())
                        .commentCount(content.getCommentCount())
                        .likeCount(content.getLikeCount())
                        .build())
                .collect(Collectors.toList());
        return new SliceImpl<>(convertContents, products.getPageable(), products.hasNext());
    }

    static ProductDetailResponseVo toProductDetailResponseVo(ProductDetailQuerydslDto productDetailDto) {
        return ProductDetailResponseVo.builder()
                .imageUrls(productDetailDto.getImageUrls())
                .memberId(productDetailDto.getMemberId())
                .productId(productDetailDto.getProductId())
                .memberNickname(productDetailDto.getMemberNickname())
                .productName(productDetailDto.getProductName())
                .price(productDetailDto.getPrice())
                .category(productDetailDto.getCategory())
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

package com.danggn.challenge.product.application;

import com.danggn.challenge.product.application.response.MainProductsResponseVo;
import com.danggn.challenge.product.application.response.ProductDetailResponseVo;
import com.danggn.challenge.product.application.usecase.ProductQueryUseCase;
import com.danggn.challenge.product.domain.repository.vo.ProductsQuerydslDto;
import com.danggn.challenge.product.domain.repository.ProductQuerydslRepository;
import com.danggn.challenge.product.domain.repository.vo.ProductDetailQuerydslDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryService implements ProductQueryUseCase {

    private final ProductQuerydslRepository productQuerydslRepository;

    @Override
    public Slice<MainProductsResponseVo> getProducts(Pageable pageable) {
        Slice<ProductsQuerydslDto> products = productQuerydslRepository.findProducts(pageable);
        return ProductApplicationAssembler.toMainProductsResponseVoSlice(products);
    }

    @Override
    public ProductDetailResponseVo getProductDetail(Long productId) {
        ProductDetailQuerydslDto productDetailDto = productQuerydslRepository.findProductDetail(productId)
                .orElseThrow();
        return ProductApplicationAssembler.toProductDetailResponseVo(productDetailDto);
    }
}

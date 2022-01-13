package com.danggn.challenge.product.presentation;

import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.presentation.request.CreateProductRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductPresentationAssembler {

    public CreateProductRequestVo toCreateProductRequestVo(CreateProductRequest createProductRequest) {
        return CreateProductRequestVo.builder()
                .files(createProductRequest.getFiles())
                .name(createProductRequest.getName())
                .category(createProductRequest.getCategory().toUpperCase())
                .price(createProductRequest.getPrice())
                .mainText(createProductRequest.getMainText())
                .build();
    }
}

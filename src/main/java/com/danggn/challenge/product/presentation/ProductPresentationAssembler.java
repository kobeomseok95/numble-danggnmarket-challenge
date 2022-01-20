package com.danggn.challenge.product.presentation;

import com.danggn.challenge.common.manager.file.MultipartFilesEmptyChecker;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductInfoRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductTradeStatusRequestVo;
import com.danggn.challenge.product.presentation.request.CreateProductRequest;
import com.danggn.challenge.product.presentation.request.UpdateProductInfoRequest;
import com.danggn.challenge.product.presentation.request.UpdateProductTradeStatusRequest;
import org.springframework.stereotype.Component;

@Component
class ProductPresentationAssembler {

    static CreateProductRequestVo toCreateProductRequestVo(CreateProductRequest createProductRequest) {
        createProductRequest.setFiles(
                MultipartFilesEmptyChecker.checkIfEmptyReturnEmptyCollection(createProductRequest.getFiles()));

        return CreateProductRequestVo.builder()
                .files(createProductRequest.getFiles())
                .name(createProductRequest.getName())
                .category(createProductRequest.getCategory().toUpperCase())
                .price(createProductRequest.getPrice())
                .mainText(createProductRequest.getMainText())
                .build();
    }

    static UpdateProductTradeStatusRequestVo toUpdateProductTradeStatusRequestVo(Long productId,
                                                                                 UpdateProductTradeStatusRequest request) {
        return UpdateProductTradeStatusRequestVo.builder()
                .productId(productId)
                .status(request.getStatus())
                .build();
    }

    static UpdateProductInfoRequestVo toUpdateProductInfoRequestVo(Long productId,
                                                                   UpdateProductInfoRequest request) {
        request.setFiles(
                MultipartFilesEmptyChecker.checkIfEmptyReturnEmptyCollection(request.getFiles()));

        return UpdateProductInfoRequestVo.builder()
                .productId(productId)
                .files(request.getFiles())
                .name(request.getName())
                .category(request.getCategory())
                .price(request.getPrice())
                .mainText(request.getMainText())
                .build();
    }

    public static UpdateProductInfoRequest toUpdateProductInfoRequest(UpdateProductInfoRequestVo requestVo) {
        return UpdateProductInfoRequest.builder()
                .productId(requestVo.getProductId())
                .imageUrls(requestVo.getImageUrls())
                .name(requestVo.getName())
                .category(requestVo.getCategory())
                .price(requestVo.getPrice())
                .mainText(requestVo.getMainText())
                .build();
    }
}

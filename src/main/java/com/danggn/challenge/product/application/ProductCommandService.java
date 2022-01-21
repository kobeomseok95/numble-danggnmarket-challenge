package com.danggn.challenge.product.application;

import com.danggn.challenge.common.manager.file.FileManager;
import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductInfoRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductTradeStatusRequestVo;
import com.danggn.challenge.product.application.usecase.ProductCommandUseCase;
import com.danggn.challenge.product.domain.Like;
import com.danggn.challenge.product.domain.Product;
import com.danggn.challenge.product.domain.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
class ProductCommandService
        implements ProductCommandUseCase {

    private final FileManager fileManager;
    private final ProductJpaRepository productJpaRepository;

    @Override
    public Long save(CreateProductRequestVo createProductRequestVo, LoginMember loginMember) {
        List<String> urls = fileManager.uploadAndReturnStoredUrl(createProductRequestVo.getFiles());
        Product product = productJpaRepository.save(
                ProductApplicationAssembler.toProductEntity(createProductRequestVo,
                        loginMember.getMember(),
                        urls));
        product.addProductImages(
                ProductApplicationAssembler.toProductImageEntity(product, urls)
        );
        return product.getId();
    }

    @Override
    public Long like(Long productId, LoginMember loginMember) {
        Product product = productJpaRepository.findWithLikesById(productId)
                .orElseThrow();
        Like like = getLikeEntity(product, loginMember);
        product.like(like);
        return product.getLikesCount();
    }

    private Like getLikeEntity(Product product, LoginMember loginMember) {
        return ProductApplicationAssembler.toLikeEntity(product, loginMember.getMember());
    }

    @Override
    public Long unlike(Long productId, LoginMember loginMember) {
        Product product = productJpaRepository.findWithLikesById(productId)
                .orElseThrow();
        Like like = getLikeEntity(product, loginMember);
        product.unlike(like);
        return product.getLikesCount();
    }

    @Override
    public void updateProductStatus(UpdateProductTradeStatusRequestVo updateProductTradeStatusRequestVo) {
        Product product = productJpaRepository.findById(updateProductTradeStatusRequestVo.getProductId())
                .orElseThrow();
        product.updateStatus(updateProductTradeStatusRequestVo.getStatus());
    }

    @Override
    public Long updateProductInfo(UpdateProductInfoRequestVo requestVo) {
        if (requestVo.getFiles().isEmpty()) {
            return updateProductNotUpdateImageUrl(requestVo);
        }
        List<String> urls = fileManager.uploadAndReturnStoredUrl(requestVo.getFiles());
        Product target = productJpaRepository.findWithImageUrlsById(requestVo.getProductId())
                .orElseThrow();
        target.updateInfoWithImageUrls(
                ProductApplicationAssembler.toProductEntity(requestVo),
                ProductApplicationAssembler.toProductImageEntity(target, urls));
        return target.getId();
    }

    private Long updateProductNotUpdateImageUrl(UpdateProductInfoRequestVo requestVo) {
        Product target = productJpaRepository.findById(requestVo.getProductId()).orElseThrow();
        target.updateInfo(ProductApplicationAssembler.toProductEntity(requestVo));
        return target.getId();
    }

    @Override
    public void deleteProduct(Long id) {
        productJpaRepository.deleteById(id);
    }
}

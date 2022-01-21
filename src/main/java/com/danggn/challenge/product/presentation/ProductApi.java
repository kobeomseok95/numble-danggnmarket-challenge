package com.danggn.challenge.product.presentation;

import com.danggn.challenge.common.security.AuthUser;
import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.product.application.usecase.ProductCommandUseCase;
import com.danggn.challenge.product.presentation.request.LikeCount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products/{productId}")
public class ProductApi {

    private final ProductCommandUseCase productCommandUseCase;

    @PostMapping("/like")
    public ResponseEntity<LikeCount> like(@PathVariable("productId") Long productId,
                                          @AuthUser LoginMember loginMember) {
        Long count = productCommandUseCase.like(productId, loginMember);
        return ResponseEntity.ok(ProductPresentationAssembler.toLikeCount(count));
    }

    @PostMapping("/unlike")
    public ResponseEntity<LikeCount> unlike(@PathVariable("productId") Long productId,
                                          @AuthUser LoginMember loginMember) {
        Long count = productCommandUseCase.unlike(productId, loginMember);
        return ResponseEntity.ok(ProductPresentationAssembler.toLikeCount(count));
    }
}

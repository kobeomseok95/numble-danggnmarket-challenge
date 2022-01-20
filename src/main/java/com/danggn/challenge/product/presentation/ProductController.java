package com.danggn.challenge.product.presentation;

import com.danggn.challenge.common.security.AuthUser;
import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.product.application.ProductCategoryProvider;
import com.danggn.challenge.product.application.usecase.ProductCommandUseCase;
import com.danggn.challenge.product.application.usecase.ProductQueryUseCase;
import com.danggn.challenge.product.presentation.request.CreateProductRequest;
import com.danggn.challenge.product.presentation.request.ProductStatusRequest;
import com.danggn.challenge.product.presentation.request.UpdateProductInfoRequest;
import com.danggn.challenge.product.presentation.request.UpdateProductTradeStatusRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductCommandUseCase productCommandUseCase;
    private final ProductQueryUseCase productQueryUseCase;
    private final ProductCategoryProvider productCategoryProvider;

    @GetMapping("")
    public String productsView(Pageable pageable, Model model) {
        model.addAttribute("products", productQueryUseCase.findProducts(pageable));
        return "/product/products";
    }

    @GetMapping("/{productId}")
    public String product(@PathVariable("productId") Long productId, Model model) {
        model.addAttribute("product", productQueryUseCase.findProductDetail(productId));
        return "/product/product";
    }

    @GetMapping("/member/{memberId}")
    public String memberProducts(@PathVariable("memberId") Long memberId,
                                 @RequestParam(defaultValue = "ALL") ProductStatusRequest status,
                                 Model model,
                                 Pageable pageable) {
        model.addAttribute("products", productQueryUseCase.findByMemberId(memberId,
                status.name(),
                pageable));
        model.addAttribute("memberId", memberId);
        return "/product/memberProducts";
    }

    @GetMapping("/add")
    public String productAddView(Model model) {
        model.addAttribute("createProductRequest", new CreateProductRequest());
        model.addAttribute("productCategory", productCategoryProvider.getProductCategoryEnums());
        return "product/addForm";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("createProductRequest") CreateProductRequest createProductRequest,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             @AuthUser LoginMember loginMember) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productCategory", productCategoryProvider.getProductCategoryEnums());
            return "/product/addForm";
        }

        Long productId = productCommandUseCase.save(
                ProductPresentationAssembler.toCreateProductRequestVo(createProductRequest),
                loginMember);
        return "redirect:/products/" + productId;
    }

    @PostMapping("/{productId}/status")
    public String changeStatus(@PathVariable("productId") Long productId,
                               UpdateProductTradeStatusRequest request,
                               @AuthUser LoginMember loginMember) {
        productCommandUseCase.updateProductStatus(
                        ProductPresentationAssembler.toUpdateProductTradeStatusRequestVo(productId, request));
        return "redirect:/products/member/" + loginMember.getMemberId();
    }

    @GetMapping("/likes")
    public String getLikeProducts(Pageable pageable,
                                  Model model,
                                  @AuthUser LoginMember loginMember) {
        model.addAttribute("products",
                productQueryUseCase.findLikeProducts(loginMember.getMemberId(), pageable));
        return "product/likeProducts";
    }

    @GetMapping("/{productId}/edit")
    public String productEditView(@PathVariable("productId") Long productId,
                                  Model model) {
        model.addAttribute("updateProductInfoRequest", getUpdateProductInfoRequest(productId));
        model.addAttribute("productCategory", productCategoryProvider.getProductCategoryEnums());
        return "/product/editForm";
    }

    private UpdateProductInfoRequest getUpdateProductInfoRequest(Long productId) {
        return ProductPresentationAssembler
                .toUpdateProductInfoRequest(productQueryUseCase.findProductInfo(productId));
    }

    @PostMapping("/{productId}/edit")
    public String productEdit(@PathVariable("productId") Long productId,
                              @Valid @ModelAttribute UpdateProductInfoRequest updateProductInfoRequest,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productCategory", productCategoryProvider.getProductCategoryEnums());
            return "/product/editForm";
        }
        productCommandUseCase.updateProductInfo(ProductPresentationAssembler.toUpdateProductInfoRequestVo(productId,
                updateProductInfoRequest));
        return "redirect:/products/" + productId;
    }

    @PostMapping("/{productId}/delete")
    public String productDelete(@PathVariable("productId") Long productId,
                                RedirectAttributes redirectAttributes) {
        productCommandUseCase.deleteProduct(productId);
        return "redirect:/products";
    }
}

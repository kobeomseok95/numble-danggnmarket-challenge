package com.danggn.challenge.product.presentation;

import com.danggn.challenge.common.security.AuthUser;
import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.product.application.ProductCategoryProvider;
import com.danggn.challenge.product.application.ProductUseCase;
import com.danggn.challenge.product.presentation.request.CreateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase productUseCase;
    private final ProductCategoryProvider productCategoryProvider;
    private final ProductPresentationAssembler presentationAssembler;

//    @GetMapping("")
//    public String productsView(Model model) {
//
//        return "";
//    }
//
//    @GetMapping("/{id}")
//    public String product(
//            @PathVariable("id") Long id,
//            Model model
//    ) {
//
//        return "";
//    }

    @GetMapping("/add")
    public String productAddView(Model model) {
        model.addAttribute("createProductRequest", new CreateProductRequest());
        model.addAttribute("productCategory", productCategoryProvider.getProductCategoryEnums());
        return "product/addForm";
    }

    @PostMapping("/add")
    public String addProduct(
            @Valid @ModelAttribute("createProductRequest") CreateProductRequest createProductRequest,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @AuthUser LoginMember loginMember
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productCategory", productCategoryProvider.getProductCategoryEnums());
            return "/product/addForm";
        }

        Long productId = productUseCase.save(
                presentationAssembler.toCreateProductRequestVo(createProductRequest),
                loginMember
        );
        return "/product/products";
    }
}

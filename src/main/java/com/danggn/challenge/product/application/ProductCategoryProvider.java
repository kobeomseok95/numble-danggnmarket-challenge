package com.danggn.challenge.product.application;

import com.danggn.challenge.product.domain.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryProvider {

    public ProductCategory[] getProductCategoryEnums() {
        return ProductCategory.values();
    }
}

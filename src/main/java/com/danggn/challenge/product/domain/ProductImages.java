package com.danggn.challenge.product.domain;

import com.danggn.challenge.common.entity.AbstractEmbeddable;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductImages extends AbstractEmbeddable {

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    @Builder.Default
    private List<ProductImage> values = new ArrayList<>();

    @Override
    public long getSize() {
        return values.size();
    }

    public void addAll(List<ProductImage> productImages) {
        this.values.addAll(productImages);
    }

    public void updateProductImages(List<ProductImage> productImages) {
        /**
         *
         * TODO 이슈 정리 이전 코드 : this.values = productImages;
         *  명시적으로 clear 해줘야 한다.
         */
        this.values.clear();
        addAll(productImages);
    }
}

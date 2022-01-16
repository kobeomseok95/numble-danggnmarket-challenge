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
public class Likes extends AbstractEmbeddable {

    @OneToMany(
            mappedBy = "product",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @Builder.Default
    private List<Like> values = new ArrayList<>();

    @Override
    public long getSize() {
        return values.size();
    }

    public void add(Like like) {
        if (contains(like)) {
            throw new IllegalArgumentException("이미 좋아요 표시한 상품입니다.");
        }
        values.add(like);
    }

    private boolean contains(Like like) {
        return values.stream().anyMatch(like::isEqual);
    }

    public void remove(Like like) {
        values.removeIf(like::isEqual);
    }
}

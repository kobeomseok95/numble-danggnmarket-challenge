package com.danggn.challenge.comment.domain;

import com.danggn.challenge.common.entity.BaseEntity;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.domain.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Column(length = 100, nullable = false)
    private String contents;

    public void updateContents(String contents) {
        this.contents = contents;
    }
}

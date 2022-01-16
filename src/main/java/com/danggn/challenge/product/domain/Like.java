package com.danggn.challenge.product.domain;

import com.danggn.challenge.common.entity.BaseEntity;
import com.danggn.challenge.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(
        name = "LIKES",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "MEMBER_ID",
                                "PRODUCT_ID"
                        })})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Like extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    public boolean isEqual(Like like) {
        return this.member.equals(like.getMember())
                && this.product.equals(like.getProduct());
    }
}

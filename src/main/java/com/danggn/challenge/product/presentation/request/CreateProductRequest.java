package com.danggn.challenge.product.presentation.request;

import com.danggn.challenge.common.validator.EnumValid;
import com.danggn.challenge.product.domain.ProductCategory;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {

    private List<MultipartFile> files;

    @NotBlank(message = "상품명을 입력해주세요.")
    @Length(min = 3, max = 100, message = "상품명을 3 ~ 50자 사이로 입력해주세요.")
    private String name;

    // TODO : presentation 계층에서 domain 계층을 보는 점이 아쉽다.
    @EnumValid(enumClass = ProductCategory.class, message = "해당 카테고리는 존재하지 않습니다.")
    private String category;

    @Min(value = 100, message = "가격을 100원 이상으로 입력해주세요.")
    private Long price;

    @NotBlank(message = "상품 상세 내용을 입력해주세요.")
    @Length(min = 10, max = 1000, message = "상품 상세 내용을 10 ~ 1000자 사이로 입력해주세요.")
    private String mainText;
}

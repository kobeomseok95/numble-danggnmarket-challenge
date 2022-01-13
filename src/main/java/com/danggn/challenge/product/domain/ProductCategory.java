package com.danggn.challenge.product.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ProductCategory {

    DIGITAL_DEVICE("디지털기기"),
    LIFE_HOME("생활가전"),
    FURNITURE_INTERIOR("가구/인테리어"),
    INFANT_CHILD("유아동"),
    LIVING_PROCESSED_FOOD("생활/가공식품"),
    INFANT_BOOK("유아도서"),
    WOMEN_CLOTHING("여성의류"),
    MEN_FASHION_STUFF("남성패션/잡화"),
    GAME_HOBBY("게임/취미"),
    BEAUTY("뷰티/미용"),
    PET_SUPPLIES("반려동물물품"),
    BOOK_TICKET_ALBUM("도서/티켓/음반"),
    PLANT("식물"),
    ETC("기타 중고물품"),
    USED_CAR("중고차")
    ;

    private final String value;
}

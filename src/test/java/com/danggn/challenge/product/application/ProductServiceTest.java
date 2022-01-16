package com.danggn.challenge.product.application;

import com.danggn.challenge.common.manager.file.FileManager;
import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductInfoRequestVo;
import com.danggn.challenge.product.application.request.UpdateProductTradeStatusRequestVo;
import com.danggn.challenge.product.domain.*;
import com.danggn.challenge.product.domain.repository.ProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    FileManager fileManager;
    @Mock
    ProductJpaRepository productJpaRepository;

    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("상품 저장 / 성공")
    void save_success() throws Exception {

        // given
        LoginMember loginMember = mock(LoginMember.class);
        CreateProductRequestVo requestVo = createMockCreateProductRequestVo();
        fileManagerRenameStub(requestVo.getFiles(), requestVo.getFileNames());
        List<String> mockUrls = List.of(requestVo.getFileNames().get(0),
                requestVo.getFileNames().get(1));
        Product product = createMockProduct();
        when(productJpaRepository.save(any(Product.class)))
                .thenReturn(product);

        // when
        productService.save(requestVo, loginMember);

        // then
        assertAll(
                () -> verify(fileManager).uploadAndReturnStoredUrl(requestVo.getFiles()),
                () -> verify(productJpaRepository).save(any(Product.class)),
                () -> assertEquals(product.getProductImages().getSize(), 2)
        );
    }

    private void fileManagerRenameStub(List<MultipartFile> files, List<String> fileNames) {
        when(fileManager.uploadAndReturnStoredUrl(files))
                .thenReturn(List.of(
                        fileNames.get(0),
                        fileNames.get(1)));
    }

    private CreateProductRequestVo createMockCreateProductRequestVo() {
        return CreateProductRequestVo.builder()
                .files(List.of(
                        new MockMultipartFile("test1.jpg", "test1.jpg".getBytes()),
                        new MockMultipartFile("test2.jpg", "test2.jpg".getBytes())))
                .name("테스트 판매 상품")
                .category("GAME_HOBBY")
                .price(10_000L)
                .mainText("테스트 판매 상품 입니다.")
                .build();
    }

    private Product createMockProduct() {
        return Product.builder()
                .id(1L)
                .productTradeStatus(ProductTradeStatus.SALE)
                .build();
    }

    private List<ProductImage> createMockProductImageEntity(List<String> urls) {
        return urls.stream()
                .map(url -> ProductImage.builder()
                        .url(url)
                        .build())
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("좋아요 / 성공")
    void like_success() throws Exception {

        // given
        LoginMember loginMember = mock(LoginMember.class);
        Product zeroLikeProduct = createZeroLikeProduct();
        when(productJpaRepository.findById(any()))
                .thenReturn(Optional.of(zeroLikeProduct));
        Like like = Like.builder()
                .product(zeroLikeProduct)
                .member(loginMember.getMember())
                .build();

        // when
        productService.like(
                zeroLikeProduct.getId(),
                loginMember
        );

        // then
        List<Like> likes = zeroLikeProduct.getLikes().getValues();
        assertAll(
                () -> assertEquals(1, likes.size())
        );
    }

    private Product createZeroLikeProduct() {
        return Product.builder()
                .id(1L)
                .likes(Likes.builder().build())
                .build();
    }

    @Test
    @DisplayName("좋아요 취소 / 성공")
    void unlike_success() throws Exception {

        // given
        LoginMember loginMember = new LoginMember(Member.builder().id(1L)
                .email("a@a.com")
                .password("123")
                .build());
        Product oneLikeProduct = createOneLikeProduct(loginMember);
        Like like = Like.builder()
                .product(oneLikeProduct)
                .member(loginMember.getMember())
                .build();
        when(productJpaRepository.findById(any()))
                .thenReturn(Optional.of(oneLikeProduct));

        // when
        productService.unlike(oneLikeProduct.getId(), loginMember);

        // then
        List<Like> likes = oneLikeProduct.getLikes().getValues();
        assertEquals(0, likes.size());
    }

    private Product createOneLikeProduct(LoginMember loginMember) {
        Product product = Product.builder()
                .id(1L)
                .likes(Likes.builder().build())
                .build();

        product.getLikes().getValues().add(Like.builder()
                .product(product)
                .member(loginMember.getMember())
                .build());
        return product;
    }

    @Test
    @DisplayName("상품 상태 변경 / 예약 중 상태로 성공")
    void updateProductStatus_success_hold() throws Exception {

        // given
        UpdateProductTradeStatusRequestVo updateRequestVo = UpdateProductTradeStatusRequestVo.builder()
                .status("HOLD")
                .build();
        Product product = Product.builder()
                .id(1L).productTradeStatus(ProductTradeStatus.SALE)
                .build();
        when(productJpaRepository.findById(any()))
                .thenReturn(Optional.of(product));

        // when
        productService.updateProductStatus(updateRequestVo);

        // then
        assertAll(
                () -> verify(productJpaRepository).findById(updateRequestVo.getProductId()),
                () -> assertEquals(product.getProductTradeStatus(), ProductTradeStatus.HOLD)
        );
    }

    @Test
    @DisplayName("상품 상태 변경 / 판매완료 상태로 성공")
    void updateProductStatus_success_soldOut() throws Exception {

        // given
        UpdateProductTradeStatusRequestVo updateRequestVo = UpdateProductTradeStatusRequestVo.builder()
                .status("SOLD_OUT")
                .build();
        Product product = Product.builder()
                .id(1L).productTradeStatus(ProductTradeStatus.SALE)
                .build();
        when(productJpaRepository.findById(any()))
                .thenReturn(Optional.of(product));

        // when
        productService.updateProductStatus(updateRequestVo);

        // then
        assertAll(
                () -> verify(productJpaRepository).findById(updateRequestVo.getProductId()),
                () -> assertEquals(product.getProductTradeStatus(), ProductTradeStatus.SOLD_OUT)
        );
    }

    @Test
    @DisplayName("상품 정보 변경 / 성공")
    void updateProductInfo_success() throws Exception {

        // given
        UpdateProductInfoRequestVo requestVo = createMockUpdateProductInfoRequestVo();
        Product product = createMockProductWithImages();
        fileManagerRenameStub(requestVo.getFiles(), requestVo.getFileNames());
        when(productJpaRepository.findWithImageUrlsById(any()))
                .thenReturn(Optional.of(product));

        // when
        Long redirectProductId = productService.updateProductInfo(requestVo);

        // then
        assertAll(
                () -> verify(productJpaRepository).findWithImageUrlsById(requestVo.getProductId()),
                () -> assertEquals(product.getProductImages().getValues().size(), requestVo.getFiles().size()),
                () -> assertEquals(product.getMainText(), requestVo.getMainText()),
                () -> assertEquals(product.getName(), requestVo.getName()),
                () -> assertEquals(product.getPrice(), requestVo.getPrice()),
                () -> assertEquals(product.getProductCategory().name(), requestVo.getCategory())
        );
    }

    private UpdateProductInfoRequestVo createMockUpdateProductInfoRequestVo() {
        return UpdateProductInfoRequestVo.builder()
                .productId(1L)
                .category("DIGITAL_DEVICE")
                .name("테스트 상품")
                .price(45_000L)
                .mainText("테스트 텍스트 입니다.")
                .files(List.of(
                        new MockMultipartFile("test1.jpg", "test1.jpg".getBytes()),
                        new MockMultipartFile("test2.jpg", "test2.jpg".getBytes())))
                .build();
    }

    private Product createMockProductWithImages() {
        return Product.builder()
                .productImages(ProductImages.builder()
                        .values(List.of(
                                ProductImage.builder().id(1L).url("test url").build()
                        ))
                        .build())
                .name("변경 전 판매상품")
                .productCategory(ProductCategory.ETC)
                .price(100L)
                .mainText("변경 전 상품 상세 설명")
                .productTradeStatus(ProductTradeStatus.SALE)
                .build();
    }
}

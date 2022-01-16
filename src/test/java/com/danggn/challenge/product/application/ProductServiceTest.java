package com.danggn.challenge.product.application;

import com.danggn.challenge.common.client.StorageClient;
import com.danggn.challenge.common.manager.file.FileManager;
import com.danggn.challenge.common.security.LoginMember;
import com.danggn.challenge.member.domain.Member;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.domain.*;
import com.danggn.challenge.product.domain.repository.ProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    FileManager fileManager;
    @Mock
    StorageClient storageClient;
    @Mock
    ProductJpaRepository productJpaRepository;
    @Mock
    ProductApplicationAssembler applicationAssembler;

    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("상품 저장 / 성공")
    void save_success() throws Exception {

        // given
        LoginMember loginMember = mock(LoginMember.class);
        CreateProductRequestVo requestVo = createMockCreateProductRequestVo();
        fileManagerRenameStub(requestVo);
        storageClientGetFileUrlStub(requestVo);
        List<String> mockUrls = List.of(requestVo.getFileNames().get(0),
                requestVo.getFileNames().get(1));
        Product product = createMockProduct();
        when(applicationAssembler.toProductEntity(requestVo, loginMember.getMember()))
                .thenReturn(product);
        when(applicationAssembler.toProductImageEntity(product, mockUrls))
                .thenReturn(createMockProductImageEntity(mockUrls));
        when(productJpaRepository.save(any(Product.class)))
                .thenReturn(product);

        // when
        productService.save(requestVo, loginMember);

        // then
        assertAll(
                () -> verify(fileManager).upload(requestVo.getFiles()),
                () -> verify(storageClient, times(2)).getUrl(any()),
                () -> verify(applicationAssembler).toProductEntity(requestVo, loginMember.getMember()),
                () -> verify(productJpaRepository).save(any(Product.class)),
                () -> verify(applicationAssembler).toProductImageEntity(product, mockUrls),
                () -> assertEquals(product.getProductImages().getSize(), 2)
        );
    }

    private void fileManagerRenameStub(CreateProductRequestVo requestVo) {
        when(fileManager.upload(requestVo.getFiles()))
                .thenReturn(List.of(
                        requestVo.getFileNames().get(0),
                        requestVo.getFileNames().get(1)));
    }

    private void storageClientGetFileUrlStub(CreateProductRequestVo requestVo) {
        when(storageClient.getUrl(requestVo.getFileNames().get(0)))
                .thenReturn(requestVo.getFileNames().get(0));
        when(storageClient.getUrl(requestVo.getFileNames().get(1)))
                .thenReturn(requestVo.getFileNames().get(1));
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
        when(applicationAssembler.toLikeEntity(zeroLikeProduct, loginMember.getMember()))
                .thenReturn(like);

        // when
        productService.like(
                zeroLikeProduct.getId(),
                loginMember
        );

        // then
        List<Like> likes = zeroLikeProduct.getLikes().getValues();
        assertAll(
                () -> verify(applicationAssembler).toLikeEntity(zeroLikeProduct, loginMember.getMember()),
                () -> assertEquals(1, likes.size())
        );
    }

    private Product createZeroLikeProduct() {
        return Product.builder()
                .id(1L)
                .likes(Likes.builder().build())
                .build();
    }

    // TODO 테스트 작성
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
        when(applicationAssembler.toLikeEntity(oneLikeProduct, loginMember.getMember()))
                .thenReturn(like);

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
}

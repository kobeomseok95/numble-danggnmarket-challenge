package com.danggn.challenge.product.application;

import com.danggn.challenge.common.client.StorageClient;
import com.danggn.challenge.product.application.request.CreateProductRequestVo;
import com.danggn.challenge.product.domain.Product;
import com.danggn.challenge.product.domain.repository.ProductJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

//    @Mock
//    FileManager fileManager;
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
        CreateProductRequestVo requestVo = createMockCreateProductRequestVo();
        when(storageClient.getUrl(requestVo.getFileNames().get(0)))
                .thenReturn(requestVo.getFileNames().get(0));
        when(storageClient.getUrl(requestVo.getFileNames().get(1)))
                .thenReturn(requestVo.getFileNames().get(1));
        List<String> mockUrls = List.of(requestVo.getFileNames().get(0),
                requestVo.getFileNames().get(1));
        when(applicationAssembler.toProductEntity(requestVo, 1L, mockUrls))
                .thenReturn(createMockProduct());
        when(productJpaRepository.save(any(Product.class)))
                .thenReturn(createMockProduct());

        // when
        productService.save(requestVo, 1L);

        // then
        assertAll(
                () -> verify(storageClient, times(2)).getUrl(any()),
                () -> verify(applicationAssembler).toProductEntity(requestVo, 1L, mockUrls),
                () -> verify(productJpaRepository).save(any(Product.class))
        );
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
                .build();
    }
}
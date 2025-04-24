package kr.hhplus.be.server.domain;

import kr.hhplus.be.server.domain.product.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDetailRepository detailRepository;

    @Nested
    class productTest {

        @DisplayName("상품 및 상품 상세정보 목록 조회")
        @Test
        void findAll() {
            // given
            Product product1 = Product.builder()
                    .productId(10L)
                    .brand("나이키")
                    .productName("에어포스")
                    .build();

            Product product2 = Product.builder()
                    .productId(20L)
                    .brand("아디다스")
                    .productName("슈퍼스타")
                    .build();

            List<Product> products = List.of(product1, product2);

            List<ProductDetail> details1 = List.of(
                    ProductDetail.builder()
                            .productDetailId(1L)
                            .product(product1)
                            .build(),
                    ProductDetail.builder()
                            .productDetailId(2L)
                            .product(product1)
                            .build()
            );

            List<ProductDetail> details2 = List.of(
                    ProductDetail.builder()
                            .productDetailId(3L)
                            .product(product2)
                            .build(),
                    ProductDetail.builder()
                            .productDetailId(4L)
                            .product(product2)
                            .build()
            );

            when(productRepository.findAll()).thenReturn(products);
            when(detailRepository.findByProductId(10L)).thenReturn(details1);
            when(detailRepository.findByProductId(20L)).thenReturn(details2);

            // when
            ProductInfo.ProductList result = productService.findAll();

            // then
            verify(productRepository).findAll();
            verify(detailRepository).findByProductId(10L);
            verify(detailRepository).findByProductId(20L);

            assertEquals(2, result.getProducts().size());
            ProductInfo.ProductInfoList firstProductInfo = result.getProducts().get(0);
            ;
            assertEquals("에어포스", firstProductInfo.getProductName());
            assertEquals(product1.getProductId(), firstProductInfo.getDetails().get(0).getProduct().getProductId());
        }

        @DisplayName("상품 단건 조회")
        @Test
        void findProduct() {
            // given
            ProductCommand.Find command = new ProductCommand.Find(10L);
            Product product1 = Product.builder()
                    .productId(10L)
                    .brand("나이키")
                    .productName("에어포스")
                    .build();

            List<ProductDetail> details1 = List.of(
                    ProductDetail.builder()
                            .productDetailId(1L)
                            .optionName("검정색")
                            .productPrice(100000L)
                            .stockQuantity(10L)
                            .product(product1)
                            .build()
            );

            when(productRepository.findById(10L)).thenReturn(product1);
            when(detailRepository.findByProductId(10L)).thenReturn(details1);

            // when
            ProductInfo.ProductInfoList result = productService.findProduct(command);

            // verify
            verify(productRepository, times(1)).findById(10L);
            verify(detailRepository, times(1)).findByProductId(10L);
            assertEquals("에어포스", result.getProductName());
            assertEquals(1, result.getDetails().size());
            assertEquals("검정색", result.getDetails().get(0).getOptionName());
            assertEquals(Long.valueOf(100000L), result.getDetails().get(0).getProductPrice());
        }

        @DisplayName("상품 재고 확인")
        @Test
        void checkProductStock() {
            // given
            ProductCommand.FindDetail command1 = new ProductCommand.FindDetail(10L, 11000L);
            ProductCommand.FindDetail command2 = new ProductCommand.FindDetail(10L, 5L);

            ProductDetail detail = ProductDetail.builder()
                    .productDetailId(10L).stockQuantity(10L).build();
            when(detailRepository.findById(command1.getProductDetailId())).thenReturn(detail);

            // when
            boolean hasSufficientStock = productService.hasSufficientStock(command2);

            // then
            verify(detailRepository).findById(command1.getProductDetailId());
            verify(detailRepository).findById(command2.getProductDetailId());
            assertThatThrownBy(() -> productService.hasSufficientStock(command1)).isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 상품 재고가 부족합니다.");
            assertThat(hasSufficientStock).isTrue();
        }

        @DisplayName("상품 재고 정상 차감")
        @Test
        void decreascStockSuccess() {
            // given
            Long productDetailId = 1L;
            Long stockQuantity = 200L;
            Long requiredQuantity = 170L;

            ProductDetail detail = ProductDetail.builder()
                    .productDetailId(productDetailId)
                    .stockQuantity(stockQuantity)
                    .build();

            when(detailRepository.findById(productDetailId)).thenReturn(detail);

            ProductCommand.FindDetail command = new ProductCommand.FindDetail(1L, requiredQuantity);

            // when
            productService.decreaseStock(command);

            // then
            assertThat(detail.getStockQuantity()).isEqualTo(stockQuantity - requiredQuantity);

        }
    }
}

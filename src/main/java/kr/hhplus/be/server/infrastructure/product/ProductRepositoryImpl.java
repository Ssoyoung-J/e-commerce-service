package kr.hhplus.be.server.infrastructure.product;

import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.hhplus.be.server.domain.order.Order;
import kr.hhplus.be.server.domain.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static kr.hhplus.be.server.domain.order.QOrder.order;
import static kr.hhplus.be.server.domain.order.QOrderItem.orderItem;
import static kr.hhplus.be.server.domain.product.QProduct.product;
import static kr.hhplus.be.server.domain.product.QProductDetail.productDetail;
import static kr.hhplus.be.server.domain.product.QStock.stock;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Product findById(Long productId) {
        return productJpaRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    @Override
    public List<ProductQuery.ProductOptionDetail> findProductDetailsByProductId(long productId) {
        return queryFactory
                .select(new QProductQuery_ProductOptionDetail(
                        productDetail.productDetailId.as("productDetailId"),
                        productDetail.optionName.as("optionName"),
                        stock.quantity.as("stockQuantity")
                ))
                .from(productDetail)
                .join(stock).on(productDetail.productDetailId.eq(stock.productDetailId))
                .where(productDetail.productId.eq(productId))
                .fetch();
    }

    @Override
    public List<ProductQuery.PriceOption> findProductOptionsById(List<Long> optionIds) {
        return queryFactory
                .select(new QProductQuery_PriceOption(
                        productDetail.productDetailId.as("productDetailId"),
                        productDetail.productPrice.as("productPrice"),
                        stock.quantity.as("stockQuantity")
                ))
                .from(productDetail)
                .join(stock).on(productDetail.productDetailId.eq(stock.productDetailId))
                .join(product).on(productDetail.productId.eq(product.productId))
                .where(productDetail.productDetailId.in(optionIds))
                .fetch();
    }

    @Override
    public List<ProductQuery.BestSelling> findBestSellingProducts(LocalDate days, long limit) {
        NumberExpression<Long> salesCount = orderItem.productQuantity.sum().castToNum(Long.class);
        LocalDateTime dateTime = LocalDateTime.of(days, LocalTime.MIN);

        return queryFactory
                .select(new QProductQuery_BestSelling(
                        product.productId,
                        product.productName,
                        salesCount
                ))
                .from(product)
                .join(productDetail).on(product.productId.eq(productDetail.productId))
                .join(orderItem).on(productDetail.productDetailId.eq(orderItem.productDetailId))
                .join(order).on(order.orderId.eq(orderItem.orderId))
                .where(
                        order.status.eq(Order.OrderStatus.PAID),
                        order.orderedAt.goe(dateTime)
                )
                .groupBy(product.productId)
                .orderBy(salesCount.desc())
                .limit(limit)
                .fetch();
    }
}

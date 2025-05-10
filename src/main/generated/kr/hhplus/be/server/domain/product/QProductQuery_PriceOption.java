package kr.hhplus.be.server.domain.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.hhplus.be.server.domain.product.QProductQuery_PriceOption is a Querydsl Projection type for PriceOption
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductQuery_PriceOption extends ConstructorExpression<ProductQuery.PriceOption> {

    private static final long serialVersionUID = -1962341499L;

    public QProductQuery_PriceOption(com.querydsl.core.types.Expression<Long> productDetailId, com.querydsl.core.types.Expression<Long> productPrice, com.querydsl.core.types.Expression<Integer> stockQuantity) {
        super(ProductQuery.PriceOption.class, new Class<?>[]{long.class, long.class, int.class}, productDetailId, productPrice, stockQuantity);
    }

}


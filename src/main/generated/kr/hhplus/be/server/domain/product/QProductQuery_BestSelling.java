package kr.hhplus.be.server.domain.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.hhplus.be.server.domain.product.QProductQuery_BestSelling is a Querydsl Projection type for BestSelling
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductQuery_BestSelling extends ConstructorExpression<ProductQuery.BestSelling> {

    private static final long serialVersionUID = 478890291L;

    public QProductQuery_BestSelling(com.querydsl.core.types.Expression<Long> productId, com.querydsl.core.types.Expression<String> productName, com.querydsl.core.types.Expression<Long> salesCount) {
        super(ProductQuery.BestSelling.class, new Class<?>[]{long.class, String.class, long.class}, productId, productName, salesCount);
    }

}


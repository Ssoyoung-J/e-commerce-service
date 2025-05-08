package kr.hhplus.be.server.domain.product;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.hhplus.be.server.domain.product.QProductQuery_ProductOptionDetail is a Querydsl Projection type for ProductOptionDetail
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QProductQuery_ProductOptionDetail extends ConstructorExpression<ProductQuery.ProductOptionDetail> {

    private static final long serialVersionUID = 1108032796L;

    public QProductQuery_ProductOptionDetail(com.querydsl.core.types.Expression<Long> productDetailId, com.querydsl.core.types.Expression<String> optionName, com.querydsl.core.types.Expression<Integer> stockQuantity) {
        super(ProductQuery.ProductOptionDetail.class, new Class<?>[]{long.class, String.class, int.class}, productDetailId, optionName, stockQuantity);
    }

}


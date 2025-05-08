package kr.hhplus.be.server.domain.order;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.hhplus.be.server.domain.order.QOrderQuery_OrderItem is a Querydsl Projection type for OrderItem
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QOrderQuery_OrderItem extends ConstructorExpression<OrderQuery.OrderItem> {

    private static final long serialVersionUID = -1362109782L;

    public QOrderQuery_OrderItem(com.querydsl.core.types.Expression<Long> productDetailId, com.querydsl.core.types.Expression<Long> productPrice, com.querydsl.core.types.Expression<Integer> productQuantity, com.querydsl.core.types.Expression<Long> userCouponId) {
        super(OrderQuery.OrderItem.class, new Class<?>[]{long.class, long.class, int.class, long.class}, productDetailId, productPrice, productQuantity, userCouponId);
    }

}


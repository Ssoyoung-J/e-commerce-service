package kr.hhplus.be.server.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 1155011281L;

    public static final QOrder order = new QOrder("order1");

    public final kr.hhplus.be.server.domain.common.QBaseEntity _super = new kr.hhplus.be.server.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> discountAmount = createNumber("discountAmount", Long.class);

    public final NumberPath<Long> finalPrice = createNumber("finalPrice", Long.class);

    public final DateTimePath<java.time.LocalDateTime> orderedAt = createDateTime("orderedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final ListPath<OrderItem, QOrderItem> orderItemList = this.<OrderItem, QOrderItem>createList("orderItemList", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final EnumPath<Order.OrderStatus> status = createEnum("status", Order.OrderStatus.class);

    public final NumberPath<Long> totalAmount = createNumber("totalAmount", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userCouponId = createNumber("userCouponId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}


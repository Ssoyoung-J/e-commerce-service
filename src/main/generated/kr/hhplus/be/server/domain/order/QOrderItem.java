package kr.hhplus.be.server.domain.order;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderItem is a Querydsl query type for OrderItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderItem extends EntityPathBase<OrderItem> {

    private static final long serialVersionUID = 572731780L;

    public static final QOrderItem orderItem = new QOrderItem("orderItem");

    public final kr.hhplus.be.server.domain.common.QBaseEntity _super = new kr.hhplus.be.server.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final NumberPath<Long> productDetailId = createNumber("productDetailId", Long.class);

    public final NumberPath<Long> productPrice = createNumber("productPrice", Long.class);

    public final NumberPath<Integer> productQuantity = createNumber("productQuantity", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userCouponId = createNumber("userCouponId", Long.class);

    public QOrderItem(String variable) {
        super(OrderItem.class, forVariable(variable));
    }

    public QOrderItem(Path<? extends OrderItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderItem(PathMetadata metadata) {
        super(OrderItem.class, metadata);
    }

}


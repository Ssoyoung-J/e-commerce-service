package kr.hhplus.be.server.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStock is a Querydsl query type for Stock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStock extends EntityPathBase<Stock> {

    private static final long serialVersionUID = 1038862010L;

    public static final QStock stock = new QStock("stock");

    public final kr.hhplus.be.server.domain.common.QBaseEntity _super = new kr.hhplus.be.server.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> productDetailId = createNumber("productDetailId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<Long> stockId = createNumber("stockId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QStock(String variable) {
        super(Stock.class, forVariable(variable));
    }

    public QStock(Path<? extends Stock> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStock(PathMetadata metadata) {
        super(Stock.class, metadata);
    }

}


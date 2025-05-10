package kr.hhplus.be.server.domain.point;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPoint is a Querydsl query type for Point
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPoint extends EntityPathBase<Point> {

    private static final long serialVersionUID = -218466859L;

    public static final QPoint point = new QPoint("point");

    public final kr.hhplus.be.server.domain.common.QBaseEntity _super = new kr.hhplus.be.server.domain.common.QBaseEntity(this);

    public final NumberPath<Long> balance = createNumber("balance", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> pointAmount = createNumber("pointAmount", Long.class);

    public final NumberPath<Long> pointId = createNumber("pointId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QPoint(String variable) {
        super(Point.class, forVariable(variable));
    }

    public QPoint(Path<? extends Point> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPoint(PathMetadata metadata) {
        super(Point.class, metadata);
    }

}


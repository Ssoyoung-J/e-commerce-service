package kr.hhplus.be.server.domain.product;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductDetail is a Querydsl query type for ProductDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductDetail extends EntityPathBase<ProductDetail> {

    private static final long serialVersionUID = 1287523780L;

    public static final QProductDetail productDetail = new QProductDetail("productDetail");

    public final kr.hhplus.be.server.domain.common.QBaseEntity _super = new kr.hhplus.be.server.domain.common.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath optionName = createString("optionName");

    public final NumberPath<Long> productDetailId = createNumber("productDetailId", Long.class);

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Long> productPrice = createNumber("productPrice", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QProductDetail(String variable) {
        super(ProductDetail.class, forVariable(variable));
    }

    public QProductDetail(Path<? extends ProductDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductDetail(PathMetadata metadata) {
        super(ProductDetail.class, metadata);
    }

}


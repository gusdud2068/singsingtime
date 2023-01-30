package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFamePlay is a Querydsl query type for FamePlay
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFamePlay extends EntityPathBase<FamePlay> {

    private static final long serialVersionUID = 794881833L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFamePlay famePlay = new QFamePlay("famePlay");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath filePath = createString("filePath");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> recommendCnt = createNumber("recommendCnt", Integer.class);

    public final NumberPath<Integer> recommendRank = createNumber("recommendRank", Integer.class);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final QUser user;

    public final NumberPath<Integer> viewCnt = createNumber("viewCnt", Integer.class);

    public final NumberPath<Integer> viewRank = createNumber("viewRank", Integer.class);

    public QFamePlay(String variable) {
        this(FamePlay.class, forVariable(variable), INITS);
    }

    public QFamePlay(Path<? extends FamePlay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFamePlay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFamePlay(PathMetadata metadata, PathInits inits) {
        this(FamePlay.class, metadata, inits);
    }

    public QFamePlay(Class<? extends FamePlay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}


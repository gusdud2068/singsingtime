package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayroom is a Querydsl query type for Playroom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlayroom extends EntityPathBase<Playroom> {

    private static final long serialVersionUID = 1891658705L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayroom playroom = new QPlayroom("playroom");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final DateTimePath<java.time.LocalDateTime> callEndTime = createDateTime("callEndTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> callStartTime = createDateTime("callStartTime", java.time.LocalDateTime.class);

    public final StringPath challenger = createString("challenger");

    public final NumberPath<Integer> challengerCnt = createNumber("challengerCnt", Integer.class);

    public final StringPath champion = createString("champion");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath curPlay = createString("curPlay");

    public final QGenre genre;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> recommendCnt = createNumber("recommendCnt", Integer.class);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final QUser user;

    public final NumberPath<Integer> userCnt = createNumber("userCnt", Integer.class);

    public QPlayroom(String variable) {
        this(Playroom.class, forVariable(variable), INITS);
    }

    public QPlayroom(Path<? extends Playroom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayroom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayroom(PathMetadata metadata, PathInits inits) {
        this(Playroom.class, metadata, inits);
    }

    public QPlayroom(Class<? extends Playroom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.genre = inits.isInitialized("genre") ? new QGenre(forProperty("genre")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}


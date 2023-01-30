package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserPlayroom is a Querydsl query type for UserPlayroom
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserPlayroom extends EntityPathBase<UserPlayroom> {

    private static final long serialVersionUID = 2054426556L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserPlayroom userPlayroom = new QUserPlayroom("userPlayroom");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPlayroom playroom;

    public final QUser user;

    public QUserPlayroom(String variable) {
        this(UserPlayroom.class, forVariable(variable), INITS);
    }

    public QUserPlayroom(Path<? extends UserPlayroom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserPlayroom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserPlayroom(PathMetadata metadata, PathInits inits) {
        this(UserPlayroom.class, metadata, inits);
    }

    public QUserPlayroom(Class<? extends UserPlayroom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.playroom = inits.isInitialized("playroom") ? new QPlayroom(forProperty("playroom"), inits.get("playroom")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}


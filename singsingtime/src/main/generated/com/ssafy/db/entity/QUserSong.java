package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserSong is a Querydsl query type for UserSong
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserSong extends EntityPathBase<UserSong> {

    private static final long serialVersionUID = -254504638L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserSong userSong = new QUserSong("userSong");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QSong song;

    public final QUser user;

    public QUserSong(String variable) {
        this(UserSong.class, forVariable(variable), INITS);
    }

    public QUserSong(Path<? extends UserSong> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserSong(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserSong(PathMetadata metadata, PathInits inits) {
        this(UserSong.class, metadata, inits);
    }

    public QUserSong(Class<? extends UserSong> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.song = inits.isInitialized("song") ? new QSong(forProperty("song")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}


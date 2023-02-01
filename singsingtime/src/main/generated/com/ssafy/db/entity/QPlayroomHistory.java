package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayroomHistory is a Querydsl query type for PlayroomHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlayroomHistory extends EntityPathBase<PlayroomHistory> {

    private static final long serialVersionUID = -1102097533L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayroomHistory playroomHistory = new QPlayroomHistory("playroomHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Short> action = createNumber("action", Short.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final DateTimePath<java.sql.Timestamp> insertedTime = createDateTime("insertedTime", java.sql.Timestamp.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final QPlayroom playroom;

    public final QUser user;

    public QPlayroomHistory(String variable) {
        this(PlayroomHistory.class, forVariable(variable), INITS);
    }

    public QPlayroomHistory(Path<? extends PlayroomHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayroomHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayroomHistory(PathMetadata metadata, PathInits inits) {
        this(PlayroomHistory.class, metadata, inits);
    }

    public QPlayroomHistory(Class<? extends PlayroomHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.playroom = inits.isInitialized("playroom") ? new QPlayroom(forProperty("playroom"), inits.get("playroom")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}


package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSong is a Querydsl query type for Song
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSong extends EntityPathBase<Song> {

    private static final long serialVersionUID = 846479319L;

    public static final QSong song = new QSong("song");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final DateTimePath<java.time.LocalDateTime> part1 = createDateTime("part1", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> part2 = createDateTime("part2", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> part3 = createDateTime("part3", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> part4 = createDateTime("part4", java.time.LocalDateTime.class);

    public final StringPath singer = createString("singer");

    public final StringPath title = createString("title");

    public QSong(String variable) {
        super(Song.class, forVariable(variable));
    }

    public QSong(Path<? extends Song> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSong(PathMetadata metadata) {
        super(Song.class, metadata);
    }

}


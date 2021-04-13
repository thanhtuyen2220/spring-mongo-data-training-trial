package com.pycogroup.superblog.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
@Builder
@QueryEntity
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @Getter
    private ObjectId id;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    @DBRef
    private Article article;
    @Getter
    @Setter
    @DBRef
    private User user;

    @Getter
    @Setter
    private Boolean removed = false;
}

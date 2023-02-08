package com.fredsonchaves07.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    private Integer id;

    private String title;

    private String description;

    private String body;

    private String slug;

    private PostStatus postStatus;

    private LocalDateTime createdOn;

    private List<Comment> comments;
}

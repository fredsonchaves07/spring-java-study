package com.fredsonchaves07.domain;

import com.fredsonchaves07.validation.BlogPostTitleAlreadyExists;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@BlogPostTitleAlreadyExists
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50, message = "Title must be minimum 3 characters, and maximum, 50 characters")
    private String title;

    @NotNull
    @Size(min = 3, max = 500, message = "Description must be minimum 3 characters, and maximum 500 characters")
    private String description;

    @NotNull
    @Size(min = 3, max = 5000, message = "Body must be minimum 3 characters, and maximum 5000 characters")
    private String body;

    private String slug;

    @Field(name = "post_status")
    private PostStatus postStatus;

    private LocalDate createdOn;

    private LocalDate updatedOn;

    private List<Comment> comments;
}

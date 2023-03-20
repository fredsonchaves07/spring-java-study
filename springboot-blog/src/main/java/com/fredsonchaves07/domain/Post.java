package com.fredsonchaves07.domain;

import com.fredsonchaves07.validation.BlogPostTitleAlreadyExists;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@BlogPostTitleAlreadyExists
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

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

    private PostStatus postStatus;

    private LocalDate createdOn;

    private LocalDate updatedOn;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments;
}

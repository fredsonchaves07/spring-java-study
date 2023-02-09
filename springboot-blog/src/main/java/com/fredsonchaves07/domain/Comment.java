package com.fredsonchaves07.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Integer id;

    private String title;

    private String authorName;

    private String body;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;
}

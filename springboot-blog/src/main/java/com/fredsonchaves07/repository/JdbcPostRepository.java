package com.fredsonchaves07.repository;

import com.fredsonchaves07.domain.Post;
import com.fredsonchaves07.repository.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcPostRepository {

    private final JdbcTemplate jdbcTemplate;

    public Set<Post> findAllPosts() {
        return jdbcTemplate.queryForStream("" +
                "select id, title, description, body, slug, post_status, created_on, updated_on from posts",
                new PostMapper()).collect(Collectors.toSet());
    }

    public void addPost(Post post) {
        final String sql = "insert into posts(title, description, body, slug, post_status, created_on, updated_on) " + "values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, post.getTitle());
            preparedStatement.setString(2, post.getDescription());
            preparedStatement.setString(3, post.getBody());
            preparedStatement.setString(4, post.getSlug());
            preparedStatement.setObject(5, post.getPostStatus());
            preparedStatement.setDate(6, java.sql.Date.valueOf(post.getCreatedOn()));
            preparedStatement.setDate(7, java.sql.Date.valueOf(post.getUpdatedOn()));
            return preparedStatement;
        });
    }
}

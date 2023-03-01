package com.fredsonchaves07.domain;

import com.fredsonchaves07.repository.JdbcPostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;

@JdbcTest
@Import(JdbcPostRepository.class)
public class JdbcRepositoryTest {

    @Autowired
    private JdbcPostRepository postRepository;

    @Test
    public void testFindAllPosts() {
        Post post = new Post();
        post.setTitle("sample blog post");
        post.setDescription("sample blog post");
        post.setBody("sample blog post");
        post.setSlug("sample-blog-post");
        post.setUpdatedOn(LocalDate.now());
        post.setCreatedOn(LocalDate.now());
        postRepository.addPost(post);
        Assertions.assertThat(postRepository.findAllPosts()).hasSize(1);
    }
}
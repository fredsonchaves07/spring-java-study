package com.fredsonchaves07.service;

import com.fredsonchaves07.domain.Post;
import com.fredsonchaves07.repository.JdbcPostRepository;
import com.fredsonchaves07.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final JdbcPostRepository postRepository;

    public void addPost(Post post) {
        post.setCreatedOn(LocalDate.now());
        post.setUpdatedOn(LocalDate.now());
        postRepository.addPost(post);
    }

    public Set<Post> findAllPosts() {
        return postRepository.findAllPosts();
    }

    public boolean postExistsWithTitle(String title) {
        return postRepository.findAllPosts()
                .stream()
                .anyMatch(post -> post.getTitle().equals(title));
    }
}

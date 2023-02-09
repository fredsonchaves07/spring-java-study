package com.fredsonchaves07.repository;

import com.fredsonchaves07.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Repository
public class PostRepository {

    private final Set<Post> posts = new CopyOnWriteArraySet<>();


    public void addPost(Post post) {
        posts.add(post);
    }


    public Set<Post> findAllPosts() {
        return posts;
    }
}

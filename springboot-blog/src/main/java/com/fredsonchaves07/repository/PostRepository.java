package com.fredsonchaves07.repository;

import com.fredsonchaves07.domain.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    boolean existsByTitle(String title);

    @Query("{ 'title' : ?0 }")
    Post findByTitle(String title);
}

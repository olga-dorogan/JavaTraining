package com.custom.model;

import java.util.List;
import java.util.UUID;

/**
 * Created by olga on 13.07.15.
 */
public interface Model {
    UUID createPost(String title, String content, List categories);
    UUID createComment(UUID post, String author, String content);
    List getAllPosts();
    List getAllCommentsOn(UUID post);
    boolean existPost(UUID post);
}

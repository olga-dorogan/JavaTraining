package com.custom.model;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by olga on 13.07.15.
 */
public class Sql2oModel implements Model {
    private Sql2o sql2o;
    private UUID uuidGenerator;

    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public UUID createPost(String title, String content, List categories) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID uuid = uuidGenerator.randomUUID();
            conn.createQuery("insert into posts(post_uuid, title, content, publishing_date) VALUES (:post_uuid, :title, :content, :date)")
                    .addParameter("post_uuid", uuid.toString())
                    .addParameter("title", title)
                    .addParameter("content", content)
                    .addParameter("date", new Date())
                    .executeUpdate();
            categories.forEach((category) -> {
                conn.createQuery("insert into posts_categories(post_uuid, category) VALUES (:post_uuid, :category)")
                        .addParameter("post_uuid", uuid)
                        .addParameter("category", category)
                        .executeUpdate();
            });
            conn.commit();
            return uuid;
        }
    }

    @Override
    public UUID createComment(UUID post, String author, String content) {
        try (Connection conn = sql2o.open()) {
            UUID uuid = uuidGenerator.randomUUID();
            conn.createQuery("insert into comments(comment_uuid, post_uuid, author, content, approved, submission_date) " +
                    "VALUES (:comment_uuid, :post_uuid, :author, :content, :approved, :date)")
                    .addParameter("comment_uuid", uuid)
                    .addParameter("post_uuid", post)
                    .addParameter("author", author)
                    .addParameter("content", content)
                    .addParameter("approved", false)
                    .addParameter("date", new Date())
                    .executeUpdate();
            return uuid;
        }
    }

    @Override
    public List getAllPosts() {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("select * from posts").executeAndFetch(Post.class);
            posts.forEach((post) -> {
                post.setCategories(getCategoriesFor(conn, post.getPost_uuid()));
            });
            return posts;
        }
    }

    private List getCategoriesFor(Connection conn, UUID post_uuid) {
        return conn.createQuery("select category from posts_categories where post_uuid=:post_uuid")
                .addParameter("post_uuid", post_uuid)
                .executeAndFetch(String.class);
    }

    @Override
    public List getAllCommentsOn(UUID post) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select * from comments where post_uuid=:post_uuid")
                    .addParameter("post_uuid", post)
                    .executeAndFetch(Comment.class);
        }
    }

    @Override
    public boolean existPost(UUID post_uuid) {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("select * from posts where post_uuid=:post")
                    .addParameter("post", post_uuid)
                    .executeAndFetch(Post.class);
            return posts.size() > 0;
        }
    }
}

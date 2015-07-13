CREATE TABLE blog.posts (
    post_uuid varchar(80) primary key,
    title text not null,
    content text,
    publishing_date date
);

CREATE TABLE blog.comments (
    comment_uuid varchar(80) primary key,
    post_uuid varchar(80) references posts(post_uuid),
    author text,
    content text,
    approved bool,
    submission_date date
);

CREATE TABLE blog.posts_categories (
    post_uuid varchar(80) references posts(post_uuid),
    category text
);

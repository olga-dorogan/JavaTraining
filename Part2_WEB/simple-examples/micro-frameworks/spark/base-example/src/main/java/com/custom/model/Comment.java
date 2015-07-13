package com.custom.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * Created by olga on 13.07.15.
 */
@Data
public class Comment {
    UUID comment_uuid;
    UUID post_uuid;
    String author;
    String content;
    boolean approved;
    Date submission_date;
}

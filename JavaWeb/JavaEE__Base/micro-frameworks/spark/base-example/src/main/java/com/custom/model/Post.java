package com.custom.model;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by olga on 13.07.15.
 */
@Data
public class Post {
    private UUID post_uuid;
    private String title;
    private String content;
    private Date publishing_date;
    private List categories;
}

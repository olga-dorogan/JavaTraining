package com.custom.model.validation;

import lombok.Data;

/**
 * Created by olga on 13.07.15.
 */
@Data
public class NewCommentPayload implements Validable {
    private String author;
    private String content;

    public boolean isValid() {
        return author != null && !author.isEmpty() && content != null && !content.isEmpty();
    }
}

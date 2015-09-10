package com.custom.model.validation;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by olga on 13.07.15.
 */
@Data
public class NewPostPayload implements Validable {
    private String title;
    private List<String> categories = new LinkedList<String>();
    private String content;

    @Override
    public boolean isValid() {
        return title != null && !title.isEmpty() && !categories.isEmpty();
    }
}

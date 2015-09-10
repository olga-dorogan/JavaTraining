package com.custom;

import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by olga on 11.04.15.
 */
@RequestScoped
public class MyBean2 {
    public String testString(@NotNull @Size(min = 1, max = 3) String str) {
        return str;
    }

    @Future
    public Date getDate(boolean isFuture) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, isFuture ? 1 : -1);
        return calendar.getTime();
    }

    public String showList(@NotNull @Size(min = 1, max = 3) List<String> list, @NotNull String prefix) {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(prefix).append(s).append(" ");
        }
        return builder.toString();
    }
}

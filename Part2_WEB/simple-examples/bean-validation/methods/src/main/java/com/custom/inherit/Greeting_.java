package com.custom.inherit;

import javax.validation.constraints.NotNull;

/**
 * Created by olga on 11.04.15.
 */
public interface Greeting_ {
    String greet(@NotNull String name);
}

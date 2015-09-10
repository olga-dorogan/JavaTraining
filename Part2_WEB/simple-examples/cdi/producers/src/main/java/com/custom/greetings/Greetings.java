package com.custom.greetings;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;
/**
 * Created by olga on 06.04.15.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({FIELD, PARAMETER, METHOD, CONSTRUCTOR })
public @interface Greetings {
    GreetingType value();
}

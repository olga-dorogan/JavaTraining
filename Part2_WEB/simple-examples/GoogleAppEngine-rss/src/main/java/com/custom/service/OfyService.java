package com.custom.service;

import com.custom.domain.Counter;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.factory;

/**
 * Created by olga on 21.06.15.
 */
public class OfyService {
    static {
        factory().register(Counter.class);
    }
    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }
}

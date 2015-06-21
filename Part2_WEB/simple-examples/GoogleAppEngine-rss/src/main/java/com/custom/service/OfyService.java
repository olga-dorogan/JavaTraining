package com.custom.service;

import com.custom.domain.Counter;
import com.custom.domain.ParfumWithPrice;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import static com.googlecode.objectify.ObjectifyService.factory;

/**
 * Created by olga on 21.06.15.
 */
public class OfyService {
    static {
        factory().register(Counter.class);
        factory().register(ParfumWithPrice.class);
    }
    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }
}

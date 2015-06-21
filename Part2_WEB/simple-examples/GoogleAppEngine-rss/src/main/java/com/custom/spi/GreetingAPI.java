package com.custom.spi;

import com.custom.Constants;
import com.custom.domain.Counter;
import com.custom.domain.GreetingImpl;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.User;
import com.googlecode.objectify.Key;

import static com.custom.service.OfyService.ofy;

/**
 * Add your first API methods in this class, or you may create another class. In that case, please
 * update your web.xml accordingly.
 */
@Api(
        name = "greeting", version = "v1", scopes = {Constants.EMAIL_SCOPE},
        clientIds = {Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
        description = "API for the Greeting part of Tested Backend application."
)
public class GreetingAPI {
    @ApiMethod(
            name = "hello",
            path = "greeting",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public GreetingImpl hello() {
        GreetingImpl greeter = new GreetingImpl();
        greeter.setMessage("Hello!");
        return greeter;
    }

    @ApiMethod(
            name = "helloWithCachedCounter",
            path = "greetingCached",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public GreetingImpl helloWithCachedCounter() {
        GreetingImpl greeter = new GreetingImpl();
        MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();
        Integer cnt = (Integer) memcacheService.get(Constants.MEMCACHE_COUNTER_KEY);
        if (cnt == null) {
            cnt = new Integer(0);
        }
        cnt++;
        memcacheService.put(Constants.MEMCACHE_COUNTER_KEY, cnt);
        greeter.setMessage("Hello! You have got this message " + cnt + " times.");
        return greeter;
    }

    @ApiMethod(
            name = "helloByName",
            path = "greetingByName",
            httpMethod = ApiMethod.HttpMethod.GET
    )
    public GreetingImpl helloByName(final User user) throws UnauthorizedException {
        if (user == null) {
            throw new UnauthorizedException("Authorization required");
        }

        String userId = user.getUserId();
        Key key = Key.create(Counter.class, userId);
        Counter counter = (Counter) ofy().load().key(key).now();
        if (counter == null) {
            counter = new Counter(userId, 0);
        } else {
            counter.setCnt(counter.getCnt() + 1);
        }
        ofy().save().entity(counter).now();

        GreetingImpl greeter = new GreetingImpl();
        greeter.setMessage("Hello, " + user.getNickname() + "! You have got this message " + counter.getCnt() + " times.");
        return greeter;
    }
}

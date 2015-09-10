package com.custom.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by olga on 01.03.15.
 */
@WebServlet("/info.byJS")
public class ServletToJson extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        final String
                HEADERS = "headers",
                PARAMS = "params",
                THREADS = "threads";
        final String
                KEY = "key",
                VALUE = "value";

        List<Map<String, String>> headers = new ArrayList<Map<String, String>>();
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            Map map = new HashMap<String, String>();
            String name = headerNames.nextElement();
            map.put(KEY, name);
            map.put(VALUE, req.getHeader(name));
            headers.add(map);
        }

        List<Map<String, String>> params = new ArrayList<Map<String, String>>();
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            Map map = new HashMap<String, String>();
            String name = paramNames.nextElement();
            map.put(KEY, name);
            map.put(VALUE, req.getParameter(name));
            params.add(map);
        }

        List<Map<String, String>> containerThreads = new ArrayList<Map<String, String>>();
        synchronized (this) {
            int active = Thread.activeCount();
            Thread[] allThreads = new Thread[active];
            if (Thread.enumerate(allThreads) == active) {
                for (int i = 0; i < allThreads.length; i++) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(KEY, Long.toString(allThreads[i].getId()));
                    map.put(VALUE, allThreads[i].getName());
                    containerThreads.add(map);
                }
            }
        }

        Map<String, List<Map<String, String>>> info = new LinkedHashMap<String, List<Map<String, String>>>();
        info.put(HEADERS, headers);
        info.put(PARAMS, params);
        info.put(THREADS, containerThreads);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.getWriter().print(new Gson().toJson(info));
    }
}


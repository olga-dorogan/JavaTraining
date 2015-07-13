package com.custom;

import com.beust.jcommander.JCommander;
import com.custom.model.Model;
import com.custom.model.Sql2oModel;
import com.custom.model.validation.NewCommentPayload;
import com.custom.model.validation.NewPostPayload;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.io.StringWriter;
import java.util.UUID;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

/**
 * Created by olga on 13.07.15.
 */
public class MainEntry {
    private static final int HTTP_BAD_REQUEST = 400;

    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error with StringWriter");
        }
    }

    public static void main(String[] args) {
        CommandLineOptions options = new CommandLineOptions();
        new JCommander(options, args);

        port(options.servicePort);

        Sql2o sql2o = new Sql2o("jdbc:mysql://" + options.dbHost + ":" + options.dbPort + "/" + options.database,
                options.dbUsername, options.dbPassword);
        Model model = new Sql2oModel(sql2o);

        post("/posts", (req, resp) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                NewPostPayload postToCreate = mapper.readValue(req.body(), NewPostPayload.class);
                if (!postToCreate.isValid()) {
                    resp.status(HTTP_BAD_REQUEST);
                    return "";
                }
                UUID uuid = model.createPost(postToCreate.getTitle(), postToCreate.getContent(), postToCreate.getCategories());
                resp.status(200);
                resp.type("application/json");
                return uuid;
            } catch (JsonParseException e) {
                resp.status(HTTP_BAD_REQUEST);
                return "";
            }
        });

        get("/posts", (req, resp) -> {
            resp.status(200);
            resp.type("application/json");
            return dataToJson(model.getAllPosts());
        });

        post("/posts/:uuid/comments", (req, resp) -> {
            ObjectMapper mapper = new ObjectMapper();
            NewCommentPayload creation = mapper.readValue(req.body(), NewCommentPayload.class);
            if (!creation.isValid()) {
                resp.status(HTTP_BAD_REQUEST);
                return "";
            }
            UUID post = UUID.fromString(req.params(":uuid"));
            if (!model.existPost(post)) {
                resp.status(400);
                return "";
            }
            UUID id = model.createComment(post, creation.getAuthor(), creation.getContent());
            resp.status(200);
            resp.type("application/json");
            return id;
        });

        get("/posts/:uuid/comments", (req, resp) -> {
            UUID post = UUID.fromString(req.params(":uuid"));
            if (!model.existPost(post)) {
                resp.status(400);
                return "";
            }
            resp.status(200);
            resp.type("application/json");
            return dataToJson(model.getAllCommentsOn(post));
        });
    }
}












package sparkexample;

import static spark.Spark.get;

/**
 * Created by olga on 14.07.15.
 */
public class Hello {

    public static void main(String[] args) {
        get("/", (req, res) -> {
            return "hello from sparkjava.com";
        });
    }
}
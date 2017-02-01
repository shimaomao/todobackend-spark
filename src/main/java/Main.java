import plumbing.JsonTransformer;
import representations.Todo;

import java.util.Arrays;

import static plumbing.DbMigrate.runMigrationsIfRequested;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        try {
            if( runMigrationsIfRequested(args) ){
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        port(getPort());

        JsonTransformer jsonTransformer = new JsonTransformer();

        redirect.get("/", "/todos");

        get("/hello", (req, res) -> "Hello World");

        get("/todos", "application/json", (request, response) -> {
            return Arrays.asList(
              Todo.of(1,"foo",false,1)
            );
        }, jsonTransformer);
    }

    static int getPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }else{
            return 4567;
        }
    }
}
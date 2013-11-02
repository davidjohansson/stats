package controllers;

import play.*;
import play.mvc.*;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;
import views.html.*;

public class Application extends Controller {
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result bench(String dateString) {
        ObjectNode result = Json.newObject();
        result.put("weight", "12332");
        return ok(result);
    }
}

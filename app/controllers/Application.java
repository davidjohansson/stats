package controllers;

import model.BodyStats;
import model.SpreadSheetException;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import readers.GoogleReaderModule;
import readers.StatsReader;
import views.html.index;
import writers.StatsWriter;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Application extends Controller {

    static StatsReader reader = createReader();
    static StatsWriter writer = createWriter();
 
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result bodystatsGet(String dateString) {
        BodyStats stats = reader.readStats(dateString);
        ObjectNode result = (ObjectNode) Json.toJson(stats);
        return ok(result);
    }
    
    
    public static Result bodystatsPut(String dateString) {
        Form<BodyStats> bodyStatsForm = Form.form(BodyStats.class);
        BodyStats stats = bodyStatsForm.bindFromRequest().get();
        return bodystatsPut(dateString, stats);
    }
    
    public static Result bodystatsPut(String any, BodyStats stats) {
        System.out.println("I got this: " + stats);
        try {
            writer.writeStats(any, stats);
            ObjectNode result = (ObjectNode) Json.toJson(stats);
            return ok(result);
        } catch (SpreadSheetException e) {
            return Controller.internalServerError();
        }
    }
    
    private static StatsReader createReader() {
        Injector injector = Guice.createInjector(new GoogleReaderModule());
        return injector.getInstance(StatsReader.class);
    }

    private static StatsWriter createWriter() {
        Injector injector = Guice.createInjector(new GoogleReaderModule());
        return injector.getInstance(StatsWriter.class);
    
    }

}

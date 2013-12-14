package controllers;

import model.BodyStats;
import model.DailyStats;
import model.PeriodizedStatsWrapper;
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

    public static Result bodystatsGet(String dateString) {
        PeriodizedStatsWrapper<BodyStats> stats;
        try {
            stats = reader.readStats(dateString, BodyStats.class);
            ObjectNode result = getJSonNodeFromObject(stats.getDaily());
            return ok(result);
        } catch (SpreadSheetException e) {
            return internalServerError("Failed to read data");
        }
    }

    public static Result dailystatsGet(String dateString) {
        PeriodizedStatsWrapper<DailyStats> stats;
        try {
            stats = reader.readStats(dateString, DailyStats.class);
            ObjectNode result = getJSonNodeFromObject(stats.getDaily());
            return ok(result);
        } catch (SpreadSheetException e) {
            return internalServerError("Failed to read data");
        }
    }

    public static Result dailystatsPut(String dateString){
        DailyStats stats = getModelObjectFromForm(DailyStats.class);
        return statsPut(dateString, stats);
    }
    
    public static Result bodystatsPut(String dateString) {
        BodyStats stats = getModelObjectFromForm(BodyStats.class);
        return statsPut(dateString, stats);
    }

    private static <T> T getModelObjectFromForm(Class<T> outClas) {
        Form<T> form = Form.form((Class<T>) outClas);
        T stats = form.bindFromRequest().get();
        return stats;
    }

    public static <T> Result statsPut(String any, T stats) {
        try {
            writer.writeStats(any, stats);
            ObjectNode result = getJSonNodeFromObject(stats);
            return ok(result);
        } catch (SpreadSheetException e) {
            return Controller.internalServerError();
        }
    }

    private static <T> ObjectNode getJSonNodeFromObject(T stats) {
        ObjectNode result = (ObjectNode) Json.toJson(stats);
        return result;
    }
    
    private static StatsReader createReader() {
        Injector injector = Guice.createInjector(new GoogleReaderModule());
        return injector.getInstance(StatsReader.class);
    }

    private static StatsWriter createWriter() {
        Injector injector = Guice.createInjector(new GoogleReaderModule());
        return injector.getInstance(StatsWriter.class);
    
    }

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
}

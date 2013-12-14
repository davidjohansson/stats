package controllers;
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;
import helpers.TestHelper;

import model.BodyStats;

import org.junit.Ignore;
import org.junit.Test;

import play.libs.F.Callback;
import play.mvc.Result;
import play.test.TestBrowser;
import controllers.Application;

public class ApplicationIntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    @Ignore
    public void test() {
        running(testServer(3333, fakeApplication(inMemoryDatabase())), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                assertThat(browser.pageSource()).contains("Your new application is ready.");
            }
        });
    }

    @Test
    public void test_get() throws Exception{
        Result bench = Application.bodystatsGet("2013-09-20");
        System.out.println(bench.getWrappedResult().toString());
    }
    
    @Test
    public void test_put() throws Exception{
        Result bench = Application.bodystatsPut(TestHelper.getRandomColumnKey(), BodyStats.getSampleBodyStats());
        System.out.println(bench.getWrappedResult().toString());
    }
}

package controllers;
import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import model.BodyStats;
import model.PeriodizedStatsWrapper;

import org.junit.Ignore;
import org.junit.Test;

import play.mvc.Content;
import readers.StatsReader;
import writers.StatsWriter;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationUnitTest {
    @Test
    @Ignore
    public void renderTemplate() {
        Content html = views.html.index.render("Your new application is ready.");
        assertThat(contentType(html)).isEqualTo("text/html");
        assertThat(contentAsString(html)).contains("Your new application is ready.");
    }
    
    @Test
    public void should_have_reader_injected() throws Exception{
        assertThat(Application.reader, notNullValue());
    }
    
    @Test
    public void should_have_writer_injected() throws Exception{
        assertThat(Application.writer, notNullValue());
    }
    
    @Test
    public void reader_should_have_authenticator_injected() throws Exception{
        assertThat(Application.reader.getAuthenticator(), notNullValue());
    }
    
    @Test
    public void reader_should_have_api_injected() throws Exception{
        assertThat(Application.reader.getApi(), notNullValue());
    }

    @Test
    public void bench_should_return_weight_stats() throws Exception{
        //Given
        StatsReader currentReader = Application.reader;
        try{
            StatsReader reader = mock(StatsReader.class);
            PeriodizedStatsWrapper<BodyStats> result = new PeriodizedStatsWrapper<BodyStats>(new BodyStats(), new BodyStats());
            when(reader.readStats(any(String.class), eq(BodyStats.class))).thenReturn(result);
            Application.reader = reader;
            
            //When
            String any = "some string";
            Application.bodystatsGet(any);
            
            //Then
            verify(reader).readStats(eq(any), eq(BodyStats.class));
        } finally{
            Application.reader = currentReader;
        }
    }
    
    @Test
    public void should_write_stats() throws Exception{
        //Given
        StatsWriter currentWriter = Application.writer;
        try{
            StatsWriter writer = mock(StatsWriter.class);
            when(writer.writeStats(any(String.class), any(BodyStats.class))).thenReturn(null);
            Application.writer = writer;
    
            //When
            String any = "some string";
            BodyStats stats = new BodyStats();
            Application.bodystatsPut(any, stats);
           
            //Then
            verify(writer).writeStats(any, stats);
        } finally{
            Application.writer = currentWriter;
        }
    }
}

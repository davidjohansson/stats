package model;


import helpers.TestHelper;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class BodyStatsUnitTest {

    private final static String JSON_STRING = "{\"fett\":\"#ERROR!\",\"fasta\":\"35\",\"fett2\":\"12.4\",\"mat\":\"12\",\"bröstmage-index\":\"131.325301204819\",\"mage\":\"83\",\"nacke\":\"43\",\"träning\":\"28\",\"brost\":\"109\",\"datum\":\"2013-09-07\",\"vikt\":\"82.4\"}";
    private BodyStats bodyStats;
    private ObjectMapper mapper = new ObjectMapper();;
    
    @Before
    public void setUp() throws JsonParseException, JsonMappingException, IOException{
        bodyStats = mapper.readValue(JSON_STRING, BodyStats.class);
    }

    @Test
    public void should_set_belly() throws Exception{
        //Then
        TestHelper.assertMage(bodyStats, 83.0f);
    }
    
    @Test
    public void should_set_weight() throws Exception {
        TestHelper.assertVikt(bodyStats, 82.4f);
    }

    @Test
    public void should_set_fat() throws Exception {
        TestHelper.assertFett2(bodyStats, 12.4f);
    }

    @Test
    public void should_set_chest() throws Exception {
        TestHelper.assertBröst(bodyStats, 109f);
    }
    
    @Test
    public void should_set_neck() throws Exception {
        TestHelper.assertNacke(bodyStats, 43f);
    }
}

package model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ResourceNotFoundException;
import com.google.gdata.util.ServiceException;

public class GoogleSpreadSheetApi implements SpreadSheetApi {

    @Override
    public <T> T getDataByFirstColumnValue(String columnValue, SpreadsheetService service, Class<T> returnType) throws SpreadSheetException {
        try {
            SpreadsheetFeed feed = getSpreadSheetFeed(service);
            SpreadsheetEntry entry = getSpreadSheetEntry(feed, "apptest");
            WorksheetEntry worksheet = getFirstWorkSheetEntry(service, entry);
            ObjectNode node = getRowDataAsBodyStats(service, worksheet, columnValue);
            return Json.fromJson(node, returnType);
        } catch (MalformedURLException e) {
            throw new SpreadSheetException(e);
        } catch (IOException e) {
            throw new SpreadSheetException(e);
        } catch (ServiceException e) {
            throw new SpreadSheetException(e);
        }
    }
    
    @Override
    public Object writeDataByFirstColumnValue(String colValue,
            SpreadsheetService service, BodyStats bodyStats) throws SpreadSheetException {
        SpreadsheetFeed feed;
        try {
            feed = getSpreadSheetFeed(service);
            SpreadsheetEntry entry = getSpreadSheetEntry(feed, "apptest");
            WorksheetEntry worksheet = getFirstWorkSheetEntry(service, entry);
            writeBodyStats(service, worksheet, colValue, bodyStats);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    private ObjectNode getRowDataAsBodyStats(SpreadsheetService service,
            WorksheetEntry worksheet, String columnValue) throws IOException, ServiceException {

        // Fetch the list feed of the worksheet.
        //URL listFeedUrl = worksheet.getListFeedUrl();
        
        URL listFeedUrl;
        try {
            listFeedUrl = new URI(worksheet.getListFeedUrl().toString()
                    + "?sq=datum=" + columnValue).toURL();
            
            ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
            ObjectNode json = getBodyStatsFromFeed(listFeed);
            
            return json;
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }

    }

    private ObjectNode getBodyStatsFromFeed(ListFeed listFeed) {
        ObjectNode json = Json.newObject();
        // Iterate through each row
        for (ListEntry row : listFeed.getEntries()) {
            // Iterate over the remaining columns, and print each cell value
            System.out.println("title: " + row.getTitle().getPlainText());
            for (String tag : row.getCustomElements().getTags()) {
                String value = row.getCustomElements().getValue(tag);
                if (value != null) {
                    json.put(tag, value.replace(",", "."));
                }
            }
        }
        return json;
    }

    private WorksheetEntry getFirstWorkSheetEntry(SpreadsheetService service,
            SpreadsheetEntry entry) throws IOException, ServiceException {
        WorksheetFeed worksheetFeed = service.getFeed(
                entry.getWorksheetFeedUrl(), WorksheetFeed.class);
        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
        WorksheetEntry worksheet = worksheets.get(0);
        return worksheet;
    }

    private SpreadsheetEntry getSpreadSheetEntry(SpreadsheetFeed feed,
            String entryName) throws ResourceNotFoundException {
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();

        // Iterate through all of the spreadsheets returned
        for (SpreadsheetEntry spreadsheet : spreadsheets) {
            // Print the title of this spreadsheet to the screen
            if (entryName.equals(spreadsheet.getTitle().getPlainText())) {
                return spreadsheet;
            }
        }

        throw new ResourceNotFoundException("No spreadsheet entry called "
                + entryName);
    }

    private SpreadsheetFeed getSpreadSheetFeed(SpreadsheetService service)
            throws MalformedURLException, IOException, ServiceException {
        URL SPREADSHEET_FEED_URL = new URL(
                "https://spreadsheets.google.com/feeds/spreadsheets/private/full");

        // Make a request to the API and get all spreadsheets.
        SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
                SpreadsheetFeed.class);
        return feed;
    }

    private void writeBodyStats(SpreadsheetService service,
            WorksheetEntry worksheet, String colValue, BodyStats bodyStats) throws IOException, ServiceException, SpreadSheetException {
        URL listFeedUrl = worksheet.getListFeedUrl();
        //ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);

        // Create a local representation of the new row.
        ListEntry row = serializeToRow(bodyStats, colValue);

        // Send the new row to the API for insertion.
        row = service.insert(listFeedUrl, row);
    }

    public ListEntry serializeToRow(BodyStats bodyStats, String date) throws SpreadSheetException {
        ListEntry row = new ListEntry();
        row.getCustomElements().setValueLocal("Datum", date);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = mapper.valueToTree(bodyStats);
        
        Iterator<String> keys = jsonObject.fieldNames();
        while(keys.hasNext()){
            String key = keys.next();
            row.getCustomElements().setValueLocal(key, jsonObject.get(key).asText().replaceAll("\\.", ","));
        }
        return row;
    }
}

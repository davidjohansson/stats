package model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import model.StatsMetaData.UpdateMode;
import play.libs.Json;
import writers.SpreadSheetIntegrationData;

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

    private ExistingRowResolver rowResolver = new ExistingRowResolver();
    private NumberFormat numberFormat = NumberFormat.getInstance();
    
    public GoogleSpreadSheetApi() {
    }

    public GoogleSpreadSheetApi(ExistingRowResolver rowResolver) {
        this.rowResolver = rowResolver;
    }

    @Override
    public <T> T getDataByFirstColumnValue(String columnValue,
            Class<T> returnType, SpreadSheetIntegrationData data)
            throws SpreadSheetException {
        try {
            SpreadsheetService service = data.getService();
            SpreadsheetFeed feed = getSpreadSheetFeed(service);
            SpreadsheetEntry entry = getSpreadSheet(feed, data.getSpreadSheet());
            WorksheetEntry worksheet = getWorkSheet(service, entry, data.getWorkSheet());
            ObjectNode node = getRowDataFromColumnValue(service, worksheet,
                    columnValue);
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
    public <T> T writeDataByFirstColumnValue(String colValue,
             T stats, SpreadSheetIntegrationData data) throws SpreadSheetException {
        SpreadsheetFeed feed;
        try {
            feed = getSpreadSheetFeed(data.getService());
            SpreadsheetEntry entry = getSpreadSheet(feed, data.getSpreadSheet());
            WorksheetEntry worksheet = getWorkSheet(data.getService(), entry, data.getWorkSheet());
            writeStats(data, worksheet, colValue, stats);
        } catch (MalformedURLException e) {
            throw new SpreadSheetException(e);
        } catch (IOException e) {
            throw new SpreadSheetException(e);
        } catch (ServiceException e) {
            throw new SpreadSheetException(e);
        }
        return null;
    }

    <T> void writeStats(SpreadSheetIntegrationData data, WorksheetEntry worksheet,
            String colValue, T stats) throws IOException, ServiceException,
            SpreadSheetException {
        URL listFeedUrl = worksheet.getListFeedUrl();

        // Create a local representation of the new row.
        SpreadsheetService service = data.getService();
        ListEntry row = rowResolver.resolveRow(service, listFeedUrl, colValue);

        if (row != null) {
            serializeToRow(stats, colValue, row, data.getUpdateMode());
            row.update();
        } else {
            row = createNewRow();
            serializeToRow(stats, colValue, row, data.getUpdateMode());
            // Send the new row to the API for insertion.
            row = service.insert(listFeedUrl, row);
        }
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

    private SpreadsheetEntry getSpreadSheet(SpreadsheetFeed feed,
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

    private WorksheetEntry getWorkSheet(SpreadsheetService service,
            SpreadsheetEntry entry, String workSheetTitle) throws IOException, ServiceException {
        WorksheetFeed worksheetFeed = service.getFeed(
                entry.getWorksheetFeedUrl(), WorksheetFeed.class);
        List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
        Iterator<WorksheetEntry> iterator = worksheets.iterator();
        while(iterator.hasNext()){
            WorksheetEntry worksheet = iterator.next();
            if(worksheet.getTitle().getPlainText().equals(workSheetTitle)){
                return worksheet;
            }
        }
        throw new ServiceException("Found no worksheet called " + workSheetTitle);
    }

    private ObjectNode getRowDataFromColumnValue(SpreadsheetService service,
            WorksheetEntry worksheet, String columnValue)
            throws SpreadSheetException {
        URL url = worksheet.getListFeedUrl();
        ListFeed listFeed = rowResolver.getListFeedByFirstColumn(service, url,
                columnValue);
        ObjectNode json = getStatsFromFeed(listFeed);
        return json;
    }

    private ObjectNode getStatsFromFeed(ListFeed listFeed) {
        ObjectNode json = Json.newObject();
        // Iterate through each row
        for (ListEntry row : listFeed.getEntries()) {
            // Iterate over the remaining columns, and print each cell value
            for (String tag : row.getCustomElements().getTags()) {
                String value = row.getCustomElements().getValue(tag);
                if (value != null) {
                    json.put(tag, value.replace(",", "."));
                }
            }
        }
        return json;
    }

    private ListEntry createNewRow() {
        return new ListEntry();
    }



    public <T> ListEntry serializeToRow(T stats, String date, ListEntry row, UpdateMode updateMode)
            throws SpreadSheetException {

        
        row.getCustomElements().setValueLocal("Datum", date);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = mapper.valueToTree(stats);

        Iterator<String> keys = jsonObject.fieldNames();
        while (keys.hasNext()) {
            String key = keys.next();
            if(updateMode == UpdateMode.OVERWRITE){
                overWriteColumnValue(row, key,  jsonObject);
            } else if(updateMode == UpdateMode.ACCUMULATE){
                try {
                    accumulateRowValues(row, key, jsonObject);
                } catch (ParseException e) {
                    throw new SpreadSheetException(e);
                }
            } else{
                throw new SpreadSheetException("Don't know what to do with update mode " + updateMode);
            }
        }
        return row;
    }

    private void accumulateRowValues(ListEntry row, String key, JsonNode jsonObject) throws ParseException {
        String value = row.getCustomElements().getValue(key);
        double spreadSheetValue = 0;
        if(value != null && value.length() > 0){
            spreadSheetValue = numberFormat .parse(value).doubleValue();
        } 
        double currentObjectValue = numberFormat .parse(jsonObject.get(key).asText()).doubleValue();
        double sum = spreadSheetValue + currentObjectValue;
        String backToStringAgain = numberFormat .format(sum);
        overWriteColumnValue(row, key, backToStringAgain);
    }

    private void overWriteColumnValue(ListEntry row, String key, JsonNode jsonObject) {
        String value = jsonObject.get(key).asText().replaceAll("\\.", ",");
        overWriteColumnValue(row, key, value);
    }
    
    private void overWriteColumnValue(ListEntry row, String key, String value) {
        row.getCustomElements().setValueLocal(key, value);
    }
}

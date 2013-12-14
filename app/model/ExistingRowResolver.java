package model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.util.ServiceException;

public class ExistingRowResolver {

    public ListEntry resolveRow(SpreadsheetService service, URL url,
            String colValue) throws SpreadSheetException {
       ListFeed feed = this.getListFeedByFirstColumn(service, url,
               colValue);

       if (feed != null) {
           if (feed.getEntries().size() > 0) {
               ListEntry entry = feed.getEntries().get(0);
               if (entry != null) {
                   return entry;
               }
           }
       }
       return null;
   }
    
    public ListFeed getListFeedByFirstColumn(SpreadsheetService service,
            URL url, String columnValue)
            throws SpreadSheetException {
        try {
            URL listFeedUrl = new URI(url.toString()
                    + "?sq=datum=" + columnValue).toURL();
            ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
            return listFeed;
        } catch (MalformedURLException e) {
            throw new SpreadSheetException(e);
        } catch (URISyntaxException e) {
            throw new SpreadSheetException(e);
        } catch (IOException e) {
            throw new SpreadSheetException(e);
        } catch (ServiceException e) {
           // throw new SpreadSheetException(e);
            return null;
        }
    }

}

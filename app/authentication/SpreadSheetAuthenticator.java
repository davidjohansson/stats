package authentication;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.AuthenticationException;

public interface SpreadSheetAuthenticator {
    
    public void authenticateService(SpreadsheetService service) throws AuthenticationException;

}

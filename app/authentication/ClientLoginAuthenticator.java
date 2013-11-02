package authentication;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.util.AuthenticationException;

public class ClientLoginAuthenticator implements SpreadSheetAuthenticator {

    @Override
    public void authenticateService(SpreadsheetService service) throws AuthenticationException {
        String USERNAME = "davidsappar@gmail.com";
        String PASSWORD = "DQ7KBKACYZQ7JQLVCMXGBEU2ROZGHAH54BOKKMKV7O55JRLETO4Q";
        service.setUserCredentials(USERNAME, PASSWORD);
    }
}

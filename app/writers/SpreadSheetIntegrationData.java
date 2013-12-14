package writers;

import model.StatsMetaData.UpdateMode;

import com.google.gdata.client.spreadsheet.SpreadsheetService;

public class SpreadSheetIntegrationData {

    private final SpreadsheetService service;
    private final String spreadSheet;
    private final String workSheet;
    private final UpdateMode updateMode;
    

    public SpreadSheetIntegrationData(SpreadsheetService service,
            String spreadSheet, String workSheet, UpdateMode updateMode) {
                this.service = service;
                this.spreadSheet = spreadSheet;
                this.workSheet = workSheet;
                this.updateMode = updateMode;
    }

    public SpreadSheetIntegrationData(SpreadsheetService service,
            String spreadSheet, String workSheet) {
        this(service, spreadSheet, workSheet, UpdateMode.NONE);
    }

    public SpreadsheetService getService() {
        return service;
    }

    public String getSpreadSheet() {
        return spreadSheet;
    }

    public String getWorkSheet() {
        return workSheet;
    }
    
    public UpdateMode getUpdateMode() {
        return updateMode;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((service == null) ? 0 : service.hashCode());
        result = prime * result
                + ((spreadSheet == null) ? 0 : spreadSheet.hashCode());
        result = prime * result
                + ((workSheet == null) ? 0 : workSheet.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpreadSheetIntegrationData other = (SpreadSheetIntegrationData) obj;
        if (service == null) {
            if (other.service != null)
                return false;
        } else if (!service.equals(other.service))
            return false;
        if (spreadSheet == null) {
            if (other.spreadSheet != null)
                return false;
        } else if (!spreadSheet.equals(other.spreadSheet))
            return false;
        if (workSheet == null) {
            if (other.workSheet != null)
                return false;
        } else if (!workSheet.equals(other.workSheet))
            return false;
        return true;
    }
}

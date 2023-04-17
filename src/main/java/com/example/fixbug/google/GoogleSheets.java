package com.example.fixbug.google;

import com.example.fixbug.utils.Logger;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.Permission;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpHeaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GoogleSheets {
    private static final String TAG = GoogleSheets.class.getSimpleName();
    public static GoogleCredentials getAuthorize() {
        try {
            String pathFileClientSecret = "E:\\google-sheet_dung.json";
            FileInputStream serviceAccount = new FileInputStream(pathFileClientSecret);
            return GoogleCredentials.fromStream(serviceAccount).createScoped(SheetsScopes.all());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Logger.d(GoogleSheets.class.getSimpleName(), e.getMessage(), e);
        }
        return null;
    }

    public static String createSpreadsheet(String title) {
        try {
            GoogleCredentials credentials = getAuthorize();
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            Sheets service = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("createSpreadsheet")
                    .build();
            // Create new spreadsheet with a title
            Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));

            spreadsheet = service.spreadsheets().create(spreadsheet).setFields("*").execute();

            // Prints the new spreadsheet id
            Logger.info("Spreadsheet ID: " + spreadsheet.getSpreadsheetId());
            String link = String.format("https://docs.google.com/spreadsheets/d/%s/edit", spreadsheet.getSpreadsheetId());
            Logger.info("link: " + link);
            Spreadsheet response1 = service.spreadsheets().get(spreadsheet.getSpreadsheetId()).setIncludeGridData (false)
                    .execute ();

            String sheetsID = response1.getSheets ().get (0).getProperties ().getTitle();

            shareSpreadsheet(spreadsheet.getSpreadsheetId());
            return spreadsheet.getSpreadsheetId();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "createSpreadsheet Exception: " + e.getMessage(), e);
        }
        return null;
    }

    public static void reNameSheet(String spreadsheetId) {
        try {
            GoogleCredentials credentials = getAuthorize();
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            Sheets sheetsService = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("newSheetSpreadsheet")
                    .build();
            UpdateSheetPropertiesRequest updateSheetPropertiesRequest = new UpdateSheetPropertiesRequest()
                    .setProperties(new SheetProperties().setTitle("New Sheet Name"))
                    .setFields("title");

            // Define the request to update the spreadsheet
            Request request = new Request()
                    .setUpdateSheetProperties(updateSheetPropertiesRequest);
            BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                    .setRequests(Arrays.asList(request));
            sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "newSheetSpreadsheet Exception: " + e.getMessage(), e);
        }
    }

    public static void newSheet(String spreadsheetId) {
        try {
            GoogleCredentials credentials = getAuthorize();
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            Sheets sheetsService = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("newSheetSpreadsheet")
                    .build();

            // Define the request to add a new sheet
            AddSheetRequest addSheetRequest = new AddSheetRequest()
                    .setProperties(new SheetProperties().setTitle("New Sheet Name"));

            // Define the request to update the spreadsheet
            Request request = new Request().setAddSheet(addSheetRequest);
            BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest()
                    .setRequests(Arrays.asList(request));

            // Execute the request to add the new sheet
            sheetsService.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "newSheetSpreadsheet Exception: " + e.getMessage(), e);
        }
    }

    public static ValueRange getData(GoogleCredentials credentials, String spreadsheetId, String range) {
        try {
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
            Sheets service = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("getDataSpreadsheet")
                    .build();

//            String range = "A1:F10";
            ValueRange response = service.spreadsheets().values().get(spreadsheetId, range).execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.isEmpty()) {
                System.out.println("no data found");
                Logger.warning(TAG, "no data found");
            }
            return response;
        } catch (Exception e ) {
            e.printStackTrace();
            Logger.error(TAG, "getDataSpreadsheet Exception: " + e.getMessage(), e);
        }
        return null;
    }

    public static void fillAllData(String spreadsheetId, List<List<Object>> data) {
        try {
            GoogleCredentials credentials = getAuthorize();
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            Sheets service = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("fillDataSpreadsheet")
                    .build();

            Spreadsheet response1 = service.spreadsheets().get(spreadsheetId).setIncludeGridData(false).execute();

            String sheetsID = response1.getSheets().get(0).getProperties().getTitle();
            ValueRange appendBody = new ValueRange().setValues(data);
            AppendValuesResponse appendResult = service.spreadsheets().values().append(spreadsheetId, sheetsID, appendBody).setValueInputOption("USER_ENTERED")
                    .setInsertDataOption("INSERT_ROWS").setIncludeValuesInResponse(true).execute();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "fillData Exception: " + e.getMessage(), e);
        }
    }

    public static void fillData(GoogleCredentials credentials, String spreadsheetId, String... data) {
        fillData(credentials, spreadsheetId, Arrays.asList(data));
    }

    public static void fillData(GoogleCredentials credentials, String spreadsheetId, List<Object> data) {
        try {
            List<List<Object>> dataImport = new ArrayList<>();
            dataImport.add(data);
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            Sheets service = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("fillDataSpreadsheet")
                    .build();

            Spreadsheet response1 = service.spreadsheets().get(spreadsheetId).setIncludeGridData(false).execute();

            String sheetsID = response1.getSheets().get(0).getProperties().getTitle();
            ValueRange appendBody = new ValueRange().setValues(dataImport);
            AppendValuesResponse appendResult = service.spreadsheets().values().append(spreadsheetId, sheetsID, appendBody).setValueInputOption("USER_ENTERED")
                    .setInsertDataOption("INSERT_ROWS").setIncludeValuesInResponse(true).execute();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "fillData Exception: " + e.getMessage(), e);
        }
    }

    public static void fillData(GoogleCredentials credentials, String spreadsheetId, int rowNumber, int columnNumber , String value) {
        try {
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            Sheets service = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("fillDataSpreadsheet")
                    .build();

            Spreadsheet spreadsheet = service.spreadsheets().get(spreadsheetId).setIncludeGridData(false).execute();
            String sheetName = spreadsheet.getSheets().get(0).getProperties().getTitle();
            int sheetID = spreadsheet.getSheets().get(0).getProperties().getSheetId();
            List<Request> requests = new ArrayList<>();
            requests.add(new Request()
                    .setUpdateCells(new UpdateCellsRequest()
                            .setStart(new GridCoordinate()
                                    .setSheetId(sheetID)
                                    .setRowIndex(rowNumber - 1)
                                    .setColumnIndex(columnNumber - 1))
                            .setRows(Collections.singletonList(
                                    new RowData().setValues(Collections.singletonList(
                                            new CellData().setUserEnteredValue(new ExtendedValue().setStringValue(value))
                                    ))
                            ))
                            .setFields("userEnteredValue")));

            BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest().setRequests(requests);
            service.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();


        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "fillData Exception: " + e.getMessage(), e);
        }
    }

    public static void clearAllData(GoogleCredentials credentials, String spreadsheetId, String range) {
        try{
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
            Sheets service = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("fillDataSpreadsheet")
                    .build();

            Spreadsheet spreadsheet = service.spreadsheets().get(spreadsheetId).setIncludeGridData(false).execute();
            String sheetName = spreadsheet.getSheets().get(0).getProperties().getTitle();
            int sheetID = spreadsheet.getSheets().get(0).getProperties().getSheetId();
            String ranges = sheetName + "!" + range;
            service.spreadsheets().values().clear(spreadsheetId, ranges, new ClearValuesRequest()).execute();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "clearAllData Exception: " + e.getMessage(), e);
        }

    }

    public static void insertNewColumn(GoogleCredentials credentials, String spreadsheetId, int columnIndex) {
        // Tạo một ColumnInsertRequest để chèn cột mới
        try {
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
            Sheets service = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("fillDataSpreadsheet")
                    .build();
            Spreadsheet spreadsheet = service.spreadsheets().get(spreadsheetId).setIncludeGridData(false).execute();
            int sheetID = spreadsheet.getSheets().get(0).getProperties().getSheetId();
            List<Request> requests = new ArrayList<>();
            requests.add(new Request()
                    .setInsertDimension(new InsertDimensionRequest()
                            .setRange(new DimensionRange()
                                    .setSheetId(sheetID)
                                    .setDimension("COLUMNS")
                                    .setStartIndex(columnIndex)
                                    .setEndIndex(columnIndex + 1))));

            // Thực hiện các yêu cầu với Google Sheets API
            BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
            service.spreadsheets().batchUpdate(spreadsheetId, body).execute();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "insertNewColumn Exception: " + e.getMessage(), e);
        }
    }

    public void deleteColumn(GoogleCredentials credentials, String spreadsheetId, int columnIndex) {
        // Tạo một DeleteDimensionRequest để xóa cột
        try {
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
            Sheets service = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("fillDataSpreadsheet")
                    .build();
            Spreadsheet spreadsheet = service.spreadsheets().get(spreadsheetId).setIncludeGridData(false).execute();

            int sheetID = spreadsheet.getSheets().get(0).getProperties().getSheetId();
            List<Request> requests = new ArrayList<>();
            requests.add(new Request()
                    .setDeleteDimension(new DeleteDimensionRequest()
                            .setRange(new DimensionRange()
                                    .setSheetId(sheetID)
                                    .setDimension("COLUMNS")
                                    .setStartIndex(columnIndex)
                                    .setEndIndex(columnIndex + 1))));

            // Thực hiện các yêu cầu với Google Sheets API
            BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
            service.spreadsheets().batchUpdate(spreadsheetId, body).execute();

        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "insertNewColumn Exception: " + e.getMessage(), e);
        }
    }

    public static void shareSpreadsheet(String spreadsheetId) {
        try {
            GoogleCredentials credentials = getAuthorize();
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);
            Drive drive = new Drive.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(), requestInitializer)
                    .setApplicationName("Drive samples")
                    .build();

            Sheets sheets = new Sheets.Builder(new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName("shareSpreadsheet")
                    .build();

            // Retrieve the Google Sheet by its ID
            Spreadsheet spreadsheet = sheets.spreadsheets().get(spreadsheetId).execute();

            // Share the Google Sheet with all users
            Permission permission = new Permission()
                    .setType("anyone")
                    .setRole("writer")
                    .setAllowFileDiscovery(false);
            drive.permissions().create(spreadsheetId, permission).setSendNotificationEmail(false).execute();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG, "shareSpreadsheet Exception: " + e.getMessage(), e);
        }
    }


    public static List<String> shareFile(GoogleCredentials credentials, String realFileId, String realUser, String realDomain) throws IOException {

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        // Build a new authorized API client service.
        Drive service = new Drive.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("Drive samples")
                .build();

        final List<String> ids = new ArrayList<>();


        JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
            @Override
            public void onFailure(GoogleJsonError e,
                                  HttpHeaders responseHeaders)
                    throws IOException {
                System.err.println(e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission,
                                  HttpHeaders responseHeaders)
                    throws IOException {
                System.out.println("Permission ID: " + permission.getId());

                ids.add(permission.getId());

            }
        };
        BatchRequest batch = service.batch();
        Permission userPermission = new Permission()
                .setType("anyone")
                .setRole("reader");
        try {
            service.permissions().create(realFileId, userPermission).queue(batch, callback);
            return ids;
        } catch (GoogleJsonResponseException e) {
            System.err.println("Unable to modify permission: " + e.getDetails());
            throw e;
        }
    }


}

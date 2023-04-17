//package com.example.fixbug.google;
//
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.model.Permission;
//import com.google.api.services.sheets.v4.Sheets;
//
//import com.google.api.services.sheets.v4.model.*;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//
//public class shraeGoogleSheets {
//
//
//    public void share(Sheets sheets, String title) {
//        try {
//
//
//            // Retrieve the Google Sheet by its ID
//            String spreadsheetId = "your-spreadsheet-id";
//            Spreadsheet spreadsheet = sheets.spreadsheets().get(spreadsheetId).execute();
//
//            // Share the Google Sheet with all users
//            List<Permission> newPermissions = new ArrayList<>();
//            Permission permission = new Permission()
//                    .setType("anyone")
//                    .setRole("writer");
//            newPermissions.add(permission);
//            SpreadsheetProperties properties = new SpreadsheetProperties();
//            properties.setTitle(title);
//
//            BatchUpdateSpreadsheetRequest batchUpdateRequest = new BatchUpdateSpreadsheetRequest();
//            batchUpdateRequest.setRequests(Collections.singletonList(
//                    new Request().setUpdateSpreadsheetProperties(new UpdateSpreadsheetPropertiesRequest()
//                            .setFields("*")
//                            .setProperties(properties)
//                            //new SpreadsheetProperties().setSharingMode("anyone")
//                    )
//            ));
//            sheets.spreadsheets().batchUpdate(spreadsheetId, batchUpdateRequest).execute();
//
//// Add the new permissions to the spreadsheet
//            BatchUpdateSpreadsheetRequest batchUpdatePermissionsRequest = new BatchUpdateSpreadsheetRequest()
//                    .setRequests(new ArrayList<>());
//            for (Permission p : newPermissions) {
//                CreatePermissionRequest createPermissionRequest = new CreatePermissionRequest();
//                batchUpdatePermissionsRequest.getRequests().add(new Request().setCreatePermission(new CreatePermissionRequest()
//                                .setType(p.getType())
//                                .setRole(p.getRole())
//                                .setEmailAddress(p.getEmailAddress())
//                        )
//                );
//            }
//            sheets.spreadsheets().batchUpdate(spreadsheetId, batchUpdatePermissionsRequest).execute();
//
//        } catch (Exception exception) {
//
//        }
//
//        Sheets sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//
//// Retrieve the Google Sheet by its ID
//        String spreadsheetId = "your-spreadsheet-id";
//        Spreadsheet spreadsheet = sheets.spreadsheets().get(spreadsheetId).execute();
//
//// Share the Google Sheet with all users
//        Permission permission = new Permission()
//                .setType("anyone")
//                .setRole("writer")
//                .setAllowFileDiscovery(false);
//        drive.permissions().create(spreadsheetId, permission).setSendNotificationEmail(false).execute();
//
//    }
//
//}

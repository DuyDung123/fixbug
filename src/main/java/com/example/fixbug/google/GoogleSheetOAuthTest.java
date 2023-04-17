package com.example.fixbug.google;

import com.example.fixbug.utils.Logger;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.auth.http.HttpCredentialsAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class GoogleSheetOAuthTest {
    public Credential authorize() {
        try {
            NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            // build GoogleClientSecrets from JSON file
            // Load client secrets.
            String pathFileClientSecret = "E:\\client_secret.json";
            InputStream in = Files.newInputStream(Paths.get(pathFileClientSecret));
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(GsonFactory.getDefaultInstance(), new InputStreamReader(in));

            // build Credential object
            // set up authorization code flow
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, GsonFactory.getDefaultInstance(), clientSecrets, Collections.singleton(SheetsScopes.SPREADSHEETS))
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("data/tokens")))
                    .setAccessType("offline")
                    .build();
            // authorize
            AuthorizationCodeInstalledApp app = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver());
            return app.authorize("user");
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public void create() {
        Credential credential = authorize();
        try {
            Sheets service = new Sheets.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), credential).setApplicationName("Google Sheets Example").build();
            String title = "My file " + Calendar.getInstance().getTime();
            Spreadsheet spreadsheet = new Spreadsheet().setProperties(new SpreadsheetProperties().setTitle(title));
            spreadsheet = service.spreadsheets().create(spreadsheet).execute();
            Logger.info("Spreadsheet ID: " + spreadsheet.getSpreadsheetId());
            String link = String.format("https://docs.google.com/spreadsheets/d/%s/edit", spreadsheet.getSpreadsheetId());
            Logger.info("Spreadsheet Link: " + link);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

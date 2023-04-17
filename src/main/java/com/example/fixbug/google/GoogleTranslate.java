package com.example.fixbug.google;

import com.google.api.gax.core.CredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.v3.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GoogleTranslate {
    public List<String> googleTranslate(String pathFileClientSecret, String projectID, List<String> listTextTranslate, String targetLanguageCode) throws IOException {
        List<String> resTranslateText = new ArrayList<>();
        TranslationServiceSettings settings = TranslationServiceSettings.newBuilder().setCredentialsProvider(new CredentialsProvider() {
            @Override
            public Credentials getCredentials() throws IOException {

                return GoogleCredentials.fromStream(new FileInputStream(pathFileClientSecret))
                        .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));
            }
        }).build();
        TranslationServiceClient translationServiceClient = TranslationServiceClient.create(settings);
        LocationName parent = LocationName.of(projectID, "global");
        TranslateTextRequest request =
                TranslateTextRequest.newBuilder()
                        .setParent(parent.toString())
                        .setMimeType("text/plain")
                        .setTargetLanguageCode(targetLanguageCode).addAllContents(listTextTranslate)
                        .build();

        TranslateTextResponse response = translationServiceClient.translateText(request);
        for (Translation translation : response.getTranslationsList()) {
            resTranslateText.add(translation.getTranslatedText());
        }
        return resTranslateText;
    }
}

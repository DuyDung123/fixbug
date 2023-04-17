package com.example.fixbug.google;

import com.example.fixbug.utils.Logger;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.util.Preconditions;
import sun.awt.resources.awt;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;

public class AuthorizationCodeInstalledAppCusTom {

    private static final String TAG = AuthorizationCodeInstalledAppCusTom.class.getSimpleName();

    public static interface Browser {

        public void browse(String url) throws IOException;
    }


    public static class DefaultBrowser implements Browser {

        @Override
        public void browse(String url) throws IOException {
            AuthorizationCodeInstalledAppCusTom.browse(url);
        }
    }

    private final AuthorizationCodeFlow flow;

    private final VerificationCodeReceiver receiver;

    private final Browser browser;
    public AuthorizationCodeInstalledAppCusTom(
            AuthorizationCodeFlow flow, VerificationCodeReceiver receiver) {
        this(flow, receiver, new DefaultBrowser());
    }

    public AuthorizationCodeInstalledAppCusTom(
            AuthorizationCodeFlow flow, VerificationCodeReceiver receiver, Browser browser) {
        this.flow = Preconditions.checkNotNull(flow);
        this.receiver = Preconditions.checkNotNull(receiver);
        this.browser = browser;
    }

    public Credential authorize(String userId) throws IOException {
        try {
            Credential credential = flow.loadCredential(userId);
            if (credential != null
                    && (credential.getRefreshToken() != null
                    || credential.getExpiresInSeconds() == null
                    || credential.getExpiresInSeconds() > 60)) {
                return credential;
            }
            // open in browser
            String redirectUri = receiver.getRedirectUri();
            AuthorizationCodeRequestUrl authorizationUrl =
                    flow.newAuthorizationUrl().setRedirectUri(redirectUri);
            onAuthorization(authorizationUrl);
            // receive authorization code and exchange it for an access token
            String code = receiver.waitForCode();
            TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectUri).execute();
            // store credential and return it
            return flow.createAndStoreCredential(response, userId);
        } finally {
            receiver.stop();
        }
    }

    protected void onAuthorization(AuthorizationCodeRequestUrl authorizationUrl) throws IOException {
        String url = authorizationUrl.build();
        Preconditions.checkNotNull(url);
        browser.browse(url);
    }

    public static void browse(String url) {
        Preconditions.checkNotNull(url);
        // Ask user to open in their browser using copy-paste
        System.out.println("Please open the following address in your browser:");
        System.out.println(" => " + url);
        System.setSecurityManager(new SecurityManager());
        // Attempt to open it in the browser
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    System.out.println("Attempting to open that address in the default browser now...");
                    desktop.browse(URI.create(url));
                }
            }
        } catch (IOException e) {
            Logger.error(  "Unable to open browser", e);
        } catch (InternalError e) {
            Logger.error( "Unable to open browser", e);
        }


    }


    public final AuthorizationCodeFlow getFlow() {
        return flow;
    }


    public final VerificationCodeReceiver getReceiver() {
        return receiver;
    }
}

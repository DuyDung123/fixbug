package com.example.fixbug.oauth2;

import java.util.Map;

import java.util.logging.Logger;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslClientFactory;

public class OAuth2SaslClientFactory implements SaslClientFactory {
    private static final Logger logger =
            Logger.getLogger(OAuth2SaslClientFactory.class.getName());

    public static final String OAUTH_TOKEN_PROP =
            "mail.imaps.sasl.mechanisms.oauth2.oauthToken";

    public SaslClient createSaslClient(String[] mechanisms,
                                       String authorizationId,
                                       String protocol,
                                       String serverName,
                                       Map<String, ?> props,
                                       CallbackHandler callbackHandler) {
        boolean matchedMechanism = false;
        for (int i = 0; i < mechanisms.length; ++i) {
            if ("XOAUTH2".equalsIgnoreCase(mechanisms[i])) {
                matchedMechanism = true;
                break;
            }
        }
        if (!matchedMechanism) {
            logger.info("Failed to match any mechanisms");
            return null;
        }
        return (SaslClient) new OAuth2SaslClient((String) props.get(OAUTH_TOKEN_PROP),
                callbackHandler);
    }

    public String[] getMechanismNames(Map<String, ?> props) {
        return new String[] {"XOAUTH2"};
    }
}

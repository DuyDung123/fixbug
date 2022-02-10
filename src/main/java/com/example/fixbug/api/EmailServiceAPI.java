package com.example.fixbug.api;

import com.example.fixbug.ErrorCallback;
import com.example.fixbug.api.mail.IMailService;
import com.example.fixbug.api.mail.response.ListEmailResponse;
import com.example.fixbug.api.mail.response.MailRefreshTokenResponse;
import com.example.fixbug.api.mail.response.MessageEmailResponse;
import com.example.fixbug.api.requesthelper.RequestHelper;
import com.example.fixbug.api.requesthelper.ResponseAPI;
import com.example.fixbug.objects.EmailObject;
import com.example.fixbug.utils.CustomLogger;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmailServiceAPI {

    private final CustomLogger logger = new CustomLogger(EmailServiceAPI.class.getSimpleName());

    public void refreshToken(String clientId, String clientSecret, String refreshToken, ResponseAPI<MailRefreshTokenResponse> callBack){
        Map<String, String> map = new HashMap<>();
        map.put("client_id", clientId);
        map.put("client_secret", clientSecret);
        map.put("refresh_token", refreshToken);
        map.put("grant_type", "refresh_token");

        RequestHelper.executeSyncRequest(IMailService.SERVICE_READ_EMAIL.refreshToken(map), callBack);
    }

    public List<EmailObject> getListEmail(String token, String email, long after, long before, ErrorCallback errorCallback) {
        String q = "in:google after:" + after + " before:" + before;
        Map<String, String> map = new HashMap<>();
        //map.put("labelIds", "INBOX");
        map.put("q", q);

        List<EmailObject> emailObjects = new ArrayList<>();
        RequestHelper.executeSyncRequest(IMailService.SERVICE_READ_EMAIL.getListEmail("Bearer " + token, email, map), new ResponseAPI<ListEmailResponse>() {
            @Override
            public void onSuccess(ListEmailResponse response, int code) {
                for (ListEmailResponse.Message message : response.getMessages()) {
                    RequestHelper.executeSyncRequest(IMailService.SERVICE_READ_EMAIL.getMessageEmail("Bearer " + token, email, message.getId()), new ResponseAPI<MessageEmailResponse>() {
                        @Override
                        public void onSuccess(MessageEmailResponse response, int code) {
                            emailObjects.add(getTextFromMessage(response));
                        }

                        @Override
                        public void onFailure(int codeError, String message) {
                            logger.error("#getListEmail getMessageEmail - onFailure email: " + email + " codeError: " + codeError + " mess: " + message);
                            if (errorCallback != null){
                                errorCallback.error(message, null);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(int code, String message) {
                logger.error("#getListEmail - onFailure email: " + email + " codeError: " + code + " mess: " + message);
                if (errorCallback != null){
                    errorCallback.error(message, null);
                }
            }
        });
        return emailObjects;
    }

    private static EmailObject getTextFromMessage(MessageEmailResponse messageEmailResponse) {
        MessageEmailResponse.Payload payload = messageEmailResponse.getPayload();
        EmailObject emailObject = new EmailObject();
        if (payload.getMimeType().contains("multipart/")) {

            payload.getHeaders().forEach((element) -> {
                if (element.getName().equals("From")) emailObject.setFrom(element.getValue());
                if (element.getName().equals("Subject")) emailObject.setTitle(element.getValue());
                if (element.getName().equals("Subject")) emailObject.setTitle(element.getValue());
            });
            for (MessageEmailResponse.Part part : payload.getParts()) {
                String res = getTextFromMimeMultipart(part);
                if (!res.equals("")) {
                    emailObject.setContent(res);
                    break;
                }
            }
        }
        return emailObject;
    }

    private static String getTextFromMimeMultipart(MessageEmailResponse.Part part) {
        String result = "";
        try {
            if (part.getMimeType().equals("text/plain")) {
                result = result + "\n" + getContent(part.getBody().getData());
            } else if (part.getMimeType().equals("text/html")) {
                String html = getContent(part.getBody().getData());
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            }
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    private static String getContent(String data) {
        byte[] valueDecoded = Base64.decodeBase64(data.getBytes());
        return new String(valueDecoded);
    }
}

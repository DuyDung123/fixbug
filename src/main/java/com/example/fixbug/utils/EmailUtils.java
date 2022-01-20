package com.example.fixbug.utils;

import com.example.fixbug.api.mail.response.MessageEmailResponse;
import com.example.fixbug.objects.EmailObject;
import org.apache.commons.codec.binary.Base64;

public class EmailUtils {

    public static EmailObject getTextFromMessage(MessageEmailResponse messageEmailResponse) {
        MessageEmailResponse.Payload payload = messageEmailResponse.getPayload();
        EmailObject emailObject = new EmailObject();
        emailObject.setSentDate(messageEmailResponse.getInternalDate());
         if (payload.getMimeType().equals("multipart/alternative")) {

            payload.getHeaders().forEach((element)->{
                if (element.getName().equals("From")) emailObject.setFrom(element.getValue());
                if (element.getName().equals("Subject")) emailObject.setTitle(element.getValue());
                if (element.getName().equals("Subject")) emailObject.setTitle(element.getValue());
            });
            for (MessageEmailResponse.Part part : payload.getParts()){
                String res = getTextFromMimeMultipart(part);
                if (!res.equals("")){
                    emailObject.setContent(res);
                    break;
                }
            }
        }
        return emailObject;
    }

    private static String getTextFromMimeMultipart(MessageEmailResponse.Part part) {
        String result = "";
        try{
                if (part.getMimeType().equals("text/plain")) {
                    result = result + "\n" + getContent(part.getBody().getData());
                } else if (part.getMimeType().equals("text/html")) {
                    String html = getContent(part.getBody().getData());
                    result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
                }
            return result;
        }catch (Exception e){
            return result;
        }
    }

    private static String getContent(String data){
        byte[] valueDecoded = Base64.decodeBase64(data.getBytes());
        String res =  new String(valueDecoded);
        return res;
    }
}

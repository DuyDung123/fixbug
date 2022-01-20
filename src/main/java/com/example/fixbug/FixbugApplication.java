package com.example.fixbug;

import com.example.fixbug.api.mail.IMailService;
import com.example.fixbug.api.mail.response.ListEmailResponse;
import com.example.fixbug.api.mail.response.MailRefreshTokenResponse;
import com.example.fixbug.api.mail.response.MessageEmailResponse;
import com.example.fixbug.api.requesthelper.RequestHelper;
import com.example.fixbug.api.requesthelper.ResponseAPI;
import com.example.fixbug.objects.EmailObject;
import com.example.fixbug.singleton.DesignSingleton;
import com.example.fixbug.utils.EmailModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import retrofit2.Call;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FixbugApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FixbugApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
       //readMail();
        //refreshToken();
        //readMail1();
        //getStringOrder(content,"商品名");
//        for (int i = 0; i<= 5;i++){
//            String str = DesignSingleton.getInstance().callStr(i);
//            System.out.println(str);
//            Thread.sleep(1000);
//            if (i == 5) {
//                i = 0;
//            }
//        }
        String token = "Bearer ya29.A0ARrdaM8eRRf9u-o7AuvG2h_W2PUkmN4SIxFVxVwl45Tq9QB6xdpCB6Tsvi4gv_xhmqE3jxJVDWgSLwjKp4JQnUpZZIxxnmM1A4Lalz3ZnQnRP37Rqn4ODW9YkqL0-BhvhJzDQQSPh1uNWVaeEy1vaNgYU3aS";
        readMailTokenApi("dinhvandung791@gmail.com", token, 1642417367, System.currentTimeMillis());
    }

    private void readMailTokenApi(String email, String token, long after, long before){
        String q = "in:inbox after:" +after + " before:" + before;
        Map<String, String> map = new HashMap<>();
        map.put("labelIds","INBOX");
        map.put("q",q);
        final ListEmailResponse[] listEmailResponse = {new ListEmailResponse()};
        RequestHelper.executeSyncRequest(IMailService.SERVICE_READ_EMAIL.getListEmail(token, email, map), new ResponseAPI<ListEmailResponse>() {
            @Override
            public void onSuccess(ListEmailResponse response, int code) {
                for (ListEmailResponse.Message message : response.getMessages()){
                    RequestHelper.executeSyncRequest(IMailService.SERVICE_READ_EMAIL.getMessageEmail(token, email, message.getId()), new ResponseAPI<MessageEmailResponse>() {
                        @Override
                        public void onSuccess(MessageEmailResponse response, int code) {
                            MessageEmailResponse.Body data = response.getPayload().getParts().get(1).getBody();
                        }

                        @Override
                        public void onFailure(int code, String message) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(int code, String message) {

            }
        });

        //System.out.print(listEmailResponse[0].getMessages().get(0).getId());
    }


    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) {
        String result = "";
        try{ int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                if (bodyPart.isMimeType("text/plain")) {
                    result = result + "\n" + bodyPart.getContent();
                    break; // without break same text appears twice in my tests
                } else if (bodyPart.isMimeType("text/html")) {
                    String html = (String) bodyPart.getContent();
                    result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
                } else if (bodyPart.getContent() instanceof MimeMultipart) {
                    result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
                }
            }
            return result;
        }catch (MessagingException | IOException e){
            return result;
        }
    }



    private void refreshToken(){
        Map<String, String> map = new HashMap<>();
        map.put("client_id","84058038785-e1i3kjj5cqc7jgupnuofn6fv8jn0rbvg.apps.googleusercontent.com");
        map.put("client_secret","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");
        map.put("refresh_token","1//0e1oE94C6j6VuCgYIARAAGA4SNwF-L9IrqRZVAz8t4KVMypbKSob8RmoZmV97tfQp5_8nm5cCi7_CJSJyJ5hKQs4ZEs3DHVZ17Hg");
        map.put("grant_type","refresh_token");
        RequestHelper.executeSyncRequest(IMailService.SERVICE.refreshToken(map), new ResponseAPI<MailRefreshTokenResponse>() {
            @Override
            public void onSuccess(MailRefreshTokenResponse response, int code) {
                System.out.println("onSuccess");
                System.out.println(response.getAccessToken());
            }

            @Override
            public void onFailure(int code, String message) {
                System.out.println("onFailure: "+ code + " message: "+ message);
            }
        });

    }

    private void readMail1(){
        String host = "imap.gmail.com";
        String port = "993";
        String mailStoreType = "imap";
        String email = "dinhvandung791@gmail.com";
        String password = "wpksfzixssntnyjy";
        Date lastDate = new Timestamp(1642411372);
        SearchTerm startDateTearm = new ReceivedDateTerm(ComparisonTerm.GE, lastDate);
        List<EmailObject> emailObjects = EmailModel.readEmail(host, port, email, password, startDateTearm);
        for (EmailObject item : emailObjects){
            System.out.println(item.getTitle());
            Date lastDates = new Timestamp(item.getSentDate());
            System.out.println(lastDates);
            System.out.println(item.getFrom());
            System.out.println(item.getContent());
            System.out.println("---------------------------------------------------------------------------------------------End");
        }
    }


    private void readMail() {
        String host = "imap.gmail.com";
        String port = "993";
        String mailStoreType = "imap";
        String email = "dinhvandung791@gmail.com";
        String email1 = "dinh66539@gmail.com";
        String email2 = "huannvtd98@gmail.com";
        String email3 = "thomn0632@gmail.com";
        String password = "wpksfzixssntnyjy";
        String token = "ya29.A0ARrdaM_Jongx6Ucdf256XRoQ4p3fs-xQyt-klUHvMx0T739-V2mCmlCT_4zBnZ-Bupjn8YFUwz-3DFpheLJp2_gkAK_I_UfFGMI3Fy00iDUkQuOn573zW00Uo_3VcFK5qVSTW01Jepd8jQM3-OihxPf7SJBE";
        String token1 = "ya29.A0ARrdaM8jZfiXTJH-oThePeX9dwCmbPORoTxRdaq-nl9iAgYYrBP8o9jImm44hFgvXyuGrL5H-qMBtyWYpOdL-0fJ7ZU5q70bYfB5B4SoxV615qOPd-CtPCca-NG84-73kO8e9pve-NTwe0jmfCFcn9V1XE_R";
        String token2 = "ya29.A0ARrdaM-5HXtla1XaBiD8RI0K1rcJV8xmvCdVovu_Z0glxEnrI4Myl7OD7FyKvQ0JzDnC7lmLs1oe_F2oCMOn3c-tau-yiH9ZgcsTBqEQ8tgMUF9mgu9PHoIlp2luRKN50EnQWKNtu2jG8P-RfKMKa4IBlVRZxA";
        String token3 = "ya29.A0ARrdaM9dRsioRCeMm0u19RjqAcD8h_4fRrFuw35y6zQXQsVseV5hPjU8PrRbA6v5UOf3Efp3B406HYCPQ8-j-rCxzVd-sVsjU2FdMNMtriguCFUK5_P-mTiGJ7hlVgXn55fXjf4_xMbPFFbOV1svWoK7AmdO";

        Date lastDate = new Timestamp(System.currentTimeMillis());
        SearchTerm startDateTearm = new ReceivedDateTerm(ComparisonTerm.GE, lastDate);
//        EmailModel.connectToImap(host, 993, email, token, true);
//        List<EmailObject> emailObjects = EmailModel.readEmailPassword(host,"993", email, password, startDateTearm, true);
        List<EmailObject> emailObjects = EmailModel.readEmail(host ,port, email, password, token, startDateTearm, (mess, e)->{
            System.out.println(mess);
        });
        for (EmailObject item : emailObjects){
            System.out.println(item.getTitle());
            System.out.println(item.getFrom());
            System.out.println(item.getContent());
            System.out.println("---------------------------------------------------------------------------------------------End");
        }
    }

    private void showLog(){

    }

    String content = "メルカリShopsをご利用いただきありがとうございます。下記の商品が購入されました。商品の発送をお願いします。\n" +
            "\n" +
            "▼商品情報\n" +
            "注文番号 : order_abH9RUTiBqujEHLKwjdz2a\n" +
            "商品名 : 新品Vignac 4本セット ネクタイピン メンズ ファッション シンプル 日常\n" +
            "商品価格 : ¥2,434\n" +
            "\n" +
            "▼配送先情報\n" +
            "購入者の配送先情報は以下のリンクよりご確認ください。\n" +
            "購入者の配送先情報を確認する\n" +
            "※ご注文の詳細はショップ管理画面の取引一覧からご確認いただけます\n" +
            "※ご注文の確認にはメルカリアプリのダウンロードが必要です\n" +
            "※取引一覧へは以下のガイドをご確認ください\n" +
            "https://merc.li/KdY2vU9Ma\n" +
            "\n" +
            "※お問い合わせの際はショップ管理画面「お問い合わせ」からお願いいたします\n" +
            "\n" +
            "※本メールを含む重要なメールは、配信停止いただけません\n" +
            "※このメールアドレスは送信専用です。ご返信いただいても対応できませんので、ご了承ください\n" +
            "\n" +
            "ーーーーーーーーーー\n" +
            "株式会社ソウゾウ\n" +
            "ーーーーーーーーーー\n" +
            "※株式会社ソウゾウはメルカリShopsを運営しています ";

    private static String getStringOrder(String input, String firstName) {
        int index = input.indexOf(firstName) + firstName.length();
        String res = "";
        while (index < input.length()) {
            Character a = input.charAt(index);
            if (a.equals('\n') && !isNumberic(a + "")) {
                break;
            }
            res = res + a;
            index++;
        }
        return res;
    }

    public static boolean isNumberic(String s) {
        if (isEmtpy(s))
            return false;
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) > '9' || s.charAt(i) < '0')
                return false;
        return true;
    }

    public static boolean isEmtpy(String text) {
        if (text == null || text.isEmpty())
            return true;
        return false;
    }
}

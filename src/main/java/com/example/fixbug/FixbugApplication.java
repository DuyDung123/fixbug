package com.example.fixbug;

import com.example.fixbug.api.EmailServiceAPI;
import com.example.fixbug.api.mail.IMailService;
import com.example.fixbug.api.mail.response.MailRefreshTokenResponse;
import com.example.fixbug.api.requesthelper.RequestHelper;
import com.example.fixbug.api.requesthelper.ResponseAPI;
import com.example.fixbug.objects.EmailObject;
import com.example.fixbug.utils.EmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication
public class FixbugApplication implements CommandLineRunner {

    @Autowired
    EmailServiceAPI emailServiceAPI;

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
        String token = "ya29.A0ARrdaM9-IzoJHFQTTPIzXv-_zdUVxAO77uTXapZEyxJVlv03JEA048Xe85MMq_FL_xYL6atr6airHGepJsxVFbMCAe8QTDdbcMEFDmklBoZLWx8LFwV4UVGQiPdJOYA0gWPzNbSICpytEf1iobKJEBMyLR-h";
        readMailTokenApi("dinhvandung791@gmail.com", token, 1642417367, System.currentTimeMillis());
    }

    private void readMailTokenApi(String email, String token, long after, long before){

        List<EmailObject> emailObjects = emailServiceAPI.getListEmail(token, email, after, before, null);
        for (EmailObject item: emailObjects){
            String orderNumber = getStringOrder(item.getContent(), "[注文番号] ");
            System.out.println(orderNumber);
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
        String password = "wpksfzixssntnyjys";
        String token = "ya29.A0ARrdaM_Jongx6Ucdf256XRoQ4p3fs-xQyt-klUHvMx0T739-V2mCmlCT_4zBnZ-Bupjn8YFUwz-3DFpheLJp2_gkAK_I_UfFGMI3Fy00iDUkQuOn573zW00Uo_3VcFK5qVSTW01Jepd8jQM3-OihxPf7SJBE";
        String token1 = "ya29.A0ARrdaM8jZfiXTJH-oThePeX9dwCmbPORoTxRdaq-nl9iAgYYrBP8o9jImm44hFgvXyuGrL5H-qMBtyWYpOdL-0fJ7ZU5q70bYfB5B4SoxV615qOPd-CtPCca-NG84-73kO8e9pve-NTwe0jmfCFcn9V1XE_R";
        String token2 = "ya29.A0ARrdaM-5HXtla1XaBiD8RI0K1rcJV8xmvCdVovu_Z0glxEnrI4Myl7OD7FyKvQ0JzDnC7lmLs1oe_F2oCMOn3c-tau-yiH9ZgcsTBqEQ8tgMUF9mgu9PHoIlp2luRKN50EnQWKNtu2jG8P-RfKMKa4IBlVRZxA";
        String token3 = "ya29.A0ARrdaM9dRsioRCeMm0u19RjqAcD8h_4fRrFuw35y6zQXQsVseV5hPjU8PrRbA6v5UOf3Efp3B406HYCPQ8-j-rCxzVd-sVsjU2FdMNMtriguCFUK5_P-mTiGJ7hlVgXn55fXjf4_xMbPFFbOV1svWoK7AmdO";

        Date lastDate = new Timestamp(System.currentTimeMillis());
        SearchTerm startDateTearm = new ReceivedDateTerm(ComparisonTerm.GE, lastDate);
//        EmailModel.connectToImap(host, 993, email, token, true);
//        List<EmailObject> emailObjects = EmailModel.readEmailPassword(host,"993", email, password, startDateTearm, true);
        List<EmailObject> emailObjects = EmailModel.readEmail(1,host ,port, email, password, null, startDateTearm, (mess, e)->{
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

    String content = "\n" +
            "古賀 郁子 様\n" +
            "\n" +
            "この度は楽天市場内のショップ「e.Zeeee」をご利用いただきまして、誠にありがとうございます。\n" +
            "\n" +
            "2021/11/10にお客様からのご入金の確認ができました。\n" +
            "\n" +
            "------------------------------------------------------------\n" +
            "■ご注文内容\n" +
            "------------------------------------------------------------\n" +
            "[注文番号]  369456-20220112-00010746\n" +
            "[注文日時]  2021/11/10 10:31:07\n" +
            "[お支払い金額]    5,000円\n" +
            "------------------------------------------------------------\n" +
            "\n" +
            "【ご注意】\n" +
            "注文内容の変更やキャンセル、配送に関するお問い合わせは、ショップまでご連絡ください。\n" +
            "=====================================================\n" +
            "■ ショップ名 ： e.Zeeee\n" +
            "■ ショップURL： https://www.rakuten.co.jp/ezeeee/\n" +
            "■ 問い合わせフォーム ：\n" +
            "https://ask.step.rakuten.co.jp/inquiry-form/?on=369456-20211110-00068808&ms=702\n" +
            "■ 電話 ： 050-3702-1194\n" +
            "=====================================================\n" +
            "\n" +
            "お支払いに関するお問い合わせは、楽天市場までご連絡ください。\n" +
            "※本ご注文に心あたりが無い場合には、大変お手数をおかけしますが、上記ショップならびに楽天市場までお問い合わせください。\n" +
            "\n" +
            "────────楽天市場 ・ お支払いに関するご質問はこちら──────────\n" +
            "■楽天市場 お客様サポートセンター\n" +
            "https://chat.ichiba.faq.rakuten.co.jp/rnt_chat_ref/100/Payment\n" +
            "※表示された画面にお困りごとをご入力頂くか、「オペレーターとチャットしたい」など、ご希望のご連絡方法をご入力ください。\n";

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

package com.example.fixbug;

import com.coupang.openapi.sdk.Hmac;
import com.example.fixbug.api.line.LineStepLmeModel;
import com.example.fixbug.api.line.reponse.CsvExport;
import com.example.fixbug.api.mail.IMailService;
import com.example.fixbug.api.mail.response.MailRefreshTokenResponse;
import com.example.fixbug.api.rakuten.IRakutenService;
import com.example.fixbug.api.rakuten.IchibaResponse;
import com.example.fixbug.api.requesthelper.RequestHelper;
import com.example.fixbug.api.requesthelper.ResponseAPI;
import com.example.fixbug.btc.BitcoinWalletCreator;
import com.example.fixbug.elasticsearch.EsLineUserObjectKey;
import com.example.fixbug.elasticsearch.EsService;
import com.example.fixbug.google.GoogleSheetsModel;
import com.example.fixbug.objects.EmailObject;
import com.example.fixbug.utils.EmailModel;
import com.example.fixbug.utils.Logger;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import okhttp3.ResponseBody;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Address;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.core.Context;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.mockito.internal.matchers.Null;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class FixbugApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FixbugApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String addressFromKey = "tortoise omit divert used manage elephant spawn rule hospital burden session satoshi";
        String addressTo = "1KjNmSw7MM3FhZWpR3coi8GBgJfSKuY93h";
        String key = "L557pLbaWWnJEqVR4MvtWz6BozgBxiRJ524WEFxwVT8LMWfdmooX";
        BitcoinWalletCreator bitcoinWalletCreator = new BitcoinWalletCreator();
        bitcoinWalletCreator.giaoDichVi(addressFromKey, addressTo);
    }

    void es() {
        try {
            readMail1();
            RestHighLevelClient restHighLevelClient = EsService.createClient("localhost", 9200, "wm", "WSS2023@");
//
//            BoolQueryBuilder builder = QueryBuilders.boolQuery();
//            builder.must(QueryBuilders.matchQuery("_id", "542@5751"));
//            List<SearchHit> searchHits = EsService.searchDocuments(restHighLevelClient, "bot_line_user", builder);

            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.must(QueryBuilders.matchQuery(EsLineUserObjectKey.ID_LINE_USER, 5655));
            List<SearchHit> searchHits = EsService.searchDocuments(restHighLevelClient, "bot_line_user", builder);

            EsService.searchDocuments(restHighLevelClient, "bot_line_user", "_id", "542@5751");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static long getLongFromString(String dateString, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date outDate;
        try {
            outDate = df.parse(dateString);
            return outDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    private static final String HOST = "api-gateway.coupang.com";
    private static final int PORT = 443;
    private static final String SCHEMA = "https";
    //replace with your own accessKey
    private static final String ACCESS_KEY = "92e6a793-384e-4680-9eb7-0fa1ae911b92";
    //replace with your own secretKey
    private static final String SECRET_KEY = "d381156e3b8de94668b9e0067c90cb33302b3803";

    public void testGoogleSheet() {
        String spreadsheetId = "1EdxrJH_mltN14RPqGEK2h7Ocogp6y7V6SKVO5egbI_4";
//        ValueRange data = GoogleSheets.getData("1EdxrJH_mltN14RPqGEK2h7Ocogp6y7V6SKVO5egbI_4", "A1:Z1000");
        CsvExport response = new LineStepLmeModel().getListDataExport("1599", "thanhntp142@gmail.com");
        GoogleSheetsModel googleSheetsModel = new GoogleSheetsModel();
        Sheets sheets = googleSheetsModel.getServiceSheet(googleSheetsModel.getAuthorize());
        if (response.getData() != null) {
            List<Object> headers = response.getData().getHeads().get(1);
            if (headers != null) {
                ValueRange data = googleSheetsModel.getData(sheets, spreadsheetId, "A1:Z1");
                if (data == null || data.getValues() == null || data.getValues().isEmpty()) {
                    //title
                    googleSheetsModel.fillData(sheets, spreadsheetId, headers);
                } else {
                    if (data.getValues().get(data.getValues().size() -1).size() != headers.size()) {
                        googleSheetsModel.clearAllData(sheets, spreadsheetId, "A1:Z1000");
                        googleSheetsModel.fillData(sheets, spreadsheetId, headers);
                    }
                }
            }
            List<List<Object>> listRowCsv = response.getData().getDataExport();
            if (listRowCsv != null) {
                List<List<Object>> dataCsv = new ArrayList<>();
                for (List<Object> objects : listRowCsv) {
                    List<Object> objectListValue = new ArrayList<>();
                    for (Object object : objects) {
                        if (object == null) {
                            objectListValue.add("");
                        } else if (object instanceof Number) {
                            objectListValue.add(String.valueOf(object));
                        } else {
                            objectListValue.add(object);
                        }
                    }
                    dataCsv.add(objectListValue);
                }
                listRowCsv = dataCsv;
                ValueRange data = googleSheetsModel.getData(sheets, spreadsheetId, "A2:Z1000");
                if (data == null || data.getValues() == null || data.getValues().isEmpty()) {
                    googleSheetsModel.fillAllData(sheets, spreadsheetId, listRowCsv);
                } else {
                    int rowNumberGoogleSheet = data.getValues().size();
                    int nextRowNew = data.getValues().size();
                    for (int i = 0; i < listRowCsv.size(); i++) { //row
                        int row = i + 2;
                        boolean check = false;
                        int j;
                        for (j = 0; j < data.getValues().size(); j++) {
                            if (data.getValues().get(j).get(0).equals(listRowCsv.get(i).get(0))) {
                                check = true;
                                break;
                            }
                        }
                        if (check) {
                            int columnCell = data.getValues().get(i).size();
                            for (int k = 0; k < listRowCsv.get(i).size(); k++) {
                                if (listRowCsv.get(i).get(k) != null && columnCell > k) {
                                    if (!listRowCsv.get(i).get(k).toString().equals(data.getValues().get(i).get(k).toString())) {
                                        int column = k + 1;
                                        googleSheetsModel.fillData(sheets, spreadsheetId, row, column, listRowCsv.get(i).get(k) == null ? null : listRowCsv.get(i).get(k) + "");
                                    }
                                }  else if (listRowCsv.get(i).get(k) == null && columnCell > k) {
                                    if (!TextUtils.isEmpty(data.getValues().get(i).get(k).toString())) {
                                        int column = k + 1;
                                        googleSheetsModel.fillData(sheets, spreadsheetId, row, column, listRowCsv.get(i).get(k) == null ? null : listRowCsv.get(i).get(k) + "");
                                    }
                                } else if (listRowCsv.get(i).get(k) != null) {
                                    if (!TextUtils.isEmpty(listRowCsv.get(i).get(k).toString())) {
                                        int column = k + 1;
                                        googleSheetsModel.fillData(sheets, spreadsheetId, row, column, listRowCsv.get(i).get(k) == null ? null : listRowCsv.get(i).get(k) + "");
                                    }
                                }
                            }
                        } else {
                            for (int k = 0; k < listRowCsv.get(i).size(); k++) {
                                int rowNew = nextRowNew + 2;
                                int column = k + 1;
                                googleSheetsModel.fillData(sheets, spreadsheetId, rowNew, column, listRowCsv.get(i).get(k) == null ? null : listRowCsv.get(i).get(k) + "");
                            }
                            nextRowNew ++;
                        }
                    }
                }
            }
            Logger.info("", "END");
        }
    }

    public static void testAPI(String mt, String pathOutput){
        System.out.println("run");
        String method = mt;
        //replace with your own vendorId
        String path = pathOutput;
        CloseableHttpClient client = null;
        try {
            //create client
            client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder()
                    .setPath(path);

            /********************************************************/
            //authorize, demonstrate how to generate hmac signature here
            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
            //print out the hmac key
            System.out.println(authorization);
            /********************************************************/

            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
            HttpGet get = new HttpGet(uriBuilder.build().toString());
            /********************************************************/
            // set header, demonstarte how to use hmac signature here
            get.addHeader("Authorization", authorization);
            /********************************************************/
            get.addHeader("content-type", "application/json");
            CloseableHttpResponse response = null;
            try {
                //execute get request
                response = client.execute(get);
                //print result
                System.out.println("status code:" + response.getStatusLine().getStatusCode());
                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                System.out.println("result:" + EntityUtils.toString(entity));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int countByteJapan(String input) {
        try {
            return input.replaceAll("\t", "").getBytes("SHIFT-JIS").length;
        } catch (Exception e) {
            return 0;
        }
    }

    private void dowLoadImageFormUrl(){
        try {
            URL url = new URL("https://dev.patdel.watermelon.vn/storage/7/42/UlRUS0oZrZc5Tn2uCfGX0w6SFnwBHdvywFqdWE0f.jpg");
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1!=(n=in.read(buf)))
            {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();

            FileOutputStream fos = new FileOutputStream("C:\\Users\\watermelon\\Desktop\\test\\borrowed_image.jpg");
            fos.write(response);
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void test(final List<Integer> str){
        for (int i : str){
            i++;
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
        String email = "kuro836@gmail.com";
        String password = "mftlmmowcwtsiixr";
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
        List<EmailObject> emailObjects = EmailModel.readEmail(1, host ,port, email, password, token, startDateTearm, (mess, e)->{
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

    public String getRakutenAuth() {
        String apiKey = "SP379498_ikU4SKB5aWUKGpa4";
        String clientSecretRakuten = "SL379498_jufp2Ju9qlyFobAa";
        String res = "ESA " + Base64.getEncoder().encodeToString((apiKey + ":" + clientSecretRakuten).getBytes());
        System.out.println(res);
        return res;
    }

    public static void searchItemRakuten(String token, String itemUrl){
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("itemUrl", itemUrl);
        RequestHelper.executeSyncRequest(IRakutenService.SERVICE.search(token, requestBody), new ResponseAPI<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody response, int code) {
                System.out.printf("onSuccess code: "+ code);
                try {
                    System.out.printf("onSuccess code: "+ response.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String message) {
                System.out.printf("onFailure code: "+ code + " mess: " + message);
            }
        });
    }

    public static void searchIchiba(String appId, String keyword, String categoryId, int pageId) {
        Map<String, String> query = new HashMap<>();
        query.put("format", "json");
        query.put("applicationId", "" + appId);
        query.put("keyword", "" + keyword);
        query.put("availability", "1");
        query.put("page", "" + pageId);
        query.put("sort", "+itemPrice");
        query.put("NGKeyword", "中古 円還元 フレッツ転用 コラボ光変更 延長保証 修理保証 福袋 キャッシュバック 乗換 レンタル");
        query.put("minPrice", "100");
        if (!TextUtils.isEmpty(categoryId)) {
            query.put("genreId", categoryId);
        }
        RequestHelper.executeSyncRequest(IRakutenService.SERVICEDEV.searchIchiba(query), new ResponseAPI<IchibaResponse>() {
            @Override
            public void onSuccess(IchibaResponse response, int code) {

            }

            @Override
            public void onFailure(int code, String message) {

            }
        });
    }
}

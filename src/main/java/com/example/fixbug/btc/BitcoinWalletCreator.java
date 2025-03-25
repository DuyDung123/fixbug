package com.example.fixbug.btc;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.Wallet;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class BitcoinWalletCreator {

    public void createBtc() {
        NetworkParameters params = MainNetParams.get();
        Context context = new Context(params);

        // Tạo một ví mới
        Wallet wallet = Wallet.createDeterministic(params, Script.ScriptType.P2PKH);
        Context.propagate(context); // Đảm bảo ví sử dụng context đúng

        // Lấy 12 cụm từ bí mật (Mnemonic phrase)
        DeterministicSeed seed = wallet.getKeyChainSeed();
        List<String> mnemonicCode = seed.getMnemonicCode();
        System.out.println("12 cụm từ bí mật của ví: " + String.join(" ", mnemonicCode));
        // Lấy địa chỉ Bitcoin
        Address address = wallet.currentReceiveAddress();
//        Address address = wallet.freshReceiveAddress();  //để đảm bảo luôn tạo địa chỉ mới.

        // In địa chỉ ví
        System.out.println("Địa chỉ ví Bitcoin: " + address.toString());

        // In private key (LƯU Ý: Không chia sẻ với ai!)
        System.out.println("Private Key: " + wallet.currentReceiveKey().getPrivateKeyAsWiF(params));

//        String apiUrl = "https://blockchain.info/rawaddr/" + address;
        String apiUrl = "https://api.blockcypher.com/v1/btc/main/addrs/" + address.toString();

        // kiểm tra địa chỉ ví có hoạt động không
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println("Lỗi: Không thể kiểm tra địa chỉ!");

                // Đọc nội dung lỗi từ response
                Scanner errorScanner = new Scanner(conn.getErrorStream());
                StringBuilder errorResponse = new StringBuilder();
                while (errorScanner.hasNext()) {
                    errorResponse.append(errorScanner.nextLine()).append("\n");
                }
                errorScanner.close();

                System.out.println("Chi tiết lỗi: " + errorResponse.toString());
                return;
            }
            Scanner scanner = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            System.out.println("Kết quả: " + response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

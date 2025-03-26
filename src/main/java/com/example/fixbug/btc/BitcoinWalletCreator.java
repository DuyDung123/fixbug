package com.example.fixbug.btc;

import org.bitcoinj.core.*;
import org.bitcoinj.kits.WalletAppKit;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
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

//            giaoDichVi()

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void giaoDichVi(String mnemonic, String sToAddress) {
        try {
            // 🔹 1. Khởi tạo mạng Bitcoin
            NetworkParameters params = MainNetParams.get();
            Context context = new Context(params);

            // 🔹 2. Tạo ví từ 12 cụm từ bí mật
            DeterministicSeed seed = new DeterministicSeed(mnemonic, null, "", Utils.currentTimeSeconds());
            Wallet wallet = Wallet.fromSeed(params, seed);

            // 🔹 3. Hiển thị địa chỉ ví gửi
            Address fromAddress = wallet.currentReceiveAddress();
            System.out.println("Ví gửi: " + fromAddress.toString());

            // 🔹 4. Địa chỉ nhận BTC
            Address toAddress = Address.fromString(params, sToAddress);
            System.out.println("Ví nhận: " + toAddress.toString());

            // 🔹 5. Kết nối mạng Bitcoin
            File file = new File("bitcoin-wallet");
            WalletAppKit kit = new WalletAppKit(params, file, "wallet") {
                @Override
                protected void onSetupCompleted() {
                    super.onSetupCompleted();
                    System.out.println("✅ Ví đã sẵn sàng!");
                }
            };
//            // 🔹 3. Kết nối tới các node cụ thể thay vì dùng seed DNS
//            PeerAddress node1 = new PeerAddress(params, InetAddress.getByName("104.131.10.218"));
//            PeerAddress node2 = new PeerAddress(params, InetAddress.getByName("176.9.19.162"));
//            PeerAddress node3 = new PeerAddress(params, InetAddress.getByName("47.91.17.146"));
//            kit.setPeerNodes(node1, node2, node3);
//            kit.startAsync();
//            kit.awaitRunning();
//            wallet = kit.wallet();

            // 🔹 6. Kiểm tra số dư
            Coin balance = wallet.getBalance();
            System.out.println("Số dư ví: " + balance.toFriendlyString());

            // 🔹 7. Kiểm tra số dư đủ không (gửi 0.001 BTC)
            Coin amountToSend = Coin.parseCoin("0.001");
            Coin fee = Transaction.REFERENCE_DEFAULT_MIN_TX_FEE; // Phí giao dịch tối thiểu
            Coin totalAmount = amountToSend.add(fee);

            if (balance.isLessThan(totalAmount)) {
                System.out.println("❌ Số dư không đủ! Cần ít nhất " + totalAmount.toFriendlyString());
                return;
            }

            // 🔹 8. Tạo giao dịch
            SendRequest sendRequest = SendRequest.to(toAddress, amountToSend);
            sendRequest.feePerKb = fee;
            wallet.completeTx(sendRequest); // Hoàn tất giao dịch
            wallet.signTransaction(sendRequest); // Ký giao dịch

            // 🔹 9. Gửi giao dịch lên mạng Bitcoin
            wallet.commitTx(sendRequest.tx);
            kit.peerGroup().broadcastTransaction(sendRequest.tx).broadcast().get();
            System.out.println("✅ Giao dịch thành công! TXID: " + sendRequest.tx.getTxId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void giaoDichVi2(String privateKeyWIF, String sToAddress) {
        try {
            // 🔹 1. Khởi tạo mạng Bitcoin
            NetworkParameters params = MainNetParams.get();
            Context context = new Context(params);

            // 🔹 2. Chuyển đổi Private Key (WIF) thành ECKey
            DumpedPrivateKey dumpedPrivateKey = DumpedPrivateKey.fromBase58(params, privateKeyWIF);
            ECKey key = dumpedPrivateKey.getKey();

            // 🔹 3. Tạo ví từ Private Key
            Wallet wallet = Wallet.fromKeys(params, Arrays.asList(key));
            Address fromAddress = LegacyAddress.fromKey(params, key);
            System.out.println("📤 Ví gửi: " + fromAddress.toString());

            // 🔹 4. Địa chỉ nhận BTC
            Address toAddress = Address.fromString(params, sToAddress);
            System.out.println("📥 Ví nhận: " + toAddress.toString());

            // 🔹 5. Kết nối mạng Bitcoin
            File file = new File("/data/bitcoin-wallet");
            WalletAppKit kit = new WalletAppKit(params, file, "wallet");
            kit.startAsync();
            kit.awaitRunning();

            // 🔹 6. Kiểm tra số dư ví
            Coin balance = wallet.getBalance();
            System.out.println("💰 Số dư ví: " + balance.toFriendlyString());

            // 🔹 7. Kiểm tra số dư có đủ không (gửi 0.001 BTC)
            Coin amountToSend = Coin.parseCoin("0.001"); // 0.001 BTC
            Coin fee = Transaction.REFERENCE_DEFAULT_MIN_TX_FEE; // Phí tối thiểu
            Coin totalAmount = amountToSend.add(fee);

            if (balance.isLessThan(totalAmount)) {
                System.out.println("❌ Số dư không đủ! Cần ít nhất " + totalAmount.toFriendlyString());
                return;
            }

            // 🔹 8. Tạo giao dịch
            SendRequest sendRequest = SendRequest.to(toAddress, amountToSend);
            sendRequest.feePerKb = fee;
            wallet.completeTx(sendRequest); // Hoàn tất giao dịch
            wallet.signTransaction(sendRequest); // Ký giao dịch với Private Key

            // 🔹 9. Gửi giao dịch lên mạng Bitcoin
            wallet.commitTx(sendRequest.tx);
            kit.peerGroup().broadcastTransaction(sendRequest.tx).broadcast().get();
            System.out.println("✅ Giao dịch thành công! TXID: " + sendRequest.tx.getTxId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

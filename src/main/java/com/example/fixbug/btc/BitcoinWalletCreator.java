//package com.example.fixbug.btc;
//
//import org.bitcoinj.core.*;
//import org.bitcoinj.kits.WalletAppKit;
//import org.bitcoinj.net.discovery.DnsDiscovery;
//import org.bitcoinj.params.MainNetParams;
//import org.bitcoinj.params.TestNet3Params;
//import org.bitcoinj.script.Script;
//import org.bitcoinj.store.BlockStore;
//import org.bitcoinj.store.MemoryBlockStore;
//import org.bitcoinj.wallet.DeterministicSeed;
//import org.bitcoinj.wallet.SendRequest;
//import org.bitcoinj.wallet.Wallet;
//import org.bitcoinj.wallet.listeners.WalletCoinsReceivedEventListener;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.InetAddress;
//import java.net.URL;
//import java.net.UnknownHostException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;
//import java.util.concurrent.TimeUnit;
//
//public class BitcoinWalletCreator {
//
//    public void createBtc() {
//        NetworkParameters params = MainNetParams.get();
//        Context context = new Context(params);
//
//        // Tạo một ví mới
//        Wallet wallet = Wallet.createDeterministic(params, Script.ScriptType.P2WPKH);
//        Context.propagate(context); // Đảm bảo ví sử dụng context đúng
//
//        // Lấy 12 cụm từ bí mật (Mnemonic phrase)
//        DeterministicSeed seed = wallet.getKeyChainSeed();
//        List<String> mnemonicCode = seed.getMnemonicCode();
//        System.out.println("12 cụm từ bí mật của ví: " + String.join(" ", mnemonicCode));
//        // Lấy địa chỉ Bitcoin
//        Address address = wallet.currentReceiveAddress();
////        Address address = wallet.freshReceiveAddress();  //để đảm bảo luôn tạo địa chỉ mới.
//
//        // In địa chỉ ví
//        System.out.println("Địa chỉ ví Bitcoin: " + address.toString());
//
//        // In private key (LƯU Ý: Không chia sẻ với ai!)
//        System.out.println("Private Key: " + wallet.currentReceiveKey().getPrivateKeyAsWiF(params));
//
////        String apiUrl = "https://blockchain.info/rawaddr/" + address;
//        String apiUrl = "https://api.blockcypher.com/v1/btc/main/addrs/" + address.toString();
//
//        // kiểm tra địa chỉ ví có hoạt động không
//        try {
//            URL url = new URL(apiUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Accept", "application/json");
//
//            if (conn.getResponseCode() != 200) {
//                System.out.println("Lỗi: Không thể kiểm tra địa chỉ!");
//
//                // Đọc nội dung lỗi từ response
//                Scanner errorScanner = new Scanner(conn.getErrorStream());
//                StringBuilder errorResponse = new StringBuilder();
//                while (errorScanner.hasNext()) {
//                    errorResponse.append(errorScanner.nextLine()).append("\n");
//                }
//                errorScanner.close();
//
//                System.out.println("Chi tiết lỗi: " + errorResponse.toString());
//                return;
//            }
//            Scanner scanner = new Scanner(url.openStream());
//            StringBuilder response = new StringBuilder();
//            while (scanner.hasNext()) {
//                response.append(scanner.nextLine());
//            }
//            scanner.close();
//
//            System.out.println("Kết quả: " + response.toString());
//
////            giaoDichVi()
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void transactionCoin(String mnemonic, String sToAddress) throws Exception {
//        NetworkParameters params = MainNetParams.get();
//        PeerGroup peerGroup = new PeerGroup(params);
//        peerGroup.addAddress(new PeerAddress(params, InetAddress.getByName("172.104.172.221"))); // Thay bằng node bạn muốn kết nối
//        peerGroup.start();
//
//        // 🔹 2. nhập ví từ 12 cụm từ bí mật
//        DeterministicSeed seed = new DeterministicSeed(mnemonic, null, "", Utils.currentTimeSeconds());
//        Wallet wallet = Wallet.fromSeed(params, seed);
//
//        // 🔹 3. Hiển thị địa chỉ ví gửi
//        Address fromAddress = wallet.currentReceiveAddress();
//        System.out.println("Ví gửi: " + fromAddress.toString());
//
//        // 4️⃣ Lấy UTXO (Unspent Transaction Outputs) để thực hiện giao dịch
//        BlockStore blockStore = new MemoryBlockStore(params);
//        BlockChain chain = new BlockChain(params, wallet, blockStore);
//        peerGroup.addWallet(wallet);
//        peerGroup.downloadBlockChain();
//
//        // 🔹 6. Kiểm tra số dư
//        Coin balance = wallet.getBalance();
//        System.out.println("Số dư ví: " + balance.toFriendlyString());
//    }
//
//    public void transactionAddressCoin(String mnemonic, String sToAddress) {
//        try {
//            // 🔹 1. Khởi tạo mạng Bitcoin
//            NetworkParameters params = MainNetParams.get();
//            Context context = new Context(params);
//
//            // 🔹 2. Tạo ví từ 12 cụm từ bí mật
//            DeterministicSeed seed = new DeterministicSeed(mnemonic, null, "", Utils.currentTimeSeconds());
//            Wallet wallet = Wallet.fromSeed(params, seed);
//
//            // 🔹 3. Hiển thị địa chỉ ví gửi
//            Address fromAddress = wallet.currentReceiveAddress();
//            System.out.println("📌 Ví gửi: " + fromAddress.toString());
//
//            // 🔹 4. Địa chỉ nhận BTC
//            Address toAddress = Address.fromString(params, sToAddress);
//            System.out.println("📌 Ví nhận: " + toAddress.toString());
//
//            // 🔹 5. Kết nối mạng Bitcoin (có thể dùng node cụ thể thay vì DNS Seed)
//            WalletAppKit kit = new WalletAppKit(params, new File("data/bitcoin-wallet"), "forwarding-service") {
//                @Override
//                protected void onSetupCompleted() {
//                    System.out.println("Wallet created and loaded successfully.");
//                    System.out.println("Receive address: " + wallet().currentReceiveAddress());
//                    System.out.println("Seed Phrase: " + wallet().getKeyChainSeed());
//                    System.out.println("Balance: " + wallet().getBalance().toFriendlyString());
//                    System.out.println("Public Key: " + wallet().findKeyFromAddress(wallet().currentReceiveAddress())
//                            .getPublicKeyAsHex());
//                    System.out.println("Private Key: " + wallet().findKeyFromAddress(wallet().currentReceiveAddress())
//                            .getPrivateKeyAsHex());
//                    wallet().encrypt("password");
//                }
//            };
////            kit.setBlockingStartup(false);
//            kit.setPeerNodes(
//                    new PeerAddress(params, InetAddress.getByName("84.247.180.248"))
//            );
////            kit.setDownloadListener(null); // Không cần tải block headers
//            kit.startAsync();
//            kit.awaitRunning();  // Đợi kết nối xong
//            kit.setAutoSave(true);
//
//            wallet = kit.wallet();
//
//            // 🔹 6. Kiểm tra số dư ví
//            Coin balance = wallet.getBalance();
//            System.out.println("📌 Số dư ví: " + balance.toFriendlyString());
//
//            // 🔹 7. Kiểm tra số dư có đủ không
//            Coin amountToSend = Coin.parseCoin("0.001"); // 0.001 BTC
//            Coin fee = Transaction.REFERENCE_DEFAULT_MIN_TX_FEE; // Phí giao dịch tối thiểu
//            Coin totalAmount = amountToSend.add(fee);
//
////            if (balance.isLessThan(totalAmount)) {
////                System.out.println("❌ Số dư không đủ! Cần ít nhất: " + totalAmount.toFriendlyString());
////                return;
////            }
//
//            // 🔹 8. Tạo giao dịch
//            SendRequest sendRequest = SendRequest.to(toAddress, amountToSend);
//            sendRequest.feePerKb = fee;
//            wallet.completeTx(sendRequest);
//            wallet.signTransaction(sendRequest);
//
//            // 🔹 9. Gửi giao dịch lên mạng Bitcoin
//            wallet.commitTx(sendRequest.tx);
//            kit.peerGroup().broadcastTransaction(sendRequest.tx).broadcast().get();
//            System.out.println("✅ Giao dịch thành công! TXID: " + sendRequest.tx.getTxId());
//            System.exit(0);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("❌ Lỗi trong quá trình giao dịch!");
//            System.exit(0);
//        }
//    }
//    public void giaoDichVi2(String privateKeyWIF, String sToAddress) {
//        try {
//            // 🔹 1. Khởi tạo mạng Bitcoin
//            NetworkParameters params = MainNetParams.get();
//            Context context = new Context(params);
//            WalletAppKit kits = new WalletAppKit(params, new File("/data/bitcoin-wallet"), "wallet");
//            kits.startAsync();
//            kits.awaitRunning();
//            Wallet wallets = kits.wallet();
//
//            // 🔹 2. Chuyển đổi Private Key (WIF) thành ECKey
//            DumpedPrivateKey dumpedPrivateKey = DumpedPrivateKey.fromBase58(params, privateKeyWIF);
//            ECKey key = dumpedPrivateKey.getKey();
//
//            // 🔹 3. Tạo ví từ Private Key
//            Wallet wallet = Wallet.fromKeys(params, Arrays.asList(key));
//            Address fromAddress = LegacyAddress.fromKey(params, key);
//            System.out.println("📤 Ví gửi: " + fromAddress.toString());
//
//            // 🔹 4. Địa chỉ nhận BTC
//            Address toAddress = Address.fromString(params, sToAddress);
//            System.out.println("📥 Ví nhận: " + toAddress.toString());
//
//            System.out.println("Network: " + params.getId());
//
//            // 🔹 5. Kết nối mạng Bitcoin
//            File file = new File("/data/bitcoin-wallet");
//            WalletAppKit kit = new WalletAppKit(params, file, "wallet");
//            kit.startAsync();
//            kit.awaitRunning();
//
//            // 🔹 6. Kiểm tra số dư ví
//            Coin balance = wallet.getBalance();
//            System.out.println("💰 Số dư ví: " + balance.toFriendlyString());
//
//            // 🔹 7. Kiểm tra số dư có đủ không (gửi 0.001 BTC)
//            Coin amountToSend = Coin.parseCoin("0.001"); // 0.001 BTC
//            Coin fee = Transaction.REFERENCE_DEFAULT_MIN_TX_FEE; // Phí tối thiểu
//            Coin totalAmount = amountToSend.add(fee);
//
//            if (balance.isLessThan(totalAmount)) {
//                System.out.println("❌ Số dư không đủ! Cần ít nhất " + totalAmount.toFriendlyString());
//                return;
//            }
//
//            // 🔹 8. Tạo giao dịch
//            SendRequest sendRequest = SendRequest.to(toAddress, amountToSend);
//            sendRequest.feePerKb = fee;
//            wallet.completeTx(sendRequest); // Hoàn tất giao dịch
//            wallet.signTransaction(sendRequest); // Ký giao dịch với Private Key
//
//            // 🔹 9. Gửi giao dịch lên mạng Bitcoin
//            wallet.commitTx(sendRequest.tx);
//            kit.peerGroup().broadcastTransaction(sendRequest.tx).broadcast().get();
//            System.out.println("✅ Giao dịch thành công! TXID: " + sendRequest.tx.getTxId());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void connect() {
//        // 1️⃣ Chọn mạng Bitcoin (MainNet cho mạng chính, TestNet3 cho test)
//        NetworkParameters params = MainNetParams.get();
//
//        // 2️⃣ Tạo thư mục lưu trữ dữ liệu blockchain và ví
//        File walletDir = new File("data/bitcoin-wallet");
//
//        // 3️⃣ Khởi tạo WalletAppKit để kết nối mạng Bitcoin
//        WalletAppKit kit = new WalletAppKit(params, walletDir, "mywallet") {
//            @Override
//            protected void onSetupCompleted() {
//                // 4️⃣ Khi ví đã sẵn sàng, lấy địa chỉ nhận Bitcoin
//                Wallet wallet = wallet();
//                Address address = wallet.currentReceiveAddress();
//                System.out.println("✅ Ví Bitcoin đã tạo thành công!");
//                System.out.println("📩 Địa chỉ ví Bitcoin: " + address.toString());
//            }
//        };
//
//        // 5️⃣ Kết nối với mạng Bitcoin
//        kit.startAsync();
//        kit.awaitRunning();
//
//        // 6️⃣ Kiểm tra trạng thái kết nối
//        PeerGroup peerGroup = kit.peerGroup();
//        System.out.println("🔗 Số lượng Peers kết nối: " + peerGroup.numConnectedPeers());
//
//        // 7️⃣ Dừng kết nối khi xong (chỉ làm nếu cần dừng)
//        // kit.stopAsync();
//    }
//}

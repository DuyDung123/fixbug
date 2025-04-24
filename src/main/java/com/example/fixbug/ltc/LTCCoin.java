package com.example.fixbug.ltc;

import com.google.gson.JsonObject;
import org.bitcoinj.core.*;
import org.bitcoinj.core.Address;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.MnemonicCode;
import org.bitcoinj.crypto.MnemonicException;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;

public class LTCCoin {

    // Lưu ý: bạn cần có LitecoinParams đã tùy chỉnh nếu đang dùng cho mạng Litecoin thật sự.
    // Ở đây dùng tạm MainNetParams vì LitecoinMainNetParams có thể là class tự viết (bạn cần đảm bảo nó kế thừa từ NetworkParameters).
    private static final NetworkParameters params = LitecoinMainNetParams.get(); // hoặc MainNetParams.get()

//    public void send() {
//        OkHttpClient client = new OkHttpClient();
//
//        // Thay thế bằng API token của bạn
//        String yourToken = "YOUR_API_TOKEN";
//        // Địa chỉ ví gửi và nhận
//        String fromAddress = "LTC_FROM_ADDRESS";
//        String toAddress = "LTC_TO_ADDRESS";
//        // Private key của ví gửi (WIF - Wallet Import Format)
//        String privateKeyWIF = "YOUR_PRIVATE_KEY_WIF";
//
//        // Tạo đối tượng JSON cho yêu cầu
//        JsonObject json = new JsonObject();
//        json.put("inputs", new org.json.JSONArray().put(new JSONObject().put("addresses", new org.json.JSONArray().put(fromAddress))));
//        json.put("outputs", new org.json.JSONArray().put(new JSONObject()
//                .put("addresses", new org.json.JSONArray().put(toAddress))
//                .put("value", 1000000) // 1 LTC = 100000000 litoshi
//        ));
//
//        // Tạo body cho yêu cầu HTTP
//        RequestBody body = RequestBody.create(
//                json.toString(),
//                MediaType.parse("application/json")
//        );
//
//        // Gửi yêu cầu tạo giao dịch mới
//        Request request = new Request.Builder()
//                .url("https://api.blockcypher.com/v1/ltc/main/txs/new?token=" + yourToken)
//                .post(body)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        String responseBody = response.body().string();
//
//        // In ra phản hồi từ API
//        System.out.println("Response: " + responseBody);
//
//        // Tiến hành ký giao dịch và gửi đi (bạn cần thêm mã ký và gửi giao dịch ở đây)
//    }

    public static void sendLTC() throws Exception {
        // Private Key WIF (phải đúng Litecoin WIF bắt đầu bằng chữ 'T')
        DumpedPrivateKey dumpedPrivateKey = DumpedPrivateKey.fromBase58(params, "T3yLWcFNQdrp2MpRpVUTHTKcbo6JcJmKgmLnNFWk3emikwpv6rk2");
        ECKey senderKey = dumpedPrivateKey.getKey();

        Wallet wallet = Wallet.createDeterministic(params, Script.ScriptType.P2PKH);
        wallet.importKey(senderKey);

        BlockStore blockStore = new MemoryBlockStore(params);
        BlockChain chain = new BlockChain(params, wallet, blockStore);

        PeerGroup peerGroup = new PeerGroup(params, chain);
        peerGroup.addWallet(wallet);
        peerGroup.start();
//        peerGroup.downloadBlockChain();

        // Địa chỉ nhận LTC (dùng đúng địa chỉ mạng chính, bắt đầu bằng chữ L hoặc M)
        Address to = LegacyAddress.fromBase58(params, "LbwCEd3QNkmfMsZptdMQWsNhDpksccJohv"); // thay thế

        Coin amountToSend = Coin.valueOf(10000); // 0.0001 LTC
        Wallet.SendResult result = wallet.sendCoins(peerGroup, to, amountToSend);
        System.out.println("Transaction hash: " + result.tx.getTxId());

        peerGroup.stop();
    }

    public static void generateWallet2() throws UnreadableWalletException {
        DeterministicSeed seed = new DeterministicSeed("vapor uncle fruit affair ...", null, "", 0);
        DeterministicKeyChain chain = DeterministicKeyChain.builder().seed(seed).build();
        ECKey key = chain.getKeyByPath(Collections.singletonList(ChildNumber.ZERO_HARDENED), true);

        System.out.println("🔑 Private key (WIF): " + key.getPrivateKeyEncoded(params));
        System.out.println("🏦 LTC Address: " + key.getPrivateKeyEncoded(params).toString());
    }

    public static void generateWallet() throws MnemonicException.MnemonicLengthException {
        SecureRandom random = new SecureRandom();
        byte[] entropy = new byte[16];
        random.nextBytes(entropy);

        MnemonicCode mc = MnemonicCode.INSTANCE;
        List<String> mnemonic = mc.toMnemonic(entropy);

        String passphrase = "";
        long creationTime = System.currentTimeMillis() / 1000L;
        DeterministicSeed seed = new DeterministicSeed(mnemonic, null, passphrase, creationTime);

        Wallet wallet = Wallet.fromSeed(params, seed, Script.ScriptType.P2PKH);

//        ECKey key = wallet.currentReceiveKey();
        ECKey key = new ECKey(random);
        LegacyAddress address = LegacyAddress.fromKey(params, key);

        System.out.println("Mnemonic: " + String.join(" ", mnemonic));
        System.out.println("LTC Address: " + address);
        System.out.println("Private Key (WIF): " + key.getPrivateKeyEncoded(params).toString());
    }

    public static void litecoinWIFGeneratorFormPrivateKey() {
        // Thay thế bằng private key hex của bạn nếu có
        String hexPrivateKey = "f8a8e2ab2349ddcbba2736fa58c40f0e4d6f8c0bbf50fefb6a0d3f8a9d0e3c6e";
        ECKey key = ECKey.fromPrivate(Utils.HEX.decode(hexPrivateKey));

        NetworkParameters params = LitecoinMainNetParams.get();
        String wif = key.getPrivateKeyEncoded(params).toBase58();

        System.out.println("✅ Private Key (hex): " + hexPrivateKey);
        System.out.println("🔐 WIF (Litecoin): " + wif);
        System.out.println("📮 Địa chỉ LTC: " + key.getPrivateKeyAsWiF(params));
    }
}

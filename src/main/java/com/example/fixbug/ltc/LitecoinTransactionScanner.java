package com.example.fixbug.ltc;

import org.bitcoinj.core.*;
import org.bitcoinj.params.AbstractBitcoinNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;
import org.bitcoinj.wallet.Wallet;

import java.util.List;

public class LitecoinTransactionScanner {

    public static class LitecoinMainNetParams extends AbstractBitcoinNetParams {
        public LitecoinMainNetParams() {
            interval = 2016;
            targetTimespan = 3 * 24 * 60 * 60;
            maxTarget = Utils.decodeCompactBits(0x1e0fffffL);
            dumpedPrivateKeyHeader = 176;
            addressHeader = 48;
            p2shHeader = 50;
            segwitAddressHrp = "ltc";
            port = 9333;
            packetMagic = 0xfbc0b6dbL;
            spendableCoinbaseDepth = 100;
            subsidyDecreaseBlockCount = 840000;
            id = ID_MAINNET;
        }


        private static LitecoinMainNetParams instance;

        public static synchronized LitecoinMainNetParams get() {
            if (instance == null) {
                instance = new LitecoinMainNetParams();
            }
            return instance;
        }

        public String getPaymentProtocolId() {
            return PAYMENT_PROTOCOL_ID_MAINNET;
        }
    }

    public static void main(String[] args) throws BlockStoreException {
        NetworkParameters params = com.example.fixbug.ltc.LitecoinMainNetParams.get();
        Context context = new Context(params);

        Wallet wallet = Wallet.createDeterministic(params, Script.ScriptType.P2PKH);

        String watchAddress = "LdFQTQomXvcBhJ7urtSGFVVi374grsbPKm"; // Thay bằng địa chỉ của bạn
        Address address = Address.fromString(params, watchAddress);
        wallet.addWatchedAddress(address);

        BlockChain chain = new BlockChain(context, wallet, new MemoryBlockStore(params));
        PeerGroup peerGroup = new PeerGroup(context, chain);
        peerGroup.addWallet(wallet);
        peerGroup.start();
        peerGroup.downloadBlockChain();

        List<Transaction> transactions = (List<Transaction>) wallet.getTransactions(true);
        for (Transaction tx : transactions) {
            System.out.println("Giao dịch:");
            System.out.println("Hash: " + tx.getTxId());
            System.out.println("Số lượng: " + tx.getValueSentToMe(wallet));
            System.out.println("Thời gian: " + tx.getUpdateTime());
            System.out.println("-------------------------------------");
        }

        peerGroup.stop();
    }
}

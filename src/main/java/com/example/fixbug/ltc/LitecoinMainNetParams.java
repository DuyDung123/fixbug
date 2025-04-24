package com.example.fixbug.ltc;

import org.bitcoinj.params.MainNetParams;

public class LitecoinMainNetParams extends MainNetParams {
    private static LitecoinMainNetParams instance;

    public LitecoinMainNetParams() {
        super();
        dumpedPrivateKeyHeader = 0xB0;
        addressHeader = 48; // 'L'
        p2shHeader = 50; // 'M'
        segwitAddressHrp = "ltc";
        id = ID_MAINNET;
        port = 9333;
        packetMagic = 0xfbc0b6dbL;
    }

    public static synchronized LitecoinMainNetParams get() {
        if (instance == null) {
            instance = new LitecoinMainNetParams();
        }
        return instance;
    }
}

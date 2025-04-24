package com.example.fixbug.xrp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.primitives.UnsignedInteger;
import okhttp3.HttpUrl;
import org.xrpl.xrpl4j.client.JsonRpcClientErrorException;
import org.xrpl.xrpl4j.client.XrplClient;
import org.xrpl.xrpl4j.client.faucet.FaucetClient;
import org.xrpl.xrpl4j.client.faucet.FundAccountRequest;
import org.xrpl.xrpl4j.codec.addresses.AddressCodec;
import org.xrpl.xrpl4j.crypto.keys.*;
import org.xrpl.xrpl4j.crypto.signing.SignatureService;
import org.xrpl.xrpl4j.crypto.signing.SingleSignedTransaction;
import org.xrpl.xrpl4j.crypto.signing.bc.BcSignatureService;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoRequestParams;
import org.xrpl.xrpl4j.model.client.accounts.AccountInfoResult;
import org.xrpl.xrpl4j.model.client.common.LedgerIndex;
import org.xrpl.xrpl4j.model.client.common.LedgerSpecifier;
import org.xrpl.xrpl4j.model.client.fees.FeeResult;
import org.xrpl.xrpl4j.model.client.ledger.LedgerRequestParams;
import org.xrpl.xrpl4j.model.client.transactions.SubmitResult;
import org.xrpl.xrpl4j.model.client.transactions.TransactionRequestParams;
import org.xrpl.xrpl4j.model.client.transactions.TransactionResult;
import org.xrpl.xrpl4j.model.transactions.Address;
import org.xrpl.xrpl4j.model.transactions.Payment;
import org.xrpl.xrpl4j.model.transactions.XAddress;
import org.xrpl.xrpl4j.model.transactions.XrpCurrencyAmount;

import java.math.BigDecimal;

public class XRPCoin {
    //https://github.com/XRPLF/xrpl4j-sample/blob/main/src/main/java/org/xrpl/xrpl4j/samples/SendXrp.java
    //https://xrpl.org/resources/dev-tools/xrp-faucets
    //https://xrpl.org/docs/tutorials/java/build-apps/get-started

    public static void sendXrpWithTag() throws Exception {
        // 1. Kết nối tới XRP Mainnet
        XrplClient client = new XrplClient(HttpUrl.get(" https://s.altnet.rippletest.net:51234/")); //https://s1.ripple.com:51234/   https://s.altnet.rippletest.net:51234/

        // 2. Khởi tạo ví chung
        String secret = "s████████████████████████"; // Private seed ví chung
        Seed seed = Seed.fromBase58EncodedSecret(() -> secret);
        KeyPair keyPair = seed.deriveKeyPair();
        Address fromAddress = keyPair.publicKey().deriveAddress();

        // 3. Lấy thông tin account hiện tại
        AccountInfoResult accountInfo = client.accountInfo(AccountInfoRequestParams.of(fromAddress));

        // 4. Lấy phí giao dịch
        FeeResult feeResult = client.fee();
        XrpCurrencyAmount fee = feeResult.drops().openLedgerFee();

        // 5. Lấy số thứ tự ledger mới nhất
        LedgerIndex currentLedger = client.ledger(
                LedgerRequestParams.builder()
                        .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                        .build()
        ).ledgerIndex().orElseThrow(() -> new RuntimeException("Ledger index not available"));

        UnsignedInteger sequence = accountInfo.accountData().sequence();
        UnsignedInteger lastLedgerSequence = currentLedger.plus(UnsignedInteger.valueOf(4)).unsignedIntegerValue();

        BigDecimal amount = new BigDecimal(10);
        String toAddress = "rPT1Sjq2YGrBMTttX4GZHjKu9dyfzbpAYe";
        int userTag = 1234567890;

        // 6. Tạo Payment object có kèm destination tag
        Payment payment = Payment.builder()
                .account(fromAddress)
                .amount(XrpCurrencyAmount.ofXrp(amount))
                .destination(Address.of(toAddress))
                .destinationTag(UnsignedInteger.valueOf(userTag))
                .sequence(sequence)
                .fee(fee)
                .signingPublicKey(keyPair.publicKey())
                .lastLedgerSequence(lastLedgerSequence)
                .build();

        // 7. Ký giao dịch
        SignatureService<PrivateKey> signatureService = new BcSignatureService();
        SingleSignedTransaction<Payment> signed = signatureService.sign(keyPair.privateKey(), payment);

        // 8. Submit giao dịch
        SubmitResult<Payment> result = client.submit(signed);

        System.out.println("Trạng thái gửi: " + result.engineResult());
        System.out.println("Transaction hash: " + signed.hash());
        System.out.println("Explorer: https://livenet.xrpl.org/transactions/" + signed.hash());
    }

    public static void sendXrp() throws JsonRpcClientErrorException, InterruptedException, JsonProcessingException {

        System.out.println("Sending XRP from fixed wallet...");

        // Kết nối tới Ripple Testnet
        HttpUrl rippledUrl = HttpUrl.get("https://s.altnet.rippletest.net:51234/"); //https://batch.rpc.nerdnest.xyz   https://s.altnet.rippletest.net:51234/
        XrplClient xrplClient = new XrplClient(rippledUrl);

        // ✅ Dùng secret có sẵn để tạo keypair
        String secret = "sEdTVQsSkn6pYbXWAkZ1npvK3WkXUQV";
        Seed seed = Seed.fromBase58EncodedSecret(Base58EncodedSecret.of(secret));
        KeyPair keyPair = seed.deriveKeyPair();

        Address classicAddress = keyPair.publicKey().deriveAddress();
        System.out.println("Ví gửi: " + classicAddress);

        // Lấy thông tin tài khoản (sequence)
        AccountInfoRequestParams requestParams = AccountInfoRequestParams.builder()
                .account(classicAddress)
                .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                .build();
        AccountInfoResult accountInfo = xrplClient.accountInfo(requestParams);
        UnsignedInteger sequence = accountInfo.accountData().sequence();

        // Lấy phí hiện tại
        FeeResult feeResult = xrplClient.fee();
        XrpCurrencyAmount fee = feeResult.drops().openLedgerFee();

        // Lấy ledger index hiện tại
        LedgerIndex validatedLedger = xrplClient.ledger(
                        LedgerRequestParams.builder()
                                .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                                .build()
                )
                .ledgerIndex()
                .orElseThrow(() -> new RuntimeException("LedgerIndex not available."));

        // Tạo Payment
        Address destination = Address.of("rwx1DG7B3NzXdSbAvNs8ZWVCvtf7nU65Zx"); // thay ví khác nếu bạn muốn
        Payment payment = Payment.builder()
                .account(classicAddress)
                .amount(XrpCurrencyAmount.ofXrp(BigDecimal.valueOf(1))) // gửi 1 XRP
                .destination(destination)
                .destinationTag(UnsignedInteger.valueOf(123456789))
                .sequence(sequence)
                .fee(fee)
                .signingPublicKey(keyPair.publicKey())
                .lastLedgerSequence(validatedLedger.plus(UnsignedInteger.valueOf(4)).unsignedIntegerValue())
                .build();

        // Ký giao dịch
        SignatureService<PrivateKey> signatureService = new BcSignatureService();
        SingleSignedTransaction<Payment> signedPayment = signatureService.sign(keyPair.privateKey(), payment);

        // Gửi giao dịch
        SubmitResult<Payment> paymentSubmitResult = xrplClient.submit(signedPayment);
        System.out.println("Submit result: " + paymentSubmitResult);

        // Theo dõi xác nhận giao dịch
        boolean validated = false;
        while (!validated) {
            Thread.sleep(4000); // đợi vài giây rồi kiểm tra lại
            TransactionResult<Payment> txResult = xrplClient.transaction(TransactionRequestParams.of(signedPayment.hash()), Payment.class);
            if (txResult.validated()) {
                System.out.println("Giao dịch đã được xác nhận!");
                System.out.println("Kết quả: " + txResult.metadata().get().transactionResult());
                System.out.println("Link explorer: https://testnet.xrpl.org/transactions/" + signedPayment.hash());
                validated = true;
            } else {
                System.out.println("Đang chờ xác nhận...");
            }
        }
    }
    public static void createAccountAndSendXrp() throws JsonRpcClientErrorException, JsonProcessingException, InterruptedException {
        System.out.println("Running the SendXrp sample...");

        // Construct a network client
        HttpUrl rippledUrl = HttpUrl.get("https://s1.ripple.com:51234/");
        XrplClient xrplClient = new XrplClient(rippledUrl);

        Seed seed = Seed.ed25519Seed();
        KeyPair keyPair = seed.deriveKeyPair();
        Address classicAddress = keyPair.publicKey().deriveAddress();
        System.out.println("User Address: " + classicAddress);
        System.out.println("Private Key: " + bytesToHex(keyPair.privateKey().value().toByteArray()));

        // Create a KeyPair
//        KeyPair randomKeyPair = Seed.ed25519Seed().deriveKeyPair();
//        System.out.println("Generated KeyPair: " + randomKeyPair.privateKey());
//        // Get the Classic and X-Addresses from testWallet
//        Address classicAddress = randomKeyPair.publicKey().deriveAddress();
//        System.out.println("Classic Address: " + classicAddress);

//        // Fund the account using the testnet Faucet
//        FaucetClient faucetClient = FaucetClient.construct(HttpUrl.get("https://faucet.altnet.rippletest.net"));
//        faucetClient.fundAccount(FundAccountRequest.of(classicAddress));
//        System.out.println("Funded the account using the Testnet faucet.");
//
//        // Wait for the Faucet Payment to get validated
//        Thread.sleep(4 * 1000);

        // Look up your Account Info
        AccountInfoRequestParams requestParams = AccountInfoRequestParams.builder()
                .account(classicAddress)
                .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                .build();
        AccountInfoResult accountInfoResult = xrplClient.accountInfo(requestParams);
        UnsignedInteger sequence = accountInfoResult.accountData().sequence();

        // Request current fee information from rippled
        FeeResult feeResult = xrplClient.fee();
        XrpCurrencyAmount openLedgerFee = feeResult.drops().openLedgerFee();

        // Get the latest validated ledger index
        LedgerIndex validatedLedger = xrplClient.ledger(
                        LedgerRequestParams.builder()
                                .ledgerSpecifier(LedgerSpecifier.VALIDATED)
                                .build()
                )
                .ledgerIndex()
                .orElseThrow(() -> new RuntimeException("LedgerIndex not available."));

        // LastLedgerSequence is the current ledger index + 4
        UnsignedInteger lastLedgerSequence = validatedLedger.plus(UnsignedInteger.valueOf(4)).unsignedIntegerValue();

        XrpCurrencyAmount fee = feeResult.drops().openLedgerFee();
        // Tạo Payment
        Address destination = Address.of("rPT1Sjq2YGrBMTttX4GZHjKu9dyfzbpAYe"); // thay ví khác nếu bạn muốn
        Payment payment = Payment.builder()
                .account(classicAddress)
                .amount(XrpCurrencyAmount.ofXrp(BigDecimal.valueOf(1))) // gửi 1 XRP
                .destination(destination)
                .destinationTag(UnsignedInteger.valueOf(123456789))
                .sequence(sequence)
                .fee(fee)
                .lastLedgerSequence(validatedLedger.plus(UnsignedInteger.valueOf(4)).unsignedIntegerValue())
                .build();

        // Ký giao dịch
        SignatureService<PrivateKey> signatureService = new BcSignatureService();
        SingleSignedTransaction<Payment> signedPayment = signatureService.sign(keyPair.privateKey(), payment);

        // Gửi giao dịch
        SubmitResult<Payment> paymentSubmitResult = xrplClient.submit(signedPayment);
        System.out.println("Submit result: " + paymentSubmitResult);

        // Theo dõi xác nhận giao dịch
        boolean validated = false;
        while (!validated) {
            Thread.sleep(4000); // đợi vài giây rồi kiểm tra lại
            TransactionResult<Payment> txResult = xrplClient.transaction(TransactionRequestParams.of(signedPayment.hash()), Payment.class);
            if (txResult.validated()) {
                System.out.println("Giao dịch đã được xác nhận!");
                System.out.println("Kết quả: " + txResult.metadata().get().transactionResult());
                System.out.println("Link explorer: https://testnet.xrpl.org/transactions/" + signedPayment.hash());
                validated = true;
            } else {
                System.out.println("Đang chờ xác nhận...");
            }
        }
    }

    public static void getAccountInfo() throws JsonRpcClientErrorException {
        System.out.println("Running the GetAccountInfo sample...");

        // Construct a network client
        HttpUrl rippledUrl = HttpUrl.get("https://s.altnet.rippletest.net:51234/");
        System.out.println("Constructing an XrplClient connected to " + rippledUrl);
        XrplClient xrplClient = new XrplClient(rippledUrl);

        // Create a random KeyPair
        KeyPair randomKeyPair = Seed.ed25519Seed().deriveKeyPair();
        System.out.println("Generated KeyPair: " + randomKeyPair);

        // Derive the Classic and X-Addresses from testWallet
        Address classicAddress = randomKeyPair.publicKey().deriveAddress();
        XAddress xAddress = AddressCodec.getInstance().classicAddressToXAddress(classicAddress, true);
        System.out.println("Classic Address: " + classicAddress);
        System.out.println("X-Address: " + xAddress);

        // Lấy Private Key và Public Key
//        PrivateKey privateKey = randomKeyPair.privateKey();
        PublicKey publicKey = randomKeyPair.publicKey();

        byte[] privateKeyBytes = randomKeyPair.privateKey().value().toByteArray();

// In ra Private Key (ở dạng hex)
        System.out.println("Private Key: " + bytesToHex(privateKeyBytes));
        System.out.println("Public Key: " + publicKey.base16Value());

        // Fund the account using the testnet Faucet
        FaucetClient faucetClient = FaucetClient.construct(HttpUrl.get("https://faucet.altnet.rippletest.net"));
        faucetClient.fundAccount(FundAccountRequest.of(classicAddress));
        System.out.println("Funded the account using the Testnet faucet.");

        // Look up your Account Info
        AccountInfoRequestParams requestParams = AccountInfoRequestParams.of(classicAddress);
        AccountInfoResult accountInfoResult = xrplClient.accountInfo(requestParams);

        // Print the result
        System.out.println(accountInfoResult);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes) {
            sb.append(String.format("%02X", b)); // in hoa, dùng "%02x" nếu bạn muốn chữ thường
        }
        return sb.toString();
    }
}

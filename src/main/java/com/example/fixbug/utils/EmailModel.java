package com.example.fixbug.utils;

import com.example.fixbug.ErrorCallback;
import com.example.fixbug.oauth2.OAuth2SaslClientFactory;
import com.example.fixbug.objects.EmailObject;

import javax.mail.*;

import com.sun.mail.imap.IMAPSSLStore;
import com.sun.mail.imap.IMAPStore;
import org.apache.log4j.Logger;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmailModel {
//    private final static Logger logger = Logger.getLogger(EmailModel.class.getSimpleName());

    private static final String EMAIL_SENDER = "nonstock.report@gmail.com";
    private static final String EMAIL_SENDER_NAME = "Nonstock Reporter";
    private static final String PASSWORD_SENDER = "melonkari2017";

    public static String getHostByDomain(String domain) {
        // Need multi domain
        if (domain.contains("gmail.com")) {
            return "imap.gmail.com";
        }
        if (domain.contains("yahoo.co.jp")) {
            return "imap.mail.yahoo.co.jp";
        }
        return "imap.gmail.com";
    }

    public static void sendEmail(String email, String subject, String content) {
        // Recipient's email ID needs to be mentioned.

        // Sender's email ID needs to be mentioned

        // Assuming you are sending email from localhost

        // Get system properties
        Properties props = System.getProperties();
//        props.setProperty("mail.user", EMAIL_SENDER);
//        properties.setProperty("mail.password", PASSWORD_SENDER);

        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(EMAIL_SENDER, PASSWORD_SENDER);
                    }
                }
        );

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(EMAIL_SENDER, EMAIL_SENDER_NAME));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, "customer"));

            // Set Subject: header field
            message.setSubject(subject);

            // Send the actual HTML message, as big as you like
            message.setText(content, "utf-8", "html");

            // Send message
            Transport.send(message);
            //logger.info("Sent message successfully :" + email);
        } catch (MessagingException | UnsupportedEncodingException mex) {
            //logger.error("Sent message error:" + email + " - " + mex);
        }
    }

    public static List<EmailObject> readEmail(String host, String port, String email, String password, SearchTerm condition) {
        /*
            String host = "imap.gmail.com";
            String port = "993";
            String mailStoreType = "imap";
            String email = "yourmailaddress@gmail.com";
            String password = "yourpassword";
        */
        try {

            // create properties
            Properties properties = new Properties();

            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", port);
            properties.put("mail.imap.starttls.enable", "true");
            properties.put("mail.imap.ssl.trust", host);


            Session emailSession = Session.getDefaultInstance(properties);
//            Session emailSession = Session.getInstance(properties);
            //logger.info("#readEmail: " + email + " - emailSession: " + emailSession.getProperties());
            // create the imap store object and connect to the imap server
            Store store = emailSession.getStore("imaps");

            //logger.info("readEmail : host: " + host + " - email: " + email + " - password: " + password);

            store.connect(host, email, password);

            // create the inbox object and open it
            Folder inbox = store.getFolder("Inbox");
            inbox.open(Folder.READ_ONLY);
            Message[] messagess = inbox.search(condition);

            List<EmailObject> mailList = new ArrayList<>();
            Folder[] folders = store.getDefaultFolder().list();
            for (Folder folder : folders){
                UIDFolder uf = (UIDFolder) folder;
                folder.open(Folder.READ_ONLY);
                Message[] messages = folder.search(condition);

                for (int i = 0, n = messages.length; i < n; i++) {
                    Message message = messages[i];
                    mailList.add(new EmailObject(uf.getUID(message), message.getSubject(), getTextFromMessage(message), message.getFrom()[0].toString(), message.getSentDate().getTime()));
                }
            }
            //logger.info(String.format("#readEmail length: %s - %s", email, mailList.size()));
            //logger.info("#readEmail parse ok: " + email);
            new Thread(() -> {
                try {
                    for (Folder folder : folders){
                        folder.close(false);
                    }
                    store.close();
                    //logger.info("#readEmail close ok: " + email);
                } catch (Exception e) {
                    //logger.error("#readEmail close Exception " + email, e);
                }
            }).start();
            return mailList;
        } catch (Exception e) {
            //logger.error(String.format("#readEmail Exception email: %s - error: %s ", email, e));
        }
        return new ArrayList<>();
    }

    public static List<EmailObject> readEmailPassword(String host, String port, String email, String pass, SearchTerm condition, boolean isReadContent){
        try {
            Properties props = new Properties();
            props.put("mail.imap.host", host);
            props.put("mail.imap.port", port);
            props.put("mail.imap.starttls.enable", "true");
            props.put("mail.imap.ssl.trust", host);
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore("imaps");
            store.connect(host, email, pass);

            Folder inbox = store.getFolder("Inbox");

            UIDFolder uf = (UIDFolder) inbox;
            inbox.open(Folder.READ_ONLY);
            // retrieve the messages from the folder in an array and print it
            List<EmailObject> mailList = new ArrayList<>();
            Message[] messages = inbox.search(condition);
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                //System.out.println("Text: " + getTextFromMessage(message));
                String content = isReadContent ? getTextFromMessage(message) : "";
                //mailList.add(new EmailObject(message.getSubject(), content, message.getFrom()[0].toString(), message.getSentDate().getTime()));
            }
            //logger.info(String.format("#readEmail length: %s - %s", email, messages.length));
            //logger.info("#readEmail parse ok: " + email);
            new Thread(() -> {
                try {
                    inbox.close(false);
                    store.close();
                    //logger.info("#readEmail close ok: " + email);
                } catch (Exception e) {
                    //logger.error("#readEmail close Exception " + email, e);
                }
            }).start();
            return mailList;
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
            //logger.error(String.format("#readEmail Exception email: %s - error: %s ", email, e));
        }
        return new ArrayList<>();
    }
    /**
     * Connects and authenticates to an IMAP server with OAuth2. You must have
     * called {@code initialize}.
     *
     * @param host Hostname of the imap server, for example {@code
     *     imap.googlemail.com}.
     * @param port Port of the imap server, for example 993.
     * @param userEmail Email address of the user to authenticate, for example
     *     {@code oauth@gmail.com}.
     * @param oauthToken The user's OAuth token.
     * @param debug Whether to enable debug logging on the IMAP connection.
     *
     * @return An authenticated IMAPStore that can be used for IMAP operations.
     */
    public static IMAPStore connectToImap(String host,
                                          int port,
                                          String userEmail,
                                          String oauthToken,
                                          boolean debug) {
        try {
            Properties props = new Properties();
            props.put("mail.imaps.sasl.enable", "true");
            props.put("mail.imaps.sasl.mechanisms", "XOAUTH2");
            props.put(OAuth2SaslClientFactory.OAUTH_TOKEN_PROP, oauthToken);
            Session session = Session.getInstance(props);
            session.setDebug(debug);

            final URLName unusedUrlName = null;
            IMAPSSLStore store = new IMAPSSLStore(session, unusedUrlName);
            final String emptyPassword = "";
            store.connect(host, port, userEmail, emptyPassword);
            return store;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static List<EmailObject> readEmail(int time, String host, String port, String email, String pass, String token, SearchTerm searchTerm, ErrorCallback callback) {
        try {
            Properties props = new Properties();
            props.put("mail.imap.ssl.enable", "true");
            props.put("mail.imap.sasl.enable", "true");
            props.put("mail.imap.sasl.mechanisms", "XOAUTH2");
            props.put("mail.imap.auth.login.disable", "true");
            props.put("mail.imap.auth.plain.disable", "true");
            //props.put("mail.imaps.sasl.mechanisms.oauth2.oauthToken", token);
            Session session = Session.getDefaultInstance(props);
            Store store = session.getStore("imap");
            store.connect(host, email, token);

            Folder inbox = store.getFolder("Inbox");

            UIDFolder uf = (UIDFolder) inbox;
            inbox.open(Folder.READ_ONLY);
            // retrieve the messages from the folder in an array and print it
            List<EmailObject> mailList = new ArrayList<>();
            Message[] messages = inbox.search(searchTerm);
            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                mailList.add(new EmailObject(uf.getUID(message), message.getSubject(), getTextFromMessage(message), message.getFrom()[0].toString(), message.getSentDate().getTime()));
            }
            //logger.info(String.format("#readEmail length: %s - %s", email, messages.length));
            //logger.info("#readEmail parse ok: " + email);
            new Thread(() -> {
                try {
                    inbox.close(false);
                    store.close();
                    //logger.info("#readEmail close ok: " + email);
                } catch (Exception e) {
                    callback.error(e.getMessage(), e);
                    //logger.error("#readEmail close Exception " + email, e);
                }
            }).start();
            return mailList;
        } catch (Exception e) {
            //System.out.println(e.fillInStackTrace());
            if (time <= 5){
                time++;
                readEmail(time, host, port, email, pass, token, searchTerm, callback);
                return new ArrayList<>();
            }else {
                callback.error(e.getMessage(), e);
            }
            //logger.error(String.format("#readEmail Exception email: %s - error: %s ", email, e));
        }
        return new ArrayList<>();
    }
    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
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
    }
}


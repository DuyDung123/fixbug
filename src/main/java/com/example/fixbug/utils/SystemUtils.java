package com.example.fixbug.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.w3c.dom.CharacterData;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemUtils {
    private final static CustomLogger logger = new CustomLogger(SystemUtils.class.getSimpleName());
    private static final Gson gson = new Gson();

    public static File getPathFile(String productImageDir, String nameFile){

        String productImageFullPath = productImageDir + File.separator + nameFile;
        File file = new File(productImageFullPath);
        return file;
    }

    public static String[] parseFileNameFromJson(String strFileNameJson) {
        Gson gson = new Gson();
        return gson.fromJson(strFileNameJson, String[].class);
    }

    public static String getValueFormXml(String xml , String nodes) {

        try{
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));

            Document doc = db.parse(is);
            NodeList root = doc.getElementsByTagName(nodes);
            Element element = (Element) root.item(0);
            return getCharacterDataFromElement(element);
        }catch (ParserConfigurationException | IOException | SAXException e){
            e.printStackTrace();
            return null;
        }
    }

    private static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public static <T> T convertStringJonToObject(String json, Class<T> tClass){
        try {
            return gson.fromJson(json, tClass);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> fromStringToArray(String json, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        try {
            JsonArray jsonArray = convertStringJonToObject(json, JsonArray.class);
            if (jsonArray == null) return list;
            for (JsonElement element : jsonArray) {
                list.add(gson.fromJson(element, tClass));
            }
        } catch (Exception ex) {
        }
        return list;
    }


    public static String regexString(String input, String regex){

        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(input);
        String result = "";
        if(matcher.find()) {
            result = matcher.group(1).trim();
        }
        logger.info(String.format("#getProductIdFromMail result: %s - regex: %s",result, regex));
        return result;
    }

    public static String randomStringNumber(int length) {
        String abc = "0123456789";
        return randomString(abc, length);
    }

    public static String randomString(String input, int length) {
        int n = input.length();
        Random random = new Random();
        String res = "";
        for (int i = 0; i < length; i++) {
            res += (input.charAt(random.nextInt(n)));
        }
        return res;
    }

    public static String randomString(int length) {
        String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return randomString(abc, length);
    }

    public static int randomTime(int max, int min){
        return ((int) (Math.random() * (max - min)) + min) * 60;
    }
}

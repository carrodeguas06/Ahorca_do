package com.liceolapaz.bcdnob.ahorca_do.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Stream;

public class JSONReader {
    private Properties prop = new Properties();

    public JSONReader() {
        try {
            prop.load(getClass().getResourceAsStream("/.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getWords() {
        ArrayList<String> words = new ArrayList<>();
        try {
            String rutaArchivo = prop.getProperty("words");
            InputStream is = getClass().getResourceAsStream(rutaArchivo);

            if (is == null) {
                System.err.println("Error: No se encuentra el archivo de palabras en: " + rutaArchivo);
                return words;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Stream<String> documentos = br.lines();


            StringBuilder fullText = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                fullText.append(line);
            }

            org.json.JSONArray jsonArray = new org.json.JSONArray(fullText.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                words.add(obj.getString("palabra"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            words.add("ERROR");
        }
        return words;
    }
}
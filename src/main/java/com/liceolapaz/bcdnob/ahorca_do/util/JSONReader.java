package com.liceolapaz.bcdnob.ahorca_do.util;

import org.hibernate.mapping.List;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Stream;

public class JSONReader {
    private Properties prop = new Properties();
    public JSONReader()
    {

    }

    public ArrayList<String> getWords()
    {
        ArrayList<String> words = new ArrayList<>();
        try {
            BufferedReader br = Files.newBufferedReader(Paths.get(prop.getProperty("words")));
            Stream<String> documentos = br.lines();

            documentos.forEach(l -> {
                JSONObject obj = new JSONObject(l);
                words.add((String) obj.get("palabra"));
            });
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return words;
    }
}

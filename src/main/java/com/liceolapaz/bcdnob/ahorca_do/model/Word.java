package com.liceolapaz.bcdnob.ahorca_do.model;

import com.liceolapaz.bcdnob.ahorca_do.util.JSONReader;

import java.util.ArrayList;
import java.util.Random;

public class Word {

    public static String getSecretWord()
    {
        JSONReader reader = new JSONReader();
        ArrayList<String> words = reader.getWords();
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()+1));
    }
}

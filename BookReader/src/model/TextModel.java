package model;

import reader.Language;
import reader.NewTextReader;

import java.io.FileInputStream;
import java.io.IOException;

public class TextModel extends AbstractModel{
    private String text;


    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

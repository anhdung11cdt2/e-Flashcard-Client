/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Welcome
 */
public class Word {
    public String name;
    public String meaning;
    public String id;
    public String type;
    public String content;
    public String language;
    public String translation;
    public String pronunciation;
    
    public Word() {
    }

    public Word(String name, String meaning) {
        this.name = name;
        this.meaning = meaning;
    }
    
    public Word(String id, String type, String content, String language, String translation, String pronunciation){
        this.id = id;
        this.type = type;
        this.content = content;
        this.language = language;
        this.translation = translation;
        this.pronunciation = pronunciation;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s",this.id, this.type, this.content, this.language, this.translation, this.pronunciation);
    }
    
    
}

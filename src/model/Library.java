/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Welcome
 */
public class Library {
    public String name;
    public ArrayList<Word> words;
    public String id;
    
    public Library() {
    }
    
    public Library(String id, String name){
        this.id = id;
        this.name = name;
    }
    
    public Word getWord(int index){
        return words.get(index);
    }
    
    public void loadLibrary(String idLib, String name){
        //8AyqBmQpncbKnjIYNRWD
        this.name = name;
        this.words = new ArrayList<>();
        List<Word> result = new ArrayList<>();
        try{
        URL url = new URL("http://159.89.202.84:8080/api/words?set_id=" + idLib);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            String m = "";
            while ((inputLine = in.readLine()) != null){
                m += inputLine + "\n";
            }
            JSONObject json = new JSONObject(m);
            JSONArray libss = json.getJSONArray("resources");
            for (int i = 0; i< json.getJSONArray("resources").length(); i++){
               String id = libss.getJSONObject(i).getString("Id");
               String type = libss.getJSONObject(i).getString("Type");
               String content = libss.getJSONObject(i).getString("Content");
               String language = libss.getJSONObject(i).getString("Language");
               String translation = libss.getJSONObject(i).getString("Translation");
               String pronunciation = libss.getJSONObject(i).getString("Pronunciation");
               Word word = new Word(id, type, content, language, translation, pronunciation);
               result.add(word);
//               System.out.println(libss.getJSONObject(i).getString("Content"));
            }
            in.close();
        } catch (Exception e){
            try{
                File file = new File("src/libLocal/" + idLib + ".csv");
                System.out.println("load file: " + file.getAbsolutePath());
            BufferedReader inp = new BufferedReader(new InputStreamReader (new FileInputStream(file)));
            this.name = inp.readLine();
            while (true){
                String line = inp.readLine();
                if (line == null) break;
                String[] record = line.split(",");
                Word word = new Word(record[0], record[1], record[2], record[3],record[4], record[5]);
                result.add(word);
            }
            } catch (FileNotFoundException ex){
                ex.printStackTrace();
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
        this.words = new ArrayList<>(result);
        saveLib(this);
    }
    
    public void saveLib(Library lib){
        File file = new File("src/libLocal/" + lib.id + ".csv");
        try{
            PrintWriter pr = new PrintWriter(file);
            pr.println(lib.name);
            for (Word word : lib.words){
                pr.println(word.toString());
            }
            pr.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    public int getLength(){
        return words.size();
    }
    
    public Library(String name, File file){
        this.name = name;
        List<Word> result = new ArrayList<>();
        
        try {
            Scanner input = new Scanner(file);
            
            while (input.hasNext()){
                String line = input.nextLine();
                System.out.println(line);
                String[] tmp = line.split(",");
                Word tmpWord = new Word(tmp[0], tmp[1]);
                result.add(tmpWord);
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        this.words = new ArrayList(result);
    }
}

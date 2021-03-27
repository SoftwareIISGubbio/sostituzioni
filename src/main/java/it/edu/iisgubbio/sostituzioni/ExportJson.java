package it.edu.iisgubbio.sostituzioni;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.edu.iisgubbio.sostituzioni.oggetti.Docente;

public class ExportJson {

    public static void main(String[] args) {
        ArrayList<Docente> x = Ambiente.getDocenti();
        Gson gestoreJson = new GsonBuilder().setPrettyPrinting().create();
        String k = gestoreJson.toJson(x);
        try (FileWriter uscita = new FileWriter("/volumes/ramdisk/dati.json");){
            uscita.write(k);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

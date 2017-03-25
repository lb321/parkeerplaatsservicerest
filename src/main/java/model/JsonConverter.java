package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class JsonConverter {
    private static final String filename = "parkeerplaats.json";
    private String path;
    private static String staticPath = null;
    private static Gson gson;

    /*public JsonConverter(){
        URL url = null;
        try {
            url = this.getClass().getClassLoader().getResource(filename);
            //// TODO: 23-3-2017
            //Paths.get(this.g);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        try{
            if (url != null)
                path = url.toURI().getPath();
            else { //het bestand bestaat nog niet, dus bestand aanmaken en standaard parkeerplaats erin zetten
                File newFile = new File(JsonConverter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + filename);
                System.out.println(JsonConverter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
                //maak bestand aan
                newFile.getParentFile().mkdirs();
                newFile.createNewFile();
                //url = this.getClass().getClassLoader().getResource(filename);
                //path = url.toURI().getPath();
                path = newFile.toURI().getPath();
                System.out.println("final path: " + path);
                //schrijf standaard parkeer plaats in bestand
                parkeerplaatsToJson(new Parkeerplaats());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }*/

    public JsonConverter(){
        File file = null;
        try {
            file = new File(JsonConverter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + filename);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        path = file.getAbsolutePath();
        System.out.println(path);
        //parkeerplaatsToJson(new Parkeerplaats());
    }

    private static Gson getGson(){
        if(gson == null){
            GsonBuilder builder = new GsonBuilder();
          /*  builder.registerTypeAdapter(XMLGregorianCalendar.class,
                    new XMLGregorianCalendarConverter.Serializer());
            builder.registerTypeAdapter(XMLGregorianCalendar.class,
                    new XMLGregorianCalendarConverter.Deserializer());*/
            gson = builder.create();
        }
        return gson;
    }

    public String getPath(){
        return path;
    }

    private static String getFilePath(){
        if(staticPath == null){
            staticPath = new JsonConverter().getPath();
            if(getParkeerplaats() == null)
                parkeerplaatsToJson(new Parkeerplaats());
        }
        return staticPath;
    }

    public static void parkeerplaatsToJson(Parkeerplaats parkeerplaats){
        try(Writer writer = new FileWriter(getFilePath())){
            getGson().toJson(parkeerplaats,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Parkeerplaats getParkeerplaats(){
        Parkeerplaats parkeerplaats = null;
        try(Reader reader = new FileReader(getFilePath())){
            parkeerplaats = getGson().fromJson(reader, Parkeerplaats.class);
        } catch (FileNotFoundException e) {
            new File(getFilePath());
            System.out.println("file not found");
            parkeerplaatsToJson(new Parkeerplaats(10,10,0));
            getParkeerplaats();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parkeerplaats;
    }

}

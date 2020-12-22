package com.game;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Jugador implements Serializable {
    int[] victorias = new int[4];
    int[] derrotas = new int[4];
    private static String saveFileName = "statsFile";


    public Jugador(){
        for(int i=0; i<victorias.length; i++) {
            victorias[i] = 0;
            derrotas[i] = 0;
        }
    }

    public void editStats(int difficulty, boolean victory){
        if(victory){
            victorias[difficulty] += 1;
        } else{
            derrotas[difficulty] += 1;
        }
    }

    public void saveSerializable(Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(saveFileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this);

            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Jugador readSerializable(Context context) {
        Jugador objectToReturn = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(saveFileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectToReturn = (Jugador) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return objectToReturn;
    }

    public static void removeSerializable(Context context) {
        context.deleteFile(saveFileName);
    }






}

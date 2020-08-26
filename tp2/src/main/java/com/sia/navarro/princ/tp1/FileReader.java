package com.sia.navarro.princ.tp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class FileReader extends Reader {
    private static File file;
    public FileReader(File file) {
        this.file = file;
    }

    public static ArrayList<String[]> tsvr() {
        ArrayList<String[]> Data = new ArrayList<>();
        try (BufferedReader TSVReader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = TSVReader.readLine()) != null) {
                String[] lineItems = line.split("\t");
                Data.add(lineItems);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
        return Data;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {

    }
}

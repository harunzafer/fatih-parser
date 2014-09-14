package com.hrzafer.fatihparser.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Resources {

    public static List<String> getLines(String path) {
        InputStream is = get(path);
        String str = IO.read(is);
        return toLines(str);
    }

    public static String read(String path) {
        InputStream is = get(path);
        return IO.read(is);
    }

    public static boolean resourceExists(String path) {
        if (Resources.class.getClassLoader().getResourceAsStream(path) == null) {
            return false;
        }
        return true;
    }

    public static InputStream get(String path) {
        File file = new File(path);
        if (file.isFile()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            InputStream is = Resources.class.getClassLoader().getResourceAsStream(path);
            return is;
        }
        return null;
    }

    private static List<String> toLines(String str) {
        Scanner scanner = new Scanner(str);
        List<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line);
        }
        scanner.close();
        return lines;
    }
}

package com.custom;

import java.io.*;

public class MainEntry {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Error! First argument must be the source directory address " +
                    "and the second must be the destination directory address.");
            return;
        }
        copyDirectory(args[0], args[1], true);
    }

    private static void copyDirectory(String sourcePath, String destPath, boolean isSourcePathRoot) throws IOException {
        File fileSource = new File(sourcePath);
        if (fileSource.isDirectory()) {
            if (!isSourcePathRoot) {
                File directoryDest = new File(destPath);
                directoryDest.mkdir();
            }
            String[] children = fileSource.list();
            for (String childPath : children) {
                copyDirectory(
                        sourcePath + File.separator + childPath,
                        destPath + File.separator + childPath,
                        false);
            }
        } else {
            File fileDest = new File(destPath);
            fileDest.createNewFile();
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                fileInputStream = new FileInputStream(fileSource);
                fileOutputStream = new FileOutputStream(fileDest);
                int read;
                while ((read = fileInputStream.read()) != -1)
                    fileOutputStream.write(read);
            } finally {
                try {
                    if (fileInputStream != null)
                        fileInputStream.close();
                } finally {
                    if (fileOutputStream != null)
                        fileOutputStream.close();
                }
            }
        }
    }


    private static void copyDisk(String from, String to) throws FileNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(from);
             FileOutputStream fileOutputStream = new FileOutputStream((to))) {
            int read;
            while ((read = fileInputStream.read()) != -1) {
                fileOutputStream.write(read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

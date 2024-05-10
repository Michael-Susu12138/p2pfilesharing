package com.michael.p2p.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for file operations.
 */
public class FileUtils {

    public static List<String> listFiles(String directoryPath) {
        List<String> fileList = new ArrayList<>();
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getName());
                }
            }
        }
        return fileList;
    }
}
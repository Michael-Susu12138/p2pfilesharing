package com.michael.p2p.file;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file read, write, send, and receive operations.
 */
public class FileService {

    public static void sendFile(Socket socket, File file) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        fis.close();
        out.close();
    }

    public static void receiveFile(Socket socket, File file) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = in.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
        fos.close();
        in.close();
    }

    public static byte[] readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        byte[] fileBytes = bos.toByteArray();
        fis.close();
        bos.close();
        return fileBytes;
    }

    public static void writeFile(byte[] data, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.close();
    }
}


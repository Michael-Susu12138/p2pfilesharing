package com.michael.p2p.network;

import com.michael.p2p.file.FileService;
import java.io.*;
import java.net.Socket;

public class FileClient {
    private String host;
    private int port;

    public FileClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void requestFile(String fileName) {
        try (Socket socket = new Socket(host, port);
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             DataInputStream in = new DataInputStream(socket.getInputStream())) {

            // Send file request to server
            out.writeUTF(fileName);

            // Wait for the server to send a response
            String response = in.readUTF();
            if ("File not found".equals(response)) {
                System.out.println("File not found on the server.");
            } else if ("Sending file".equals(response)) {
                File file = new File("downloaded_" + fileName);
                FileService.receiveFile(socket, file);
                System.out.println("File downloaded: " + file.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }
}

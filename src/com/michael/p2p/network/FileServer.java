package com.michael.p2p.network;

import com.michael.p2p.file.FileService;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer extends Thread {
    private ServerSocket serverSocket;

    public FileServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started on port: " + port);
    }

    public void run() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket.getRemoteSocketAddress());
                new FileHandler(clientSocket).start();
            } catch (IOException e) {
                System.out.println("Server error: " + e.getMessage());
                break;
            }
        }
    }

    private class FileHandler extends Thread {
        private Socket socket;

        public FileHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                String fileName = in.readUTF();
                File fileToSend = new File("C:\\Users\\User\\eclipse-workspace\\p2pfilesharing\\bin\\serverFiles/" + fileName);
                if (fileToSend.exists() && !fileToSend.isDirectory()) {
                    out.writeUTF("Sending file");
                    FileService.sendFile(socket, fileToSend);
                    System.out.println("File sent: " + fileName);
                } else {
                    out.writeUTF("File not found");
                    System.out.println("Requested file not found: " + fileName);
                }
            } catch (IOException e) {
                System.out.println("Error handling client " + socket.getRemoteSocketAddress() + ": " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        int port = 5000; //  port number
        if (args.length > 0) {
            port = Integer.parseInt(args[0]); // setting the port number via command line
        }

        try {
            new FileServer(port).start();
        } catch (IOException e) {
            System.out.println("Could not start server: " + e.getMessage());
        }
    }
}

package com.michael.p2p.runner;

import com.michael.p2p.network.FileServer;
import com.michael.p2p.network.FileClient;

import java.io.IOException;
import java.util.Scanner;

public class P2PApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the server port: ");
        int port = scanner.nextInt();
        scanner.nextLine();  

        // start server
        try {
            FileServer server = new FileServer(port);
            server.start();
        } catch (IOException e) {
            System.out.println("Unable to start server: " + e.getMessage());
            return;
        }

        // clients
        System.out.println("Enter commands (connect [host] [port] [file], quit):");
        while (true) {
            String command = scanner.nextLine();
            if (command.equals("quit")) {
                break;
            } else if (command.startsWith("connect ")) {
                String[] parts = command.split(" ");
                FileClient client = new FileClient(parts[1], Integer.parseInt(parts[2]));
                client.requestFile(parts[3]);
            }
        }

        System.out.println("Shutting down...");
        scanner.close();
    }
}
package org.example.client.Client;

import java.io.IOException;

public class ClientMain {
    public static Client client;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        client = new Client("127.0.0.1", 4200);
        client.startInteractiveMode();
    }
}

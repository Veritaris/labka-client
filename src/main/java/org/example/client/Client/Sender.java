package org.example.client.Client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

@SuppressWarnings("FieldCanBeLocal")
public class Sender {
    private final DatagramSocket datagramSocket;
    public DatagramPacket datagramPacket;
    private ByteArrayOutputStream baos;
    private ObjectOutputStream oos;
    private final int serverPort;

    public Sender(DatagramSocket datagramSocket, int serverPort) {
        this.datagramSocket = datagramSocket;
        this.serverPort = serverPort;
    }

    public void sendMessage(Object commandObject, InetAddress address) {
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(commandObject);
            byte[] payload = baos.toByteArray();
            datagramPacket = new DatagramPacket(payload, payload.length, address, this.serverPort);
            this.datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            System.out.printf("Something wrong with given message: %s\n", commandObject.toString());
        }
    }
}

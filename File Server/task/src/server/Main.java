package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int PORT = 2137;
    private static final String address = "127.0.0.1";
    public static void main(String[] args) {
        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(address))) {
            RequestHandler requestHandler = new RequestHandler(server);
            while (!server.isClosed()) {
                Socket socket = server.accept();
                executor.submit(() -> {
                    try (
                            DataInputStream input = new DataInputStream(socket.getInputStream());
                            DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                    ) {
                        String request = input.readUTF();
                        synchronized (requestHandler) {
                            requestHandler.setRequest(request);
                            requestHandler.chooseAction(input, output);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
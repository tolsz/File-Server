package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Connection {
    private static final int PORT = 2137;
    private static final String address = "127.0.0.1";
    final String path =  System.getProperty("user.dir") + "/src/client/data/";
    //my path
    //final String path = System.getProperty("user.dir") + "/File Server/task/src/client/data/";
    InputHandler handler;

    public Connection() {
        this.handler = new InputHandler();
        handler.process();
    }

    public void connect() {
        try (
                Socket socket = new Socket(InetAddress.getByName(address), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            output.writeUTF(handler.getRequest());
            System.out.println("The request was sent.");
            if (handler.option.equals("GET")) {
                getFile(input);
            } else if (handler.option.equals("PUT")) {
                putFile(output, input);
            } else if (handler.option.equals("DELETE")) {
                deleteFile(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void putFile(DataOutputStream output, DataInputStream input) {
        File file = new File(path + handler.getFileName());
        int length = (int) file.length();
        byte[] message = new byte[length];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(message);
            output.writeInt(length);
            output.write(message);
            output.flush();
            String response = input.readUTF();
            String[] responseArray = response.split(" ");
            if ("200".equals(responseArray[0])) {
                System.out.println("Response says that file is saved! ID = " + responseArray[1]);
            } else {
                System.out.println("The response says that creating the file was forbidden!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getFile(DataInputStream input) {
        try {
            String code = input.readUTF();
            if ("404".equals(code)) {
                System.out.println("The response says that this file is not found!");
                return;
            }
            int length = input.readInt();
            byte[] message = new byte[length];
            input.readFully(message, 0, message.length);
            System.out.print("The file was downloaded! Specify a name for it: ");
            String name = new Scanner(System.in).nextLine();
            FileOutputStream fos = new FileOutputStream(path + name);
            fos.write(message);
            fos.close();
            System.out.println("File saved on the hard drive!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(DataInputStream input) {
        try {
            String response = input.readUTF();
            if ("200".equals(response)) {
                System.out.println("The response says that this file was deleted successfully!");
            } else {
                System.out.println("The response says that this file is not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

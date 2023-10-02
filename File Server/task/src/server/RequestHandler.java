package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class RequestHandler {
    private String[] request;
    private String action;
    private final ServerSocket server;
    private final Operations operations;
    private String fileNameOrID;

    public RequestHandler(ServerSocket server) {
        operations = new Operations();
        this.server = server;
    }

    public void chooseAction(DataInputStream dis, DataOutputStream dos) {
        switch (action) {
            case "PUT":
                fileNameOrID = request[1];
                operations.put(dis, dos, fileNameOrID);
                break;
            case "exit":
                operations.save();
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "GET":
                if ("ID".equals(request[1])) {
                    fileNameOrID = request[2];
                    operations.get(dos, fileNameOrID, false);
                } else if ("NAME".equals(request[1])) {
                    fileNameOrID = request[2];
                    operations.get(dos, fileNameOrID, true);
                }
                break;
            case "DELETE":
                if ("ID".equals(request[1])) {
                    fileNameOrID = request[2];
                    operations.delete(dos, fileNameOrID, false);
                } else if ("NAME".equals(request[1])) {
                    fileNameOrID = request[2];
                    operations.delete(dos, fileNameOrID, true);;
                }
        }
    }

    public void setRequest(String request) {
        this.request = request.split(" ");
        action = this.request[0];
    }
}

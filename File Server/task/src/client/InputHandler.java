package client;

import java.util.Scanner;

public class InputHandler {
    private final static Scanner scanner = new Scanner(System.in);
    private final StringBuilder request = new StringBuilder();
    private String fileName;
    String option;

    public void process() {
        System.out.print("Enter action (1 - get a file, 2 - save a file, 3 - delete a file): ");
        String command = scanner.nextLine();
        switch (command) {
            case "1":
                request.append("GET ");
                option = "GET";
                getDeleteFile("get");
                break;
            case "2":
                request.append("PUT ");
                option = "PUT";
                putFile();
                break;
            case "3":
                request.append("DELETE ");
                option = "DELETE";
                getDeleteFile("delete");
                break;
            case "exit":
                request.append("exit");
                option = "exit";
        }
    }

    private void getDeleteFile(String getOrDelete) {
        if ("get".equals(getOrDelete)) {
            System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): ");
        } else {
            System.out.print("Do you want to delete the file by name or by id (1 - name, 2 - id): ");
        }
        String option = scanner.nextLine();
        if ("1".equals(option)) {
            request.append("NAME ");
            System.out.print("Enter name: ");
            request.append(scanner.nextLine());
        } else if ("2".equals(option)) {
            request.append("ID ");
            System.out.print("Enter id: ");
            request.append(scanner.nextLine());
        }
    }

    private void putFile() {
        System.out.print("Enter name of the file: ");
        fileName = scanner.nextLine();
        System.out.print("Enter name of the file to be saved on server: ");
        String serverName = scanner.nextLine();
        if (serverName.isEmpty()) {
            request.append(fileName);
        } else {
            request.append(serverName);
        }
    }

    public String getRequest() {
        return request.toString();
    }

    public String getFileName() {
        return fileName;
    }
}

package client;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
        Connection connection = new Connection();
        connection.connect();
    }
}

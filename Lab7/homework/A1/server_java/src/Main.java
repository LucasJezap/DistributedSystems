import server.CryptoServer;

import java.io.IOException;
import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        CryptoServer cryptoServer = new CryptoServer();
        cryptoServer.run();
        System.out.println("[" + new Timestamp(System.currentTimeMillis()) + "] The server has closed");
    }
}

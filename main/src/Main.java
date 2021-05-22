import game.Game;
import ia.Basic;
import network.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Game.getInstance().ia = new Basic();
        Client client = Client.getInstance();
        client.connect("127.0.0.1",2121);
    }
}

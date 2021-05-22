package game;

import tile.*;

public class Game {

    public static Game game;
    public Tile[][] tiles;

    public static void parseMap(String mapStr) {
        Tile[][] tiles = new Tile[31][31];
        for (int i = 0; i < 31; i++) {
            for (int j = 0; j < 31; j++) {
                char current = mapStr.charAt(i*31+j);
                switch (current) {
                    case 'R': tiles[i][j] = new Road(); break;
                    case 'E': tiles[i][j] = new Empty(); break;
                    case 'H': tiles[i][j] = new House(); break;
                    case 'S': tiles[i][j] = new Restaurant(); break;
                }
            }
        }
        Game.game = new Game();
        Game.game.tiles = tiles;
    }

}

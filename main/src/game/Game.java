package game;

import deliveries.Biker;
import tile.*;
import utils.Position;

public class Game {

    private static Game game;

    public Tile[][] tiles;
    public int teamNumber;
    public Biker[] bikers;

    private Game() {
        bikers = new Biker[2];
    }

    public static Game getInstance() {
        if(game == null) game = new Game();
        return game;
    }

    public void parseMap(String mapStr) {
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
        Game.game.tiles = tiles;
    }

    public void initBikers(int id, int x, int y) {
        bikers[id] = new Biker(new Position(x,y));
    }

}

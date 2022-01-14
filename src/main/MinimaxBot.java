package main;

public class MinimaxBot {
    private final int maxDepth = 3;
    private Point tileToKill;
    private Point tileToRevive;

    public MinimaxBot() {

    }

    public void playTurn(Game game) {
        calculateTiles(0, true, new Game(game));
        game.setAliveAt(tileToKill.i, tileToKill.j, false);
        game.setAliveAt(tileToRevive.i, tileToRevive.j, true);
        game.cycle();
        game.switchPlayer();
    }

    private int calculateTiles(int currDepth, boolean redPlayer, Game game) {
        if(game.isOver()) {
            if(game.winner() == Player.BLUE) return -1000;
            else return 1000;
        }
        if(currDepth == maxDepth) {
            return game.getRedCells() - game.getBlueCells();
        }
        int bestValue = redPlayer ? -1000 : 1000;
        Point bestKill = new Point(-1, -1);
        Point bestRevive = new Point(-1, -1);
        for(Tile kill : game.getKillableTiles()) {
            for(Tile revive : game.getRevivableTiles()) {
                Game newGame = new Game(game);
                newGame.setAliveAt(kill.getPos().i, kill.getPos().j, false);
                newGame.setAliveAt(revive.getPos().i, revive.getPos().j, true);
                newGame.switchPlayer();
                game.cycle();
                int val = calculateTiles(currDepth+1, !redPlayer, newGame);
                if((redPlayer && val >= bestValue) || (!redPlayer && val <= bestValue)) {
                    bestValue = val;
                    bestKill = kill.getPos();
                    bestRevive = revive.getPos();
                }
            }
        }
        if(currDepth == 0) {
            tileToKill = bestKill;
            tileToRevive = bestRevive;
        }
        return bestValue;
    }
}

import java.util.*;

public class Game {
    
    private int [][]m = new int[10][10];
    private static final int MINE = -1;
    
    private Game() {
    }
    
    private static final class SingletonHolder {
        private static final Game SINGLETON = new Game();
    }
    
    public static Game getInstance() {
        return SingletonHolder.SINGLETON;
    }
    
    public int getCellValue(int i,int j) {
        return m[i][j];
    }
    
    public void startGame() {
        init();
        generateMines(20);
        calculateEmptySpaces();
    }
    
    private void init() {
        for (int i=0; i < m.length; i++) {
            for (int j=0; j < m[i].length; j++) {
                m[i][j] = 0;
            }
        }
    }
    
    private void generateMines(int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            
            if (m[x][y] == MINE) {
                i--;
            } else {
                m[x][y] = MINE;
            }
        }
    }
    
    private void calculateEmptySpaces() {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[i].length; j++) {
                if (m[i][j] != MINE) {
                    int noOfMine = 0;
                    noOfMine += isMine(i-1,j-1) ? 1 : 0;
                    noOfMine += isMine(i,j-1) ? 1 : 0;
                    noOfMine += isMine(i+1,j-1) ? 1 : 0;
                    noOfMine += isMine(i-1,j) ? 1 : 0;
                    noOfMine += isMine(i+1,j) ? 1 : 0;
                    noOfMine += isMine(i-1,j+1) ? 1 : 0;
                    noOfMine += isMine(i,j+1) ? 1 : 0;
                    noOfMine += isMine(i+1,j+1) ? 1 : 0;
                    m[i][j] = noOfMine;
                }
            }
        }
    }
    
    private boolean isMine(int i, int j) {
        try {
            return m[i][j] == MINE;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}
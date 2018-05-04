// $Id: Tile.java,v 1.1 2014/12/01 00:33:19 cheon Exp $

package kushnir.andrey.n_puzzle;

/** Данный класс представляет собой уникальную клетку игровой доски. */
public class Tile {

    /** Номер клетки. */
    private final int number;
    
    /** Создать новую клетку, имеющую данный номер */
    public Tile(int number) {
        this.number = number;
    }

    /** Возвратить номер клетки. */
    public int number() {
        return number;
    }
}

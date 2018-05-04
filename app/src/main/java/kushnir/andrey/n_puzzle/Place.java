// $Id: Place.java,v 1.1 2014/12/01 00:33:19 cheon Exp $

package kushnir.andrey.n_puzzle;


public class Place {

    private final int x;

    private final int y;

    private final int position;

    private Tile tile;

    private Board board;

    public Place(int x, int y, int position, Board board) {
        this.x = x;
        this.y = y;
        this.position = position;
        this.board = board;
    }

    public Place(int x, int y, int number, int position, Board board) {
        this(x, y, position, board);
        tile = new Tile(number);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPos() {
        return position;
    }

    public boolean hasTile() {
        return tile != null;
    }

    /** Return the tile placed in this place; null is returned if no
     * tile is placed. */
    public Tile getTile() {
        return tile;
    }

    /** Place the given tile in this place. */
    public void setTile(Tile tile) {
        this.tile = tile;
    }
    
    /** Is the tile in this place slidable? Return false if this place
     * is empty, i.e., no tile is placed. */
    public boolean slidable() {
    	return hasTile() && board.slidable(this);
    }

}

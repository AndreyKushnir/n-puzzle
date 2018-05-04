// $Id: Board.java,v 1.1 2014/12/01 00:33:19 cheon Exp $

package kushnir.andrey.n_puzzle;

import java.util.*;


/**
 * A puzzle frame consisting of <code>size</code> * <code>size</code>
 * places where puzzle tiles can be placed.
 *
 * @see Place
 * @see Tile
 */
public class Board {

    /**
     * Dimension of this board. This board will have
     * <code>size</code> * <code>size</code> places.
     */
    private final int size;

    /**
     * Number of tile moves made so far.
     */
    private int numOfMoves;

    /**
     * Places of this board.
     */
    private final List<Place> places;

    /**
     * Listeners listening to board changes such as sliding of tiles.
     */
    private final List<BoardChangeListener> listeners;

    /**
     * To arrange tiles randomly.
     */
    private final static Random random = new Random();


    private final int lastPosition;

    /**
     * Create a new board of the given dimension. Initially, the tiles
     * are ordered with the blank tile as the last tile.
     */
    public Board(int size) {
        listeners = new ArrayList<BoardChangeListener>();
        this.size = size;
        lastPosition = size * size;
        places = new ArrayList<Place>(lastPosition);
        for (int y = 1; y <= size; y++) {
            for (int x = 1; x <= size; x++) {
                int position = (y - 1) * size + x - 1;
                places.add(x == size && y == size ?
                        new Place(x, y, position, this)
                        : new Place(x, y, position + 1, position, this));
            }
        }
        numOfMoves = 0;
    }

    /**
     * Rearrange the tiles to create a new, solvable puzzle.
     */
    public void rearrange() {
        numOfMoves = 0;
        for (int i = 0; i < lastPosition; i++) {
            swapTiles();
        }
        do {
            swapTiles();
        } while (!solvable() || solved());
    }

    /**
     * Swap two tiles randomly.
     */
    private void swapTiles() {

        int pos1 = lastPosition;
        int pos2 = lastPosition;

        while (pos1 == lastPosition || pos2 == lastPosition) {
            pos1 = random.nextInt(lastPosition);
            pos2 = random.nextInt(lastPosition);
        }
        if (pos1 != pos2) {
            Place p1 = places.get(pos1);
            Place p2 = places.get(pos2);
            Tile t = p1.getTile();
            p1.setTile(p2.getTile());
            p2.setTile(t);
        }
    }

    /**
     * Is the puzzle (current arrangement of tiles) solvable?
     */
    private boolean solvable() {
        // alg. from: http://www.cs.bham.ac.uk/~mdr/teaching/modules04/
        //                 java2/TilesSolvability.html
        //
        // count the number of inversions, where an inversion is when
        // a tile precedes another tile with a lower number on it.
        int inversion = 0;
        for (Place p : places) {
            Tile pt = p.getTile();
            for (Place q : places) {
                Tile qt = q.getTile();
                if (p != q && pt != null && qt != null &&
                        p.getPos() < q.getPos() &&
                        pt.number() > qt.number()) {
                    inversion++;
                }
            }
        }
        final boolean isEvenSize = size % 2 == 0;
        final boolean isEvenInversion = inversion % 2 == 0;
        boolean isBlankOnOddRow = blank().getY() % 2 == 1;
        // from the bottom
        isBlankOnOddRow = isEvenSize != isBlankOnOddRow;
        return (!isEvenSize && isEvenInversion) ||
                (isEvenSize && isBlankOnOddRow == isEvenInversion);
    }


    /**
     * Is this puzzle solved?
     */
    public boolean solved() {
        boolean result = true;
        for (Place p : places) {
            result = result &&
                    ((p.getPos() == lastPosition) ||
                            (p.getTile() != null &&
                                    p.getTile().number() == p.getPos()));
        }
        return result;
    }

    /**
     * Slide the given tile, which is assumed to be slidable, and
     * notify the change to registered board change listeners, if any.
     *
     * @see Board#slidable(Place)
     */
    public void slide(Place p) {
                final Place to = blank();
                to.setTile(p.getTile());
                p.setTile(null);
                numOfMoves++;
                notifyTileSliding(p, to, numOfMoves);
                if (solved()) {
                    notifyPuzzleSolved(numOfMoves);
                }
    }

    /**
     * Is the tile in the given place slidable?
     */
    public boolean slidable(Place place) {

        return isBlank(x - 1, y) || isBlank(x + 1, y)
                || isBlank(x, y - 1) || isBlank(x, y + 1);
    }

    /**
     * Is the place at the given indices empty?
     */
    private boolean isBlank(int x, int y) {
        return (0 < x && x <= size) && (0 < y && y <= size)
                && placeAt(x, y).getTile() == null;
    }

    /**
     * Return the blank place.
     */
    public Place blank() {
        for (Place p : places) {
            if (p.getTile() == null) {
                return p;
            }
        }
        //assert false : "should never reach here!";
        return null;
    }

    /**
     * Return all the places of this board.
     */
    public Iterable<Place> places() {
        return places;
    }

    /** Return the place at the given indices. */


    /**
     * Return the dimension of this board.
     */
    public int size() {
        return size;
    }

    /**
     * Return the number of tile moves made so far.
     */
    public int numOfMoves() {
        return numOfMoves;
    }

    /**
     * Зарегистрировать слушателя, реагирующего на изменения игровой доски.
     */
    public void addBoardChangeListener(BoardChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    /**
     * Unregister the given listener from listening to board changes.
     */
    public void removeBoardChangeListener(BoardChangeListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notify a tile sliding to registered board change listeners.
     */
    private void notifyTileSliding(Place from, Place to, int numOfMoves) {
        for (BoardChangeListener listener : listeners) {
            listener.tileSlid(from, to, numOfMoves);
        }
    }

    /**
     * Notify solving of the puzzle to registered board change listeners.
     */
    private void notifyPuzzleSolved(int numOfMoves) {
        for (BoardChangeListener listener : listeners) {
            listener.solved(numOfMoves);
        }
    }

    private void startTraining() {
        ArrayList<Integer> shortestPath = Algorithms.getShortestPath();
        for (Integer.)
    }


    public int getMinMoves() {
        return Algorithms.calcMinMoves(places, size);
    }

    /**
     * Реагирует на изменения игровой доски.
     */
    public interface BoardChangeListener {

        /**
         * Called when the tile located placeAt the <code>from</code>
         * place was slid to the empty <code>to</code> place. Both places
         * will be provided in new states; i.e., <code>from</code> will
         * be empty and <code>to</code> will be the tile moved.
         */
        void tileSlid(Place from, Place to, int numOfMoves);

        /**
         * Called when the puzzle is solved. The number of tile moves
         * is provided as the argument.
         */
        void solved(int numOfMoves);
    }

    public List<Place> getPlaces() {
        return places;
    }

    public int getSize() {
        return size;
    }
}

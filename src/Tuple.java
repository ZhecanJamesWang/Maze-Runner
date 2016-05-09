/**
 * Created by james.
 * Tuple used to store dual values together, used mainly for x and y coords.
 * @param <X> column value
 * @param <Y> row value
 */
public class Tuple<X, Y> {
    public final int x;
    public final int y;

    /**
     * Constructor which takes in an x and y, saves them.
     * @param x value
     * @param y value
     */
    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y value
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * Prints a string representation of the tuple
     * @return
     */
    public String toString() {
        return "(" + Integer.toString(x) + "," + Integer.toString(y) + ")";
    }
} 
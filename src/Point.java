public class Point implements Comparable<Point> {
    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + (x + 1) + "," + (y + 1) + ")";
    }

    @Override
    public int compareTo(Point other) {
        if (other == null) {
            return 1;
        }
        if (y < other.y) {
            return -1;
        }
        if (y > other.y) {
            return 1;
        }
        return x - other.x;
    }
}
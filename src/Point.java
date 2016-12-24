public class Point implements Comparable<Point> {
    public final int x, y;
    public int score;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + (x + 1) + "," + (y + 1) + ")";
    }

    @Override
    public int compareTo(Point other) {
        return other.score - score;
    }
}
import java.awt.geom.Line2D;
import java.util.List;

public class Math {

    public static boolean isLinesIntersect(Point p1, Point q1, Point p2, Point q2) {
        //does not handle special cases (but we don't need them in this problem)
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);
        return (o1 != o2 && o3 != o4);
    }

    /**
     * To find orientation of ordered triplet (p, q, r).
     * The function returns following values
     * 0 --> p, q and r are colinear
     * 1 --> Clockwise
     * 2 --> Counterclockwise
     */
    private static int orientation(Point p, Point q, Point r) {
        // See http://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        if (val == 0) {
            return 0;  // colinear
        }
        return (val > 0)? 1: 2; // clock or counterclock wise
    }

    public static double getArea(List<Point> points) {
        double area = 0.0;
        Point previous = points.get(points.size() - 1);

        for (int i=0; i< points.size(); i++) {
            Point current = points.get(i);
            area += (previous.getX() + current.getX()) * (previous.getY() - current.getY());
            previous = current;
        }
        return java.lang.Math.abs(area / 2);
    }


    public static Double calcRC(Point point1, Point point2) {
        int deltax = point2.getX() - point1.getX();
        int deltay = point2.getY() - point1.getY();
        if (deltay == 0) {
            //not possible
            return Double.MAX_VALUE;
        }
        return Double.valueOf((double)deltax / deltay);
    }
}

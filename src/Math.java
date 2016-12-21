public class Math {

    /**
     * Returns if two lines intersect
     */
    public static boolean isLinesIntersect(Point p1, Point q1, Point p2, Point q2) {
        //does not handle special cases (subsections, parallel lines) but we don't need them in this problem
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
     * -1 --> Counterclockwise
     */
    public static int orientation(Point p, Point q, Point r) {
        // See http://www.geeksforgeeks.org/orientation-3-ordered-points/
        // for details of below formula.
        int val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
        return (val == 0) ? 0 : (val > 0) ? 1 : -1;
    }

    /**
     * Calculates the area of a polygon
     */
    public static double getArea(Point[] points) {
        double area = 0.0;

        area += addDelta(points[points.length-1], points[0]);
        for (int i = 1; i < points.length; i++) {
            area += addDelta(points[i-1], points[i]);
        }

        return java.lang.Math.abs(area / 2);
    }

    /**
     * Adds a delta to calculate the polygone surface
     */
    public static double addDelta(Point a, Point b) {
        return (a.x + b.x) * (a.y - b.y);
    }

    /**
     * calculate the RC
     */
    public static double calcRC(Point point1, Point point2) {
        int deltax = point2.x - point1.x;
        int deltay = point2.y - point1.y;
        if (deltay == 0) {
            //not possible
            return Double.MAX_VALUE;
        }
        return (double)deltax / deltay;
    }
}

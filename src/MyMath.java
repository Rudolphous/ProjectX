public class MyMath {
    /**
     * Returns if two lines intersect
     */
    public static boolean isLinesIntersect(Point from1, Point to1, Point from2, Point to2) {
        if (isNotOverlapping(from1.x, to1.x, from2.x, to2.x)) {
            return false;
        }

        if (isNotOverlapping(from1.y, to1.y, from2.y, to2.y)) {
            return false;
        }

        //does not handle special cases (subsections, parallel lines) but we don't need them in this problem
        int o1 = orientation(from1, to1, from2);
        int o2 = orientation(from1, to1, to2);
        int o3 = orientation(from2, to2, from1);
        int o4 = orientation(from2, to2, to1);
        return o1 != o2 && o3 != o4;
    }

    private static boolean isNotOverlapping(int a, int b, int c, int d) {
        int maxab = Math.max(a, b);
        int mincd = Math.min(c, d);

        if (maxab < mincd) {
            return true;
        }

        int minab = Math.min(a, b);
        int maxcd = Math.max(c, d);

        return (minab > maxcd);
    }

    /**
     * To find orientation of ordered triplet (a, b, c).
     * The function returns following values
     * 0 --> a, b and c are collinear
     * 1 --> Clockwise
     * -1 --> Counterclockwise
     */
    public static int orientation(Point a, Point b, Point c) {
        int val = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
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
        //if (deltay == 0) {
            //not possible in this program
            //return Double.MAX_VALUE;
        //}
        return (double)deltax / deltay;
    }
}

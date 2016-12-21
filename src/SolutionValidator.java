import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

public class SolutionValidator {

    public boolean isValidSolutions(Point[] points) {
        BitSet gehadx = new BitSet(points.length);
        BitSet gehady = new BitSet(points.length);
        Point previousPoint = points[points.length - 1];

        Set<Double> listOfRCs = new HashSet<Double>();
        for (Point point : points) {
            Double currentRC = calcRC(previousPoint, point);
            if (listOfRCs.contains(currentRC)) {
                //we have already this direction
                return false;
            }
            listOfRCs.add(currentRC);
            gehadx.set(point.getX());
            gehady.set(point.getY());
            previousPoint = point;
        }

        int nextClearx = gehadx.nextClearBit(0);
        int nextCleary = gehady.nextClearBit(0);
        if (nextClearx != points.length || nextCleary != points.length) {
            return false;
        }

        return !hasIntersectingLines(points);
    }

    private boolean hasIntersectingLines(Point[] points) {
        int len = points.length;
        for (int i = 0; i <= len; i++) {
            for (int j = i + 1; j <= len; j++) {
                int index1 = validIndex(points, i - 1);
                int index2 = validIndex(points, i);
                int index3 = validIndex(points, j);
                int index4 = validIndex(points, j + 1);

                if (index1 == index3 || index2 == index3 || index1 == index4) continue;

                Point p1 = points[index1];
                Point p2 = points[index2];
                Point p3 = points[index3];
                Point p4 = points[index4];
                if (Math.isLinesIntersect(p1, p2, p3, p4)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static int validIndex(Point[] points, int index) {
        if (index < 0) index += points.length;
        if (index >= points.length) index -= points.length;
        return index;
    }

    private static Double calcRC(Point point1, Point point2) {
        int deltax = point1.getX() - point2.getX();
        int deltay = point2.getY() - point1.getY();
        if (deltay == 0) {
            //not possible
            return null;
        }
        return Double.valueOf((double) deltax / deltay);
    }

}

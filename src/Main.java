import java.util.Arrays;

public class Main {

    private static PolygonGenerator generator = new PolygonGenerator(23);
    private static double minArea = Double.MAX_VALUE;
    private static double maxArea = 0.0;
    private static Point[] maxPoints = null;
    private static Point[] minPoints = null;
    private static boolean higher = true;

    public static void solution(Point[] solution) {
        int area = generator.getArea();

        if (area > maxArea) {
            maxArea = area;
            System.out.println("\nmax(" + solution.length + "):" + toString(solution) + "=" + maxArea / 2);
            maxPoints = solution.clone();
        }
        if (area < minArea) {
            minArea = area;
            System.out.println("\nmin(" + solution.length + "):" + toString(solution) + "=" + minArea / 2);
            minPoints = solution.clone();
        }
    }

    private static String toString(Point[] solution) {
        return Arrays.toString(solution);
    }

    public static void main(String args[]) throws Exception {
        long start = System.nanoTime();
        improve("(19,4), (1,21), (2,1), (23,2), (22,23), (3,22), (4,19), (6,20), (5,18), (7,17), (8,15), (9,14), (11,16), (10,13), (15,11), (12,12), (17,6), (14,10), (18,7), (21,5), (16,8), (20,3), (13,9)");
        long duration = System.nanoTime() - start;
        System.out.println("duration: " + (double) duration / 1000000);
        System.out.println("solutions: " + generator.numberOfSolutions);
    }

    public static void improve(String s) {
        Point points[] = SolutionToPoinsConvertor.convertToPoints(s);
        generator = new PolygonGenerator(points.length);
        int len = points.length;

        start:

        for (int maxaantal=0; maxaantal< len; maxaantal++) {

            System.out.println("maxaantal: " + maxaantal);

            for (int start=0; start< len; start++) {
                generator.clear();
                System.out.print("S" + start + " ");

                for (int index=start; index<start + len - maxaantal; index++) {
                    generator.doMove(points[index % len]);
                }
                generator.generateAllSolutions();
                if (higher && maxPoints != null) {
                    points = maxPoints.clone();
                    maxaantal = 0;
                    maxPoints = null;
                    continue start;
                }

                if (!higher && minPoints != null) {
                    points = minPoints.clone();
                    maxaantal = 0;
                    minPoints = null;
                    continue start;
                }
            }
            System.out.println();
        }
    }

    public static void doMoves(String s) {
        Point points[] = SolutionToPoinsConvertor.convertToPoints(s);
        for (Point point : points) {
            generator.doMove(point);
        }
    }
}

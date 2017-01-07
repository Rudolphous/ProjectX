import java.util.Arrays;

public class Improve implements SolutionPrinter {

    private PolygonGenerator generator;
    private double minArea = Double.MAX_VALUE;
    private double maxArea = 0.0;
    private Point[] maxPoints = null;
    private Point[] minPoints = null;
    private static boolean higher = false;

    public void solution(Point[] solution) {
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
        Improve improve = new Improve();
        higher = args[0].equals("max");
        String polygon = "(36,40), (43,45), (35,43), (25,41), (41,44), (32,37), (30,36), (22,27), (29,35), (10,17), (26,47), (4,6), (1,2), (5,5), (2,3), (7,7), (14,1), (6,8), (8,11), (3,4), (9,15), (13,14), (17,10), (12,13), (18,9), (16,12), (11,16), (15,21), (19,25), (24,28), (20,19), (31,29), (21,20), (27,32), (23,22), (28,33), (34,38), (37,39), (38,31), (42,26), (33,34), (44,24), (46,18), (45,23), (39,30), (40,42), (47,46)";
        polygon = args[1];
        improve.doit(polygon);
    }

    private void doit(String s) {
        long start = System.nanoTime();
        improve(s);
        long duration = System.nanoTime() - start;
        System.out.println("duration: " + (double) duration / 1000000);
        System.out.println("solutions: " + generator.numberOfSolutions);
    }

    public void improve(String s) {
        Point points[] = SolutionToPoinsConvertor.convertToPoints(s);
        generator = new PolygonGenerator(points.length, this);
        int len = points.length;

        start:

        for (int maxaantal=0; maxaantal< 9999; maxaantal++) {

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
}

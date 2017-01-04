import java.util.Arrays;

public class Improve implements Printer {

    private PolygonGenerator generator;
    private double minArea = Double.MAX_VALUE;
    private double maxArea = 0.0;
    private Point[] maxPoints = null;
    private Point[] minPoints = null;
    private boolean higher = false;

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
        improve.doit();
    }

    private void doit() {
        long start = System.nanoTime();
        improve("(22,25), (24,28), (55,32), (40,31), (49,38), (46,33), (53,41), (58,27), (51,47), (45,45), (50,46), (52,40), (44,35), (41,42), (37,48), (42,39), (43,34), (35,36), (33,43), (34,37), (29,44), (30,49), (56,55), (47,53), (61,58), (68,64), (64,61), (65,65), (70,68), (66,54), (71,71), (59,66), (67,69), (48,60), (63,67), (54,62), (69,70), (60,59), (62,63), (57,57), (38,51), (27,50), (10,56), (21,52), (39,30), (17,29), (9,8), (4,4), (3,3), (6,7), (2,2), (5,11), (1,1), (7,6), (8,5), (11,12), (15,13), (12,15), (14,16), (19,10), (16,14), (20,9), (13,18), (18,26), (23,24), (26,19), (25,23), (32,20), (36,17), (31,21), (28,22)");
        long duration = System.nanoTime() - start;
        System.out.println("duration: " + (double) duration / 1000000);
        System.out.println("solutions: " + generator.numberOfSolutions);
    }

    public void improve(String s) {
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
                generator.generateAllSolutions(this);
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

    public void doMoves(String s) {
        Point points[] = SolutionToPoinsConvertor.convertToPoints(s);
        for (Point point : points) {
            generator.doMove(point);
        }
    }
}

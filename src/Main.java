import java.util.Arrays;

public class Main {

    private static PolygonGenerator generator = new PolygonGenerator(29);
    private static double minArea = Double.MAX_VALUE;
    private static double maxArea = 0.0;

    public static void solution(Point[] solution) {
        int area = generator.getArea();

        if (area > maxArea) {
            maxArea = area;
            System.out.println("max(" + solution.length + "):" + toString(solution) + "=" + maxArea/2);
        }
        if (area < minArea) {
            minArea = area;
            System.out.println("min(" + solution.length + "):" + toString(solution) + "=" + minArea/2);
        }
    }

    private static String toString(Point[] solution) {
        return Arrays.toString(solution);
    }

    public static void main(String args[]) throws Exception {
        long start = System.nanoTime();
        doMoves("(4,25), (7,21), (2,27), (5,20), (1,29), (3,26), (12,24), (14,28), (10,18), (21,12), (16,14), (24,9), (17,13), (19,11), (20,8), (15,15), (18,10), (22,5), (25,4)");
        generator.generateAllSolutions();
        long duration = System.nanoTime() - start;
        System.out.println("duration: " + (double) duration / 1000000);
        System.out.println("solutions: " + generator.numberOfSolutions);
    }

    public static void doMoves(String s) {
        Point points[] = SolutionToPoinsConvertor.convertToPoints(s);
        for (Point point : points) {
            generator.doMove(point);
        }
    }
}

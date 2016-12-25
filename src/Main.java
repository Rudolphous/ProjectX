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
        doMoves("(5,20), (4,25), (1,21), (3,26), (7,23), (11,24), (12,28), (2,27), (14,29), (10,18), (21,13), (16,15), (15,14), (22,5), (17,12), (18,11), (24,9), (19,10), (20,8)");
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

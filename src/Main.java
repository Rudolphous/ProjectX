import java.util.Arrays;

public class Main {

    private static PolygonGenerator generator = new PolygonGenerator(37);
    private static double minArea = Double.MAX_VALUE;
    private static double maxArea = 0.0;

    public static void solution(Point[] solution) {
        int area = generator.getArea();

        if (area > maxArea) {
            maxArea = area;
            System.out.println("max(" + solution.length + "):" + toString(solution) + "=" + (double)maxArea/2);
        }
        if (area < minArea) {
            minArea = area;
            System.out.println("min(" + solution.length + "):" + toString(solution) + "=" + (double)minArea/2);
        }
    }

    private static String toString(Point[] solution) {
        return Arrays.toString(solution);
    }

    public static void main(String args[]) throws Exception {
        long start = System.nanoTime();
        generator.generateAllSolutions();
        long duration = System.nanoTime() - start;
        System.out.println("duration: " + (double) duration / 1000000);
        System.out.println("solutions: " + generator.numberOfSolutions);
    }
}

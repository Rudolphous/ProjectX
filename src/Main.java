import java.util.List;

public class Main {

    private static PolygonGenerator generator = new PolygonGenerator(8);
    private static SolutionValidator validator = new SolutionValidator();
    private static double minArea = Double.MAX_VALUE;
    private static double maxArea = 0.0;

    public static void solution(List<Point> points) {
        double currentArea = Math.getArea(points);

        if (currentArea > maxArea) {
            maxArea = currentArea;
            System.out.println("max(" + generator.numberOfPoints + "):" + points + "=" + maxArea + " " + validator.isValidSolutions(points));
        }
        if (currentArea < minArea) {
            minArea = currentArea;
            System.out.println("min(" + generator.numberOfPoints + "):" + points + "=" + minArea + " " + validator.isValidSolutions(points));
        }
    }

    public static void main(String args[]) throws Exception {
        long start = System.nanoTime();
        generator.generateAllSolutions();
        long duration = System.nanoTime() - start;
        System.out.println("duration: " + (double)duration / 1000000);
        System.out.println("solutions: " + generator.aantal);
    }
}

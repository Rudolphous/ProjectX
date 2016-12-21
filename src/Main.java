public class Main {

    private static PolygonGenerator generator = new PolygonGenerator(8);
    private static SolutionValidator validator = new SolutionValidator();
    private static double minArea = Double.MAX_VALUE;
    private static double maxArea = 0.0;

    public static void solution(Point[] solution) {
        double currentArea = Math.getArea(solution);

        if (currentArea > maxArea) {
            maxArea = currentArea;
            //System.out.println("max(" + solution.length + "):" + solution + "=" + maxArea + " " + validator.isValidSolutions(solution));
        }
        if (currentArea < minArea) {
            minArea = currentArea;
            //System.out.println("min(" + solution.length + "):" + solution + "=" + minArea + " " + validator.isValidSolutions(solution));
        }
    }

    public static void main(String args[]) throws Exception {
        long start = System.nanoTime();
        generator.generateAllSolutions();
        long duration = System.nanoTime() - start;
        System.out.println("duration: " + (double) duration / 1000000);
        System.out.println("solutions: " + generator.numberOfSolutions);
    }
}

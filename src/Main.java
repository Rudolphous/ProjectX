import java.util.Arrays;

public class Main {

    private static PolygonGenerator generator;
    private static double minArea = Double.MAX_VALUE;
    private static double maxArea = 0.0;

    public static void solution(Point[] solution) {
        int area = generator.getArea();

        if (area > maxArea) {
            maxArea = area;
            System.out.println("\nmax(" + solution.length + "):" + toString(solution) + "=" + maxArea / 2);
        }
        if (area < minArea) {
            minArea = area;
            System.out.println("\nmin(" + solution.length + "):" + toString(solution) + "=" + minArea / 2);
        }
    }

    private static String toString(Point[] solution) {
        return Arrays.toString(solution);
    }

    public static void main(String args[]) throws Exception {
        long start = System.nanoTime();
        String s = "(72,96), (66,97), (43,91), (34,86), (27,87), (10,66), (21,79), (29,85), (35,58), (8,8), (9,11), (7,7), (6,6), (3,5), (4,9), (20,53), (1,1), (17,4), (5,2), (2,3), (12,10), (11,13), (14,15), (18,14), (15,16), (26,12), (13,17), (19,28), (24,24), (28,21), (16,22), (25,20), (36,18), (31,19), (22,27), (23,34), (45,36), (55,32), (40,25), (49,29), (57,31), (68,23), (52,39), (64,26), (51,40), (60,30), (46,37), (32,35), (41,46), (73,45), (81,42), (67,38), (78,41), (65,33), (96,50), (85,44), (86,68), (89,64), (93,49), (91,59), (97,54), (94,61), (88,77), (92,72), (87,80), (90,69), (95,56), (84,71), (82,51), (74,70), (77,75), (63,52), (71,65), (75,67), (79,57), (76,62), (80,55), (83,43), (70,47), (33,48), (42,74), (44,83), (38,63), (39,78), (37,60), (30,84), (47,92), (56,93), (58,90), (53,81), (48,73), (59,89), (50,76), (54,82), (61,95), (69,94), (62,88)";
        generator = new PolygonGenerator(131);
        doMoves(s);
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

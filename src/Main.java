import java.util.Arrays;

public class Main {

    private static PolygonGenerator generator = new PolygonGenerator(97);
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
        doMoves("(41,36), (46,35), (49,30), (45,31), (55,27), (57,32), (52,29), (51,34), (40,37), (60,45), (64,46), (81,42), (68,40), (78,41), (67,38), (73,39), (65,33), (85,44), (86,68), (89,61), (94,50), (93,56), (95,49), (96,54), (90,59), (88,64), (87,71), (92,77), (97,69), (91,80), (84,70), (80,75), (75,57), (74,65), (70,51), (63,47), (71,48), (76,55), (79,67), (82,72), (83,43), (33,52), (42,63), (77,78), (39,62), (38,74), (37,60), (30,82), (44,84), (47,92), (56,73), (48,90), (59,76), (54,83), (50,96), (58,95), (66,88), (72,81), (62,93), (69,89), (61,94), (53,97), (34,91), (43,87), (27,86), (21,79), (10,66), (29,85), (35,58), (8,8), (9,11), (7,7), (17,53), (6,6), (4,5), (1,4), (3,1), (20,9), (5,2), (2,3), (12,10), (11,13), (14,15), (18,12), (16,14), (13,16)");
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

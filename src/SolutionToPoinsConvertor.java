public class SolutionToPoinsConvertor {

    public static Point[] convertToPoints(String s) {
        s = s.replaceAll(" ", "");
        String parts[] = s.split(",\\(");
        Point[] points = new Point[parts.length];
        for (int i = 0; i<points.length; i++) {
            points[i] = converToPoint(parts[i]);
        }
        return points;
    }

    private static Point converToPoint(String s) {
        s = s.replaceAll("[()]", "");
        String parts[] = s.split(",");
        int x = Integer.valueOf(parts[0].trim());
        int y = Integer.valueOf(parts[1].trim());
        return new Point(x-1, y-1);
    }

}

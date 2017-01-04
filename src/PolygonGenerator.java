import java.util.ArrayDeque;
import java.util.Queue;

public class PolygonGenerator {
    private final int numberOfPoints;
    private final Point points[];
    private final boolean usedX[];
    private final boolean usedY[];
    private final SolutionPrinter solutionPrinter;
    private final SlopeUniqChecker slopeUniqChecker;

    private int rawArea;
    public int currentSize;
    public long numberOfSolutions = 0;


    public PolygonGenerator(int numberOfPoints, SolutionPrinter solutionPrinter) {
        this.numberOfPoints = numberOfPoints;
        this.points = new Point[numberOfPoints];
        this.usedX = new boolean[numberOfPoints];
        this.usedY = new boolean[numberOfPoints];
        this.rawArea = 0;
        this.solutionPrinter = solutionPrinter;
        this.slopeUniqChecker = new SlopeUniqChecker(numberOfPoints);
    }

    public void generateAllSolutions() {
        if (currentSize == numberOfPoints) {
            numberOfSolutions++;
            rawArea += MyMath.addDelta(lastPoint(), firstPoint());
            solutionPrinter.solution(points);
        }

        Queue<Point> moves = generatePossibleMoves();
        tryAllMoves(moves);
    }

    private void tryAllMoves(Queue<Point> moves) {
        int orginalArea = rawArea;
        while (!moves.isEmpty()) {
            Point move = moves.poll();
            Integer slope = doMove(move);
            generateAllSolutions();
            undoLastMove(orginalArea, slope);
        }
    }

    public Integer doMove(Point move) {
        usedX[move.x] = true;
        usedY[move.y] = true;
        points[currentSize] = move;
        Integer slope = null;
        if (currentSize >= 1) {
            //slope is only possible with two points
            slope = getSlope(lastPoint(), move);
            slopeUniqChecker.useSlope(slope);
            rawArea += MyMath.addDelta(lastPoint(), move);
        }
        currentSize++;
        return slope;
    }

    private void undoLastMove(int orgArea, Integer slope) {
        Point lastPoint = lastPoint();
        usedX[lastPoint.x] = false;
        usedY[lastPoint.y] = false;
        currentSize--;
        rawArea = orgArea;
        if (slope != null) {
            slopeUniqChecker.unuseSlope(slope);
        }
    }

    private Queue<Point> generatePossibleMoves() {
        Queue<Point> list = new ArrayDeque<>(21);
        for (int y = 0; y < numberOfPoints; y++) {
            if (usedY[y]) continue;
            for (int x = 0; x < numberOfPoints; x++) {
                if (usedX[x]) continue;
                Point newPoint = new Point(x, y);

                if (!isValidMove(newPoint)) {
                    continue;
                }

                list.add(newPoint);
            }
        }

        return list;
    }

    public boolean isValidMove(Point newPoint) {
        if (isDuplicateSlope(newPoint)) {
            return false;
        }

        if (doesPointLeadToInvalidIntersections(newPoint)) {
            return false;
        }
        return true;
    }

    private boolean doesPointLeadToInvalidIntersections(Point newPoint) {
        if (isAlmostComplete()) {
            //now we do more checks since this is the last point and we close the loop (two lines are added instead of one)
            for (int i = 1; i < currentSize - 1; i++) {
                //newpoint to first point
                if (MyMath.isLinesIntersect(points[i], points[i + 1], newPoint, firstPoint())) {
                    return true;
                }
            }

            for (int i = 0; i < currentSize - 2; i++) {
                //lastpoint to new point
                if (MyMath.isLinesIntersect(points[i], points[i + 1], lastPoint(), newPoint)) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < currentSize - 2; i++) {
                if (MyMath.isLinesIntersect(points[i], points[i + 1], lastPoint(), newPoint)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isAlmostComplete() {
        return currentSize == numberOfPoints - 1;
    }

    private Point firstPoint() {
        return points[0];
    }

    private Point lastPoint() {
        return points[currentSize - 1];
    }

    private boolean isDuplicateSlope(Point newPoint) {
        if (currentSize == 0) {
            return false;
        }

        if (isAlmostComplete()) {
            int slope1 = getSlope(newPoint, firstPoint());
            int slope2 = getSlope(lastPoint(), newPoint);
            return slope1 == slope2 || slopeUniqChecker.isSlopeUsed(slope1) || slopeUniqChecker.isSlopeUsed(slope2);
        }

        int slope = getSlope(lastPoint(), newPoint);
        return slopeUniqChecker.isSlopeUsed(slope);
    }

    private int getSlope(Point p1, Point p2) {
        return slopeUniqChecker.lookupSlope(p1.x, p1.y, p2.x, p2.y);
    }

    public int getArea() {
        return (rawArea < 0) ? -rawArea : rawArea;
    }

    public void clear() {
        currentSize = 0;
        for (int i=0; i<numberOfPoints; i++) {
           usedX[i] = false;
           usedY[i] = false;
        }
        rawArea = 0;
        numberOfSolutions = 0;
        slopeUniqChecker.clear();
    }
}
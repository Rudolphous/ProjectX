import java.util.ArrayDeque;
import java.util.Queue;

public class PolygonGenerator {
    private final int numberOfPoints;
    private final Point points[];
    private final boolean usedX[];
    private final boolean usedY[];
    private final double[] listOfRCs;

    private int rawArea;
    public int currentSize;
    public long numberOfSolutions = 0;


    public PolygonGenerator(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
        this.points = new Point[numberOfPoints];
        this.usedX = new boolean[numberOfPoints];
        this.usedY = new boolean[numberOfPoints];
        this.listOfRCs = new double[numberOfPoints];
        this.rawArea = 0;
    }

    public void generateAllSolutions(SolutionPrinter printer) {
        if (currentSize == numberOfPoints) {
            numberOfSolutions++;
            rawArea += MyMath.addDelta(lastPoint(), firstPoint());
            printer.solution(points);
        }

        Queue<Point> moves = generatePossibleMoves();
        int orginalArea = rawArea;
        tryAllMoves(printer, moves, orginalArea);
    }

    private void tryAllMoves(SolutionPrinter printer, Queue<Point> moves, int orginalArea) {
        while (!moves.isEmpty()) {
            Point move = moves.poll();
            doMove(move);
            generateAllSolutions(printer);
            undoLastMove(orginalArea);
        }
    }

    public void doMove(Point move) {
        usedX[move.x] = true;
        usedY[move.y] = true;
        points[currentSize] = move;
        if (currentSize >= 1) {
            //rc is only possible with two points, so we two points we have one rc
            listOfRCs[currentSize - 1] = MyMath.calcRC(lastPoint(), move);
            rawArea += MyMath.addDelta(lastPoint(), move);
        }
        currentSize++;
    }

    private void undoLastMove(int orgArea) {
        Point lastPoint = lastPoint();
        usedX[lastPoint.x] = false;
        usedY[lastPoint.y] = false;
        currentSize--;
        rawArea = orgArea;
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
        if (isDuplicateRC(newPoint)) {
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

    private boolean isDuplicateRC(Point newPoint) {
        if (currentSize == 0) {
            return false;
        }

        if (isAlmostComplete()) {
            Double rc1 = MyMath.calcRC(newPoint, firstPoint());
            Double rc2 = MyMath.calcRC(lastPoint(), newPoint);
            return rc1.equals(rc2) || containsRC(rc1, rc2);
        }

        Double rc = MyMath.calcRC(lastPoint(), newPoint);
        return containsRC(rc);
    }

    private boolean containsRC(Double rc) {
        for (int i=0; i<currentSize-1; i++) {
            if (listOfRCs[i] == rc) {
                return true;
            }
        }
        return false;
    }

    private boolean containsRC(Double rc1, Double rc2) {
        for (int i=0; i<currentSize-1; i++) {
            if (listOfRCs[i] == rc1 || listOfRCs[i] == rc2) {
                return true;
            }
        }

        return false;
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
    }
}
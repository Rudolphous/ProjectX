import java.util.*;

import static java.lang.Math.abs;

public class PolygonGenerator {

    private final int numberOfPoints;
    private final int middleOfBoard;
    private final Point points[];
    private final boolean usedX[];
    private final boolean usedY[];
    private final double[] listOfRCs;

    private int rawArea;
    private int currentSize;
    private int maxretries = 1;
    public long numberOfSolutions = 0;


    public PolygonGenerator(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
        this.points = new Point[numberOfPoints];
        this.middleOfBoard = this.numberOfPoints / 2;
        this.usedX = new boolean[numberOfPoints];
        this.usedY = new boolean[numberOfPoints];
        this.listOfRCs = new double[numberOfPoints];
        this.rawArea = 0;
    }

    public int generateAllSolutions() {
        if (currentSize == numberOfPoints) {
            numberOfSolutions++;
            rawArea += addDelta(lastPoint(), firstPoint());
            Main.solution(points);
            return currentSize;
        }

        Queue<Point> moves = generatePossibleMoves();

        /*if (currentSize > numberOfPoints * 0.50) maxretries = 3;
        if (currentSize > numberOfPoints * 0.75) maxretries = 4;
        if (currentSize > numberOfPoints * 0.80) maxretries = 5;
        if (currentSize > numberOfPoints * 0.85) maxretries = 6;
        if (currentSize > numberOfPoints * 0.90) maxretries = 10;    */


        int orginalArea = rawArea;
        int retries = 0;
        int bestScore = currentSize;
        for (;;) {
            if (moves.isEmpty()) {
                return bestScore;
            }

            Point move = moves.poll();
            doMove(move);
            int currentScore = generateAllSolutions();
            undoLastMove(orginalArea);
            if (currentScore > bestScore) {
                bestScore = currentScore;
            }
            if (retries == maxretries) {
                break;
            }
            retries++;
        }
        return bestScore;
    }

    private static int addDelta(Point a, Point b) {
        return (a.x + b.x) * (a.y - b.y);
    }

    private void doMove(Point move) {
        usedX[move.x] = true;
        usedY[move.y] = true;
        points[currentSize] = move;
        if (currentSize >= 1) {
            //rc is only possible with two points, so we two points we have one rc
            listOfRCs[currentSize - 1] = Math.calcRC(lastPoint(), move);
            rawArea += Math.addDelta(lastPoint(), move);
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
        Point lastPoint = (currentSize > 0) ? lastPoint() : null;

        Queue<Point> list = new PriorityQueue<Point>();
        for (int y = 0; y < numberOfPoints; y++) {
            if (usedY[y]) continue;
            for (int x = 0; x < numberOfPoints; x++) {
                if (usedX[x]) continue;
                Point newPoint = new Point(x, y);
                if (!isValidMirroringPoint(newPoint)) {
                    continue;
                }

                if (isDuplicateRC(newPoint)) {
                    continue;
                }

                if (doesPointLeadToInvalidIntersections(newPoint)) {
                    continue;
                }

                if (lastPoint != null) {
                    newPoint.score = evalMinimize(lastPoint, newPoint);
                }

                list.add(newPoint);
            }
        }

        return list;
    }

    private int evalMinimize(Point from, Point move) {
        //we stay as close as possible to the current last point
        return -calculateDistance(from, move);
    }

    private int calculateDistance(Point from, Point to) {
        int x = abs(to.x - from.x);
        int y = abs(to.y - from.y);
        return x * x + y * y;
    }

    private boolean isValidMirroringPoint(Point newPoint) {
        switch (currentSize) {
            case 0:
                return isValidMirroringFirstPoint(newPoint);
            case 1:
                return isValidMirroringOtherPoint(newPoint);
            case 2:
                return isValidMirroringOtherPoint(newPoint) && Math.orientation(points[0], points[1], newPoint) >= 0;
            default:
                //others
                return isValidMirroringOtherPoint(newPoint);
        }
    }

    private boolean isValidMirroringOtherPoint(Point newPoint) {
        return true;
    }

    private boolean isValidMirroringFirstPoint(Point newPoint) {
        return newPoint.x <= middleOfBoard && newPoint.y <= middleOfBoard;
    }

    private boolean doesPointLeadToInvalidIntersections(Point newPoint) {
        if (isAlmostComplete()) {
            //now we do more checks since this is the last point and we close the loop (two lines are added instead of one)
            for (int i = 1; i < currentSize - 1; i++) {
                //newpoint to first point
                if (Math.isLinesIntersect(points[i], points[i + 1], newPoint, firstPoint())) {
                    return true;
                }
            }

            for (int i = 0; i < currentSize - 2; i++) {
                //lastpoint to new point
                if (Math.isLinesIntersect(points[i], points[i + 1], lastPoint(), newPoint)) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < currentSize - 2; i++) {
                if (Math.isLinesIntersect(points[i], points[i + 1], lastPoint(), newPoint)) {
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
            Double rc1 = Math.calcRC(newPoint, firstPoint());
            Double rc2 = Math.calcRC(lastPoint(), newPoint);
            return rc1.equals(rc2) || containsRC(rc1, rc2);
        }

        Double rc = Math.calcRC(lastPoint(), newPoint);
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
}
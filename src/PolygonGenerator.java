import java.util.ArrayList;
import java.util.List;

public class PolygonGenerator {

    private final int numberOfPoints;
    private final int halfPoints;
    private final Point points[];
    private final boolean gehadx[];
    private final boolean gehady[];
    private final double[] listOfRCs;

    private int currentSize;
    public long numberOfSolutions = 0;

    public PolygonGenerator(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
        this.points = new Point[numberOfPoints];
        this.halfPoints = this.numberOfPoints / 2; //if max is 7 then 3 is just valid
        this.gehadx = new boolean[numberOfPoints];
        this.gehady = new boolean[numberOfPoints];
        this.listOfRCs = new double[numberOfPoints];
    }

    public void generateAllSolutions() {
        if (currentSize == numberOfPoints) {
            numberOfSolutions++;
            Main.solution(points);
            return;
        }
        List<Point> moves = generatePossibleMoves();
        for (Point move : moves) {
            doMove(move);
            generateAllSolutions();
            undoLastMove();
        }
    }

    private void doMove(Point move) {
        gehadx[move.getX()] = true;
        gehady[move.getY()] = true;
        if (currentSize >= 1) {
            //rc is only possible with two points, so we two points we have one rc
            listOfRCs[currentSize - 1] = Math.calcRC(lastPoint(), move);
        }
        points[currentSize] = move;
        currentSize++;
    }

    private void undoLastMove() {
        Point lastPoint = lastPoint();
        gehadx[lastPoint.getX()] = false;
        gehady[lastPoint.getY()] = false;
        currentSize--;
    }

    private List<Point> generatePossibleMoves() {
        List<Point> list = new ArrayList<Point>();
        for (int y = 0; y < numberOfPoints; y++) {
            if (gehady[y]) continue;
            for (int x = 0; x < numberOfPoints; x++) {
                if (gehadx[x]) continue;
                Point newPoint = new Point(x, y);
                if (!isValidMirroringPoint(newPoint)) {
                    continue;
                }

                if (doesPointLeadToInvalidIntersections(newPoint)) {
                    continue;
                }

                if (isDuplicateRC(newPoint)) {
                    continue;
                }

                list.add(newPoint);
            }
        }
        return list;
    }

    private boolean isValidMirroringPoint(Point newPoint) {
        switch (currentSize) {
            case 0:
                return isValidMirroringFirstPoint(newPoint);
            case 1:
                return isValidMirroringOtherPoint(newPoint);
            default:
                //others
                return isValidMirroringOtherPoint(newPoint);
        }
    }

    private boolean isValidMirroringOtherPoint(Point newPoint) {
        return firstPoint().compareTo(newPoint) <= 0;
    }

    private boolean isValidMirroringFirstPoint(Point newPoint) {
        return newPoint.x <= halfPoints && newPoint.y <= halfPoints;
    }

    private boolean doesPointLeadToInvalidIntersections(Point newPoint) {
        if (isAlmostComplete()) {
            //now we do more checks since this is the last point and we close the loop (two lines are added instead of one)
            for (int i = 1; i < numberOfPoints - 2; i++) {
                //newpoint to first point
                if (Math.isLinesIntersect(points[i], points[i + 1], newPoint, firstPoint())) {
                    return true;
                }
            }

            for (int i = 0; i < numberOfPoints - 3; i++) {
                //lastpoint to new point
                if (Math.isLinesIntersect(points[i], points[i + 1], lastPoint(), newPoint)) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < currentSize - 2; i++) {
                //a-b c-np
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

            if (rc1.equals(rc2)) {
                return true;
            }

            return containsRC(rc1, rc2);
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
}
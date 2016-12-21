import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PolygonGenerator {

    private static Random RANDOM = new Random(13);

    private final int numberOfPoints;
    private final int middleOfBoard;
    private final Point points[];
    private final boolean usedX[];
    private final boolean usedY[];
    private final double[] listOfRCs;

    private int rawArea;
    private int currentSize;
    public long numberOfSolutions = 0;

    public PolygonGenerator(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
        this.points = new Point[numberOfPoints];
        this.middleOfBoard = this.numberOfPoints / 2; //if max is 7 then 3 is just valid
        this.usedX = new boolean[numberOfPoints];
        this.usedY = new boolean[numberOfPoints];
        this.listOfRCs = new double[numberOfPoints];
        this.rawArea = 0;
    }

    public void generateAllSolutions() {
        if (currentSize == 0) {
            doMove(new Point(0,0));
            doMove(new Point(1,1));
            doMove(new Point(3,2));
            doMove(new Point(2,3));
            doMove(new Point(5,4));
        }

        if (currentSize == numberOfPoints) {
            numberOfSolutions++;
            rawArea += addDelta(lastPoint(), firstPoint());
            Main.solution(points);
            return;
        }
        List<Point> moves = generatePossibleMoves();
        int orginalArea = rawArea;
        int error = 0;
        for (;;) {
            if (moves.isEmpty()) {
                break;
            }
            int randomIndex = RANDOM.nextInt(moves.size());
            Point move = moves.get(randomIndex);
            moves.remove(randomIndex);
            doMove(move);
            generateAllSolutions();
            undoLastMove();
            error++;
            rawArea = orginalArea; //simple and less buggy than calculate this in undolastmove
            if (error == 3) {
                break;
            }
        }
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

    private void undoLastMove() {
        Point lastPoint = lastPoint();
        usedX[lastPoint.x] = false;
        usedY[lastPoint.y] = false;
        currentSize--;
    }

    private List<Point> generatePossibleMoves() {
        List<Point> list = new ArrayList<Point>();
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
            case 2:
                return isValidMirroringOtherPoint(newPoint) && Math.orientation(points[0], points[1], newPoint) >= 0;
            default:
                //others
                return isValidMirroringOtherPoint(newPoint);
        }
    }

    private boolean isValidMirroringOtherPoint(Point newPoint) {
        return firstPoint().compareTo(newPoint) <= 0;
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
import java.util.*;

public class PolygonGenerator {

    public final int numberOfPoints;
    private final int halfPoints;
    private List<Point> points;
    private boolean gehadx[];
    private boolean gehady[];
    private List<Double> listOfRCs;
    public long aantal = 0;

    public PolygonGenerator(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
        this.points = new ArrayList(numberOfPoints);
        this.halfPoints = this.numberOfPoints / 2; //if max is 7 then 3 is just valid
        this.gehadx = new boolean[numberOfPoints];
        this.gehady =  new boolean[numberOfPoints];
        this.listOfRCs = new ArrayList<Double>();
    }

    public void generateAllSolutions() {
        if (points.size() == numberOfPoints) {
            aantal++;
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
        if (!points.isEmpty()) {
            //rc is only possible with two points
            listOfRCs.add(Math.calcRC(lastPoint(), move));
        }
        points.add(move);
    }

    private void undoLastMove() {
        Point lastPoint = lastPoint();
        gehadx[lastPoint.getX()] = false;
        gehady[lastPoint.getY()] = false;
        if (!listOfRCs.isEmpty()) {
            listOfRCs.remove(listOfRCs.size() - 1);
        }
        points.remove(points.size() - 1);
    }

    private List<Point> generatePossibleMoves() {
        List<Point> list = new ArrayList<Point>();
        for (int y=0; y<numberOfPoints; y++) {
            if (gehady[y]) continue;
            for (int x=0; x<numberOfPoints; x++) {
                if (gehadx[x]) continue;
                Point newPoint = new Point(x, y);
                if (!isValidPoint(newPoint)) {
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

    private boolean isValidPoint(Point newPoint) {
        switch (points.size()) {
            case 0:
                return isValidFirstPoint(newPoint);
            case 1:
                return isValidOtherPoint(newPoint);
            default:
                //others
                return isValidOtherPoint(newPoint);
        }
    }

    private boolean isValidOtherPoint(Point newPoint) {
        return points.get(0).compareTo(newPoint) <= 0;
    }

    private boolean isValidFirstPoint(Point newPoint) {
        return newPoint.getX() <= halfPoints && newPoint.getY() <= halfPoints;
    }

    private boolean doesPointLeadToInvalidIntersections(Point newPoint) {
        if (isAlmostComplete()) {
            //now we do more checks since this is the last point and we close the loop (two lines are added instead of one)

            for (int i=1; i<numberOfPoints-2; i++) {
                //newpoint to first point
                if (Math.isLinesIntersect(points.get(i), points.get(i+1), newPoint, points.get(0))) {
                    return true;
                }
            }

            for (int i=0; i<numberOfPoints-3; i++) {
                //lastpoint to new point
                if (Math.isLinesIntersect(points.get(i), points.get(i+1), lastPoint(), newPoint)) {
                    return true;
                }
            }
        } else {
            for (int i=0; i<points.size()-2; i++) {
                //a-b c-np
                if (Math.isLinesIntersect(points.get(i), points.get(i+1), lastPoint(), newPoint)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isAlmostComplete() {
        return points.size() == numberOfPoints-1;
    }

    private Point lastPoint() {
        if (points.isEmpty()) {
            return null;
        }
        return points.get(points.size() - 1);
    }

    private boolean isDuplicateRC(Point newPoint) {
        if (points.isEmpty()) {
            return false;
        }

        if (isAlmostComplete()) {
            Double rc1 = Math.calcRC(newPoint, points.get(0));
            Double rc2 = Math.calcRC(lastPoint(), newPoint);

            if (rc1.equals(rc2)) {
                return true;
            }

            if (listOfRCs.contains(rc1)) {
                return true;
            }

            if (listOfRCs.contains(rc2)) {
                return true;
            }

            return false;
        }

        Double rc = Math.calcRC(lastPoint(), newPoint);
        return (listOfRCs.contains(rc));
    }
}
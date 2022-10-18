import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class ErrorReport {
    static void process(Throwable a) {
        System.out.println(a.getMessage());
    }
}

public class DouglasPeuckerReduction {
    public static List<Point> reduce(Point[] points, double tolerance) {
        if (points == null || points.length < 3) return Arrays.asList(points);

        int firstPoint = 0;
        int lastPoint = points.length - 1;

        SortedList<Integer> pointIndexsToKeep;
        try {
            pointIndexsToKeep = new SortedList<>(LinkedList.class);
        } catch (Throwable t) {
            t.printStackTrace(System.out);
            ErrorReport.process(t);
            return null;
        }

        //Add the first and last index to the keepers
        pointIndexsToKeep.add(firstPoint);
        pointIndexsToKeep.add(lastPoint);

        //The first and the last point cannot be the same
        while (points[firstPoint].equals(points[lastPoint])) {
            lastPoint--;
        }

        reduce(points, firstPoint, lastPoint, tolerance, pointIndexsToKeep);

        List<Point> returnPoints = new ArrayList<>(pointIndexsToKeep.size());

        for (int pIndex : pointIndexsToKeep) {
            returnPoints.add(points[pIndex]);
        }

        return returnPoints;
    }


    private static void reduce(Point[] points, int firstPoint, int lastPoint, double tolerance, List<Integer> pointIndexsToKeep) {
        double maxDistance = 0;
        int indexFarthest = 0;

        Line tmpLine = new Line(points[firstPoint], points[lastPoint]);

        for (int index = firstPoint; index < lastPoint; index++) {
            double distance = tmpLine.getDistanceFrom(points[index]);
            if (distance > maxDistance) {
                maxDistance = distance;
                indexFarthest = index;
            }
        }

        if (maxDistance > tolerance && indexFarthest != 0) {
            //Add the largest point that exceeds the tolerance
            pointIndexsToKeep.add(indexFarthest);

            reduce(points, firstPoint, indexFarthest, tolerance, pointIndexsToKeep);
            reduce(points, indexFarthest, lastPoint, tolerance, pointIndexsToKeep);
        }
    }

}
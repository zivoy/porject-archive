import java.awt.geom.Point2D;

/**
 * @author ziv
 */

public class Line {
    Point2D start, end;
    double a, b, c;

    public Line(Point2D point, Point2D point1) {
        this.start = point;
        this.end = point1;
        this.a = this.end.getY() - this.start.getY();
        this.b = this.end.getX() - this.start.getX();
        this.c = this.b * (start.getY() - this.getGradient() * this.start.getX());
    }

    public double getGradient() {
        if (this.a == 0 && this.b == 0) return 0;
        if (this.b == 0) return this.a * Double.POSITIVE_INFINITY;
        return this.a / this.b;
    }

    public double length_squared() {
        return this.dot(this);
    }

    public double distance_squared(Point2D point) {
        double top = Math.abs(this.a * point.getX() - this.b * point.getY() + this.c);
        double bottom = length_squared();
        return top * top / bottom;
    }

    public double dot(Line other) {
        return this.a * other.a + this.b * other.b;
    }

    public double getDistanceFrom(Point2D point) {
        double lengthSq = length_squared();
        if (lengthSq == 0.0) return this.start.distance(point);

        double dotProduct = this.dot(new Line(this.start, point));
        double projectionMultipleSq = dotProduct * dotProduct / lengthSq;
        if (0 <= projectionMultipleSq && projectionMultipleSq <= 1) return Math.sqrt(this.distance_squared(point));

        double distSq;
        if (projectionMultipleSq < 0) distSq = this.start.distanceSq(point);
        else distSq = this.end.distanceSq(point);
        return Math.sqrt(distSq);
    }

    public Point2D getByX(double x) {
        double m, b;
        if (this.b == 0) return new Point2D.Double(x, Double.POSITIVE_INFINITY);
        m = this.a / this.b;
        b = this.c / this.b;
        return new Point2D.Double(x, m * x + b);
    }
}

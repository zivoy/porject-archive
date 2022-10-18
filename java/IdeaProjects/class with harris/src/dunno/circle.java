package dunno;

public class circle {
    private double radius;

    public circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getArea() {
        return radius * radius * Math.PI;
    }
}

class B extends circle {
    private double length;

    B(double radius, double length) {
        super(radius);
        this.length = length;
    }

    @Override
    public double getArea() {
        return length;
    }
}
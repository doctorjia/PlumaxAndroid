package me.kyleyan.plumax;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

public class Tile {
    private final static String LOG_TAG = Tile.class.getSimpleName();
    private int x;
    private int y;
    private int d;
    private Paint tileColor;
    private Triangle triangle;

    public Tile(int x, int y, int d) {
        this.x = x;
        this.y = y;
        this.d = d;
        tileColor = new Paint();
        tileColor.setColor(d == 1 ? Color.BLACK : Color.YELLOW);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint(tileColor);

        paint.setColor(tileColor.getColor());
        paint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.lineTo(triangle.getA().x, triangle.getA().y);
        path.lineTo(triangle.getB().x, triangle.getB().y);
        path.lineTo(triangle.getC().x, triangle.getC().y);
        path.lineTo(triangle.getA().x, triangle.getA().y);
        path.close();

        canvas.drawPath(path, paint);
    }

    public boolean isTouched(int x, int y) {
        return true;
    }

    public void handleTouch() {

    }

    public void setTriangle(Point a, Point b, Point c) {
        this.triangle = new Triangle(a, b, c);
    }

    private static class Triangle {

        private final Point a;
        private final Point b;
        private final Point c;

        public Triangle(Point a, Point b, Point c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public Point getA() {
            return a;
        }

        public Point getB() {
            return b;
        }

        public Point getC() {
            return c;
        }

    }


}

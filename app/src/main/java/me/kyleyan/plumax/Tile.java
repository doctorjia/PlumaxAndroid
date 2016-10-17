package me.kyleyan.plumax;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Tile {
    private final static String LOG_TAG = Tile.class.getSimpleName();
    private int x;
    private int y;
    private int d;
    private Paint tileColor;

    public Tile(int x, int y, int d) {
        this.x = x;
        this.y = y;
        this.d = d;
        tileColor = new Paint();
        tileColor.setColor(d == 1 ? Color.BLACK : Color.WHITE);
    }

    public void draw() {

    }


}

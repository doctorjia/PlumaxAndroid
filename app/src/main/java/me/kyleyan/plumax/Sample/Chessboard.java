package me.kyleyan.plumax.Sample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public final class Chessboard extends View {
    private static final String TAG = Chessboard.class.getSimpleName();

    private static final int COLS = 8;
    private static final int ROWS = 8;

    private final ChessTile[][] mChessTiles;

    private int x0 = 0;
    private int y0 = 0;
    private int squareSize = 0;

    /** 'true' if black is facing player. */
    private boolean flipped = false;

    public Chessboard(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.mChessTiles = new ChessTile[COLS][ROWS];

        setFocusable(true);
        buildChessTiles();
    }

    private void buildChessTiles() {
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                mChessTiles[c][r] = new ChessTile(c, r);
            }
        }
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        final int width = getWidth();
        final int height = getHeight();

        this.squareSize = Math.min(
                getSquareSizeWidth(width),
                getSquareSizeHeight(height)
        );

        computeOrigins(width, height);

        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                final int xCoord = getXCoord(c);
                final int yCoord = getYCoord(r);

                final Rect ChessTileRect = new Rect(
                        xCoord,               // left
                        yCoord,               // top
                        xCoord + squareSize,  // right
                        yCoord + squareSize   // bottom
                );

                mChessTiles[c][r].setTileRect(ChessTileRect);
                mChessTiles[c][r].draw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        ChessTile ChessTile;
        for (int c = 0; c < COLS; c++) {
            for (int r = 0; r < ROWS; r++) {
                ChessTile = mChessTiles[c][r];
                if (ChessTile.isTouched(x, y))
                    ChessTile.handleTouch();
            }
        }

        return true;
    }

    private int getSquareSizeWidth(final int width) {
        return width / 8;
    }

    private int getSquareSizeHeight(final int height) {
        return height / 8;
    }

    private int getXCoord(final int x) {
        return x0 + squareSize * (flipped ? 7 - x : x);
    }

    private int getYCoord(final int y) {
        return y0 + squareSize * (flipped ? y : 7 - y);
    }

    private void computeOrigins(final int width, final int height) {
        this.x0 = (width  - squareSize * 8) / 2;
        this.y0 = (height - squareSize * 8) / 2;
    }


}

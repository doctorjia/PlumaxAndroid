package me.kyleyan.plumax;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import me.kyleyan.plumax.Game.Models.Board;
import me.kyleyan.plumax.Game.Models.Location;

public class BoardView extends View {

    private int x0 = 0;
    private int y0 = 0;
    private int tileSize = 0;

    private static final String LOG_TAG = BoardView.class.getSimpleName();

    private static final int BOARD_SIZE = 3;

    private final Tile[][][] mTiles;

    private final Board board;


    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTiles = new Tile[BOARD_SIZE*4+1][BOARD_SIZE*4+1][2];
        this.board = new Board();
        setFocusable(true);
        buildTiles();
    }

    private void buildTiles() {
        final int width = getWidth();
        final int height = getHeight();
        this.tileSize = Math.min(getTileWidth(width), getTileHeight(height));
        computeOrigins(width, height);
        for (int i = 1; i <= BOARD_SIZE * 4; i++) {
            for (int j = 1; j <= BOARD_SIZE * 4; j++) {
                for (int k = 0; k < 2; k++) {
                    if (this.board.isValidLocation(new Location(i, j, k))) {
                        this.mTiles[i][j][k] = new Tile(i, j, k);
                        int xCoord = getXCoord(i);
                        int yCoord = getYCoord(j);
                        Point a, b, c;
                        if (k == 1) {
                            a = new Point(xCoord, yCoord);
                            b = new Point(xCoord - tileSize / 2,
                                    yCoord + (int) (tileSize * Math.sqrt(3) / 2));
                            c = new Point(xCoord + tileSize / 2,
                                    yCoord + (int) (tileSize * Math.sqrt(3) / 2));
                        } else {
                            a = new Point(xCoord, yCoord);
                            b = new Point(xCoord - tileSize, yCoord);
                            c = new Point(xCoord - tileSize / 2,
                                    yCoord + (int) (tileSize * Math.sqrt(3) / 2));
                        }
                        mTiles[i][j][k].setTriangle(a, b, c);
                    }
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final int width = getWidth();
        final int height = getHeight();

        this.tileSize = Math.min(getTileWidth(width), getTileHeight(height));

        computeOrigins(width, height);

        for (int i = 1; i <= BOARD_SIZE * 4; i++) {
            for (int j = 1; j <= BOARD_SIZE * 4; j++) {
                for (int k = 0; k < 2; k++) {
                    if (mTiles[i][j][k] != null) {
                        mTiles[i][j][k].draw(canvas);
                    }
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        final int x = (int) event.getX();
        final int y = (int) event.getY();

        Tile tile;
        for (int i = 1; i <= BOARD_SIZE * 4; i++) {
            for (int j = 1; j <= BOARD_SIZE * 4; j++) {
                for (int k = 0; k < 2; k++) {
                    tile = mTiles[i][j][k];
                    if (tile != null && tile.isTouched(x, y)) {
                        tile.handleTouch();
                    }
                }
            }
        }

        return true;
    }

    private int getTileWidth(final int width) {
        return width / BOARD_SIZE / 4;
    }

    private int getTileHeight(final int height) {
        return height / BOARD_SIZE / 4;
    }

    private int getXCoord(final int x) {
        return this.x0 - this.tileSize * x;
    }

    private int getYCoord(final int y) {
        return y0 + (int) (this.tileSize * Math.sqrt(3) / 2) * y;
    }

    private void computeOrigins(final int width, final int height) {
//        this.x0 = (width  - this.tileSize * BOARD_SIZE * 3) / 2;
//        this.y0 = (height - (int)(this.tileSize * Math.sqrt(3) / 2) * BOARD_SIZE * 4) / 2;
        this.x0 = (width  - this.tileSize * BOARD_SIZE * 3) / 2 + this.tileSize * BOARD_SIZE * 3;
        this.y0 = (height - (int)((this.tileSize - 1)* Math.sqrt(3) / 2) * BOARD_SIZE * 4) / 2;

    }

}

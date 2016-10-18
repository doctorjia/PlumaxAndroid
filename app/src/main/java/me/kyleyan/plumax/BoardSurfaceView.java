package me.kyleyan.plumax;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import me.kyleyan.plumax.Game.Models.Board;
import me.kyleyan.plumax.Game.Models.Location;

import static me.kyleyan.plumax.Game.Models.Board.BOARD_SIZE;

public class BoardSurfaceView extends SurfaceView {

    private Bitmap bmpBackground;
    private Tile[][][] mTiles;
    private int tileSize = 0;
    private int x0;
    private int y0;
    private Board board;

    public BoardSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        board = new Board();
        mTiles = new Tile[BOARD_SIZE][BOARD_SIZE][2];
        SurfaceHolder holder = getHolder();
        bmpBackground = BitmapFactory.decodeResource(getResources(), R.drawable.board);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas(null);

                int size = Math.min(canvas.getWidth(), canvas.getHeight());
                bmpBackground = Bitmap.createScaledBitmap(
                        bmpBackground, size, size, false);
                x0 = (canvas.getWidth() + bmpBackground.getWidth()) / 2;
                y0 = (canvas.getHeight() + bmpBackground.getHeight()) / 2;
                drawBoard(canvas);
                buildTiles();
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    protected void drawBoard(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        canvas.drawBitmap(bmpBackground,
                (canvas.getWidth() - bmpBackground.getWidth()) / 2,
                (canvas.getHeight() - bmpBackground.getHeight()) / 2,
                null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();

        Point p = new Point((int)x, (int)y);
        return super.onTouchEvent(event);
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

        public boolean contains(Point p) {
            int x = p.x, y = p.y;
            int x1 = a.x, y1 = a.y;
            int x2 = b.x, y2 = b.y;
            int x3 = c.x, y3 = c.y;

            // no need to divide by 2.0 here, since it is not necessary in the equation
            double ABC = Math.abs (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
            double ABP = Math.abs (x1 * (y2 - y) + x2 * (y - y1) + x * (y1 - y2));
            double APC = Math.abs (x1 * (y - y3) + x * (y3 - y1) + x3 * (y1 - y));
            double PBC = Math.abs (x * (y2 - y3) + x2 * (y3 - y) + x3 * (y - y2));

            return ABP + APC + PBC == ABC;
        }
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
        this.x0 = (width  - this.tileSize * BOARD_SIZE * 3) / 2 + this.tileSize * BOARD_SIZE * 3;
        this.y0 = (height - (int)((this.tileSize - 1)* Math.sqrt(3) / 2) * BOARD_SIZE * 4) / 2;

    }

}

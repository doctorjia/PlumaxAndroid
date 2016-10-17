package me.kyleyan.plumax;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.AttributeSet;
import android.view.View;

public class BoardView extends View {

    private static final String LOG_TAG = BoardView.class.getSimpleName();

    private static final int BOARD_SIZE = 3;

    private final Tile[][] mTiles;



    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mTiles = new Tile[BOARD_SIZE*4+1][BOARD_SIZE*4+1];
        setFocusable(true);

    }

    private void buildTiles() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        VectorDrawableCompat boardVector = VectorDrawableCompat.create(getResources(), R.drawable.ic_board_alt, null);
        int size = Math.min(getWidth(), getHeight());
        boardVector.setBounds(0,0,size,size);
        boardVector.draw(canvas);
    }
}

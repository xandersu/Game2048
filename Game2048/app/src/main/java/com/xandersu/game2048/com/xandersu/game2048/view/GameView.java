package com.xandersu.game2048.com.xandersu.game2048.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.xandersu.game2048.Constant;
import com.xandersu.game2048.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by lenovo on 2016/11/26.
 */

public class GameView extends LinearLayout {

    private static final String GAMEVIEW = "GameView";
    private Game2048Item[][] itemsMap = new Game2048Item[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();
    private int mItemWidth;



    public GameView(Context context) {
        super(context);
        initView();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
//        setColumnCount(4);
        setOrientation(LinearLayout.VERTICAL);
        setOnTouchListener(new OnTouchListener() {
            private float startX, startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        startX = event.getX();
                        startY = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        break;

                    case MotionEvent.ACTION_UP:
                        float offsetX = event.getX() - startX;
                        float offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {

                            if (offsetX > 50) {
                                moveRight();
                            } else if (offsetX < -50) {
                                moveLeft();
                            }
                        } else {
                            if (offsetY > 50) {
                                moveDown();
                            } else if (offsetY < -50) {
                                moveUp();
                            }
                        }

                        break;

                    default:
                        break;
                }

                return true;
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mItemWidth = Math.min(w, h)/4;
        Constant.CARD_WIDTH = mItemWidth;

        Log.d(TAG, "onSizeChanged: itemWidth = " + mItemWidth);

        addItems(mItemWidth,mItemWidth);
        startGame();
    }

    private void addItems(int itemWidth,int itemHeight){
        Game2048Item item;
        LinearLayout line;
        LinearLayout.LayoutParams lineLP;
        for (int y = 0; y < 4; y++) {
          line = new LinearLayout(getContext());
            lineLP = new LinearLayout.LayoutParams(-1,itemHeight);
            addView(line,lineLP);
            for (int x = 0; x < 4; x++) {
                item = new Game2048Item(getContext());
//                item.setNumber(1024);
//                addView(item,itemWidth,itemHeight);
                line.addView(item,itemWidth,itemHeight);
                itemsMap[x][y] = item;
            }
        }
    }

    private void moveUp() {
        Log.d(GAMEVIEW, "moveUp---");
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y+1; y1 < 4; y1++) {
                    if(itemsMap[x][y1].getNumber() >0 ){
                        if( itemsMap[x][y].getNumber() <=0){
                            MainActivity.getInstance().getAnimLayer().createMoveAnim(itemsMap[x][y1],itemsMap[x][y], x, x, y1, y);
                            itemsMap[x][y].setNumber(itemsMap[x][y1].getNumber());
                            itemsMap[x][y1].setNumber(0);
                            y--;
                            merge = true;
                        }else if(itemsMap[x][y].equals(itemsMap[x][y1]) ){
                            MainActivity.getInstance().getAnimLayer().createMoveAnim(itemsMap[x][y1],itemsMap[x][y], x, x, y1, y);
                            itemsMap[x][y].setNumber(itemsMap[x][y].getNumber()*2);
                            addScore(itemsMap[x][y].getNumber());
                            itemsMap[x][y1].setNumber(0);
                            merge = true;
                        }
                            break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }



    private void moveDown() {
        Log.d(GAMEVIEW, "moveDown___");
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y-1; y1 >=0; y1--) {
                    if(itemsMap[x][y1].getNumber() >0 ){
                        if( itemsMap[x][y].getNumber() <=0){
                            MainActivity.getInstance().getAnimLayer().createMoveAnim(itemsMap[x][y1],itemsMap[x][y], x, x, y1, y);
                            itemsMap[x][y].setNumber(itemsMap[x][y1].getNumber());
                            itemsMap[x][y1].setNumber(0);
                            y++;
                            merge = true;
                        }else if(itemsMap[x][y].equals(itemsMap[x][y1]) ){
                            MainActivity.getInstance().getAnimLayer().createMoveAnim(itemsMap[x][y1],itemsMap[x][y], x, x, y1, y);
                            itemsMap[x][y].setNumber(itemsMap[x][y].getNumber()*2);
                            addScore(itemsMap[x][y].getNumber());
                            itemsMap[x][y1].setNumber(0);
                            merge = true;
                        }
                            break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void moveLeft() {
        Log.d(GAMEVIEW, "moveLeft+++");
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int x1 = x+1; x1 < 4; x1++) {
                    if(itemsMap[x1][y].getNumber() >0 ){
                        if( itemsMap[x][y].getNumber() <=0){
                            MainActivity.getInstance().getAnimLayer().createMoveAnim(itemsMap[x1][y],itemsMap[x][y], x1, x, y, y);
                            itemsMap[x][y].setNumber(itemsMap[x1][y].getNumber());
                            itemsMap[x1][y].setNumber(0);
                            x--;
                            merge = true;
                        }else if(itemsMap[x][y].equals(itemsMap[x1][y]) ){
                            MainActivity.getInstance().getAnimLayer().createMoveAnim(itemsMap[x1][y],itemsMap[x][y], x1, x, y, y);
                            itemsMap[x][y].setNumber(itemsMap[x][y].getNumber()*2);
                            addScore(itemsMap[x][y].getNumber());
                            itemsMap[x1][y].setNumber(0);
                            merge = true;
                        }
                            break;
                    }
                }
            }
        }
//        invalidate();
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void moveRight() {
        Log.d(GAMEVIEW, "moveRight===");
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >=0; x--) {
                for (int x1 = x-1; x1 >=0; x1--) {
                    if(itemsMap[x1][y].getNumber() >0 ){
                        if( itemsMap[x][y].getNumber() <=0){
                            MainActivity.getInstance().getAnimLayer().createMoveAnim(itemsMap[x1][y],itemsMap[x][y], x1, x, y, y);
                            itemsMap[x][y].setNumber(itemsMap[x1][y].getNumber());
                            itemsMap[x1][y].setNumber(0);
                            x++;
                            merge = true;
                        }else if(itemsMap[x][y].equals(itemsMap[x1][y]) ){
                            MainActivity.getInstance().getAnimLayer().createMoveAnim(itemsMap[x1][y],itemsMap[x][y], x1, x, y, y);
                            itemsMap[x][y].setNumber(itemsMap[x][y].getNumber()*2);
                            addScore(itemsMap[x][y].getNumber());
                            itemsMap[x1][y].setNumber(0);
                            merge = true;
                        }
                            break;
                    }
                }
            }
        }
        if(merge){
            addRandomNum();
            checkComplete();
        }
    }

    private void checkComplete() {
        boolean complete = true;
        boolean success = false;

        ALL:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if( itemsMap[x][y].getNumber() == 0||
                        (x>0&&itemsMap[x][y].equals(itemsMap[x-1][y]))||
                        (x<3&&itemsMap[x][y].equals(itemsMap[x+1][y]))||
                        (y>0&&itemsMap[x][y].equals(itemsMap[x][y-1]))||
                        (y<3&&itemsMap[x][y].equals(itemsMap[x][y+1]))
                        ){
                    complete = false;
                    break ALL;
                }
            }
        }

        ALL1:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if( itemsMap[x][y].getNumber() == 2048){
                    success = true;
                showSuccessDialog();
//                    Log.e(TAG, "success ::" + itemsMap[x][y].getNumber());
                    break ALL1;
//                    break ;
                }
                }
            }


        if(complete ){
        new AlertDialog.Builder(getContext()).setTitle("Game Over!").setMessage("你失败了！")
                .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                }).show();

        }

//        if(success ){
//        new AlertDialog.Builder(getContext()).setTitle("WIN!").setMessage("你赢了！")
//                .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startGame();
//                    }
//                }).show();
//        }
    }

    private void showSuccessDialog() {
        new AlertDialog.Builder(getContext()).setTitle("WIN!").setMessage("你赢了！")
                .setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                }).show();
    }

    private void addScore(int score){
        MainActivity.getInstance().addScore(score);
    }

    private void addRandomNum(){

        emptyPoints.clear();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if( itemsMap[x][y].getNumber() <= 0){
                    emptyPoints.add(new Point(x,y));
                }
            }
        }
        Point p = emptyPoints.remove((int)(Math.random()* emptyPoints.size()));
        itemsMap[p.x][p.y].setNumber(Math.random()>0.1?2:4);
        MainActivity.getInstance().getAnimLayer().createScaleTo1(itemsMap[p.x][p.y]);
    }

    public void startGame(){
        MainActivity.getInstance().clearScore();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                itemsMap[x][y].setNumber(0);
            }
        }
        addRandomNum();
        addRandomNum();
    }
}

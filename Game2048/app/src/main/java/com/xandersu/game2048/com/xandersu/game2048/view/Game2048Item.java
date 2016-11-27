package com.xandersu.game2048.com.xandersu.game2048.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by lenovo on 2016/11/26.
 */

public class Game2048Item extends FrameLayout {
    private int mNumber;
    private TextView mTextView;


    public Game2048Item(Context context) {
        super(context);
        init();
    }

    public Game2048Item(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Game2048Item(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View background = new View(getContext());

        mTextView = new TextView(getContext());
        mTextView.setTextSize(32);
        mTextView.setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        layoutParams.setMargins(8, 8, 8, 8);
        background.setBackgroundColor(0x33ffffff);

        addView(background, layoutParams);
        addView(mTextView, layoutParams);
        setNumber(0);
    }


    public int getNumber() {
        return mNumber;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public void setNumber(int number) {
        mNumber = number;
        if (number <= 0) {
            mTextView.setText("");
        } else {
            mTextView.setText(mNumber + "");
        }

        switch (number) {
            case 0:
                mTextView.setBackgroundColor(0xFFC8BCB2);
                break;
            case 2:
                mTextView.setBackgroundColor(0xffeee4da);
                break;
            case 4:
                mTextView.setBackgroundColor(0xffede0c8);
                break;
            case 8:
                mTextView.setBackgroundColor(0xfff2b179);
                break;
            case 16:
                mTextView.setBackgroundColor(0xfff59563);
                break;
            case 32:
                mTextView.setBackgroundColor(0xfff67c5f);
                break;
            case 64:
                mTextView.setBackgroundColor(0xfff65e3b);
                break;
            case 128:
                mTextView.setBackgroundColor(0xffedcf72);
                break;
            case 256:
                mTextView.setBackgroundColor(0xffedcc61);
                break;
            case 512:
                mTextView.setBackgroundColor(0xffedc850);
                break;
            case 1024:
                mTextView.setBackgroundColor(0xffedc53f);
                break;
            case 2048:
                mTextView.setBackgroundColor(0xffedc22e);
                break;
            default:
                mTextView.setBackgroundColor(0xff3c3a32);
                break;
        }
    }


    public boolean equals(Game2048Item item) {
        return getNumber() == item.getNumber();
    }
}

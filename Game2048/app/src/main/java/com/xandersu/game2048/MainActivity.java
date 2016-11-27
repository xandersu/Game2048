package com.xandersu.game2048;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xandersu.game2048.com.xandersu.game2048.view.AnimLayer;
import com.xandersu.game2048.com.xandersu.game2048.view.GameView;
import com.xandersu.game2048.utils.SpUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView tv_score;
    private TextView tv_max_score;
    private int score = 0;
    private static MainActivity mainActivity = null;
    private Button bt_restart;
    private GameView mGameView;
    private AnimLayer mAnimlayer;

    public static MainActivity getInstance(){
        return mainActivity;
    }

   public MainActivity(){
       mainActivity = this;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_max_score = (TextView) findViewById(R.id.tv_max_score);
        bt_restart = (Button) findViewById(R.id.bt_restart);
        showMaxScore(SpUtil.getInt(getApplicationContext(),Constant.MAXSCORE,0));
        mGameView = (GameView) findViewById(R.id.gameview);
        mAnimlayer = (AnimLayer) findViewById(R.id.animlayer);
        bt_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameView.startGame();
            }
        });
    }

    public void clearScore(){
        score = 0;
        showScore(0);
    }

    public void showScore(int score){
        if(tv_score != null ){
            tv_score.setText("当前分数:" + score + "");
//            Log.e(TAG, "showScore: " + score);
        }

    }

    public void showMaxScore(int score){
        if(tv_max_score != null ){
            tv_max_score.setText("最高分数:" + score + "");
//            Log.e(TAG, "showScore: " + score);
        }

    }

    public void addScore(int s){
        score += s;
        showScore(score);
        int maxScore = Math.max(score,getMaxScore());
        SpUtil.putInt(getApplicationContext(),Constant.MAXSCORE,maxScore);
        showMaxScore(maxScore);
    }

    private int getMaxScore() {
       return SpUtil.getInt(getApplicationContext(),Constant.MAXSCORE,0);
    }

    public AnimLayer getAnimLayer() {
        return mAnimlayer;
    }
}

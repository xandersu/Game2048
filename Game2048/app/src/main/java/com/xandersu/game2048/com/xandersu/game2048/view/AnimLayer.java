package com.xandersu.game2048.com.xandersu.game2048.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.xandersu.game2048.Constant;

import java.util.ArrayList;
import java.util.List;

public class AnimLayer extends FrameLayout {

	public AnimLayer(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initLayer();
	}

	public AnimLayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayer();
	}

	public AnimLayer(Context context) {
		super(context);
		initLayer();
	}
	
	private void initLayer(){
	}
	
	public void createMoveAnim(final Game2048Item from,final Game2048Item to,int fromX,int toX,int fromY,int toY){
		
		final Game2048Item c = getCard(from.getNumber());
		
		LayoutParams lp = new LayoutParams(Constant.CARD_WIDTH, Constant.CARD_WIDTH);
		lp.leftMargin = fromX*Constant.CARD_WIDTH;
		lp.topMargin = fromY*Constant.CARD_WIDTH;
		c.setLayoutParams(lp);
		
		if (to.getNumber()<=0) {
			to.getTextView().setVisibility(View.INVISIBLE);
		}
		TranslateAnimation ta = new TranslateAnimation(0, Constant.CARD_WIDTH*(toX-fromX), 0, Constant.CARD_WIDTH*(toY-fromY));
		ta.setDuration(100);
		ta.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				to.getTextView().setVisibility(View.VISIBLE);
				recycleCard(c);
			}
		});
		c.startAnimation(ta);
	}



	private Game2048Item getCard(int num){
		Game2048Item c;
		if (cards.size()>0) {
			c = cards.remove(0);
		}else{
			c = new Game2048Item(getContext());
			addView(c);
		}
		c.setVisibility(View.VISIBLE);
		c.setNumber(num);
		return c;
	}
	private void recycleCard(Game2048Item c){
		c.setVisibility(View.INVISIBLE);
		c.setAnimation(null);
		cards.add(c);
	}
	private List<Game2048Item> cards = new ArrayList<Game2048Item>();
	
	public void createScaleTo1(Game2048Item target){
		ScaleAnimation sa = new ScaleAnimation(0.1f, 1, 0.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(100);
		target.setAnimation(null);
		target.getTextView().startAnimation(sa);
	}
	
}

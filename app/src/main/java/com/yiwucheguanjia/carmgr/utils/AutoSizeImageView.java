package com.yiwucheguanjia.carmgr.utils;

import com.android.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;

/**
 * 当宽度一定时,如满屏,高度自适应
 * 
 * @author Thinkpad
 *
 */
public class AutoSizeImageView extends NetworkImageView {

	public AutoSizeImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public AutoSizeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AutoSizeImageView(Context context) {
		super(context);
	}
	private int bitmapHeight;
	private int bitmapWidth;
	@Override
	public void setImageDrawable(Drawable drawable) {
		//
		if (drawable != null) {
			bitmapHeight = drawable.getIntrinsicHeight();
			bitmapWidth = drawable.getIntrinsicWidth();
		}
		super.setImageDrawable(drawable);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (bitmapWidth > 0) {
			int newWidth = getWidth();
			int newHeight = newWidth * bitmapHeight / bitmapWidth;
				LayoutParams params = getLayoutParams();
	  
				if(newWidth>bitmapWidth){
					setScaleType(ScaleType.FIT_XY);
					if (bitmapWidth > 0) {
						newHeight = newWidth * bitmapHeight / bitmapWidth;
						params.height = newHeight;
						params.width = newWidth;
					}
				}else{
					setScaleType(ScaleType.CENTER_INSIDE);
				}
				setLayoutParams(params);
				setMeasuredDimension(newWidth, newHeight);
			} else {
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			}
	}
}

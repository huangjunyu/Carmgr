package com.yiwucheguanjia.carmgr.welcome;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.chechezhi.ui.guide.AbsGuideActivity;
import com.chechezhi.ui.guide.SingleElement;
import com.chechezhi.ui.guide.SinglePage;
import com.yiwucheguanjia.carmgr.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class GuideActivity extends AbsGuideActivity {
    @Override
    public List<SinglePage> buildGuideContent() {

        // prepare the information for our guide
        List<SinglePage> guideContent = new ArrayList<SinglePage>();

        SinglePage page01 = new SinglePage();
        page01.mBackground = getResources().getDrawable(R.mipmap.welcome1);
        guideContent.add(page01);

        SinglePage page02 = new SinglePage();
        page02.mBackground = getResources().getDrawable(R.mipmap.welcome2);
        guideContent.add(page02);

        SinglePage page05 = new SinglePage();
        page05.mCustomFragment = new EntryFragment();
        guideContent.add(page05);

        return guideContent;
    }

    @Override
    public boolean drawDot() {
        return true;
    }

    @Override
    public Bitmap dotDefault() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_dot_default);    }

    @Override
    public Bitmap dotSelected() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_dot_selected);
    }

    @Override
    public int getPagerId() {
        return R.id.guide_container;
    }
    public void entryApp() {
        finish();
    }
}

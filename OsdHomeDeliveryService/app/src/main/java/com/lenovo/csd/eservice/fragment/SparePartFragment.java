package com.lenovo.csd.eservice.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovo.csd.eservice.R;
import com.lenovo.csd.eservice.adapter.OrderFragmentAdapter;
import com.lenovo.csd.eservice.http.callback.NoDoubleClickLinstenner;

import java.util.ArrayList;
import java.util.List;

/**
 * 备件tab页面
 * Created by shengtao
 * on 2016/7/26
 * 23:48
 */
public class SparePartFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private TextView mTxtUnusedLine;//未使用Tab指示线
    private TextView mTxtUsedLine;//已使用Tab指示线
    private RelativeLayout mRelativeUnused;
    private RelativeLayout mRelativeUsed;
    private TextView mTxtUnusedParts;
    private ImageView mImgUnusedPart;
    private ImageView mImgUsedPart;
    private TextView mTxtUsedParts;
    private ViewPager mViewPager;

    //变量
    private UnusedPartsFragment unusedFragment;//未使用备件fragment
    private UsedPartsFragment usedFragment;//已使用备件fragment
    private List<Fragment> fragments;//数据
    private OrderFragmentAdapter partsAdapter;//适配器


    //常量
    private static final int TAB_UNUSED = 0;//未使用页面下标位置
    private static final int TAB_USED = 1;
    public static final String UNUSED_TYPE = "0";
    public static final String USED_TYPE = "1";//已使用类型
    public static final String PAGE_SIZE = "10";//每页最多六条


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_spare_part, null);
        mTxtUnusedLine = (TextView) view.findViewById(R.id.text_partUnusedLine);
        mTxtUsedLine = (TextView) view.findViewById(R.id.text_partsUsedLine);
        mRelativeUnused = (RelativeLayout) view.findViewById(R.id.relative_UnusedTab);
        mRelativeUsed = (RelativeLayout) view.findViewById(R.id.relative_UsedTab);
        mTxtUnusedParts = (TextView) view.findViewById(R.id.text_unusedPart);
        mTxtUsedParts = (TextView) view.findViewById(R.id.text_usedPart);
        mImgUnusedPart = (ImageView) view.findViewById(R.id.img_partUnused);
        mImgUsedPart = (ImageView) view.findViewById(R.id.img_partUsed);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_backUpParts);

        fragments = new ArrayList<>();
        unusedFragment = new UnusedPartsFragment();
        usedFragment = new UsedPartsFragment();
        fragments.add(unusedFragment);
        fragments.add(usedFragment);
        partsAdapter = new OrderFragmentAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(partsAdapter);
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        //设置监听
        mRelativeUnused.setOnClickListener(noDoubleClickLinstenner);
        mRelativeUsed.setOnClickListener(noDoubleClickLinstenner);
        mViewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.isHidden()) {
            return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * tab切换要用，预留
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case TAB_UNUSED:
                changeUIStatus(TAB_UNUSED);
                unusedFragment.setSelfSelect(true);
                usedFragment.setSelfSelect(false);
                break;
            case TAB_USED:
                changeUIStatus(TAB_USED);
                unusedFragment.setSelfSelect(false);
                usedFragment.setSelfSelect(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private NoDoubleClickLinstenner noDoubleClickLinstenner = new NoDoubleClickLinstenner() {
        @Override
        public void doClick(View view) {
            switch (view.getId()) {
                case R.id.relative_UnusedTab:
                    changeUIStatus(TAB_UNUSED);
                    //viewpager 切换到未使用标签
                    mViewPager.setCurrentItem(TAB_UNUSED);

                    break;
                case R.id.relative_UsedTab:
                    changeUIStatus(TAB_USED);
                    //viewpager切换到已使用标签
                    mViewPager.setCurrentItem(TAB_USED);

                    break;
            }
        }
    };

    /**
     * 切换状态
     */
    private void changeUIStatus(int position) {
        switch (position) {
            case TAB_UNUSED:
                mTxtUnusedLine.setBackgroundColor(getResources().getColor(R.color.red_E47B78));
                mTxtUnusedParts.setTextColor(getResources().getColor(R.color.red_E47B78));
                mImgUnusedPart.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_unusedpart_selected));
                mTxtUsedLine.setBackgroundColor(getResources().getColor(R.color.gray_F8F8F8));
                mTxtUsedParts.setTextColor(getResources().getColor(R.color.gray_cccccc));
                mImgUsedPart.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_usedpart_unselected));


                break;
            case TAB_USED:
                mTxtUnusedLine.setBackgroundColor(getResources().getColor(R.color.gray_F8F8F8));
                mTxtUnusedParts.setTextColor(getResources().getColor(R.color.gray_cccccc));
                mImgUnusedPart.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_unusedpart_unselected));

                mTxtUsedLine.setBackgroundColor(getResources().getColor(R.color.red_E47B78));
                mTxtUsedParts.setTextColor(getResources().getColor(R.color.red_E47B78));
                mImgUsedPart.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_usedpart_selected));


                break;
        }
    }

    @Override
    public void setSelected(boolean selected) {
        if(unusedFragment == null){
            unusedFragment = UnusedPartsFragment.newInstance();
        }
        if(usedFragment == null){
            usedFragment = UsedPartsFragment.newInstance();
        }
        unusedFragment.setParentSelet(selected);
        usedFragment.setParentSelet(selected);
    }
}


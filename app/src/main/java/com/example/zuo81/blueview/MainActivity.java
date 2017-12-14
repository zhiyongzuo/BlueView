package com.example.zuo81.blueview;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.zuo81.blueview.fragment.PageFragment;
import com.example.zuo81.blueview.model.PageModel;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    List<PageModel> pageModels = new ArrayList<>();
    {
        pageModels.add(new PageModel("RulerView", R.layout.ruler_view));
        pageModels.add(new PageModel("SquareView", R.layout.square_image));
      //下面放在括号内可以，括号外出错
      test = 3;
    }
    int test = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.addLogAdapter(new AndroidLogAdapter());

        mTabLayout = findViewById(R.id.tab_layout_activity_main);
        mViewPager = findViewById(R.id.view_pager_activity_main);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PageFragment.newInstance(pageModels.get(position).getRes());
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return pageModels.get(position).getTitle();
            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
    }
}

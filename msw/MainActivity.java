package com.cowboy.msw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.cowboy.msw.ui.main.PlaceholderFragment;
import com.cowboy.msw.ui.main.SectionsPagerAdapter;
import com.cowboy.msw.ui.main.WalletFramgnet;

public class MainActivity extends AppCompatActivity {

    public final static int SCAN_REQUEST = 1;
    public final static int FINAL_REQUEST = 2;
    public final static int SCAN_RESULT_OK = 1;
    public final static int SCAN_RESULT_STOP = 0;

    public static WalletFramgnet currentFragment = null;
    private ViewPager viewPager = null;
    private SectionsPagerAdapter sectionsPagerAdapter = null;
    private int tabResIds[] = {R.id.tabLayoutHome, R.id.tabLayoutLike, R.id.tabLayoutWeb};
    private int tabBtnIds[] = {R.id.tabBtnHome, R.id.tabBtnLike, R.id.tabBtnWeb};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                setTitle(R.string.app_name);
                selectTab(i);
                if (i == 0) {
                    PlaceholderFragment fragment = (PlaceholderFragment) sectionsPagerAdapter.getItem(0);
                    fragment.backToHome(MainActivity.this);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setupTabs();
        viewPager.setCurrentItem(0);
    }

    @Override
    public void setTitle(int resId) {
        TextView title = (TextView)findViewById(R.id.title);
        title.setText(resId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode%2 == SCAN_REQUEST) {
            if (resultCode == SCAN_RESULT_OK) {
                if (currentFragment != null) {
                    currentFragment.addItem();
                }
            }
        } else {
            if (resultCode == 1) {
                PlaceholderFragment fragment = (PlaceholderFragment) sectionsPagerAdapter.getItem(0);
                fragment.backToHome(this);
            } else if (currentFragment != null) {
                currentFragment.clearItems();
            }
        }
    }

    protected void setupTabs() {
        View.OnClickListener tabListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<tabResIds.length; i++) {
                    ImageView imgView = (ImageView)findViewById(tabBtnIds[i]);
                    if (tabResIds[i] == v.getId()) {
                        imgView.setColorFilter(getResources().getColor(R.color.mainForeColor));
                        viewPager.setCurrentItem(i);
                    } else {
                        imgView.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                    }
                    if (i == 0) {
                        PlaceholderFragment fragment = (PlaceholderFragment) sectionsPagerAdapter.getItem(0);
                        fragment.backToHome(MainActivity.this);
                    }
                }
            }
        };
        for (int i=0; i<tabResIds.length; i++) {
            findViewById(tabResIds[i]).setOnClickListener(tabListener);
        }
    }

    protected void selectTab(int idx) {
        for(int i=0; i<tabResIds.length; i++) {
            ImageView imgView = (ImageView)findViewById(tabBtnIds[i]);
            if (i == idx) {
                imgView.setColorFilter(getResources().getColor(R.color.mainForeColor));
            } else {
                imgView.setColorFilter(getResources().getColor(android.R.color.darker_gray));
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() != 0) {
            viewPager.setCurrentItem(0);
        } else {
            PlaceholderFragment fragment = (PlaceholderFragment) sectionsPagerAdapter.getItem(0);
            if (!fragment.backToHome(this)) {
                finish();
            }
        }
    }
}
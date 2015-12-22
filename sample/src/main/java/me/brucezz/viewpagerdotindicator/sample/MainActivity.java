package me.brucezz.viewpagerdotindicator.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import me.brucezz.viewpagerdotindicator.DotIndicator;

/**
 * Created by zero on 12/22/2015.
 * DotIndicator
 */
public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter adapter;
    private List<View> views;

    private DotIndicator indicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadViews();

        initViewPager();

        indicator = (DotIndicator) findViewById(R.id.dot_indicator);

    }

    private void loadViews() {
        views = new ArrayList<>();
        views.add(loadImage(R.drawable.img1));
        views.add(loadImage(R.drawable.img2));
        views.add(loadImage(R.drawable.img3));
        views.add(loadImage(R.drawable.img4));
        views.add(loadImage(R.drawable.img5));

    }

    private ImageView loadImage(int resId) {
        ImageView img = new ImageView(this);
        img.setImageResource(resId);
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        return img;
    }

    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }
        };

        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicator.moveWithViewPager(position, positionOffset);//跟随ViewPager同步移动
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "Settings");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == 1) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package me.brucezz.viewpagerdotindicator.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import me.brucezz.viewpagerdotindicator.DotIndicator;


/**
 * Created by zero on 12/22/2015.
 * DotIndicator
 */
public class SettingsActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private DotIndicator indicator;

    private TextView sizeText;
    private TextView intervalText;
    private TextView countText;
    private TextView durationText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initDotSwitchView();

        sizeText = (TextView) findViewById(R.id.tv_size);
        intervalText = (TextView) findViewById(R.id.tv_interval);
        countText = (TextView) findViewById(R.id.tv_count);
        durationText = (TextView) findViewById(R.id.tv_duration);

        findViewById(R.id.prev).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);

        ((SeekBar) findViewById(R.id.size)).setOnSeekBarChangeListener(this);
        ((SeekBar) findViewById(R.id.interval)).setOnSeekBarChangeListener(this);
        ((SeekBar) findViewById(R.id.count)).setOnSeekBarChangeListener(this);
        ((SeekBar) findViewById(R.id.duration)).setOnSeekBarChangeListener(this);
    }

    /**
     * 用代码设置DotIndicator的属性
     */
    private void initDotSwitchView() {
        indicator = (DotIndicator) findViewById(R.id.dot_indicator);

        indicator.setDotSize(12);//12dp
        indicator.setDotCount(5);
        indicator.setDotInterval(24);//24dp
        indicator.setDotColor(Color.BLUE);
        indicator.setDotSelectedColor(Color.RED);
        indicator.setDuration(300);//300ms

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.prev) {
            indicator.previous();
            return;
        }

        if (v.getId() == R.id.next) {
            indicator.next();
            return;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {

            case R.id.size:
                //size 10 ~ 24dp
                indicator.setDotSize(progress + 10);
                sizeText.setText("Size: " + (progress + 10) + "dp");
                break;

            case R.id.interval:
                //interval 0 ~ 48dp
                indicator.setDotInterval(progress);
                intervalText.setText("Interval: " + progress + "dp");
                break;

            case R.id.count:
                //count 3 ~ 9
                indicator.setDotCount(progress + 3);
                countText.setText("Count: " + (progress + 3));
                break;

            case R.id.duration:
                //duration 100 ~ 2000ms
                indicator.setDuration(10 * (progress + 10));
                durationText.setText("Duration: " + (10 * (progress + 10)) + "ms");
                break;
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}

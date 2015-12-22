# ViewPagerDotIndicator
ViewPager Dot Indicator

# Usage

- 属性设置

```Java
dotIndicator.setDotSize(12);//12dp
dotIndicator.setDotColor(5);
dotIndicator.setDotInterval(24);//24dp
dotIndicator.setDotColor(0x70ffffff);
dotIndicator.setDotSelectedColor(0xffffff);
dotIndicator.setCurrent(0);
dotIndicator.setDuration(300);//300ms
```

- 普通切换

dotSwitchView.next();//上一个
dotSwitchView.previous();//下一个

- 和ViewPager配合使用

**dotIndicator.moveWithViewPager()**

```Java
viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        dotIndicator.moveWithViewPager(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
});
```


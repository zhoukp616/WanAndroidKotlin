package com.zkp.android.widget;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.PointF;

/**
 * @author: zkp
 * @project: Gank
 * @package: com.zkp.gank.widget
 * @time: 2019/5/14 10:28
 * @description:
 */
public class Unit implements Comparable<Unit>, Cloneable {

    static long DURATION = 800;

    private ValueAnimator valueanimator = ValueAnimator.ofFloat(0, 1);

    /**
     * 当前点的值
     */
    private float value;
    /**
     * 当前点的额外信息（可选，x轴）
     */
    private String extX;

    /**
     * 当前点的坐标信息,都是相对的canvas而不是linesArea
     */
    private PointF xy;

    /**
     * 当前点的动画进度，
     * 默认为1表示无动画
     */
    private float percent = 1f;

    public Unit(float value) {
        this.value = value;
    }

    public Unit(float value, String extX) {
        this.value = value;
        this.extX = extX;
    }


    public float getValue() {
        return value;
    }

    PointF getXY() {
        return xy;
    }

    void setXY(PointF xy) {
        this.xy = xy;
    }

    float getPercent() {
        return percent;
    }

    void setPercent(float percent) {
        this.percent = percent;
    }

    String getExtX() {
        return extX;
    }

    public void setExtX(String extX) {
        this.extX = extX;
    }

    void cancelToEndAnim() {
        if (valueanimator.isRunning()) {
            valueanimator.cancel();
        }
        percent = 1f;
    }

    void startAnim(TimeInterpolator value) {
        if (percent > 0 || valueanimator.isRunning()) {
            return;
        }
        // 如果value小于一定阈值就不开启动画
        if (Math.abs((int) this.value) < 0.1) {
            percent = 1;
            return;
        }
        valueanimator.setFloatValues(0, 1);
        valueanimator.setDuration(DURATION);
        valueanimator.setInterpolator(value);
        valueanimator.addUpdateListener(animation -> percent = (float) animation.getAnimatedValue());
        valueanimator.start();
    }


    @Override
    public int compareTo(Unit o) {
        if (value == o.value) {
            return 0;
        } else if (value > o.value) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Unit)) {
            return false;
        }
        Unit unit = (Unit) obj;
        return value == unit.value
                && (extX == unit.extX) || (extX != null && extX.equals(unit.extX));
    }

    @Override
    protected Unit clone() {
        // 转化为深度拷贝，防止在集合中排序时的引用问题
        try {
            return (Unit) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "value=" + value +
                ", extX='" + extX + '\'' +
                ", xy=" + xy +
                '}';
    }
}

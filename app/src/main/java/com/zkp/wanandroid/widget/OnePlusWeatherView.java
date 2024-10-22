package com.zkp.wanandroid.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.zkp.wanandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zkp
 * @project: Gank
 * @package: com.zkp.gank.widget
 * @time: 2019/5/14 15:30
 * @description:
 */
public class OnePlusWeatherView extends View {

    private static final String TAG = "OnePlusWeatherView";
    /**
     * 点的集合
     */
    ArrayList<PointF> points = new ArrayList<>();
    /**
     * 6组元数据
     */
    private List<WeatherBean> data = new ArrayList<>();
    @SuppressLint("UseSparseArrays")
    /*
      天气图标集合
     */
    private Map<Integer, Bitmap> icons = new HashMap<>(5);
    /**
     * 每一天所占的宽度，应为屏幕宽度的1/6
     */
    private int interval;
    private int screenWidth;
    /**
     * 所有最高温度点的最高高度
     */
    private int maxPointHeight;
    /**
     * 所有最低温度点的最低高度
     */
    private int minPointHeight;
    /**
     * 元数据中所有温度的的最高和最低温度
     */
    private int maxTemperature;
    private int minTemperature;
    /**
     * 折线拐点的半径,就是小圆点
     */
    private float pointRadius;
    private int viewHeight;
    private int viewWidth;
    /**
     * 折线拐点的单位高度
     */
    private float pointUnitH;
    /**
     * 天气图标的边长
     */
    private float iconWidth;

    /**
     * 画线的笔
     */
    private Paint linePaint;
    /**
     * 画文字的笔
     */
    private Paint textPaint;
    /**
     * 画拐点的笔
     */
    private Paint circlePaint;

    public OnePlusWeatherView(Context context) {
        this(context, null);
    }

    public OnePlusWeatherView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnePlusWeatherView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initSize(context);

        initPaint(context);

        initIcons();
    }

    /**
     * 初始化一些固定数据,如长宽，间隔之类的
     *
     * @param context context
     */
    private void initSize(Context context) {
        //拿到屏幕的宽高，单位是像素
        screenWidth = getResources().getDisplayMetrics().widthPixels;

        //控件高度为屏幕宽度 - 100dp
        viewHeight = (int) (screenWidth - dp2pxF(getContext(), 100));
        //控件宽度为屏幕宽度
        viewWidth = screenWidth;

        //间隔为屏幕的1/5
        interval = (int) (screenWidth / 5.0f);

        //所有最高温度点的最高高度，默认是控件的高度的1/2
        maxPointHeight = viewHeight / 2;
        //所有最低温度点的最低高度，默认给个15dp,就是从控件下边到最低点的距离
        minPointHeight = (int) dp2pxF(getContext(), 20f);

        //天气图标的边长，默认是间隔的一半
        iconWidth = interval / 2.0f;

        //默认折线拐点圆的半径为3dp
        pointRadius = dp2pxF(context, 3f);
    }

    /**
     * 初始化画笔
     *
     * @param context context
     */
    private void initPaint(Context context) {
        //创建一个画线的笔
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置线宽度
        linePaint.setStrokeWidth(dp2px(context, 1f));

        //创建一个画文字的笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.CENTER);  //设置文字居中
        textPaint.setColor(Color.WHITE);

        //创建一个画折线拐点(圆)的笔
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.WHITE);
    }

    /**
     * 初始化天气图标
     */
    private void initIcons() {
        icons.clear();
        int[] weatherIds = WeatherBean.getAllWeatherId();
        for (int weatherId : weatherIds) {
            Bitmap bmp = getWeatherIconForId(weatherId, iconWidth, iconWidth);
            icons.put(weatherId, bmp);
        }
    }

    public static float dp2pxF(Context c, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    /**
     * 下面都是工具类，dp单位转px单位
     */
    public static int dp2px(Context c, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    /**
     * 根据天气ID获取对应的图标并缩放到指定大小
     *
     * @param weatherId weatherId
     * @param width     指定宽度
     * @param height    指定高度
     * @return
     */
    private Bitmap getWeatherIconForId(int weatherId, float width, float height) {
        int iconResId = getIconResId(weatherId);
        Bitmap bmp;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = true 表示只读图片，不加载到内存中，
        // 设置这个参数为ture，就不会给图片分配内存空间，但是可以获取到图片的大小等属性; 设置为false, 就是要加载这个图片.
        options.inJustDecodeBounds = true;
        //拿到图片的参数，图片参数会传到options中
        BitmapFactory.decodeResource(getResources(), iconResId, options);
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        options.inSampleSize = 1; //先设置采样大小为1
        if (outWidth > width || outHeight > height) {
            //如果图片的实际宽或高要比指定的宽或高大就要缩小
            // 计算出实际宽高和目标宽高的比率 ,四舍五入
            int ratioW = Math.round(outWidth / width);
            int ratioH = Math.round(outHeight / height);
            //取大的,对原图进行降采样
            options.inSampleSize = Math.max(ratioW, ratioH);
        }
        //设置为false了，就是要加载图片了
        options.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeResource(getResources(), iconResId, options);
        return bmp;
    }

    /**
     * 通过天气Id获取对应的图片ResId
     *
     * @param weatherId weatherId
     * @return
     */
    private int getIconResId(int weatherId) {
        int resId;
        switch (weatherId) {
            case WeatherBean.SUN:
                resId = R.drawable.icon_sunny;
                break;
            case WeatherBean.CLOUDY:
                resId = R.drawable.icon_cloudly_more;
                break;
            case WeatherBean.RAIN:
                resId = R.drawable.icon_rain;
                break;
            case WeatherBean.SNOW:
                resId = R.drawable.icon_snowy;
                break;
            case WeatherBean.SUN_CLOUDY:
                resId = R.drawable.icon_cloudly;
                break;
            case WeatherBean.THUNDER:
                resId = R.drawable.icon_thunder_storm;
                break;
            default:
                resId = R.drawable.icon_sunny;
        }
        return resId;
    }

    /**
     * 通过天气Id获取对应的天气
     *
     * @param weatherId weatherId
     * @return
     */
    private String getWeather(int weatherId) {
        String weather;
        switch (weatherId) {
            case WeatherBean.SUN:
                weather = "晴";
                break;
            case WeatherBean.CLOUDY:
                weather = "阴";
                break;
            case WeatherBean.RAIN:
                weather = "雨";
                break;
            case WeatherBean.SNOW:
                weather = "雪";
                break;
            case WeatherBean.SUN_CLOUDY:
                weather = "多云";
                break;
            case WeatherBean.THUNDER:
                weather = "雷阵雨";
                break;
            default:
                weather = "晴";
        }
        return weather;
    }

    public static int sp2px(Context c, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initSize(getContext());
        calculatePointUnitHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (data == null || data.size() == 0) {
            return;
        }

        drawVerticalLines(canvas);

        drawDateAndWeekText(canvas);

        drawWeatherIcon(canvas);

        drawBrokenLine(canvas);

        drawTemperatureText(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //设置控件的最终视图大小(宽高)
        setMeasuredDimension(viewWidth, viewHeight);
        //计算温度点的单位高度
        calculatePointUnitHeight();
    }

    /**
     * 画竖线 4根
     *
     * @param canvas
     */
    private void drawVerticalLines(Canvas canvas) {
        canvas.save();//保存画布当前状态(平移、放缩、旋转、错切、裁剪等),和canvas.restore()配合使用

        linePaint.setColor(Color.WHITE);
        linePaint.setAlpha(50);

        float starX, starY, stopX, stopY;
        starY = 0;
        stopY = viewHeight;
        for (int i = 0; i < 4; i++) {
            starX = stopX = (i + 1) * interval;

            canvas.drawLine(starX, starY, stopX, stopY, linePaint);
        }
        canvas.restore();
    }

    /**
     * 画日期的文字
     *
     * @param canvas
     */
    private void drawDateAndWeekText(Canvas canvas) {
        canvas.save();

        //画最上面那一行的日期
        textPaint.setTextSize(sp2pxF(getContext(), 14f));
        //拿到字体测量器
        Paint.FontMetrics metrics = textPaint.getFontMetrics();
        float x;
        //ascent:上坡度，是文字的基线到文字的最高处的距离
        //descent:下坡度,，文字的基线到文字的最低处的距离
        float dateY = dp2pxF(getContext(), 10f) - (metrics.ascent + metrics.descent) / 2;
        for (int i = 0; i < 5; i++) {
            String dateText = data.get(i).getDate();
            x = i * interval + interval / 2.0f;
            canvas.drawText(dateText, x, dateY, textPaint);
        }

        //画第二行的星期
        textPaint.setTextSize(sp2pxF(getContext(), 12f));
        //ascent:上坡度，是文字的基线到文字的最高处的距离
        //descent:下坡度,，文字的基线到文字的最低处的距离
        float weekY = dp2pxF(getContext(), 30f) - (metrics.ascent + metrics.descent) / 2;
        for (int i = 0; i < 5; i++) {
            String weekText = data.get(i).getWeek();
            x = i * interval + interval / 2.0f;
            canvas.drawText(weekText, x, weekY, textPaint);
        }

        canvas.restore();
    }

    /**
     * 画天气图标
     *
     * @param canvas
     */
    private void drawWeatherIcon(Canvas canvas) {
        canvas.save();
        float iconX, iconY;   //图标的坐标
        //Y坐标都是一样的，默认为控件高度的3/8再往上一点
        iconY = viewHeight / 4.0f - dp2pxF(getContext(), 10f);
        float weatherY = viewHeight / 4.0f + dp2pxF(getContext(), 20f);
        for (int i = 0; i < 5; i++) {
            //拿到每一天的天气对应的图标
            Bitmap icon = icons.get(data.get(i).getWeatherId());
            iconX = i * interval + interval / 2.0f;

            //创建一个用来绘制图标的矩形区域
            RectF iconRect = new RectF(iconX - iconWidth / 2.0f,
                    iconY - iconWidth / 2.0f,
                    iconX + iconWidth / 2.0f,
                    iconY + iconWidth / 2.0f);
            assert icon != null;
            canvas.drawBitmap(icon, null, iconRect, null);
            canvas.drawText(getWeather(data.get(i).getWeatherId()), iconX, weatherY, textPaint);
        }

        canvas.restore();
    }

    /**
     * 画折线图了
     *
     * @param canvas
     */
    private void drawBrokenLine(Canvas canvas) {
        canvas.save();

        //初始化点的集合,放在这里是因为传入了数据才有点,也遍历出了最高温度和最低温度，才可以初始化
        initPoints();

        //设置为填充且描边
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint.setColor(Color.WHITE);
        linePaint.setAlpha(120);

        //定义一个用于绘制折线的Path
        //Path()类用于画线（直线，曲线都可以），有时也用于画轮廓,就是描述路径的类
        //用Canvas中的drawPath能把这个路径画出来
        Path linePath = new Path();
        for (int i = 0; i < points.size(); i++) {
            float x = points.get(i).x;
            float y = points.get(i).y;

            if (i == 0) {
                linePath.moveTo(x, y);
            } else if (i == points.size() - 1) {
                linePath.lineTo(x, y);
                linePath.lineTo(points.get(0).x, points.get(0).y);
            } else {
                linePath.lineTo(x, y);
            }
        }
        //画折线
        canvas.drawPath(linePath, linePaint);

        //画拐点
        for (PointF point : points) {
            if (point.x != 0 && point.x != screenWidth) {
                //不在屏幕边框上的点就画出来
                canvas.drawCircle(point.x, point.y, pointRadius, circlePaint);
            }
        }

        canvas.restore();
    }

    /**
     * 画出与点对应的温度
     *
     * @param canvas
     */
    private void drawTemperatureText(Canvas canvas) {
        canvas.save();

        textPaint.setTextSize(sp2pxF(getContext(), 12f));

        String text;
        float x;
        float y;

        //先画上面6个点的温度,数据对应的是0~5,坐标对应的是points集合中的1~6
        for (int i = 0; i < 5; i++) {
            //上面的是最高温度
            text = data.get(i).getMaxTemperature() + "°";
            x = points.get(i + 1).x;
            y = points.get(i + 1).y - dp2pxF(getContext(), 12f); //要比拐点高一点,注意是减
            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            canvas.drawText(text, x, y - (metrics.ascent + metrics.descent) / 2, textPaint);
        }

        //再画下面6个点的温度,数据对应的是0~5,坐标对应的是points集合中的14~9
        for (int i = 0; i < 5; i++) {
            //下面的是最低温度,注意这里是4-i
            text = data.get(4 - i).getMinTemperature() + "°";
            x = points.get(i + 8).x;
            y = points.get(i + 8).y + dp2pxF(getContext(), 12f);  //要比拐点低一点，注意是加
            Paint.FontMetrics metrics = textPaint.getFontMetrics();
            canvas.drawText(text, x, y - (metrics.ascent + metrics.descent) / 2, textPaint);
        }

        canvas.restore();
    }

    public static float sp2pxF(Context c, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    /**
     * 初始化点的集合，总共有16个点， 温度点12个，屏幕边缘上4个
     */
    private void initPoints() {
        points.clear();
        float x;
        float y;

        //先初始化上段折线的点
        for (int i = 0; i < 5; i++) {
            //拿到最高温度与最低温度的温度差
            int temGap = data.get(i).getMaxTemperature() - minTemperature;
            x = i * interval + interval / 2.0f;
            //计算出拐点的Y坐标
            y = (viewHeight - (minPointHeight + temGap * pointUnitH));
            if (i == 0) {
                //先添加屏幕左边框上面的一个点,x=0,高度就比第一今日最高点低10dp
                points.add(new PointF(0, y + dp2pxF(getContext(), 10f)));
            }
            //再把这个点添加上
            points.add(new PointF(x, y));

            if (i == 4) {
                //最后添加屏幕右框上面的一个点,x=屏幕宽度，高度就比最右边那天的最高点低10dp
                points.add(new PointF(screenWidth, y + dp2pxF(getContext(), 10f)));
            }
        }

        //再初始化下段折线的点，这次顺序是从右往左了,注意这里是i--
        for (int i = 4; i >= 0; i--) {
            //拿到最低温度与最低温度的温度差
            int temGap = data.get(i).getMinTemperature() - minTemperature;
            x = i * interval + interval / 2.0f;
            y = (viewHeight - (minPointHeight + temGap * pointUnitH));  //计算出拐点的Y坐标
            if (i == 4) {
                //先添加屏幕右边框下面的一个点,x=屏幕宽度，高度就比最右边那天的最低点低10dp,形成平行效果
                points.add(new PointF(screenWidth, y + dp2pxF(getContext(), 10f)));
            }
            //再把这个点添加上
            points.add(new PointF(x, y));

            if (i == 0) {
                //最后添加屏幕左边框下面的一个点,x=0，高度就比最右边那天的最低点低10dp
                points.add(new PointF(0, y + dp2pxF(getContext(), 10f)));
            }
        }
    }

    /**
     * 计算温度点的单位高度
     */
    private void calculatePointUnitHeight() {
        int lastMaxTem = -1000;   //临时存储当前遍历到的最高温度和最低温度
        int lastMinTem = 1000;

        for (WeatherBean bean : data) {
            if (bean.getMaxTemperature() > lastMaxTem) {
                lastMaxTem = bean.getMaxTemperature();
            }

            if (bean.getMinTemperature() < lastMinTem) {
                lastMinTem = bean.getMinTemperature();
            }
        }

        maxTemperature = lastMaxTem;
        minTemperature = lastMinTem;

        //计算出温差值
        float gap = (maxTemperature - minTemperature) / 1.0f;
        gap = (gap == 0.0f ? 1.0f : gap);      //保证分母不为0
        //计算出折线拐点的单位高度
        pointUnitH = (maxPointHeight - minPointHeight) / gap;
    }

    /**
     * 传入数据
     *
     * @param data
     */
    public void setData(List<WeatherBean> data) {
        if (data == null || data.isEmpty() || data.size() < 5) {
            return;
        }

        this.data = data;
        calculatePointUnitHeight();

        //View本身调用迫使view重画
        invalidate();
    }
}
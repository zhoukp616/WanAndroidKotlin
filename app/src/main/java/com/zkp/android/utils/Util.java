package com.zkp.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.widget.EdgeEffect;

import java.lang.reflect.Field;

/**
 * @author: zkp
 * @project: Gank
 * @package: com.zkp.gank.utils
 * @time: 2019/5/14 10:27
 * @description:
 */
public class Util {

    public static int getCeil5(float num) {
        boolean isNegative = num < 0;
        return (((int) ((isNegative ? -num : num) + 4.9f)) / 5 * 5) * (isNegative ? -1 : 1);
    }

    public static float calcTextSuitBaseY(RectF rectF, Paint paint) {
        return rectF.top + rectF.height() / 2 -
                (paint.getFontMetrics().ascent + paint.getFontMetrics().descent) / 2;
    }

    public static float size2sp(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, context.getResources().getDisplayMetrics());
    }

    public static int dip2px(float dipValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static float getTextHeight(Paint textPaint) {
        return -textPaint.ascent() - textPaint.descent();
    }

    public static void trySetColorForEdgeEffect(EdgeEffect edgeEffect, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            edgeEffect.setColor(color);
            return;
        }
        try {
            Field edgeField = EdgeEffect.class.getDeclaredField("mEdge");
            edgeField.setAccessible(true);
            Drawable mEdge = (Drawable) edgeField.get(edgeEffect);
            mEdge.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            mEdge.setCallback(null);
            Field glowField = EdgeEffect.class.getDeclaredField("mGlow");
            glowField.setAccessible(true);
            Drawable mGlow = (Drawable) glowField.get(edgeEffect);
            mGlow.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            mGlow.setCallback(null);
        } catch (Exception ignored) {
        }
    }

    public static int tryGetStartColorOfLinearGradient(LinearGradient gradient) {
        try {
            Field field = LinearGradient.class.getDeclaredField("mColors");
            field.setAccessible(true);
            int[] colors = (int[]) field.get(gradient);
            return colors[0];
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Field field = LinearGradient.class.getDeclaredField("mColor0");
                field.setAccessible(true);
                return (int) field.get(gradient);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return Color.BLACK;
    }

}

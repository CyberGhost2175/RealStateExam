package com.example.realstateexamapp.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class Utils {

    public static ViewGroup.LayoutParams createLayoutParamsInDp(Context context, int widthDp, int heightDp, int marginDp) {
        int widthPixels = dpToPixels(context, widthDp);
        int heightPixels = dpToPixels(context, heightDp);

        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(widthPixels, heightPixels);
        int marginPixels = dpToPixels(context, marginDp);
        layoutParams.setMargins(marginPixels, marginPixels, marginPixels, marginPixels);

        return layoutParams;
    }

    public static ViewGroup.LayoutParams createLayoutParams(Context context, ViewGroup.LayoutParams layoutParams, int marginDp) {
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(layoutParams);
        int marginPixels = dpToPixels(context, marginDp);
        marginLayoutParams.setMargins(marginPixels, marginPixels, marginPixels, marginPixels);
        return layoutParams;
    }

    public static int dpToPixels(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
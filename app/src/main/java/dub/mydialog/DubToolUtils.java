package dub.mydialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class DubToolUtils {
    private static DubAlertDialog alterDialog = null;
    private static DubCustomDialog loadingDialog = null;

    public static void showHintInfoDialog(Context context, String message) {
        showAlertInfoDialog(context, "提示", message, "知道了", null);
    }

    public static void showHintInfoDialog(Context context, String message, DubAlertDialog.OnDubClickListener centerListener) {
        showAlertInfoDialog(context, "提示", message, "知道了", centerListener);
    }

    public static void showHintInfoDialog(Context context, String title, String message, DubAlertDialog.OnDubClickListener leftListener) {
        showAlertInfoDialog(context, title, message, "确定", "取消", leftListener, null);
    }

    public static void showAlertInfoDialog(Context context, String title, String message, String centerStr, DubAlertDialog.OnDubClickListener centerListener) {
        showAlertInfoDialog(context, title, message, null, centerStr, null, null, centerListener, null);
    }

    public static void showAlertInfoDialog(Context context, String title, String message, String leftStr, String rightStr, DubAlertDialog.OnDubClickListener leftListener, DubAlertDialog.OnDubClickListener rightListener) {
        showAlertInfoDialog(context, title, message, leftStr, null, rightStr, leftListener, null, rightListener);
    }

    public static void showAlertInfoDialog(Context context, String title, String message, String leftStr, String centerStr, String rightStr,
                                           DubAlertDialog.OnDubClickListener leftListener, DubAlertDialog.OnDubClickListener centerListener, DubAlertDialog.OnDubClickListener rightListener) {
        try {
            DubAlertDialog.Builder builder = new DubAlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setCanceledOnTouchOutside(false);
            if (!TextUtils.isEmpty(leftStr)) builder.setPositiveButton(leftStr, leftListener);
            if (!TextUtils.isEmpty(centerStr)) builder.setNeutralButton(centerStr, centerListener);
            if (!TextUtils.isEmpty(rightStr)) builder.setNegativeButton(rightStr, rightListener);
            alterDialog = builder.create();
            alterDialog.show();
        } catch (Exception e) {//避免context先销毁再弹框时报错
            e.printStackTrace();
        }
    }

    //关闭AlertInfo对话框
    public static void closeAlertInfoDialog() {
        try {
            if (null != alterDialog) alterDialog.dismiss();
            alterDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //显示loading对话框
    public static void showLoadingDialog(Context context, String showStr) {
        try {
            RelativeLayout loadingRy = new RelativeLayout(context);

            GradientDrawable loadingRyBgShape = new GradientDrawable();
            loadingRyBgShape.setColor(Color.parseColor("#7d666666"));
            loadingRyBgShape.setCornerRadius(dip2px(context, 6));
            setBackgroundAllVersion(loadingRy, loadingRyBgShape);

            ProgressBar loadingPB = new ProgressBar(context);
            loadingPB.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.progressbar_style_drawable_shape));
            RelativeLayout.LayoutParams loadingPbLp = new RelativeLayout.LayoutParams(dip2px(context, 60), dip2px(context, 60));
            loadingPbLp.addRule(RelativeLayout.CENTER_IN_PARENT);
            loadingRy.addView(loadingPB, loadingPbLp);

            TextView loadingT = new TextView(context);
            loadingT.setGravity(Gravity.CENTER);
            loadingT.setTextColor(Color.WHITE);
            loadingT.setTextSize(15);
            loadingT.setText(showStr);
            //loadingT.setVisibility(View.GONE);
            RelativeLayout.LayoutParams loadingTLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            loadingTLp.setMargins(dip2px(context, 5), dip2px(context, 5), dip2px(context, 5), dip2px(context, 5));
            loadingTLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            loadingTLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            loadingRy.addView(loadingT, loadingTLp);

            loadingDialog = new DubCustomDialog(context, loadingRy, R.style.loadingDialogTheme);
            loadingDialog.setCustomDialogWidthHeight(200, 120);
            loadingDialog.show();//显示Dialog
        } catch (Exception e) {//避免context先销毁再弹框时报错
            e.printStackTrace();
        }
    }

    //兼容低版本的设置View背景方法,避免报错
    public static void setBackgroundAllVersion(View view, Drawable bgDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(bgDrawable);
        } else {
            view.setBackgroundDrawable(bgDrawable);
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}

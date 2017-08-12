package guoShubang;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dub.mydialog.R;


/**
 * 对话框工具类
 */
public class DialogUtils {


    /**
     * 普通对话框
     *
     * @param message
     * @param listener
     * @return
     */
    public static AlertDialog showNormalDialog(Context context, String message, DialogInterface.OnClickListener listener) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setTitle("温馨提示");
        builder.setMessage(message);
        builder.setCancelable(false);//外部不可点击
        builder.setPositiveButton("确定", listener);
        builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        // 显示
        alertDialog.show();
        return alertDialog;
    }

    /**
     * 普通对话框只有一个按钮
     *
     * @param message
     * @return
     */
    public static void showDialog(Context context, String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setTitle("温馨提示");
        builder.setMessage(message);
        builder.setCancelable(false);//外部不可点击
        builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        // 显示
        alertDialog.show();
    }

    /**
     * 普通对话框只有一个按钮
     *
     * @return
     */
    public static void showDialogSingle(Context context, int resId, DialogInterface.OnClickListener listener) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setTitle("温馨提示");
        builder.setMessage(resId);
        builder.setCancelable(false);//外部不可点击
        builder.setNegativeButton("返回", listener);
        AlertDialog alertDialog = builder.create();
        // 显示
        alertDialog.show();
    }

    /**
     * 提示是否登陆对话框
     *
     * @return
     */
    public static AlertDialog loginDialog(final Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//        builder.setIcon(R.mipmap.ic_launcher);
//        builder.setTitle("温馨提示");
        builder.setMessage("您当前未登录，是否要登陆？");
        builder.setCancelable(false);//外部不可点击
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //跳转到登陆界面
                //ActivityJump.startActivity(activity, LoginActivity.class);
            }
        });
        builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        // 显示
        alertDialog.show();
        return alertDialog;
    }

    /**
     * 等待动画
     *
     * @return
     */
    public static Dialog loadingDialog(Context context, String text) {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView tv = (TextView) view.findViewById(R.id.tv_more);
        tv.setText(text);

        Dialog dialog = new Dialog(context, R.style.loading_Dialog);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        return dialog;
    }

    //下方显示对话框
    public static Dialog downDialog(Activity context, View view) {
        Dialog dialog = new Dialog(context, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.main_menu_animstyle);// 设置显示动画
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = context.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);// 设置显示位置
        dialog.setCanceledOnTouchOutside(false);// 设置点击外围解散
        return dialog;
    }

    //无网络对话框
    public static Dialog noNetworkDialog(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_no_network, null);
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        TextView more = (TextView) view.findViewById(R.id.tv_more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNetworkConnected(context)){
                    dialog.dismiss();
                }else {
                    dialog.show();
                    //ToastUtils.getInstance().toast("网络连接失败，请检查网络");
                }
            }
        });
        if (isNetworkConnected(context)){
            dialog.dismiss();
        }else {
            dialog.show();
            //ToastUtils.getInstance().toast("网络连接失败，请检查网络");
        }
        return dialog;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}

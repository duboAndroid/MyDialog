package dub.mydialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DubAlertDialog extends Dialog {
    private View mDialogView;
    private AnimationSet mModalInAnim;

    public DubAlertDialog(Context context, int theme) {
        super(context, theme);
        mModalInAnim = (AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.alert_dialog_in);//只有进入动画,退出动画在activity结束时会导致窗体泄漏
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onStart() {
        if (null != mDialogView) mDialogView.startAnimation(mModalInAnim);
    }

    public static class Builder {
        private Context context;
        private String title, message, leftBtnText, centerBtnText, rightBtnText;
        private View contentView;
        private LayoutParams contentViewParams;
        private boolean topLineGone = false, bottomLineGone = false, outSide = true, backCancel = false;
        private OnDubClickListener leftBtnClickListener, centerBtnClickListener, rightBtnClickListener;
        private DubAlertDialog dialog = null;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public void setTopLineGone(boolean topLineGone) {
            this.topLineGone = topLineGone;
        }

        public void setBottomLineGone(boolean bottomLineGone) {
            this.bottomLineGone = bottomLineGone;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public void setContentViewParams(LayoutParams contentViewParams) {
            this.contentViewParams = contentViewParams;
        }

        public Builder setPositiveButton(String leftBtnText, OnDubClickListener listener) {
            this.leftBtnText = leftBtnText;
            this.leftBtnClickListener = listener;
            return this;
        }

        public Builder setNeutralButton(String centerBtnText, OnDubClickListener listener) {
            this.centerBtnText = centerBtnText;
            this.centerBtnClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String rightBtnText, OnDubClickListener listener) {
            this.rightBtnText = rightBtnText;
            this.rightBtnClickListener = listener;
            return this;
        }

        public void setCanceledOnTouchOutside(boolean outSide) {
            this.outSide = outSide;
        }

        public void setCancelable(boolean backCancel) {
            this.backCancel = backCancel;
        }

        public DubAlertDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            dialog = new DubAlertDialog(context, R.style.alertDialog);
            View dialogView = inflater.inflate(R.layout.dialog_alert_layout, null);
            ((TextView) dialogView.findViewById(R.id.title)).setText(title);

            if (topLineGone) {
                dialogView.findViewById(R.id.topLineV).setVisibility(View.GONE);
            } else {
                dialogView.findViewById(R.id.topLineV).setVisibility(View.VISIBLE);
            }

            if (bottomLineGone) {
                dialogView.findViewById(R.id.bottomLineV).setVisibility(View.GONE);
            } else {
                dialogView.findViewById(R.id.bottomLineV).setVisibility(View.VISIBLE);
            }

            if (null != contentView) {
                ((LinearLayout) dialogView.findViewById(R.id.dialog_body)).removeAllViews();
                if (null != contentViewParams) {
                    ((LinearLayout) dialogView.findViewById(R.id.dialog_body)).addView(contentView, contentViewParams);
                } else {
                    ((LinearLayout) dialogView.findViewById(R.id.dialog_body)).addView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                }
            } else if (!TextUtils.isEmpty(message)) {
                TextView messageT = (TextView) dialogView.findViewById(R.id.message);
                messageT.setMovementMethod(ScrollingMovementMethod.getInstance());//设置一个滚动实例
                messageT.setText(message);
            }//end of else if

            if (!TextUtils.isEmpty(leftBtnText)) {
                ((Button) dialogView.findViewById(R.id.leftButton)).setText(leftBtnText);
                dialogView.findViewById(R.id.leftButton).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (null != leftBtnClickListener) {
                            if (!leftBtnClickListener.onClick(null, DialogInterface.BUTTON_POSITIVE))
                                dismissDialog();
                        } else {
                            dismissDialog();
                        }
                    }
                });
            } else {
                dialogView.findViewById(R.id.leftButton).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(centerBtnText)) {
                ((Button) dialogView.findViewById(R.id.centerButton)).setText(centerBtnText);
                dialogView.findViewById(R.id.centerButton).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (null != centerBtnClickListener) {
                            if (!centerBtnClickListener.onClick(null, DialogInterface.BUTTON_NEUTRAL))
                                dismissDialog();
                        } else {
                            dismissDialog();
                        }
                    }
                });
            } else {
                dialogView.findViewById(R.id.centerButton).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(rightBtnText)) {
                ((Button) dialogView.findViewById(R.id.rightButton)).setText(rightBtnText);
                dialogView.findViewById(R.id.rightButton).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (null != rightBtnClickListener) {
                            if (!rightBtnClickListener.onClick(null, DialogInterface.BUTTON_NEGATIVE))
                                dismissDialog();
                        } else {
                            dismissDialog();
                        }
                    }
                });
            } else {
                dialogView.findViewById(R.id.rightButton).setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(leftBtnText) && !TextUtils.isEmpty(centerBtnText) && !TextUtils.isEmpty(rightBtnText)) {
                dialogView.findViewById(R.id.leftLineV).setVisibility(View.VISIBLE);
                dialogView.findViewById(R.id.rightLineV).setVisibility(View.VISIBLE);
            } else if (TextUtils.isEmpty(leftBtnText) && !TextUtils.isEmpty(centerBtnText) && !TextUtils.isEmpty(rightBtnText)) {
                dialogView.findViewById(R.id.leftLineV).setVisibility(View.GONE);
                dialogView.findViewById(R.id.rightLineV).setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(leftBtnText) && TextUtils.isEmpty(centerBtnText) && !TextUtils.isEmpty(rightBtnText)) {
                dialogView.findViewById(R.id.leftLineV).setVisibility(View.VISIBLE);
                dialogView.findViewById(R.id.rightLineV).setVisibility(View.GONE);
            } else if (TextUtils.isEmpty(leftBtnText) && !TextUtils.isEmpty(centerBtnText) && TextUtils.isEmpty(rightBtnText)) {
                dialogView.findViewById(R.id.leftLineV).setVisibility(View.GONE);
                dialogView.findViewById(R.id.rightLineV).setVisibility(View.GONE);
            } else {
                dialogView.findViewById(R.id.bottomLineV).setVisibility(View.GONE);
                dialogView.findViewById(R.id.bottomLy).setVisibility(View.GONE);
            }

            dialog.setContentView(dialogView);
            dialog.setCanceledOnTouchOutside(outSide);
            dialog.setCancelable(backCancel);
            return dialog;
        }

        private void dismissDialog() {
            if (null != dialog) {
                dialog.dismiss();
                dialog = null;
            } else {
                System.out.println("抱歉,对话框关闭异常!");
            }
        }
    }//end of builder

    public interface OnDubClickListener {
        boolean onClick(DialogInterface dialogInterface, int flag);
    }
}

package dub.mydialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import static dub.mydialog.DubToolUtils.dip2px;

public class DubCustomDialog extends Dialog {
	private Context context;

    public DubCustomDialog(Context context, Integer layout, Integer style) {
    	super(context, style);
    	init(context);
        this.setContentView(layout);
    }

    public DubCustomDialog(Context context, View layout, Integer style) {
        super(context, style);
        init(context);
        this.setContentView(layout);
    }
    
    private void init(Context context) {
    	this.context = context;
        //this.getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.setCanceledOnTouchOutside(false);
        //this.setCancelable(false);
	}
   
    public void setCustomDialogWidthHeight(Integer widthDp, Integer heightDp) {
    	Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        if(null != widthDp)params.width = dip2px(context,widthDp);
        if(null != heightDp)params.height = dip2px(context,heightDp);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
	}

    public void setDialogWidthHeightFillParent() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = LinearLayout.LayoutParams.FILL_PARENT;
        params.height = LinearLayout.LayoutParams.FILL_PARENT;
        params.gravity = Gravity.CENTER;
        //window.setGravity(Gravity.CENTER);
        window.setAttributes(params);
    }
}
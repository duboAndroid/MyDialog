package dub.mydialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import guoShubang.DialogUtils;

public class MainActivity extends AppCompatActivity {
    private Dialog dialogCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.test);
        TextView tv1 = (TextView) findViewById(R.id.test1);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DubToolUtils.showAlertInfoDialog(MainActivity.this, "这是一个title", "这是中间的内容", "按钮", new DubAlertDialog.OnDubClickListener() {

                    @Override
                    public boolean onClick(DialogInterface dialogInterface, int flag) {
                        return false;
                    }
                });
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCities.show();
            }
        });

        View view_Cities = View.inflate(this, R.layout.dailog_coupon, null);
        //View view_Cities = getLayoutInflater().inflate(R.layout.dailog_coupon, null);
        ImageView tv_clear_cities = (ImageView) view_Cities.findViewById(R.id.tv_clear);
        tv_clear_cities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCities.dismiss();
            }
        });
        dialogCities = DialogUtils.downDialog(this, view_Cities);
    }
}

package dub.mydialog;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.test);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DubToolUtils.showAlertInfoDialog(MainActivity.this,"这是一个title","这是中间的内容","按钮",new DubAlertDialog.OnDubClickListener(){

                    @Override
                    public boolean onClick(DialogInterface dialogInterface, int flag) {
                        return false;
                    }
                });
            }
        });
    }
}

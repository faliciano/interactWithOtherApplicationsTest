package us.syh.interactwithotherapplicationstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //拨打电话
        findViewById(R.id.button_1_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number=Uri.parse("tel:5551234");
                Intent callIntent=new Intent(Intent.ACTION_DIAL,number);
                startActivity(callIntent);
            }
        });
        //查看地图
        findViewById(R.id.button_2_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location=Uri.parse("geo:0.0?q=Amphitheatre+Parkway,+Mountain+View,+California");
                Intent mapIntent=new Intent(Intent.ACTION_VIEW,location);
                startActivity(mapIntent);
            }
        });
        //查看网页
        findViewById(R.id.button_3_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage=Uri.parse("https://faliciano.github.io/");
                Intent webIntent=new Intent(Intent.ACTION_VIEW,webpage);
                startActivity(webIntent);
            }
        });
        //发送一个带附件的email
        findViewById(R.id.button_4_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent=new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"faliciano@faliciano.github.io"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Email Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT,"Email message text");
                emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse("content://path/to/email/attachment"));
                startActivity(emailIntent);
            }
        });
        //创建日历事件
        findViewById(R.id.button_5_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendarIntent=new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
                Calendar beginTime=Calendar.getInstance();
                beginTime.set(2019,11,8,9,45);
                Calendar endTime=Calendar.getInstance();
                endTime.set(2019,11,8,12,00);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,beginTime);
                calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endTime);
                calendarIntent.putExtra(CalendarContract.Events.TITLE,"Ninja Class");
                calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Secret dojo");
                startActivity(calendarIntent);
            }
        });
        //验证打开地图
        findViewById(R.id.button_6_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location=Uri.parse("geo:0.0?q=Amphitheatre+Parkway,+Mountain+View,+California");
                Intent mapIntent=new Intent(Intent.ACTION_VIEW,location);
                PackageManager packageManager=getPackageManager();
                List<ResolveInfo> activities=packageManager.queryIntentActivities(mapIntent,PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() >0;
                if(isIntentSafe){
                    Toast.makeText(MainActivity.this,"存在",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"不存在",Toast.LENGTH_LONG).show();
                }
            }
        });
        //创建选择器
        findViewById(R.id.button_7_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage=Uri.parse("https://faliciano.github.io/");
                Intent webIntent=new Intent(Intent.ACTION_VIEW,webpage);
                Intent chooser=Intent.createChooser(webIntent,"选择你要打开网页的应用");
                if(webIntent.resolveActivity(getPackageManager())!=null){
                    startActivity(chooser);
                }
            }
        });
        //照相并返回相片
        findViewById(R.id.button_8_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }
}

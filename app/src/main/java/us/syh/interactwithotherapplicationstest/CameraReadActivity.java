package us.syh.interactwithotherapplicationstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class CameraReadActivity extends AppCompatActivity {
    private int REQ_CODE=1;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_read);
        imageView=findViewById(R.id.imageView_cr_1);
        //拍照
        findViewById(R.id.button_cr_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotosIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机
                if(takePhotosIntent.resolveActivity(getPackageManager())!=null){//检查是否有相机软件
                    startActivityForResult(takePhotosIntent,REQ_CODE);//启动相机
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQ_CODE && resultCode==RESULT_OK){
            /*缩略图信息储存在返回的intent中的bundle中的，
            * 对应bundle中的键值为data,因此从intent中取出
            * bundle再根据data取出来bitmap即可
            */
            Bundle extras=data.getExtras();
            Bitmap bitmap=(Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}

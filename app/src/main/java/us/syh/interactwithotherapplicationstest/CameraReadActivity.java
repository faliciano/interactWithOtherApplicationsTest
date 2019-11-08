package us.syh.interactwithotherapplicationstest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraReadActivity extends AppCompatActivity {
    private int REQ_CODE=1,REQ_CODE_2=2;
    private ImageView imageView_1,imageView_2;
    //原图方法
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_read);
        this.setTitle("调用照相机并接收返回相片");
        imageView_1=findViewById(R.id.imageView_cr_1);
        imageView_2=findViewById(R.id.imageView_cr_2);
        //拍照(返回缩略图)
        findViewById(R.id.button_cr_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhotosIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机
                if(takePhotosIntent.resolveActivity(getPackageManager())!=null){//检查是否有相机软件
                    startActivityForResult(takePhotosIntent,REQ_CODE);//启动相机
                }
            }
        });
        //拍照（返回原图）
        findViewById(R.id.button_cr_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
    }
    //重写接收回调对象
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //缩略图显示
        if(requestCode==REQ_CODE && resultCode==RESULT_OK){
            /*缩略图信息储存在返回的intent中的bundle中的，
            * 对应bundle中的键值为data,因此从intent中取出
            * bundle再根据data取出来bitmap即可
            */
            Bundle extras=data.getExtras();
            assert extras != null;
            Bitmap bitmap=(Bitmap) extras.get("data");
            imageView_1.setImageBitmap(bitmap);
        }
        //原图显示
        if(requestCode==REQ_CODE_2 && resultCode==RESULT_OK){
            try {
                /*如果拍照成功，将Uri用bitmapfactory的decodestream方法转为bitmap*/
                Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                imageView_2.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }
    //拍照（原图）
    private void takePhoto(){
        Intent takenPhotoIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机的intent
        if(takenPhotoIntent.resolveActivity(getPackageManager())!=null){
            File imageFile=createImageFile();
            if(imageFile!=null){
                //if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    /*7.0以上通过FileProvoider将File转化为Uri*/
                //int FILE_PROVIDER_AUTHORITY = 2;
                imageUri= FileProvider.getUriForFile(this, "us.syh.interactwithotherapplicationstest.fielrpovider",imageFile);
                /*}else{
                    imageUri=Uri.fromFile(imageFile);
                }*/
                takenPhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);//将用于输出的Uri传递给相机
                startActivityForResult(takenPhotoIntent,REQ_CODE_2);//打开相机
            }
        }
    }
    //创建图片
    private File createImageFile(){
        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName="JPEG_"+timestamp+"_";
        File storageDir=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile=null;
        try{
            imageFile=File.createTempFile(imageFileName,".jpg",storageDir);
        }catch (IOException e){
            e.printStackTrace();
        }
        return imageFile;
    }
    //发送一条扫描文件广播
    private void galleryAddPic(Uri uri){
        Intent mediaScanIntent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(uri);
        sendBroadcast(mediaScanIntent);
    }
}

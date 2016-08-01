package com.omen.www.thread01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private Button mLoadImageButton;
    private Button mToastButton;
    private ProgressBar mProgressBar;
    public static final String TAG = "TTTTTTTTTT";

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case  0:
                    mProgressBar.setProgress(0);
                    mProgressBar.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mProgressBar.setProgress((int)msg.obj);
                    break;
                case 2:
                    mImageView.setImageBitmap((Bitmap)msg.obj);
                    mProgressBar.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.activity_main_image_view);
        mLoadImageButton = (Button) findViewById(R.id.activity_main_load_image_button);
        mToastButton = (Button) findViewById(R.id.activity_main_toast_button);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_main_progress_bar);


        mLoadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       Message message=new Message();
                       message.what = 0;//让ProgressBar显示出来
                       mHandler.sendMessage(message);

                       for (int i = 0; i < 101; i++) {
                           sleep();
                           Message msg2 = new Message();
                           msg2.what = 1;//让ProgressBar往前走
                           msg2.obj=i;
                           mHandler.sendMessage(msg2);
                       }
                       Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                       Message msg3=mHandler.obtainMessage();
                       msg3.what=2;
                       msg3.obj=bitmap;
                       mHandler.sendMessage(msg3);
                   }

                   private void sleep() {
                       try {
                           Thread.sleep(50);
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }).start();

            }
        });

        mToastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "XXXXXXXXXXXXXX", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

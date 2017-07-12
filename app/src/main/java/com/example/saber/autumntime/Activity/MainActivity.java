package com.example.saber.autumntime.Activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saber.autumntime.Adapter.FragmentAdapter;
import com.example.saber.autumntime.Fragment.FragmentDouban;
import com.example.saber.autumntime.Fragment.FragmentGuoke;
import com.example.saber.autumntime.Fragment.FragmentZhihu;
import com.example.saber.autumntime.Presenter.ILoadBitmapPresenter;
import com.example.saber.autumntime.Presenter.impl.LoadBitmapPresenterImpl;
import com.example.saber.autumntime.R;
import com.example.saber.autumntime.Utils.GlobalConsts;
import com.example.saber.autumntime.Utils.ImageResizer;
import com.example.saber.autumntime.Views.IAvatarView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.saber.autumntime.R.menu.options;

public class MainActivity extends AppCompatActivity implements IAvatarView{

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private FragmentZhihu fragmentZhihu;
    private FragmentGuoke fragmentGuoke;
    private FragmentDouban fragmentDouban;
    private List<Fragment> fragments = new ArrayList<>();

    private TabLayout tabLayout;

    //图片显示的uri
    private Uri imageUri = null;
    private ImageView ivAvatar;
    //图片压缩的类
    private ImageResizer imageResizer;
    //设置头像
    private TextView tvTakePhoto;
    private TextView tvSelectPhoto;
    private TextView tvCancel;
    private View preView;

    private ILoadBitmapPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        //获取头布局中的控件
        View headerLayout = navigationView.getHeaderView(0);
        ivAvatar = (ImageView) headerLayout.findViewById(R.id.civ_avatar);
        LinearLayout llCollection = (LinearLayout) headerLayout.findViewById(R.id.ll_collection);
        LinearLayout llDownload = (LinearLayout) headerLayout.findViewById(R.id.ll_downloaded);
        //初始化popupWindow
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.view_select_image,null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);

        /**
         * popupwindow里的监听事件
         */
        tvTakePhoto = (TextView) view.findViewById(R.id.tv_take_photo);
        tvSelectPhoto = (TextView) view.findViewById(R.id.tv_select_photos);
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);

        //更换头像
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //设置屏幕透明度
                WindowManager.LayoutParams lp = MainActivity.this.getWindow().getAttributes();
                lp.alpha = 0.5f;
                MainActivity.this.getWindow().setAttributes(lp);

                //随便设置一个backgroundDrawable
                popupWindow.setBackgroundDrawable(new BitmapDrawable());

                //出现在父布局的下方
                View rootView = findViewById(R.id.drawer_layout);
                popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT,0,0);
                //以动画形式出现，PopupWindow里面的view是不能设置动画的，所以要设置动画必须用到PopupWindow的setAnimationStyle(int style)方法
                popupWindow.setAnimationStyle(R.style.popupwindow_anim_style);

            }
        });


        /**
         * popupWindow消失时的监听
         */
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //恢复屏幕透明度
                WindowManager.LayoutParams lp = MainActivity.this.getWindow().getAttributes();
                lp.alpha = 1.0f;
                MainActivity.this.getWindow().setAttributes(lp);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        /**
         * 拍摄照片
         */
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getExternalCacheDir(),"cacheImg.jpg");
                try {
                    if(file.exists()){
                        file.delete();
                    }

                    file.createNewFile();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Android 7.0以上,获取uri
                if(Build.VERSION.SDK_INT>=24){
                    //第二个参数为菜单文件注册provider的authorities
                    imageUri = FileProvider.getUriForFile(MainActivity.this,"com.example.AutumnTime.fileprovider",file);
                }else{
                    imageUri = Uri.fromFile(file);
                }

                //启动相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent, GlobalConsts.TAKE_PHOTO);

                popupWindow.dismiss();
            }
        });
        /**
         * 相册中选择照片
         */
        tvSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/3/28
                //运行时权限
                if(ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    openAlbum();
                }
                popupWindow.dismiss();

            }
        });
        //查看收藏
        llCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "收藏有没有", Toast.LENGTH_SHORT).show();
            }
        });

        //查看下载
        llDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "跳到下载界面", Toast.LENGTH_SHORT).show();
            }
        });





        // 当tab layout位置为果壳精选时，隐藏fab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FloatingActionButton fab =(FloatingActionButton)findViewById(R.id.fab);
                if(tab.getPosition() == 1){
                    fab.hide();
                }else {
                    fab.show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v,"显示日历还没写",Snackbar.LENGTH_SHORT).setAction("好吧", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "很快写好，放心吧", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });


        //恢复保存的图片
        File file = new File(getExternalCacheDir(),"avatar.jpg");
        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ivAvatar.setImageBitmap(bitmap);
            //压缩图片(耗时)，再模糊化背景
            presenter.compressBitmap(bitmap);
        }

    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,GlobalConsts.SELECT_PHOTO);
    }


    /**
     * Activity的回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case GlobalConsts.TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    //将拍摄的照片显示出来
                    Bitmap bitmap = null;
                    try {

                        //两次解析流，会使流的位置发生变化，所以这里用fileDescriptor来压缩Bitmap
                        FileInputStream fileInputStream = (FileInputStream) getContentResolver().openInputStream(imageUri);
                        FileDescriptor fileDescriptor = null;
                        try {
                            fileDescriptor = fileInputStream.getFD();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //压缩Bitmap
                        bitmap = imageResizer.decodeSampleFromFileDescriptor(fileDescriptor,ivAvatar.getWidth(),ivAvatar.getHeight());

                        ivAvatar.setImageBitmap(bitmap);

                        //压缩图片(耗时)，再模糊化背景
                        presenter.compressBitmap(bitmap);

                        //保存bitmap
                        presenter.saveBitmap(MainActivity.this,bitmap);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case GlobalConsts.SELECT_PHOTO:
                if (resultCode == RESULT_OK){
                    //判断手机的版本号
                    if(Build.VERSION.SDK_INT>=19){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
        }
    }

    /**
     * 4.4以下处理图片的方法
     * @param data
     */
    private void handleImageBeforeKitKat(Intent data) {
        //获取Uri 查数据
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }
    /**
     *4.4以上处理图片的方法
     * @param data
     */
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if(DocumentsContract.isDocumentUri(this,uri)){ //如果是document类型的uri,则通过document id 处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){//如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if("file".equalsIgnoreCase(uri.getScheme())){//如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        //根据图片路径显示图片
        displayImage(imagePath);
    }

    /**
     * 获取图片路径  getContentResolver
     * @param uri
     * @param selection
     * @return
     */
    private String getImagePath(Uri uri,String selection) {
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor c = getContentResolver().query(uri,null,selection,null,null);
        if(c != null){
            while(c.moveToNext()){
                path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            c.close();
        }
        return path;
    }

    /**
     * 显示图片
     * @param imagePath
     */
    private void displayImage(String imagePath) {
        if(imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            //bitmap转化为数据流
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(new File(imagePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            FileDescriptor fileDescriptor = null;
            try {
                fileDescriptor  = fileInputStream.getFD();
            } catch (IOException e) {
                e.printStackTrace();
            }

            bitmap = imageResizer.decodeSampleFromFileDescriptor(fileDescriptor,ivAvatar.getWidth(),ivAvatar.getHeight());

            ivAvatar.setImageBitmap(bitmap);

            //压缩图片(耗时)，再模糊化背景
            presenter.compressBitmap(bitmap);

            //保存bitmap
            presenter.saveBitmap(MainActivity.this,bitmap);

        }else {
            Toast.makeText(this, "获取图片失败", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 初始化
     */
    private void initViews() {
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        viewPager = (ViewPager)findViewById(R.id.vp);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        //初始化fragment
        fragmentZhihu = new FragmentZhihu();
        fragmentGuoke = new FragmentGuoke();
        fragmentDouban = new FragmentDouban();
        fragments.add(fragmentZhihu);
        fragments.add(fragmentGuoke);
        fragments.add(fragmentDouban);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);

        //TabLayout关联ViewPager
        tabLayout.setupWithViewPager(viewPager);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //默认选择第一个
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                
                switch (item.getItemId()){
                    

                    
                }
                
                
                
                
                
                
                //关闭滑动菜单
                drawerLayout.closeDrawers();
                return true;
            }
        });
        //初始化bitmap图片压缩类
        imageResizer = new ImageResizer();

        //初始化模糊图片的presenter
        presenter = new LoadBitmapPresenterImpl(MainActivity.this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_helpyouself:
                Toast.makeText(this, "随便看看，现在还没有数据", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }


    /**
     * 显示，隐藏FloatingActionButton
     */
    public void showFloatingActionButton(){
        fab.show();
    }
    public void hideFloatingActionButton(){
        fab.hide();
    };

    /**
     * 更改NavigationView headerView 的背景
     * @param bitmap
     */
    @Override
    public void changeBackground(Bitmap bitmap) {
        /**
         * 显示图片
         */
        View headerLayout = navigationView.getHeaderView(0);
        ImageView ivBac = (ImageView) headerLayout.findViewById(R.id.iv_bac);
        ivBac.setImageBitmap(bitmap);

        //透明前景
        preView = headerLayout.findViewById(R.id.view);
        preView.setVisibility(View.VISIBLE);
    }
}

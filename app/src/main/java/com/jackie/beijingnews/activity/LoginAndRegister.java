package com.jackie.beijingnews.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jackie.beijingnews.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by baigu on 2016/4/16.
 */
public class LoginAndRegister extends Activity {

    private ViewPager viewPager;
    private List<View> views = new ArrayList<>();
    private PagerAdapter pagerAdapter;
    private LayoutInflater layoutInflater;
    private TextView textView_login;
    private TextView textView_register;

    private Button button_login;
    private EditText editText_login_pwd;
    private EditText editText_login_phone;

    private Button button_register;
    private EditText editText_pwd;
    private EditText editText_user;
    private EditText editText_phone;

    //相册和拍照相关
    private Button btn_choose;
    private String imageName;
    private ImageView iv_photo;
    private File cropResultFile;
    private Button btn_take;
    private AlertDialog alertDialog;

    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_loginandregister);

        textView_login = (TextView) findViewById(R.id.login);
        textView_register = (TextView) findViewById(R.id.register);

        layoutInflater = LayoutInflater.from(this);
        View view1 = layoutInflater.inflate(R.layout.login_item, null);
        View view2 = layoutInflater.inflate(R.layout.register_item, null);
        views.add(view1);
        views.add(view2);

        //对EditText组件的监听。分两种：登录页面和注册页面。
        //登录页面
        button_login = (Button) view1.findViewById(R.id.btn_login);
        editText_login_phone = (EditText) view1.findViewById(R.id.edit_login_phone);
        editText_login_pwd = (EditText) view1.findViewById(R.id.edit_login_pw);

        editText_login_phone.addTextChangedListener(new TextChangeLogin());
        editText_login_pwd.addTextChangedListener(new TextChangeLogin());

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_phone = editText_login_phone.getText().toString();
                String login_pwd = editText_login_pwd.getText().toString();

                RequestParams params = new RequestParams();
                try {
                    params.put("phone_number", login_phone);
                    params.put("password", login_pwd);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String url = "http://q1615y8856.iok.la/waimai/login";

                AsyncHttpClient client = new AsyncHttpClient();
                client.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, org.apache.http.Header[] headers, String s, Throwable throwable) {
                        Toast.makeText(LoginAndRegister.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, String s) {
                        Toast.makeText(LoginAndRegister.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //注册页面
        button_register = (Button) view2.findViewById(R.id.btn_register);
        editText_pwd = (EditText) view2.findViewById(R.id.edit_pw);
        editText_user = (EditText) view2.findViewById(R.id.edit_user);
        editText_phone = (EditText) view2.findViewById(R.id.edit_phone);

        //注册事件
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lname = editText_user.getText().toString();
                String phone_number = editText_phone.getText().toString();
                String password = editText_pwd.getText().toString();

                RequestParams params = new RequestParams();
                try {
                    params.put("lname", lname);
                    params.put("phone_number", phone_number);
                    params.put("password", password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String url = "http://q1615y8856.iok.la/waimai/register";

                AsyncHttpClient client = new AsyncHttpClient();
                client.post(url, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, org.apache.http.Header[] headers, String s, Throwable throwable) {
                        Toast.makeText(LoginAndRegister.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int i, org.apache.http.Header[] headers, String s) {
                        Toast.makeText(LoginAndRegister.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        editText_pwd.addTextChangedListener(new TextChange());
        editText_user.addTextChangedListener(new TextChange());
        editText_phone.addTextChangedListener(new TextChange());


        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        textView_login.setTextColor(getResources().getColor(R.color.red));
                        textView_register.setTextColor(getResources().getColor(R.color.black));
                        break;
                    case 1:
                        textView_login.setTextColor(getResources().getColor(R.color.black));
                        textView_register.setTextColor(getResources().getColor(R.color.red));
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(views.get(position));
                return views.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);

        textView_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });


        //相册和拍照相关
        iv_photo = (ImageView) view2.findViewById(R.id.iv_photo);
        //加载对话框视图
        View view_alertdialog = LayoutInflater.from(this).inflate(R.layout.activity_gallery_takephoto, null);
        btn_take = (Button) view_alertdialog.findViewById(R.id.btn_take);
        btn_choose = (Button) view_alertdialog.findViewById(R.id.btn_choose);
        //创建对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(view_alertdialog);
        alertDialog = builder.create();
        //为iv_photo注册事件监听
        iv_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
        btn_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MediaStore.ACTION_IMAGE_CAPTURE = "android.media.action.IMAGE_CAPTURE"
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageName = getNowTime() + ".png";
                //拍摄的照片存储路径
                cropResultFile = new File(Environment.getExternalStorageDirectory().getPath(), imageName);
                //存储
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropResultFile));
                startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
                alertDialog.cancel();
            }
        });
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent.ACTION_GET_CONTENT="android.intent.action.GET_CONTENT";
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                imageName = getNowTime() + ".png";
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                alertDialog.cancel();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_REQUEST_TAKEPHOTO:
                    startPhotoZoom(Uri.fromFile(cropResultFile), 320);
                    break;

                case PHOTO_REQUEST_GALLERY:
                    if (data != null) {
                        startPhotoZoom(data.getData(), 320);
                    }
                    break;

                case PHOTO_REQUEST_CUT:

                    BitmapFactory.Options opts = new BitmapFactory.Options();
                    opts.inJustDecodeBounds = true;
                    Bitmap bitmap = BitmapFactory.decodeFile(cropResultFile.getPath(), opts);

                    //原始图片的宽高
                    int bitmap_w = opts.outWidth;
                    int bitmap_h = opts.outHeight;

                    //获取屏幕信息
                    WindowManager windowManager = getWindowManager();
                    Display display = windowManager.getDefaultDisplay();
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    display.getMetrics(displayMetrics);
                    //获取屏幕宽高
                    int sw = displayMetrics.widthPixels;
                    int sh = displayMetrics.heightPixels;

                    //计算各宽高比例
                    int sampleW = bitmap_w / sw;
                    int sampleH = bitmap_h / sh;

                    //计算采样因子
                    int sampleSize = (sampleH > sampleW) ? sampleH : sampleW;

                    opts.inJustDecodeBounds = false;
                    opts.inSampleSize = sampleSize;
                    Bitmap bitmap_later = BitmapFactory.decodeFile(cropResultFile.getPath(), opts);
                    iv_photo.setImageBitmap(bitmap_later);
                    break;

            }

        }
    }

    private void startPhotoZoom(Uri uri, int size) {
        //MediaStore.EXTRA_OUTPUT ="output";
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // true:不返回uri，false：返回uri
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra("noFaceDetection", false); // 取消人脸识别

        //裁剪后的图片存储的位置
        //注意：此路径将拍照存储的路径覆盖。
        cropResultFile = new File(Environment.getExternalStorageDirectory().getPath(), imageName);
        intent.putExtra("output", Uri.fromFile(cropResultFile));

        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @SuppressLint("SimpleDateFormat")
    private String getNowTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(date);
    }


    public void backUp(View view) {
        LoginAndRegister.this.finish();
    }

    public void register(View view) {
        Toast.makeText(LoginAndRegister.this, "系统维护中，请稍后重试！", Toast.LENGTH_SHORT).show();
    }

    public void login(View view) {
        Toast.makeText(LoginAndRegister.this, "请先注册，再登录！", Toast.LENGTH_SHORT).show();
    }


    class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            boolean user = editText_user.getText().length() > 0;
            boolean pwd = editText_pwd.getText().length() > 0;
            boolean phone = editText_phone.getText().length() > 0;

            if (user & pwd & phone) {
                button_register.setEnabled(true);
            } else {
                button_register.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    class TextChangeLogin implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean user = editText_login_pwd.getText().length() > 0;
            boolean pwd = editText_login_phone.getText().length() > 0;

            if (user & pwd) {
                button_login.setEnabled(true);
            } else {
                button_login.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}

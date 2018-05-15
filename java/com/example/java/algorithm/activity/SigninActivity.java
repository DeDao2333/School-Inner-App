package com.example.java.algorithm.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java.algorithm.R;
import com.example.java.algorithm.javabean._User;
import com.example.java.algorithm.model.NetWork;
import com.example.java.algorithm.model.SingleUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class SigninActivity extends BaseActivity implements View.OnClickListener{

    private EditText mUserName;
    private EditText mPassword;
    private Button mSignin;
    private Button mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mUserName = (EditText) findViewById(R.id.TV_userName);
        mPassword = (EditText) findViewById(R.id.TV_password);
        mSignin = (Button) findViewById(R.id.bt_sign_in);
        mSignup = (Button) findViewById(R.id.bt_sign_up);

        mSignin.setOnClickListener(this);
        mSignup.setOnClickListener(this);
    }

    private void SignIn() {
        String userName,passWord;
        userName = mUserName.getText().toString();
        passWord = mPassword.getText().toString();
        _User user = new _User();
        user.setUsername(userName);
        user.setPassword(passWord);

        user.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    Toast.makeText(SigninActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    MainAC.ToMainAc(SigninActivity.this);
                    finish();
                } else {
                    Toast.makeText(SigninActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
        SingleUser.setUser(user);
    }

    private void Signup() {
        final String userName,passWord;
        userName = mUserName.getText().toString();
        passWord = mPassword.getText().toString();
        _User mUser = new _User();
        mUser.setUsername(userName);
        mUser.setPassword(passWord);
//        mUser.setNickname("昵称");
        mUser.setSno("201421501061");
        mUser.setMajor("计算机");
        if (!userName.equals("") && userName.length() == 11 && passWord.length() > 5) {
            mUser.signUp(new SaveListener<_User>() {
                @Override
                public void done(_User myUser, BmobException e) {
                    if (e == null) {
                        Toast.makeText(SigninActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SigninActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {

        if (NetWork.isConnection(this)) {
            switch (view.getId()) {
                case R.id.bt_sign_in:
                    SignIn();
                    break;
                case R.id.bt_sign_up:
                    Signup();
                    break;
            }
        } else {
            Toast.makeText(this, "请连接网络", Toast.LENGTH_SHORT).show();
        }
    }
}

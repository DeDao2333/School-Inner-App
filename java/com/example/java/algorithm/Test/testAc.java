package com.example.java.algorithm.Test;

import android.os.Bundle;
import android.widget.TextView;

import com.example.java.algorithm.R;
import com.example.java.algorithm.activity.BaseActivity;
import com.example.java.algorithm.javabean.mainPost;
import com.example.java.algorithm.javabean.user_type;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class testAc extends BaseActivity {

    TextView mTextView;
    List<String> mStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mTextView = (TextView) findViewById(R.id.tv_test_ac);
        user_type userType = new user_type();
        userType.setObjectId("guN4FFFb");

        BmobQuery<mainPost> query = new BmobQuery<>();
        query.addWhereEqualTo("type_sign", userType);
        query.findObjects(new FindListener<mainPost>() {
            @Override
            public void done(List<mainPost> list, BmobException e) {
                for (mainPost mainPost : list) {
                    mStrings.add(mainPost.getContent());
                }
                mTextView.setText(mStrings.toString());
            }
        });
    }
}

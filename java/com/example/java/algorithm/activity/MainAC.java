package com.example.java.algorithm.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.example.java.algorithm.R;
import com.example.java.algorithm.fragment.CommunityFragment;
import com.example.java.algorithm.fragment.MainPostFragment;
import com.example.java.algorithm.fragment.OnFragmentInteractionListener;
import com.example.java.algorithm.fragment.UserFragment;
import com.example.java.algorithm.model.jump_fragment;

public class MainAC extends BaseActivity implements
        OnFragmentInteractionListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mFragmentManager;
    private MainPostFragment mMainPostFragment;

    //initialize views
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ac);

        mFragmentManager = getSupportFragmentManager();
        initView();

    }


    public void initView() {

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        mMainPostFragment = new MainPostFragment();
        to_Fragment(new jump_fragment() {
            @Override
            public void ToFragment(FragmentTransaction ft) {
                ft.replace(R.id.fragment_container_mainac, mMainPostFragment);
                ft.commit();
            }
        });

    }

    public static void ToMainAc(Activity activity) {
        Intent intent = new Intent(activity, MainAC.class);
        activity.startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //fragment中点击事件
    @Override
    public void onItemClick(int itemID) {
        switch (itemID) {
            case R.id.team:
                to_Activity(new TeamActivity(),null);
                break;
            case R.id.shand:
                to_Activity(new ShPostActivity(),null);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                to_Fragment(new jump_fragment() {
                    @Override
                    public void ToFragment(FragmentTransaction ft) {
                        ft.replace(R.id.fragment_container_mainac, new MainPostFragment());
//                        ft.show(mMainPostFragment);
                        ft.commit();
                    }
                });
                return true;
            case R.id.navigation_message:
                to_Fragment(new jump_fragment() {
                    @Override
                    public void ToFragment(FragmentTransaction ft) {

                    }
                });
                return true;
            case R.id.navigation_community:
                to_Fragment(new jump_fragment() {
                    @Override
                    public void ToFragment(FragmentTransaction ft) {
                        ft.replace(R.id.fragment_container_mainac,
                                new CommunityFragment());
//                        if(!mMainPostFragment.isHidden()){
//                            ft.hide(mMainPostFragment);
//                            ft.add(R.id.fragment_container_mainac, new CommunityFragment());
//                        }

                        ft.commit();
                    }
                });
                return true;
            case R.id.navigation_user:
                to_Fragment(new jump_fragment() {
                    @Override
                    public void ToFragment(FragmentTransaction ft) {
                        ft.replace(R.id.fragment_container_mainac, new UserFragment());
                        ft.commit();
                    }
                });
                return true;
        }
        return false;
    }

}

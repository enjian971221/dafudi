package com.zkwl.qhzwj;

import android.Manifest;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.zkwl.app_classroom.fragment.ClassRoomFragment;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.MainActivityBinding;
import com.zkwl.qhzwj.fragment.HomeFragment;
import com.zkwl.qhzwj.fragment.MineFragment;
import com.zkwl.qhzwj.receiver.WechatAlipayReceiver;
import com.zkwl.qhzwj.ui.bottombarlayout.BottomBarItem;
import com.zkwl.qhzwj.ui.bottombarlayout.BottomBarLayout;
import com.zkwl.qhzwj.util.StatusBar;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;


@AndroidEntryPoint
public class MainActivity extends BaseActivity<LoginViewModel, MainActivityBinding> {



    @Override
    public int getLayoutId() {
        return R.layout.main_activity;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(com.qw.soul.permission.bean.Permission[] allPermissions) {

                    }

                    @Override
                    public void onPermissionDenied(com.qw.soul.permission.bean.Permission[] refusedPermissions) {
                        Toast.makeText(MainActivity.this,"请开启权限",Toast.LENGTH_LONG).show();
                    }
                });



        StatusBar.setStatusBar(this,true);
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });

        ClassRoomFragment classRoomFragment =new ClassRoomFragment();
        HomeFragment homeFragment =new HomeFragment();
//        HealthyFragment healthyFragment =new HealthyFragment();
        MineFragment mineFragment =new MineFragment();

        switchFragment(homeFragment);

        getViewDataBinding().bblHomeTab.setCurrentItem(0);

        getViewDataBinding().bblHomeTab.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
                switch (currentPosition) {
                    case 0:
                        switchFragment(homeFragment);
                        break;
                    case 1:
                        switchFragment(classRoomFragment);
                        break;
                    case 2:
//                        switchFragment(healthyFragment);
                        break;
                    case 3:
                        switchFragment(mineFragment);
                        break;
                }

            }
        });



        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.zkwl.qhzwj.receiver.MY_BROADCAST");//网络变化是会发送该广播
        WechatAlipayReceiver wechatAlipayReceiver=new WechatAlipayReceiver();
        registerReceiver(wechatAlipayReceiver,intentFilter);

    }

    public void switchFragment(Fragment fragment) {
        try {
            // 开启事物
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            // 1.先隐藏当前所有的Fragment
            List<Fragment> childFragments = getSupportFragmentManager().getFragments();
            for (Fragment childFragment : childFragments) {
                fragmentTransaction.hide(childFragment);
            }
            // 2.如果容器里面没有我们就添加，否则显示
            if (!childFragments.contains(fragment)) {
                fragmentTransaction.add(R.id.fl_home_content, fragment);
            } else {
                fragmentTransaction.show(fragment);
            }
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

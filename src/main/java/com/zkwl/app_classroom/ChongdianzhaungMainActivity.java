package com.zkwl.app_classroom;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.app_classroom.bottombarlayout.BottomBarItem;
import com.zkwl.app_classroom.bottombarlayout.BottomBarLayout;
import com.zkwl.app_classroom.databinding.ActivityClassroommainBinding;
import com.zkwl.app_classroom.fragment.ClassRoomFragment;
import com.zkwl.app_classroom.model.ClassRomModel;
import com.zkwl.app_classroom.util.StatusBar;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;


@AndroidEntryPoint
public class ChongdianzhaungMainActivity extends BaseActivity<ClassRomModel, ActivityClassroommainBinding> {


    @Override
    public int getLayoutId() {
        return R.layout.activity_classroommain;
    }



    @Override
    public void initData(@Nullable Bundle savedInstanceState) {


        StatusBar.setStatusBar(this,true);
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });

        ClassRoomFragment classRoomFragment =new ClassRoomFragment();
        switchFragment(classRoomFragment);
        getViewDataBinding().bblHomeTab.setCurrentItem(0);

        getViewDataBinding().bblHomeTab.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
                switch (currentPosition) {
                    case 0:
                        switchFragment(classRoomFragment);
                        break;
                }
            }
        });
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
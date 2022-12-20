package com.zkwl.app_classroom.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.king.frame.mvvmframe.base.BaseFragment;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.api.ClassroomConstants;
import com.zkwl.app_classroom.databinding.FragmentAboutinstructorBinding;
import com.zkwl.app_classroom.model.ClassRomDetailsModel;
import com.zkwl.app_classroom.util.ClassroomImgUtil;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 讲师介绍
 */

@AndroidEntryPoint
public class AboutInstructorFragment extends BaseFragment<ClassRomDetailsModel, FragmentAboutinstructorBinding> {

    private ClassRomDetailsModel classRomDetailsModel;
    private String content;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_aboutinstructor;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        classRomDetailsModel = new ViewModelProvider(requireActivity()).get(ClassRomDetailsModel.class);

        classRomDetailsModel.getCourseinfoLiveData().observe(this, data -> {

            getViewDataBinding().nickName.setText(data.getData().getJSONObject("lecturer_data").getString("nick_name"));
            getViewDataBinding().title.setText(data.getData().getJSONObject("lecturer_data").getString("title"));
            ClassroomImgUtil.loadImage(getViewDataBinding().imageUrl,data.getData().getJSONObject("lecturer_data").getString("image_url"),1);

            content =data.getData().getJSONObject("lecturer_data").getString("content");
            if (TextUtils.isEmpty(content)) {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) getViewDataBinding().webview.getLayoutParams(); //取控件textView当前的布局参数
                linearParams.height = 1920;// 控件的高强制设成20
                getViewDataBinding().webview.setLayoutParams(linearParams);
                return;
            }
            if (Objects.requireNonNull(content).contains("img")) {
                content = Objects.requireNonNull(content).replace("src=\"", "src=\"" + ClassroomConstants.BASE_URL);
            } else {
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) getViewDataBinding().webview.getLayoutParams(); //取控件textView当前的布局参数
                linearParams.height = 1920;// 控件的高强制设成20
                getViewDataBinding().webview.setLayoutParams(linearParams);
            }
            content = getHtmlData(content);
            getViewDataBinding().webview.loadDataWithBaseURL(null, content, "text/html;charset=utf-8", null, null);

        });


    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:100%; height:auto;}*{margin:0px;}</style>"
                + "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

}

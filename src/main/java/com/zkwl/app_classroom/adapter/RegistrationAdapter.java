package com.zkwl.app_classroom.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.databinding.ItemRegistrationBinding;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class RegistrationAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemRegistrationBinding>> {

    click click;
    Context context;


    public RegistrationAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemRegistrationBinding> holder, JSONObject item, int position) {
        holder.setIsRecyclable(false);
        holder.mBinding.nameedit.setText(item.getString("nick_name") == null ? "" : item.getString("nick_name"));
        holder.mBinding.phoneedit.setText(item.getString("mobile_phone") == null ? "" : item.getString("mobile_phone"));
        holder.mBinding.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.delete(item, position);
            }
        });

        TextWatcher textWatcher =  new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && charSequence.length() > 0) {
                    item.put("nick_name", holder.mBinding.nameedit.getText().toString());
                    click.update(item, position);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        holder.mBinding.nameedit.addTextChangedListener(textWatcher);

        TextWatcher textWatcher1 =  new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && charSequence.length() > 0) {
                    item.put("mobile_phone", holder.mBinding.phoneedit.getText().toString());
                    click.update(item, position);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        holder.mBinding.phoneedit.addTextChangedListener(textWatcher1);

    }


    public interface click {
        void delete(JSONObject item, int position);

        void update(JSONObject item, int position);
    }
}
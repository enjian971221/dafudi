package com.zkwl.qhzwj.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/9/21.
 */

public class EthnicGroupBean implements OptionDataSet {

    /**
     * label : 汉族
     * value : 0
     */

    private String label;
    private String value;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public CharSequence getCharSequence() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public List<? extends OptionDataSet> getSubs() {
        return null;
    }
}

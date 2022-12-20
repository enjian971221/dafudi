package com.zkwl.qhzwj.bean;



import java.util.List;

/**
 * @author songkai
 * @date on 2019/8/23
 */
public class SelectOptionDataSetCommon implements OptionDataSet {

    private String name;
    private String code;

    public SelectOptionDataSetCommon(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public CharSequence getCharSequence() {
        return name;
    }

    @Override
    public String getValue() {
        return name;
    }

    @Override
    public List<? extends OptionDataSet> getSubs() {
        return null;
    }
}

package com.zkwl.qhzwj.bean;


import java.util.List;

/**
 * Created by fuchaoyang on 2018/2/11.<br/>
 * description：{ OptionPicker}专用数据集
 */

public interface OptionDataSet extends PickerDataSet {

  /**
   * @return 下一级的数据集
   */
  List<? extends OptionDataSet> getSubs();
}

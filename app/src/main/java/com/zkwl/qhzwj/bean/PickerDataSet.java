package com.zkwl.qhzwj.bean;

/**
 * 创建时间：2018年01月31日16:34 <br>
 * 作者：fuchaoyang <br>
 * 描述：数据实现接口，用户显示文案
 */

public interface PickerDataSet {
  CharSequence getCharSequence();

  /**
   * @return 上传的value，用于匹配初始化选中的下标
   */
  String getValue();
}

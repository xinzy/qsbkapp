package com.xinzy.qsbk.common.base;

/**
 * Created by Xinzy on 2016/4/28.
 */
public interface IBaseView<T extends IBasePresenter>
{
    void setPresenter(T presenter);
}

package com.xinzy.qsbk.logic.main.view;

import com.xinzy.qsbk.common.base.IBaseView;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.main.presenter.IContentPresenter;

import java.util.List;

/**
 * Created by Xinzy on 2016/4/27.
 */
public interface IContentView extends IBaseView<IContentPresenter>
{

    void showLoading(boolean isShow);

    void showDataAfterLoad(List<Content> contents);

    String contentListApi();
}

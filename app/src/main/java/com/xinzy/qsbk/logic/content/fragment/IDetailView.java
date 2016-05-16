package com.xinzy.qsbk.logic.content.fragment;

import com.xinzy.qsbk.common.base.IBaseView;
import com.xinzy.qsbk.common.model.Comment;
import com.xinzy.qsbk.logic.content.presenter.IDetailPresenter;

import java.util.List;

/**
 * Created by gaodun on 2016/5/16.
 */
public interface IDetailView extends IBaseView<IDetailPresenter>
{

    void showLoading(boolean isShow);

    void loadResult(boolean success);

    void showDataAfterLoad(List<Comment> contents, boolean refresh);
}

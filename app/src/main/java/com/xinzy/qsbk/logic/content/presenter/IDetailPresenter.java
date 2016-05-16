package com.xinzy.qsbk.logic.content.presenter;

import com.xinzy.qsbk.common.base.IBasePresenter;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.content.view.ContentDetailView;

/**
 * Created by gaodun on 2016/5/16.
 */
public interface IDetailPresenter extends IBasePresenter
{

    void setContent(Content content);

    void loading(int page);

    void onSupportClick(ContentDetailView view, Content content);

    void onUnsupportClick(ContentDetailView view, Content content);
}

package com.xinzy.qsbk.logic.main.presenter;

import com.xinzy.qsbk.common.base.IBasePresenter;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.main.view.ContentItemView;

/**
 * Created by Xinzy on 2016/4/28.
 */
public interface IContentPresenter extends IBasePresenter
{

	void loading(int page);

	void onSupportClick(ContentItemView itemView, Content content);

	void onUnsupportClick(ContentItemView itemView, Content content);
}

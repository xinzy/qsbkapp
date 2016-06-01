package com.xinzy.qsbk.logic.awkward.fragment;

import com.xinzy.qsbk.common.base.IBaseView;
import com.xinzy.qsbk.logic.awkward.presenter.IAwkwardPresenter;

/**
 * Created by Xinzy on 2016/6/1.
 */
public interface IAwkwardView extends IBaseView<IAwkwardPresenter>
{
	void showLoading(boolean isShow);

	void loadResult(int mintime);
}

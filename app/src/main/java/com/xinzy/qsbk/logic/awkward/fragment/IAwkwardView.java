package com.xinzy.qsbk.logic.awkward.fragment;

import com.xinzy.qsbk.common.base.IBaseView;
import com.xinzy.qsbk.common.model.Awkward;
import com.xinzy.qsbk.logic.awkward.presenter.IAwkwardPresenter;

import java.util.List;

/**
 * Created by Xinzy on 2016/6/1.
 */
public interface IAwkwardView extends IBaseView<IAwkwardPresenter>
{
	void showLoading(boolean isShow);

	void loadResult(int mintime, int maxtime);

	void showData(List<Awkward> data);
}

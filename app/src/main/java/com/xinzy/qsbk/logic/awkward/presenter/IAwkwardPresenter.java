package com.xinzy.qsbk.logic.awkward.presenter;

import com.xinzy.qsbk.common.base.IBasePresenter;
import com.xinzy.qsbk.logic.awkward.adapter.SectionsPagerAdapter;

/**
 * Created by Xinzy on 2016/6/1.
 */
public interface IAwkwardPresenter extends IBasePresenter
{

	void setCategory(SectionsPagerAdapter.Category category);

	void loading(boolean refresh, int time);

}

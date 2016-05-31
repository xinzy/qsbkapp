package com.xinzy.qsbk.logic.awkward.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.base.AbsBaseFragment;
import com.xinzy.qsbk.logic.awkward.adapter.SectionsPagerAdapter;

/**
 * Created by Xinzy on 2016/5/31.
 */
public class AwkwardFragment extends AbsBaseFragment
{

	private static final String KEY_CATEGORY = "CATEGORY";

	private SectionsPagerAdapter.Category mCategory;

	public static final AwkwardFragment newInstance(SectionsPagerAdapter.Category category)
	{
		AwkwardFragment fragment = new AwkwardFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(KEY_CATEGORY, category);
		fragment.setArguments(bundle);

		return fragment;
	}

	@Override
	protected int getLayout()
	{
		return R.layout.fragment_awkward;
	}

	@Override
	protected void initializeView()
	{
		mCategory = getArguments().getParcelable(KEY_CATEGORY);

		TextView textView = (TextView) rootView.findViewById(R.id.section_label);
		textView.setText(getString(R.string.section_format, 3));
	}
}

package com.xinzy.qsbk.logic.awkward.presenter;

import com.xinzy.okhttp3.OkHttpUtils;
import com.xinzy.okhttp3.callback.Callback;
import com.xinzy.qsbk.common.api.Apis;
import com.xinzy.qsbk.common.model.Awkward;
import com.xinzy.qsbk.common.util.Logger;
import com.xinzy.qsbk.logic.awkward.adapter.SectionsPagerAdapter;
import com.xinzy.qsbk.logic.awkward.fragment.IAwkwardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Xinzy on 2016/6/1.
 */
public class AwkwardPresenter implements IAwkwardPresenter
{
	private IAwkwardView mView;
	private SectionsPagerAdapter.Category mCategory;

	public AwkwardPresenter(IAwkwardView mView)
	{
		this.mView = mView;
		mView.setPresenter(this);
	}

	@Override
	public void setCategory(SectionsPagerAdapter.Category category)
	{
		mCategory = category;
	}

	@Override
	public void loading(boolean refresh, int time)
	{
		if (refresh)
		{
			time = (int) (System.currentTimeMillis() / 1000);
			mView.showLoading(true);
		}
		String url = Apis.getAwkwardUrl(mCategory.id, time);
		Logger.i("api url = " + url);

		final AwkwardCallback callback = new AwkwardCallback();
		OkHttpUtils.get().url(url).build().execute(callback);
	}

	@Override
	public void start()
	{
		loading(true, 0);
	}

	class AwkwardCallback extends Callback<List<Awkward>>
	{
		private boolean success;
		private int minTime;
		private int maxTime;

		@Override
		public List<Awkward> parseNetworkResponse(Response response) throws Exception
		{
			JSONObject rootJson = new JSONObject(response.body().string());
			String message = rootJson.optString("message");
			if ("success".equals(message))
			{
				success = true;
				List<Awkward> lists = new ArrayList<>();

				JSONObject dataJson = rootJson.optJSONObject("data");
				minTime = dataJson.optInt("min_time");
				maxTime = dataJson.optInt("max_time");

				JSONArray array = dataJson.optJSONArray("data");
				final int length;
				if (array != null && (length = array.length()) > 0)
				{
					for (int i = 0; i < length; i++)
					{
						lists.add(Awkward.parse(array.optJSONObject(i)));
					}
				}

				return lists;
			}

			return null;
		}

		@Override
		public void onError(Call call, Exception e)
		{
			mView.showLoading(false);
			mView.loadResult(0, 0);
		}

		@Override
		public void onResponse(List<Awkward> response)
		{
			mView.showLoading(false);
			mView.loadResult(minTime, maxTime);
			mView.showData(response);
		}
	}
}

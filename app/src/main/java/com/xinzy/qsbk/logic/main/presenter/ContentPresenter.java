package com.xinzy.qsbk.logic.main.presenter;

import android.support.annotation.NonNull;

import com.xinzy.okhttp3.OkHttpUtils;
import com.xinzy.okhttp3.callback.Callback;
import com.xinzy.qsbk.common.api.Apis;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.common.util.Logger;
import com.xinzy.qsbk.logic.main.fragment.IContentView;
import com.xinzy.qsbk.logic.main.view.ContentItemView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Xinzy on 2016/4/28.
 */
public class ContentPresenter implements IContentPresenter
{
    @NonNull
    private IContentView mContentView;

    public ContentPresenter(@NonNull IContentView view)
    {
        mContentView = view;
        mContentView.setPresenter(this);
    }

    @Override
    public void start()
    {
        loading(1);
    }

    @Override
    public void loading(final int page)
    {
        if (page == 1)
        {
            mContentView.showLoading(true);
        }
        String type = mContentView.getType();
        String url = Apis.getContentListApi(type, page);
        Logger.w(url);

        OkHttpUtils.get().url(url).build().execute(new Callback<List<Content>>()
        {
            @Override
            public List<Content> parseNetworkResponse(Response response) throws Exception
            {
                String data = response.body().string();

                try
                {
                    JSONObject rootJson = new JSONObject(data);
                    final int status = rootJson.optInt("err");

                    if (status == 0)
                    {
                        JSONArray array = rootJson.optJSONArray("items");

                        if (array != null && array.length() > 0)
                        {
                            List<Content> lists = new ArrayList<>();
                            final int length = array.length();

                            for (int i = 0; i < length; i++)
                            {
                                JSONObject json = array.optJSONObject(i);
                                Content content = Content.parse(json);

                                lists.add(content);
                            }

                            return lists;
                        }
                    }
                } catch (Exception e)
                {
                    Logger.e("loading data", e);
                }
                return null;
            }

            @Override
            public void onError(Call call, Exception e)
            {
                mContentView.loadResult(false);
            }

            @Override
            public void onResponse(List<Content> response)
            {
                mContentView.showLoading(false);
                mContentView.loadResult(true);
                mContentView.showDataAfterLoad(response, page == 1);
            }
        });


    }

    @Override
    public void onSupportClick(ContentItemView itemView, Content content)
    {
        final int userState = content.getUserState();
        content.setUserState(Content.STATE_SUPPORT);
        itemView.getSupportImageView().setSelected(true);
        itemView.startAnim(itemView.getSupportImageView());
        if (userState == Content.STATE_NONE)
        {
            content.getVote().setUp(content.getVote().getUp() + 1);
        } else if (userState == Content.STATE_UNSUPPORT)
        {
            content.getVote().setUp(content.getVote().getUp() + 1);
            content.getVote().setDown(content.getVote().getDown() + 1);
            itemView.getUnsupportImageView().setSelected(false);
        }
        itemView.setDataText(content);
    }

    @Override
    public void onUnsupportClick(ContentItemView itemView, Content content)
    {
        final int userState = content.getUserState();
        content.setUserState(Content.STATE_UNSUPPORT);
        itemView.getUnsupportImageView().setSelected(true);
        itemView.startAnim(itemView.getUnsupportImageView());
        if (userState == Content.STATE_NONE)
        {
            content.getVote().setDown(content.getVote().getDown() - 1);
        } else if (userState == Content.STATE_SUPPORT)
        {
            content.getVote().setUp(content.getVote().getUp() - 1);
            content.getVote().setDown(content.getVote().getDown() - 1);
            itemView.getSupportImageView().setSelected(false);
        }
        itemView.setDataText(content);
    }
}

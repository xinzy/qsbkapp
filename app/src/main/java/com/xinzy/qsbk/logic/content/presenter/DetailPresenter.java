package com.xinzy.qsbk.logic.content.presenter;

import com.xinzy.okhttp3.OkHttpUtils;
import com.xinzy.okhttp3.callback.Callback;
import com.xinzy.qsbk.common.api.Apis;
import com.xinzy.qsbk.common.model.Comment;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.content.fragment.IDetailView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by gaodun on 2016/5/16.
 */
public class DetailPresenter implements IDetailPresenter
{

    private IDetailView mDetailView;
    private Content mContent;

    public DetailPresenter(IDetailView mDetailView)
    {
        this.mDetailView = mDetailView;
        this.mDetailView.setPresenter(this);
    }

    @Override
    public void setContent(Content content)
    {
        this.mContent = content;
    }

    @Override
    public void loading(final int page)
    {
        if (page == 1)
        {
            mDetailView.showLoading(true);
        }

        String url = Apis.getCommentListApi(mContent.getId(), page);
        OkHttpUtils.get().url(url).build().execute(new Callback<List<Comment>>()
        {
            @Override
            public List<Comment> parseNetworkResponse(Response response) throws Exception
            {
                JSONObject rootJson = new JSONObject(response.body().string());
                if (rootJson.optInt("err", -1) == 0)
                {
                    JSONArray array = rootJson.optJSONArray("items");
                    int length;
                    if (array != null && (length = array.length()) > 0)
                    {
                        List<Comment> comments = new ArrayList<>(length);
                        for (int i = 0; i < length; i++)
                        {
                            Comment comment = new Comment(array.optJSONObject(i));
                            comments.add(comment);
                        }
                        return comments;
                    }
                }

                return null;
            }

            @Override
            public void onError(Call call, Exception e)
            {
                mDetailView.showLoading(false);
            }

            @Override
            public void onResponse(List<Comment> response)
            {
                mDetailView.showLoading(false);
                mDetailView.loadResult(true);
                mDetailView.showDataAfterLoad(response, page == 1);
            }
        });
    }

    @Override
    public void start()
    {
        loading(1);
    }
}

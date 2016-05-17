package com.xinzy.qsbk.logic.content.presenter;

import com.xinzy.okhttp3.OkHttpUtils;
import com.xinzy.okhttp3.callback.Callback;
import com.xinzy.qsbk.common.api.Apis;
import com.xinzy.qsbk.common.model.Comment;
import com.xinzy.qsbk.common.model.Content;
import com.xinzy.qsbk.logic.content.fragment.IDetailView;
import com.xinzy.qsbk.logic.content.view.ContentDetailView;

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
    private Content     mContent;

    public DetailPresenter(IDetailView mDetailView)
    {
        this.mDetailView = mDetailView;
        this.mDetailView.setPresenter(this);
    }

    @Override
    public void start()
    {
        loading(1);
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
                    int       length;
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
    public void onSupportClick(ContentDetailView view, Content content)
    {
        final int userState = content.getUserState();
        content.setUserState(Content.STATE_SUPPORT);
        view.getSupportImageView().setSelected(true);
        view.startAnim(view.getSupportImageView());
        if (userState == Content.STATE_NONE)
        {
            content.getVote().setUp(content.getVote().getUp() + 1);
        } else if (userState == Content.STATE_UNSUPPORT)
        {
            content.getVote().setUp(content.getVote().getUp() + 1);
            content.getVote().setDown(content.getVote().getDown() + 1);
            view.getUnsupportImageView().setSelected(false);
        }
        view.setDataText(content);
    }

    @Override
    public void onUnsupportClick(ContentDetailView view, Content content)
    {
        final int userState = content.getUserState();
        content.setUserState(Content.STATE_UNSUPPORT);
        view.getUnsupportImageView().setSelected(true);
        view.startAnim(view.getUnsupportImageView());
        if (userState == Content.STATE_NONE)
        {
            content.getVote().setDown(content.getVote().getDown() - 1);
        } else if (userState == Content.STATE_SUPPORT)
        {
            content.getVote().setUp(content.getVote().getUp() - 1);
            content.getVote().setDown(content.getVote().getDown() - 1);
            view.getSupportImageView().setSelected(false);
        }
        view.setDataText(content);
    }

    @Override
    public void showReferDialog(Comment comment)
    {
        if (comment.getRefer() != null)
        {
            mDetailView.showReferDialog(comment.getRefer());
        }
    }
}

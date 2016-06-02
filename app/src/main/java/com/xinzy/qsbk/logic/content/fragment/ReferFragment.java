package com.xinzy.qsbk.logic.content.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinzy.qsbk.R;
import com.xinzy.qsbk.common.media.CircleTransformation;
import com.xinzy.qsbk.common.model.Comment;
import com.xinzy.qsbk.common.model.User;
import com.xinzy.qsbk.common.util.Utils;

/**
 * Created by Xinzy on 2016/5/17.
 */
public class ReferFragment extends DialogFragment
{
	private static final String DIALOG_TAG = ReferFragment.class.getSimpleName();

	private static final String KEY_COMMENT = "COMMENT";

	private ImageView mAvatarView;
	private TextView  usernameTextView;
	private TextView  floorTextView;
	private TextView  contentTextView;

	private Comment mComment;

	public static final ReferFragment newInstance(Comment comment)
	{
		ReferFragment fragment = new ReferFragment();
		Bundle        bundle   = new Bundle();
		bundle.putParcelable(KEY_COMMENT, comment);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onAttach(Context context)
	{
		super.onAttach(context);
		Bundle bundle = getArguments();
		mComment = bundle.getParcelable(KEY_COMMENT);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		Dialog dialog = getDialog();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

		View view = inflater.inflate(R.layout.fragment_refer, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
	{
		mAvatarView = (ImageView) view.findViewById(R.id.reply_avatar_view);
		usernameTextView = (TextView) view.findViewById(R.id.reply_username_text);
		floorTextView = (TextView) view.findViewById(R.id.reply_floor_text);
		contentTextView = (TextView) view.findViewById(R.id.reply_content_text);

		if (mComment != null)
		{
			User user = mComment.getUser();
			if (user != null)
			{
				Glide.with(getContext()).load(user.getAvatar()).bitmapTransform(new CircleTransformation(getContext())).error(R.drawable.default_avatar).into(mAvatarView);
			}
			usernameTextView.setText(user == null ? "匿名用户" : user.getUsername());
			floorTextView.setText(mComment.getFloor() + "");
			contentTextView.setText(mComment.parseContent());
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();
		int width = (int) (Utils.getScreenSize(getContext()).x * 0.9);
		getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
	}

	public void show(FragmentManager manager)
	{
		show(manager, DIALOG_TAG);
	}
}

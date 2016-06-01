package com.xinzy.qsbk.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xinzy on 2016/6/1.
 */
public class Awkward implements Parcelable
{
	public static final int TYPE_TEXT = 0;
	public static final int TYPE_IMG = 1;
	public static final int TYPE_GIF = 2;
	public static final int TYPE_VIDEO = 3;
	public static final int TYPE_IMGS = 4;

	private User author;
	private int id;
	private int mediaType;
	private String content;
	private int favors;
	private int comments;
	private int views;
	private int shares;
	private int time;
	private boolean isFavor;
	private String shareUrl;
	private Image image;
	private List<Image> images;
	private Comment hotComment;
	private Video video;

	public Awkward()
	{
	}

	protected Awkward(Parcel in)
	{
		this.author = in.readParcelable(User.class.getClassLoader());
		this.id = in.readInt();
		this.mediaType = in.readInt();
		this.content = in.readString();
		this.favors = in.readInt();
		this.comments = in.readInt();
		this.views = in.readInt();
		this.shares = in.readInt();
		this.time = in.readInt();
		this.isFavor = in.readByte() != 0;
		this.shareUrl = in.readString();
		this.image = in.readParcelable(Image.class.getClassLoader());
		this.images = in.createTypedArrayList(Image.CREATOR);
		this.hotComment = in.readParcelable(Comment.class.getClassLoader());
		this.video = in.readParcelable(Video.class.getClassLoader());
	}

	public static final Awkward parse(JSONObject json)
	{
		Awkward item = new Awkward();
		JSONObject data = json.optJSONObject("group");

		item.id = data.optInt("id");
		item.content = data.optString("content");
		item.favors = data.optInt("favorite_count");
		item.views = data.optInt("go_detail_count");
		item.comments = data.optInt("comment_count");
		item.shareUrl = data.optString("share_url");
		item.shares = data.optInt("share_count");
		item.time = data.optInt("create_time");

		final int type = item.mediaType = data.optInt("media_type");
		switch (type)
		{
		case TYPE_IMG:
			JSONObject img = data.optJSONObject("large_image");
			if (img == null) img = data.optJSONObject("middle_image");
			if (img != null) item.image = Image.parse(img);
			break;

		case TYPE_GIF:
			JSONObject gif = data.optJSONObject("large_image");
			if (gif == null) gif = data.optJSONObject("middle_image");
			if (gif != null)
			{
				item.image = Image.parse(gif);
				item.image.setGif(true);
			}
			break;

		case TYPE_IMGS:
			JSONArray array = data.optJSONArray("large_image_list");
			if (array != null) item.images = Image.parse(array);
			break;

		case TYPE_VIDEO:
			JSONObject video = data.optJSONObject("origin_video");
			if (video == null) video = data.optJSONObject("720p_video");
			JSONObject cover = data.optJSONObject("large_cover");
			if (cover == null) cover = data.optJSONObject("medium_cover");

			item.video = Video.parse(video, cover);
			break;

		case TYPE_TEXT:
		default:
			break;
		}

		return item;
	}

	public User getAuthor()
	{
		return author;
	}

	public void setAuthor(User author)
	{
		this.author = author;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setMediaType(int mediaType)
	{
		this.mediaType = mediaType;
	}

	public int getMediaType()
	{
		return mediaType;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public int getFavors()
	{
		return favors;
	}

	public void setFavors(int favors)
	{
		this.favors = favors;
	}

	public int getComments()
	{
		return comments;
	}

	public void setComments(int comments)
	{
		this.comments = comments;
	}

	public int getViews()
	{
		return views;
	}

	public void setViews(int views)
	{
		this.views = views;
	}

	public int getShares()
	{
		return shares;
	}

	public void setShares(int shares)
	{
		this.shares = shares;
	}

	public int getTime()
	{
		return time;
	}

	public void setTime(int time)
	{
		this.time = time;
	}

	public boolean isFavor()
	{
		return isFavor;
	}

	public void setFavor(boolean favor)
	{
		isFavor = favor;
	}

	public String getShareUrl()
	{
		return shareUrl;
	}

	public void setShareUrl(String shareUrl)
	{
		this.shareUrl = shareUrl;
	}

	public Image getImage()
	{
		return image;
	}

	public void setImage(Image image)
	{
		this.image = image;
	}

	public List<Image> getImages()
	{
		return images;
	}

	public void setImages(List<Image> images)
	{
		this.images = images;
	}

	public Comment getHotComment()
	{
		return hotComment;
	}

	public void setHotComment(Comment hotComment)
	{
		this.hotComment = hotComment;
	}

	public Video getVideo()
	{
		return video;
	}

	public void setVideo(Video video)
	{
		this.video = video;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeParcelable(this.author, flags);
		dest.writeInt(this.id);
		dest.writeInt(this.mediaType);
		dest.writeString(this.content);
		dest.writeInt(this.favors);
		dest.writeInt(this.comments);
		dest.writeInt(this.views);
		dest.writeInt(this.shares);
		dest.writeInt(this.time);
		dest.writeByte(this.isFavor ? (byte) 1 : (byte) 0);
		dest.writeString(this.shareUrl);
		dest.writeParcelable(this.image, flags);
		dest.writeTypedList(this.images);
		dest.writeParcelable(this.hotComment, flags);
		dest.writeParcelable(this.video, flags);
	}

	public static final Creator<Awkward> CREATOR = new Creator<Awkward>()
	{
		@Override
		public Awkward createFromParcel(Parcel source)
		{
			return new Awkward(source);
		}

		@Override
		public Awkward[] newArray(int size)
		{
			return new Awkward[size];
		}
	};

	public static final class Video implements Parcelable
	{
		private int width;
		private int height;
		private String url;
		private String cover;

		public Video()
		{
		}

		protected Video(Parcel in)
		{
			this.width = in.readInt();
			this.height = in.readInt();
			this.url = in.readString();
			this.cover = in.readString();
		}

		public static final Video parse(JSONObject json, JSONObject cover)
		{
			Video video = new Video();
			JSONArray array = json.optJSONArray("url_list");
			if (array != null && array.length() > 0) video.url = array.optJSONObject(0).optString("url");
			else return null;

			video.width = json.optInt("width");
			video.height = json.optInt("height");

			if (cover != null)
			{
				array = cover.optJSONArray("url_list");
				video.cover = array.optJSONObject(0).optString("url");
			}
			return video;
		}

		public int getWidth()
		{
			return width;
		}

		public void setWidth(int width)
		{
			this.width = width;
		}

		public int getHeight()
		{
			return height;
		}

		public void setHeight(int height)
		{
			this.height = height;
		}

		public String getUrl()
		{
			return url;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}

		@Override
		public int describeContents()
		{
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags)
		{
			dest.writeInt(this.width);
			dest.writeInt(this.height);
			dest.writeString(this.url);
			dest.writeString(this.cover);
		}

		public static final Creator<Video> CREATOR = new Creator<Video>()
		{
			@Override
			public Video createFromParcel(Parcel source)
			{
				return new Video(source);
			}

			@Override
			public Video[] newArray(int size)
			{
				return new Video[size];
			}
		};
	}

	public static final class Image implements Parcelable
	{
		private int width;
		private int height;
		private String url;
		private boolean isGif;

		public Image()
		{
		}

		protected Image(Parcel in)
		{
			this.width = in.readInt();
			this.height = in.readInt();
			this.url = in.readString();
			this.isGif = in.readInt() == 1;
		}

		public static final Image parse(JSONObject json)
		{
			Image img = new Image();
			img.width = json.optInt("width");
			img.height = json.optInt("height");
			JSONArray array = json.optJSONArray("url_list");
			if (array != null && array.length() > 0) img.url = array.optJSONObject(0).optString("url");

			return img;
		}

		public static final List<Image> parse(JSONArray array)
		{
			List<Image> images = new ArrayList<>();
			final int length = array.length();
			for (int i = 0; i < length; i++)
			{
				JSONObject json = array.optJSONObject(i);
				Image img = new Image();
				img.width = json.optInt("width");
				img.height = json.optInt("height");
				img.url = json.optString("url");
				img.isGif = json.optBoolean("is_gif");

				images.add(img);
			}

			return images;
		}

		public int getWidth()
		{
			return width;
		}

		public void setWidth(int width)
		{
			this.width = width;
		}

		public int getHeight()
		{
			return height;
		}

		public void setHeight(int height)
		{
			this.height = height;
		}

		public String getUrl()
		{
			return url;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}

		public boolean isGif()
		{
			return isGif;
		}

		public void setGif(boolean gif)
		{
			isGif = gif;
		}

		@Override
		public int describeContents()
		{
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags)
		{
			dest.writeInt(this.width);
			dest.writeInt(this.height);
			dest.writeString(this.url);
			dest.writeInt(this.isGif ? 1 : 0);
		}

		public static final Creator<Image> CREATOR = new Creator<Image>()
		{
			@Override
			public Image createFromParcel(Parcel source)
			{
				return new Image(source);
			}

			@Override
			public Image[] newArray(int size)
			{
				return new Image[size];
			}
		};
	}

	public static final class Comment implements Parcelable
	{
		private User    user;
		private int 	id;
		private String  content;
		private boolean liked;
		private int     likeCount;
		private int     time;

		public Comment()
		{
		}

		protected Comment(Parcel in)
		{
			this.id = in.readInt();
			this.user = in.readParcelable(User.class.getClassLoader());
			this.content = in.readString();
			this.liked = in.readByte() != 0;
			this.likeCount = in.readInt();
			this.time = in.readInt();
		}

		public int getId()
		{
			return id;
		}

		public void setId(int id)
		{
			this.id = id;
		}

		public User getUser()
		{
			return user;
		}

		public void setUser(User user)
		{
			this.user = user;
		}

		public String getContent()
		{
			return content;
		}

		public void setContent(String content)
		{
			this.content = content;
		}

		public boolean isLiked()
		{
			return liked;
		}

		public void setLiked(boolean liked)
		{
			this.liked = liked;
		}

		public int getLikeCount()
		{
			return likeCount;
		}

		public void setLikeCount(int likeCount)
		{
			this.likeCount = likeCount;
		}

		public int getTime()
		{
			return time;
		}

		public void setTime(int time)
		{
			this.time = time;
		}

		@Override
		public int describeContents()
		{
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags)
		{
			dest.writeInt(this.id);
			dest.writeParcelable(this.user, flags);
			dest.writeString(this.content);
			dest.writeByte(this.liked ? (byte) 1 : (byte) 0);
			dest.writeInt(this.likeCount);
			dest.writeInt(this.time);
		}

		public static final Creator<Comment> CREATOR = new Creator<Comment>()
		{
			@Override
			public Comment createFromParcel(Parcel source)
			{
				return new Comment(source);
			}

			@Override
			public Comment[] newArray(int size)
			{
				return new Comment[size];
			}
		};
	}

	public static final class User implements Parcelable
	{
		private int    userid;
		private String username;
		private String avatar;

		public User()
		{
		}

		protected User(Parcel in)
		{
			this.userid = in.readInt();
			this.username = in.readString();
			this.avatar = in.readString();
		}

		public int getUserid()
		{
			return userid;
		}

		public void setUserid(int userid)
		{
			this.userid = userid;
		}

		public String getUsername()
		{
			return username;
		}

		public void setUsername(String username)
		{
			this.username = username;
		}

		public String getAvatar()
		{
			return avatar;
		}

		public void setAvatar(String avatar)
		{
			this.avatar = avatar;
		}

		@Override
		public int describeContents()
		{
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags)
		{
			dest.writeInt(this.userid);
			dest.writeString(this.username);
			dest.writeString(this.avatar);
		}

		public static final Creator<User> CREATOR = new Creator<User>()
		{
			@Override
			public User createFromParcel(Parcel source)
			{
				return new User(source);
			}

			@Override
			public User[] newArray(int size)
			{
				return new User[size];
			}
		};
	}
}

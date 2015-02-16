package cn.hi.eim.view;

import java.util.List;

import org.jivesoftware.smackx.packet.VCard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout.LayoutParams;
import cn.hi.eim.R;
import cn.hi.eim.comm.Constant;
import cn.hi.eim.manager.ContacterManager;
import cn.hi.eim.manager.UserManager;
import cn.hi.eim.manager.XmppConnectionManager;
import cn.hi.eim.model.ChartHisBean;
import cn.hi.eim.model.User;
import cn.hi.eim.util.FaceUtils;
import cn.hi.eim.util.FileUtil;
import cn.hi.eim.util.StringUtil;
import cn.hi.eim.util.SystemUtils;

public class RecentChartAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<ChartHisBean> inviteUsers;
	private Context context;
	private OnClickListener contacterOnClick;
	private int mRightWidth = 0;
	private SharedPreferences preferences;
	int expression_wh = -1;

	public RecentChartAdapter(Context context, List<ChartHisBean> inviteUsers,
			int rightWidth) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.inviteUsers = inviteUsers;
		mRightWidth = rightWidth;
		expression_wh = (int) context.getResources().getDimension(
				R.dimen.chat_recent_list_expression_wh);
	}

	public void setNoticeList(List<ChartHisBean> inviteUsers) {
		this.inviteUsers = inviteUsers;
	}

	@Override
	public int getCount() {
		return inviteUsers.size();
	}

	@Override
	public Object getItem(int position) {
		return inviteUsers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	final Html.ImageGetter imageGetter_resource = new Html.ImageGetter() {
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			source = FaceUtils.getImgNum(source);
			drawable = FileUtil.getImageFromAssetsToDrawable(context,
					"ico/face/e_" + source + ".png");
			drawable.setBounds(0, 0, expression_wh, expression_wh);// 设置显示的图像大小
			return drawable;
		};
	};

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ChartHisBean notice = inviteUsers.get(position);
		Integer ppCount = notice.getNoticeSum();
		User u = notice.getUser();
		ViewHolderx holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.recent_chart_item, null);
			holder = new ViewHolderx();
			holder.newTitle = (TextView) convertView
					.findViewById(R.id.new_title);
			holder.itemIcon = (ImageView) convertView
					.findViewById(R.id.new_icon);
			holder.newContent = (TextView) convertView
					.findViewById(R.id.new_content);
			holder.newDate = (TextView) convertView.findViewById(R.id.new_date);
			holder.paopao = (TextView) convertView.findViewById(R.id.paopao);
			holder.item_right = (RelativeLayout) convertView
					.findViewById(R.id.item_right);
			holder.item_left = (RelativeLayout) convertView
					.findViewById(R.id.item_left);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolderx) convertView.getTag();
		}
		// connection.getRoster().getEntry(user)
		
		/*if(u==null){
			try {
				jid = StringUtil.getJidByName(context, jid);
				u = ContacterManager.getByUserJid(jid, XmppConnectionManager
						.getInstance().getConnection());
				*//** 获取用户信息 ***//*
				VCard vCard = UserManager.getInstance(context).getUserVCard(jid);
				u.setvCard(vCard);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (null == u) {
				u = new User();
				u.setJID(jid);
				u.setName(jid);
				u.setvCard(new VCard());
			}
		}*/
		holder.newTitle.setText(SystemUtils.getUserNameToLocal(u.getName()));
		int gender = u.getGender();
		if (gender==0) {
			holder.newTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.q_chat, 0);
		} else {
			holder.newTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,
					R.drawable.k_chat, 0);
		}

		if (!TextUtils.isEmpty(u.getAvatar())) {
			holder.itemIcon.setImageBitmap(FileUtil.getImageFromAssetsFile(
					context, u.getAvatar()));
		} else {
			holder.itemIcon.setImageBitmap(BitmapFactory.decodeResource(
					context.getResources(), R.drawable.head_default_round));
		}

		String content = FaceUtils.chineseConvert(FaceUtils
				.replaceSpaceToCode(notice.getContent()));
		holder.newContent.setText(Html.fromHtml(content, imageGetter_resource,
				null));

		holder.newContent.setTag(u);
		holder.newDate.setText(notice.getNoticeTime().substring(5, 16));

		LinearLayout.LayoutParams lp1 = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		holder.item_left.setLayoutParams(lp1);
		LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth,
				LayoutParams.MATCH_PARENT);
		holder.item_right.setLayoutParams(lp2);

		if (ppCount != null && ppCount > 0) {
			holder.paopao.setText(ppCount + "");
			holder.paopao.setVisibility(View.VISIBLE);

		} else {
			holder.paopao.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(contacterOnClick);
		holder.item_right.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.e("main", "position:" + position);
				if (mListener != null) {
					mListener.onRightItemClick(v, position);
				}
			}
		});

		return convertView;
	}

	public class ViewHolderx {
		public ImageView itemIcon;
		public TextView newTitle;
		public TextView newContent;
		public TextView newDate;
		public TextView paopao;
		public RelativeLayout item_right;
		public RelativeLayout item_left;

	}

	/**
	 * 这里用一句话描述这个方法的作用.
	 * 
	 * @param contacterOnClick
	 * @author shimiso
	 * @update 2012-6-28 下午1:29:27
	 */
	public void setOnClickListener(OnClickListener contacterOnClick) {

		this.contacterOnClick = contacterOnClick;
	}

	/**
	 * 单击事件监听器
	 */
	private onRightItemClickListener mListener = null;

	public void setOnRightItemClickListener(onRightItemClickListener listener) {
		mListener = listener;
	}

	public interface onRightItemClickListener {
		void onRightItemClick(View v, int position);
	}
}
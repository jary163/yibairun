package cn.hi.eim.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.hi.eim.R;
import cn.hi.eim.model.Expression;
import cn.hi.eim.util.FileUtil;
/**
 * 表情列表
 * @author daobo.yuan
 *
 */
public class ExpressionImageAdapter extends BaseAdapter {
	List<Expression> expressionList;
	Context context;
	LayoutInflater lfInflater;

	public ExpressionImageAdapter(Context context,List<Expression> expressionList) {
		this.context = context;
		this.expressionList = expressionList;
		lfInflater = LayoutInflater.from(this.context);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return expressionList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return expressionList.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		Expression exp = expressionList.get(position);
		if (convertView == null) {
			convertView = lfInflater.inflate(R.layout.expression_list_item, null);
			holder = new ViewHolder();
			holder.iv_id = (ImageView)convertView.findViewById(R.id.iv_id);
			holder.tv_id = (TextView)convertView.findViewById(R.id.tv_id);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//删除图标
		if(exp.getDrableCode()==-1){
			holder.iv_id.setBackgroundResource(R.drawable.chat_del);
		}else{
			//TODO   设置删除图标
			//holder.iv_id.setBackgroundResource(exp.getDrableId());
			holder.iv_id.setImageBitmap(FileUtil.getImageFromAssetsFile(context, "ico/face/e_"+exp.getDrableCode()+".png"));
		}
		holder.tv_id.setText(exp.getCode());
		return convertView;
	}
	
	class ViewHolder{
		ImageView iv_id;
		TextView tv_id;
	}
}
package com.example.jsonbite;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class jsonAapter extends BaseAdapter {
    private List<money> list;
    private Context context;
    private LayoutInflater inflater;
    
    
    public jsonAapter(Context context,List<money> list )
    {
    	this.list=list;
    	this.context=context;
    	inflater=LayoutInflater.from(context);
    }
    public jsonAapter(Context context)
    {
    	this.context=context;
    	inflater=LayoutInflater.from(context);
    }
    public void setData(List<money> data)
    {
    	this.list=data;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder=null;
		if(convertView==null)
		{
			convertView=inflater.inflate(R.layout.money_item, null);
			holder=new Holder(convertView);
			convertView.setTag(holder);
		}else{
			holder=(Holder)convertView.getTag();
		}
		money money1=list.get(position);
		if (money1.isFlag()) {
			holder.price.setTextColor(Color.parseColor("#7FFFAA"));
		}
		holder.name.setText(money1.getName());
		holder.price.setText("最新交易价："+new BigDecimal(money1.getLast().toString()).toString());
		holder.buy.setText("买一价："+new BigDecimal(money1.getBuy().toString()).toString()+" ");
		holder.sell.setText("卖一价："+new BigDecimal(money1.getSell().toString()).toString()+" ");
		holder.vol.setText("交易量："+new BigDecimal(money1.getVolume().toString()).toString()+" ");
		return convertView;
	}
	class Holder{
		private TextView name;
		private TextView price;
		private TextView buy;
		private TextView sell;
		private TextView vol;
		public Holder(View view)
		{
			name=(TextView) view.findViewById(R.id.textname);
			price=(TextView) view.findViewById(R.id.textprice);
			buy=(TextView) view.findViewById(R.id.textbuy);
			sell=(TextView) view.findViewById(R.id.textsell);
			vol=(TextView) view.findViewById(R.id.textvol);
		}
	}

}

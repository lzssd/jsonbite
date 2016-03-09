package com.example.jsonbite;

import android.R.bool;

public class money {

	private String name;
	private boolean flag;
	
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private Double last;
	public Double getLast() {
		return last;
	}
	public void setLast(Double last) {
		this.last = last;
	}
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Double getBuy() {
		return buy;
	}
	public void setBuy(Double buy) {
		this.buy = buy;
	}
	public Double getSell() {
		return sell;
	}
	public void setSell(Double sell) {
		this.sell = sell;
	}
	private Double high;
	private Double low;
	private Double volume;
	private Double buy;
	private Double sell;
	
	public money(String name,Double last,Double high,Double low,Double volume,Double buy,Double sell,boolean flag)
	{
		this.name=name;
		this.last=last;
		this.high=high;
		this.low=low;
		this.volume=volume;
		this.buy=buy;
		this.sell=sell;
		this.flag=flag;
	}

	
}

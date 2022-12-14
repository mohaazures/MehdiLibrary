package org.ads.myads.Models;

import com.google.gson.annotations.SerializedName;

public class Argentina{

	@SerializedName("Native")
	private String mynative;

	@SerializedName("Banner")
	private String banner;

	@SerializedName("Interstitial")
	private String interstitial;

	@SerializedName("Reward")
	private String reward;

	public String getNative(){
		return mynative;
	}

	public String getBanner(){
		return banner;
	}

	public String getInterstitial(){
		return interstitial;
	}

	public String getReward(){
		return reward;
	}
}
package com.yiwucheguanjia.merchantcarmgr.city.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * 用于暂时存储一些变量
 */

public class SharedPreferencesUtils {

	/**
	 * 保存用户选择的城市
	 */
	public static void saveCityName(Context context, String cityName) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("cityName", cityName).commit();

	}

	public static String getCityName(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString("cityName", "广州");
	}
	public static void clearData(Context context){
		String cityName = PreferenceManager.getDefaultSharedPreferences(context).getString("cityName", "广州");
		PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString("cityName", cityName).commit();
//		int i = 0;
//		while (true){
//			if (PreferenceManager.getDefaultSharedPreferences(context).getString(i + "",null) != null){
//				PreferenceManager.getDefaultSharedPreferences(context).edit().remove(i + "").commit();
//			}else {
//				break;
//			}
//		}
	}
	public static void saveAreaName(Context context, String cityName,int i) {
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString(i + "", cityName).commit();

	}
	public static String getAreaName(Context context,int j) {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(j + "", null);
	}

}

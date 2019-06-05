package com.zkp.android.modules.main.fragment.setting

import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat
import com.zkp.android.R
import com.zkp.android.utils.CacheUtils

/**
 * @author: hmc
 * @project: WanAndroid
 * @package: com.zkp.android.modules.main.Fragment.setting
 * @time: 2019/6/4 14:20
 * @description:
 */
class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.fragment_setting)
        findPreference("clearCache").summary = CacheUtils().getTotalCacheSize()
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        when (preference.key) {
            "clearCache" -> {
                CacheUtils().clearAllCache()
                findPreference(preference.key).summary = CacheUtils().getTotalCacheSize()
            }
            else -> {
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

}
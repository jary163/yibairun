package com.yibairun.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.yibairun.application.AppController;

public class SYSharedPreferences {

	private static final String SP_NAME = "yibairun";
    public static final String KEY_UUID = "UUID";

	private static SYSharedPreferences instance = new SYSharedPreferences();

	public SYSharedPreferences() {
	}

	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new SYSharedPreferences();
		}
	}

	public static SYSharedPreferences getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}

	private SharedPreferences getSp() {
		return AppController.getInstance().getSharedPreferences(SP_NAME,
				Context.MODE_PRIVATE);
	}

	public int getInt(String key, int def) {
		try {
			SharedPreferences sp = getSp();
			if (sp != null)
				def = sp.getInt(key, def);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

	public void putInt(String key, int val) {
		try {
			SharedPreferences sp = getSp();
			if (sp != null) {
				Editor e = sp.edit();
				e.putInt(key, val);
				e.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public long getLong(String key, long def) {
        try {
            SharedPreferences sp = getSp();
            if (sp != null)
                def = sp.getLong(key, def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return def;
    }

    public void putLong(String key, long val) {
        try {
            SharedPreferences sp = getSp();
            if (sp != null) {
                Editor e = sp.edit();
                e.putLong(key, val);
                e.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public String getString(String key, String def) {
		try {
			SharedPreferences sp = getSp();
			if (sp != null)
				def = sp.getString(key, def);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

	public void putString(String key, String val) {
		try {
			SharedPreferences sp = getSp();
			if (sp != null) {
				Editor e = sp.edit();
				e.putString(key, val);
				e.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean getBoolean(String key, boolean def) {
		try {
			SharedPreferences sp = getSp();
			if (sp != null)
				def = sp.getBoolean(key, def);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return def;
	}

	public void putBoolean(String key, boolean val) {
		try {
			SharedPreferences sp = getSp();
			if (sp != null) {
				Editor e = sp.edit();
				e.putBoolean(key, val);
				e.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(String key) {
		try {
			SharedPreferences sp = getSp();
			if (sp != null) {
				Editor e = sp.edit();
				e.remove(key);
				e.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

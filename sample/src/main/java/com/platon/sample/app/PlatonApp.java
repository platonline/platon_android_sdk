package com.platon.sample.app;

import android.app.Application;

import com.platon.sample.db.DBHelper;
import com.stanko.tools.Initializer;
import com.stanko.tools.Log;

import static androidx.core.util.PatternsCompat.WEB_URL;
public class PlatonApp extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		// StanKoUtils
		Initializer.init(this);
		Log.init(this);

		// DB
		DBHelper.init(this);

	}

	public static boolean isValidURL(final String url) {
		return WEB_URL.matcher(url).matches();
	}

}

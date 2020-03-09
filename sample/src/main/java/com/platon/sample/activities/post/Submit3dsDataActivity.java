package com.platon.sample.activities.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;

import com.platon.sample.activities.BaseActivity;

public class Submit3dsDataActivity extends BaseActivity {

	private final static String KEY_HTML_PAGE = "KEY_HTML_PAGE";

	@Override
	protected void onCreate(@Nullable final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getIntent() == null) {
			finish();
			return;
		}

		final String htmlPage = getIntent().getStringExtra(KEY_HTML_PAGE);
		if (TextUtils.isEmpty(htmlPage)) {
			finish();
			return;
		}

		final WebView webView = new WebView(this);
		setContentView(webView);

		webView.loadData(htmlPage, "text/html", "UTF-8");
	}

	public static void performTransaction(final Context context, final String htmlPage) {
		final Intent intent = new Intent(context, Submit3dsDataActivity.class);
		intent.putExtra(KEY_HTML_PAGE, htmlPage);
		context.startActivity(intent);
	}

}

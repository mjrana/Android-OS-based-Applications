package ltu.org;

import android.webkit.WebView;



import android.webkit.WebViewClient;

public class HelloWebViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
				
		view.loadUrl(url);
		view.refreshPlugins(true);
		return true;
	}

}

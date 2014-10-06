package photon.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by oded on 6/20/14.
 */
public class WebViewClientImpl extends WebViewClient {

    private MainActivity mainActivity;
//    private BitmapDrawable icon;

    WebViewClientImpl(MainActivity mainActivity) {

        this.mainActivity = mainActivity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (url.contains("http")) {

            // do your handling codes here, which url is the requested url
            // probably you need to open that url rather than redirect:
            view.loadUrl(url);
        } else {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            mainActivity.startActivity(i);
        }
        return false; // then it is not handled by default action
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        mainActivity.getActionBar().setIcon(android.R.drawable.stat_sys_download);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mainActivity.getActionBar().setIcon(R.drawable.ic_launcher);
//        mainActivity.getActionBar().setIcon(R.drawable.ic_launcher);
        mainActivity.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
    }
}

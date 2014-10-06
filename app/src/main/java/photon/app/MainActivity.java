package photon.app;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


public class MainActivity extends Activity {

    public WebView webView;
    public SpeechRecognizer speechRecognizer;
    public Intent speechRecognitionIntent;
    private AudioManager aManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CookieManager.getInstance().setAcceptCookie(true);
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClientImpl(this));
        webView.getSettings().setLoadWithOverviewMode(true);
//        webView.setWebChromeClient(new WebChromeClient());
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this, BestSpeechRecognition.getComponent());
        speechRecognitionIntent = BestSpeechRecognition.getIntent();
        speechRecognizer.setRecognitionListener(new SpeechRecognitionListener(this));
        aManager = (AudioManager) getSystemService(AUDIO_SERVICE);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_in_browser:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(webView.getUrl()));
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        aManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        speechRecognizer.startListening(speechRecognitionIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        speechRecognizer.stopListening();
        aManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
    }
    @Override
    public void onBackPressed() {
        speechRecognizer.stopListening();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        speechRecognizer.stopListening();
        super.onDestroy();
    }
}

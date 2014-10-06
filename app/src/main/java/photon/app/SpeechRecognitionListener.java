package photon.app;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by oded on 6/20/14.
 */
public class SpeechRecognitionListener implements RecognitionListener {
    MainActivity mainActivity;

    public SpeechRecognitionListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        Log.d("speech", bundle.toString());
//        mainActivity.getActionBar().setIcon(android.R.drawable.ic_btn_speak_now);
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("speech", "onBeginningOfSpeech");
    }

    @Override
    public void onRmsChanged(float v) {
        Log.d("speech", "rms: " + v);

        if (v < 4) return;

        if (new Random().nextBoolean()) {
            mainActivity.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
        } else {
            mainActivity.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        }
    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        Log.d("speech", "onEndOfSpeech");
    }

    @Override
    public void onError(int i) {
        mainActivity.speechRecognizer.startListening(mainActivity.speechRecognitionIntent);
        Log.d("speech", "onError: " + i);
    }

    @Override
    public void onResults(Bundle results) {
        Log.d("speech", "onResults: " + results.toString());
        String result = getFirstResult(results);
        mainActivity.setTitle(result);
        mainActivity.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        useResult(result);
        mainActivity.speechRecognizer.startListening(mainActivity.speechRecognitionIntent);
    }

    @Override
    public void onPartialResults(Bundle results) {

        Log.d("speech", "onPartialResults: " + results.toString());
        mainActivity.getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        String result = getFirstResult(results);
        mainActivity.setTitle(result);
//        useResult(result);

    }

    String getFirstResult(Bundle results) {
        if (results != null) {
            ArrayList<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        }
        return null;
    }

    void useResult(String result) {

        if (!result.toLowerCase().contains("show me")) {
            mainActivity.setTitle("Say \"Show me " + result + "\"");
            return;
        }

        result = result.replace("show me", "");

        String query = null;
        try {
            query = URLEncoder.encode(result, "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        mainActivity.webView.loadUrl("https://www.google.com/search?q=" + query + "&btnI");
//        mainActivity.webView.loadUrl("http://robin-labs.appspot.com/api?query=hi" + query);
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        Log.d("speech", "onEvent: " + i);
    }


}

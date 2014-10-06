package photon.app;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.speech.RecognizerIntent;

import java.util.List;

/**
 * Created by oded on 6/20/14.
 */
public class BestSpeechRecognition {
    static Intent getIntent() {

        PackageManager pm = App.instance.getPackageManager();

        Intent it = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        it.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, MainActivity.class
                .getPackage().getName());

        List<ResolveInfo> rsi = pm.queryIntentActivities(new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

        if ((rsi != null) && (rsi.size() > 0)) {
            for (ResolveInfo ri : rsi) {
                ActivityInfo ai = ri.activityInfo;
                if (ai.name != null && ai.name.contains(".google.")) {
                    String s = ai.loadLabel(pm).toString().toLowerCase();

                    if (s.contains("voice search")) {
                        it.setClassName(ai.packageName, ai.name);
                        return it;
                    }
                }
            }
            ActivityInfo ai = rsi.get(0).activityInfo;
            it.setPackage(ai.packageName);
            // it.setClassName(ai.packageName, ai.name);
            it.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
            it.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
            it.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            return it;
        }

        return null;

    }

    static ComponentName getComponent() {
        PackageManager pm = App.instance.getPackageManager();
        Intent it = new Intent("android.speech.RecognitionService");
        List<ResolveInfo> ss = pm.queryIntentServices(it, PackageManager.GET_SERVICES);
        if ((ss != null) && (ss.size() > 0)) {
            for (ResolveInfo ri : ss) {
                ServiceInfo si = ri.serviceInfo;
                if (si.name.contains("google")) return new ComponentName(si.packageName, si.name);
            }
            ServiceInfo si = ss.get(0).serviceInfo;
            return new ComponentName(si.packageName, si.name);
        }

        return null;
    }
}

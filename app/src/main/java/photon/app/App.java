package photon.app;

import android.app.Application;
import android.content.Context;

/**
 * Created by oded on 6/20/14.
 */
public class App extends Application {

    static App instance;

    public App() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

}

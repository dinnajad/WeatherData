/**
 * 
 */
package karro.spike.weatherdataspike;

import android.app.Application;

/**
 * taken from http://stackoverflow.com/questions/3667022/checking-if-an-android-application-is-running-in-the-background
 * @author Karro 
 *
 */
//Don't forget to add it to your manifest by doing
//<application android:name="your.package.MyApplication" ...
public class MyApplication extends Application {
 @Override
 public void onCreate() {
     // Simply add the handler, and that's it! No need to add any code
     // to every activity. Everything is contained in MyLifecycleHandler
     // with just a few lines of code. Now *that's* nice.
     registerActivityLifecycleCallbacks(new MyLifecycleHandler());
 }
}

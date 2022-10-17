package org.ads.myads;

import android.app.Activity;

public interface InitListener {
    void OnInitialized(Activity activity);
    void OnFailed(String Error);
}

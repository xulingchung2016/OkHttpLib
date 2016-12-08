package com.xlc.okhttplib;


import okhttp3.Call;

import java.io.IOException;

/**
 * Created by dzc on 15/12/10.
 */
public interface JsonCallback {
    public void onFailure(Call call, Exception e) ;
    public void onResponse(Call call,String object) throws IOException;

    public abstract void onStarted();
    public void onFinish();
	

}

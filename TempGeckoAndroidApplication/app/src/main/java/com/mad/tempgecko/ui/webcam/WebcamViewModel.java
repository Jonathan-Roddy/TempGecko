package com.mad.tempgecko.ui.webcam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WebcamViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public WebcamViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
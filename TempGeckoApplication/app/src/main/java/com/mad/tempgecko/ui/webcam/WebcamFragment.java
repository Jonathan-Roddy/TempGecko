package com.mad.tempgecko.ui.webcam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mad.tempgecko.R;

public class WebcamFragment extends Fragment {

    private WebcamViewModel webcamViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        webcamViewModel = new ViewModelProvider(this).get(WebcamViewModel.class);
        View root = inflater.inflate(R.layout.fragment_webcam, container, false);

//        final TextView textView = root.findViewById(R.id.text_home);
//        webcamViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return root;
    }
}
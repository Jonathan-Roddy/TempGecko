package com.mad.tempgecko.ui.webcam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.tempgecko.R;

import static android.content.ContentValues.TAG;

public class WebcamFragment extends Fragment {

    WebView wvWebcam;
    String raspberryPiIP;
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

        wvWebcam = (WebView) root.findViewById(R.id.wvWebcam);

        // Fetch IP from Firebase so no need to change hardcode here
        // Connect to FirebaseFirestore Cloud DB
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                raspberryPiIP = document.getString("WebcamIP");
//                                System.out.println("///////////////////////// inside db" + raspberryPiIP);
                                updateWebcam(raspberryPiIP);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

//        System.out.println("///////////////////////// call raspberryPiIP" + raspberryPiIP);


        return root;
    }

    private void updateWebcam(String raspberryPiIP) {
        wvWebcam.requestFocus();
        wvWebcam.setInitialScale(1);
        wvWebcam.getSettings().setJavaScriptEnabled(true);
        wvWebcam.getSettings().setLoadWithOverviewMode(true);
        wvWebcam.getSettings().setUseWideViewPort(true);
        wvWebcam.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wvWebcam.setScrollbarFadingEnabled(false);
        wvWebcam.loadUrl("http://"+raspberryPiIP +":8081/");
    }


}
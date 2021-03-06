package com.mad.tempgecko.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mad.tempgecko.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class DashboardFragment extends Fragment {

    // Connect to FirebaseFirestore Cloud DB
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Declaring variables
    TextView tvMonitorStatus, tvWebcamStatus, tvLigthsNumber, tvTempNumber, tvHumidNumber, tvPressureNumber;

    Switch sLED, sHeatMat, sWindow;
    double legthOfLights = 0.0, temperature= 0.0, humidity= 0.0, pressure= 0.0;
    boolean lightOn = false, heatMat= false, window= false, monitorStatus = false;
    String nowTimeStamp;
    String newtimeStamp;
    String calculateNowTimeStamp, calculateNewTimeStamp;

//    DateTime lightsOn;
    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        // Setting Variables
        tvMonitorStatus = (TextView) root.findViewById(R.id.tvMonitorStatus);
        tvWebcamStatus = (TextView) root.findViewById(R.id.tvWebcamStatus);
        tvWebcamStatus.setText("Active");

        // Icons
        tvLigthsNumber = (TextView) root.findViewById(R.id.tvLigthsNumber);
        tvTempNumber = (TextView) root.findViewById(R.id.tvTempNumber);
        tvHumidNumber = (TextView) root.findViewById(R.id.tvHumidNumber);
        tvPressureNumber = (TextView) root.findViewById(R.id.tvPressureNumber);

        //Switches
        sLED = (Switch) root.findViewById(R.id.sLED);
        sHeatMat = (Switch) root.findViewById(R.id.sHeatMat);
        sWindow = (Switch) root.findViewById(R.id.sWindow);
//
        // Call Firebase
        fetchInitialFirebaseStats();

        // Update switches to firebase if checked
        updateSwitches();

        // update icons if firebase has been updated
        updateIcons();

        return root;
    }

    private void fetchInitialFirebaseStats() {
        db.collection("1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getId() + " => " + document.getData());

//                                // Status Update
//                                boolean mStatus = document.getBoolean("Monitor");
//                                boolean wStatus = document.getBoolean("WebcamStatus");
//
//                                if(mStatus) tvMonitorStatus.setText("Online");
//                                else tvMonitorStatus.setText("Offline");
//
//                                if(wStatus) tvWebcamStatus.setText("Online");
//                                else tvWebcamStatus.setText("Offline");

                                // Switches
                                boolean MainLED = document.getBoolean("MainLED");
                                boolean HeatMat = document.getBoolean("HeatMat");
                                boolean Window = document.getBoolean("Window");

                                if(MainLED) sLED.setChecked(true);
                                else sLED.setChecked(false);

                                if(HeatMat) sHeatMat.setChecked(true);
                                else sLED.setChecked(false);

                                if(Window) sWindow.setChecked(true);
                                else sLED.setChecked(false);

                                // Icons
                                // Lights on
//                                tvLigthsNumber.setText(document.getString("LightLength"));
                                // Temperature
                                tvTempNumber.setText(document.getString("Temperture"));
                                // Humidity
                                tvHumidNumber.setText(document.getString("humidity"));
                                // Pressure
                                tvPressureNumber.setText(document.getString("pressure"));

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void updateSwitches() {

            // Update Main LED
            sLED.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sLED.isChecked()) {
                        // Update Main LED on cloud
                        db.collection("1").document("tKKVzC7Joswkyh8z12h6")
                                .update( "MainLED", true );
                        // Start timer and store to Database : Last Turned on + length it has been on for
                        nowTimeStamp = new SimpleDateFormat("HH:mm:ss_dd-MM-yyyy").format(Calendar.getInstance().getTime());
                        db.collection("1").document("tKKVzC7Joswkyh8z12h6")
                                .update( "LightsOn", nowTimeStamp );

                        calculateNowTimeStamp = new SimpleDateFormat("HHmmssddMMyyyy").format(Calendar.getInstance().getTime());
//                        System.out.println("///////////////////////////////////// nowTimeStamp "+ calculateNowTimeStamp);


                    } else {
                        // Update Main LED on cloud
                        db.collection("1").document("tKKVzC7Joswkyh8z12h6")
                                .update( "MainLED", false );
                        // Compare when Light was last turned on and store to Database
                        newtimeStamp = new SimpleDateFormat("HH:mm:ss_dd-MM-yyyy").format(Calendar.getInstance().getTime());

                        calculateNewTimeStamp = new SimpleDateFormat("HHmmssddMMyyyy").format(Calendar.getInstance().getTime());
//                        System.out.println("///////////////////////////////////// newtimeStamp "+ calculateNewTimeStamp);

                        long nowTs = Long.parseLong(calculateNowTimeStamp);
                        long newTs = Long.parseLong(calculateNewTimeStamp);

                        long input = (newTs - nowTs)/100000000;
                        long numberOfDays;
                        long numberOfHours;
                        long numberOfMinutes;
                        long numberOfSeconds;

                        numberOfDays = input / 86400;
                        numberOfHours = (input % 86400 ) / 3600 ;
                        numberOfMinutes = ((input % 86400 ) % 3600 ) / 60;
                        numberOfSeconds = ((input % 86400 ) % 3600 ) % 60  ;

                        String timeLength = String.valueOf(numberOfSeconds) + "seconds "+ String.valueOf(numberOfMinutes) +"minutes "+ String.valueOf(numberOfHours) +"hours "+ String.valueOf(numberOfDays)+"days";
                        String timeLength2 = String.valueOf(numberOfDays) +":"+ String.valueOf(numberOfHours)  +":"+ String.valueOf(numberOfMinutes)  +":"+  String.valueOf(numberOfSeconds);

//                        System.out.println("///////////////////////////////////// times "+ input);
//                        System.out.println("///////////////////////////////////// timeLength " + timeLength);

//                        String LightLength = String.valueOf(input);
                        db.collection("1").document("tKKVzC7Joswkyh8z12h6")
                                .update( "LightLength", timeLength2 );

                        // store the time and date of when the lights were turned off
                        db.collection("1").document("tKKVzC7Joswkyh8z12h6")
                                .update( "LastLightsOn", newtimeStamp );
                    }
                }

            });

            // Update HeatMat
            sHeatMat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sHeatMat.isChecked()) {
                        // Update Main LED on cloud
                        db.collection("1").document("tKKVzC7Joswkyh8z12h6")
                                .update( "HeatMat", true );

                    } else {
                        // Update Main LED on cloud
                        db.collection("1").document("tKKVzC7Joswkyh8z12h6")
                                .update( "HeatMat", false );
                    }
                }
            });

            // Update Window
            sWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sWindow.isChecked()) {
                        // Update Main LED on cloud
                        db.collection("1").document("tKKVzC7Joswkyh8z12h6")
                                .update( "Window", true );

                    } else {
                        // Update Main LED on cloud
                        db.collection("1").document("tKKVzC7Joswkyh8z12h6")
                                .update( "Window", false );
                    }
                }
            });

    }

    private void updateIcons() {
        final DocumentReference docRef = db.collection("1").document("tKKVzC7Joswkyh8z12h6");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());

                    // Check if Monitoring system is online.
                    monitorStatus = snapshot.getBoolean("Monitor");
                    if(monitorStatus){
                        tvMonitorStatus.setText("Online");
                        tvMonitorStatus.setTextColor(GREEN);

                        sLED.setClickable(true);
                        sHeatMat.setClickable(true);
                        sWindow.setClickable(true);

                    }
                    else{
                        tvMonitorStatus.setText("Offline");
                        tvMonitorStatus.setTextColor(RED);

                        sLED.setClickable(false);
                        sHeatMat.setClickable(false);
                        sWindow.setClickable(false);
                    }

                    tvLigthsNumber.setText(snapshot.getString("LightLength"));
                    // Temperature
                    tvTempNumber.setText(snapshot.getString("Temperture"));
                    // Humidity
                    tvHumidNumber.setText(snapshot.getString("humidity"));
                    // Pressure
                    tvPressureNumber.setText(snapshot.getString("pressure"));

                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

}
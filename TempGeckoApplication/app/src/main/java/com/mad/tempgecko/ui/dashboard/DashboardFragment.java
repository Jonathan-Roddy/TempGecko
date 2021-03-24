package com.mad.tempgecko.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mad.tempgecko.R;

public class DashboardFragment extends Fragment {

    // Declaring variables
    TextView tvMonitorStatus, tvWebcamStatus, tvLigthsNumber, tvTempNumber, tvHumidNumber, tvPressureNumber;
    Switch sLED, sHeatMat, sWindow;
    double legthOfLights = 0.0, temperature= 0.0, humidity= 0.0, pressure= 0.0;
    boolean lightOn = false, heatMat= false, window= false;


    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Setting Variables
        tvMonitorStatus = (TextView) root.findViewById(R.id.tvMonitorStatus);
        tvWebcamStatus = (TextView) root.findViewById(R.id.tvWebcamStatus);
        tvLigthsNumber = (TextView) root.findViewById(R.id.tvLigthsNumber);
        tvTempNumber = (TextView) root.findViewById(R.id.tvTempNumber);
        tvHumidNumber = (TextView) root.findViewById(R.id.tvHumidNumber);
        tvPressureNumber = (TextView) root.findViewById(R.id.tvPressureNumber);

        sLED = (Switch) root.findViewById(R.id.sLED);
        sHeatMat = (Switch) root.findViewById(R.id.sHeatMat);
        sWindow = (Switch) root.findViewById(R.id.sWindow);

        fetchFirebaseStats();
        

        return root;
    }

    private void fetchFirebaseStats() {

    }


}
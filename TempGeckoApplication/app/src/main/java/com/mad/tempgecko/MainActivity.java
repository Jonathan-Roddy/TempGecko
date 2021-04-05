package com.mad.tempgecko;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_dashboard, R.id.navigation_webcam)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Logo from : https://cdn.dribbble.com/users/1231052/screenshots/6600616/image.png

        // Steps to get it working
        // Set up SSH on Raspberry Pi
        // Connect to Pi via VNC View via IP
        // Use that IP to connect to the WebCam via $ sudo motion
        // Open TempGecko py file on Pi and run
        // This will send data to the firebase firestore so that this Application can store and use

        // Setting live Webcam
        // https://www.instructables.com/How-to-Make-Raspberry-Pi-Webcam-Server-and-Stream-/
//        Make sure 'daemon' is ON.
//        Set 'framerate' anywhere in between 1000 to 1500.
//        Keep 'Stream_port' to 8081.
//        'Stream_quality' should be 100.
//        Change 'Stream_localhost' to OFF.
//        Change 'webcontrol_localhost' to OFF.
//        Set 'quality' to 100.
//        Set 'width' & 'height' to 640 & 480.
//        Set 'post_capture' to 5.
//        Press ctrl + x to exit. Type y to save and enter to conform.
//        Again type in the command 'sudo nano /etc/default/motion ' and press enter.
//
//                Set ' start_motion_daemon ' to yes. Save and exit.
        // sudo apt-get install motion
        // sudo nano /etc/motion/motion.conf
        // sudo nano /etc/default/motion
        // sudo service motion restart
        // sudo  motion


    }

}
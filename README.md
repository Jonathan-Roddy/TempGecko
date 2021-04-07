# TempGecko
This is my Internet of Things project where I will be utilising: Raspberry Pi connected with SenseHat and LEDs that is connected to a Firebase cloud Database that I can control via an Android Application

This application is for any small lizard pet owner so that they may be able to monitor their pets enclousure using an Android application and Raspberry pi

## Componates
To be able to run this complete system you will need the following:
- Android Studio 
- Raspberry Pi
- SenseHat
- Breadboad
- 3 different LEDS (Representing, Main enclousure LED, Heat Mat and the act of opening or closing a window)
- 3 220v Resisters
- 4 GPIO pins (female to male)
- USB Webcam

## System Architecture
System Architecture: 
![ System Architecture](https://github.com/Jonathan-Roddy/TempGecko/blob/main/TempGeckoArchitecture.png "System Architecture")

## FireStore Architecture
FireStore Architecture:
![ FireStore Architecture](https://github.com/Jonathan-Roddy/TempGecko/blob/main/Image%20of%20Google%20Firebase-Firestore%20.png "FireStore Architecture")

## Features

- Broadcast a live stream using a USB webcam connected to the Raspberry Pi
- Uses the SenseHat to gather data like temperature, humidity and air pressure
- Allows the user to activate 3 LEDs to represent turning on and off an overheat LED, Heat mat and opening or closing of a window.

## Files

- The Android application project is saved under 'TempGeckoAndroidApplication' and you will be able to open this in Android studio
- The Raspberry Pi code is saved under 'TempGeckoRaspberryPi' and this includes the JSON file to connect the Pi to the Firebase Firestore cloud database and the python script
- I have included an image of the logo and an image of the Cloud db as well
- I have used the public libary 'Motion' to be able to build a local web server on the Raspberry Pi so that I can livestream a USB webcam on its local IP. 
--URL for libary : https://www.instructables.com/How-to-Make-Raspberry-Pi-Webcam-Server-and-Stream-/

## Future goals

I would take it would be enabling a better data visualisation that allowed graphs like temp and humidity controls over a given timeframe. I could use this data to calculate the running cost on energy and find a coloration between the two. I would also like to bring this application to iOS and Web devices so that more people would be able to utilise this application.

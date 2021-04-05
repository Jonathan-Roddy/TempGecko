import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
from sense_hat import SenseHat
from picamera import PiCamera
import RPi.GPIO as GPIO
import time
import sys

# initialise SenseHat
sense = SenseHat()

# initialise Camera

#GPIO pin for MainLED
LedPin = 11
#GPIO pin for HeatMat
HeatMat = 12
#GPIO pin for Window
Window = 13

GPIO.setmode(GPIO.BOARD)# Numbers pins by physical location
GPIO.setup(LedPin,GPIO.OUT)
GPIO.setup(HeatMat,GPIO.OUT)
GPIO.setup(Window,GPIO.OUT)

GPIO.output(LedPin,GPIO.HIGH)
GPIO.output(HeatMat,GPIO.HIGH)
GPIO.output(Window,GPIO.HIGH)

# Connect to Firebase
cred=credentials.Certificate("tempgecko.json")
firebase_admin.initialize_app(cred)

db=firestore.client();
db_ref = db.collection(u'1').document(u'tKKVzC7Joswkyh8z12h6')

monitor = False

def toggle_led(doc_snapshot, changes, read_time):
    for doc in doc_snapshot:
        # MainLED
        MainLEDstatus = doc.to_dict()['MainLED']
        if MainLEDstatus is True:
            #print ("Turn on LED")
            GPIO.output(LedPin, GPIO.LOW)
        else:
            #print ("Turn off LED")
            GPIO.output(LedPin, GPIO.HIGH)
        
        #HeatMat
        HeatMatstatus = doc.to_dict()['HeatMat']
        if HeatMatstatus is True:
            #print ("Turn on HeatMat")
            GPIO.output(HeatMat, GPIO.LOW)
        else:
            #print ("Turn off HeatMat")
            GPIO.output(HeatMat, GPIO.HIGH)
            
        #Window
        Windowstatus = doc.to_dict()['Window']
        if Windowstatus is True:
            #print ("Open Window")
            GPIO.output(Window, GPIO.LOW)
        else:
            #print ("CLose Window")
            GPIO.output(Window, GPIO.HIGH)
          
status_ref=db.collection(u'1').document(u'tKKVzC7Joswkyh8z12h6')

status_watch = status_ref.on_snapshot(toggle_led)
   
def checkValuesAndUpdate():
    # Get Readings from SenseHat   
    temp = round(sense.get_temperature())
    humidity = round(sense.get_humidity())
    pressure = round(sense.get_pressure())
    
#     print('Temperature is %d C Humidity is %d percent Pressure is %d mbars' %(temp,humidity,pressure))

    # check again every 1.5 seconds
    time.sleep(1.5)
    
    #convert numbers to String so it can be parsed to Firebase -> Android Application
    stringTemp = str(temp)
    stringHumidity = str(humidity)
    stringPressure = str(pressure)

    # Set the capital field
    db_ref.update({u'Temperture': stringTemp})
    db_ref.update({u'humidity': stringHumidity})
    db_ref.update({u'pressure': stringPressure})

def startMonitor(Monitor):
    # Monitoring system is Active if this process is called
    
    db_ref.update({u'Monitor': Monitor})
        
def cleanUp():
    startMonitor(False)
    GPIO.output(LedPin,GPIO.HIGH)
    GPIO.output(HeatMat,GPIO.HIGH)
    GPIO.output(Window,GPIO.HIGH)
    GPIO.cleanup()
    sense.clear()
    
try:
    startMonitor(True)
    print('Monitor is Active')
    while True:
        checkValuesAndUpdate()       
        time.sleep(1)

except KeyboardInterrupt:
    print('Cleanup and Exit')
    cleanUp()
    sys.exit()
        
        
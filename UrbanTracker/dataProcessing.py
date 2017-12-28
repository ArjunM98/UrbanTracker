import serial
import time
import os
from pyowm import OWM

owm = OWM('83b21368c6d49f7c656484ed8177b3cc')

ser = serial.Serial('COM3',9600)
print(ser.name)

hour = int(time.strftime("%H"))

while True: 
    title= time.strftime("%Y")[2:]+"-"+time.strftime("%m")+"-"+time.strftime("%d")+"-"+str(hour)
    title = title.replace("0", "")    

    filepath = os.path.join('C:\\Users\looperS\Desktop\QHacks\SensorDataRepository', title)
    
    print(title)
    f= open(filepath,"w+")
    
    i=0
    while i<60:
        i+=1
        data = ser.readline()[:-2]  
        
        if i%2==0:
            obs = owm.weather_at_id(5992495)             
            w = obs.get_weather()
            weather=w.get_temperature ("celsius")['temp']
            line= str(data)[2:-1] + " " + str(weather)+ "\n"
        else: 
            line=str(data)[2:-1]+" "
        
        print(line)
        f.write(line)
            
    f.close()
    
    hour+=1




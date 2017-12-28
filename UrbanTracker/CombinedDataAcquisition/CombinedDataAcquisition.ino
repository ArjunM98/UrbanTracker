#include <CurieTime.h>

#include <math.h>

//Temperature Variables

const int B=4275;
const int R0 = 100000;
const int pinTempSensor = A0;  


//Light Variables
const int pinLightSensor = A3;
int lightIntensity = 0;



void setup()
{
    Serial.begin(9600);
    
}

void loop()
{
    //Light Conversion
    lightIntensity = analogRead(pinLightSensor);
   

    
    //Temperature Conversion
    int a = analogRead(pinTempSensor );
    float R = 1023.0/((float)a)-1.0;
    R = 100000.0*R;
    float temperature=1.0/(log(R/100000.0)/B+1/298.15)-303.15;
 
   //Printing Data
    if (lightIntensity>800) {
        Serial.println("ON");
    /*} else if(lightIntensity>500) {
        Serial.println("Half the lights are on"); */
    } else {
        Serial.println("OFF");
    }
    Serial.println(temperature);
    
    delay(1000);

}

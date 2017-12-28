void setup() {
    Serial.begin(9600);
}

void loop() {
    int counter = 0;
    bool appStatus = false; 
    for(int i=0;i<50;i++){
       int sensorValue = analogRead(A2);
       if(sensorValue==1023){
          counter+=1;
       }
       delay(100);
    }
    if(counter>1){
        appStatus=true;
    }
    if(appStatus){
      Serial.println("ON");
    }else{
      Serial.println("OFF");}

 delay(100);  
}

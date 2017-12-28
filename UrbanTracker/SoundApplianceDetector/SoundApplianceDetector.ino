const int pinAdc = A1;
float initSD=0.0;
int avg=0;

void setup()
{
    Serial.begin(9600);
     int valSum=0;
     int sum=0;
     for(int i=0;i<50;i++){
      int val = analogRead(pinAdc);
      valSum+=val;
      int x= abs(val-130);
      int xs=pow(x,2);
      sum+=xs;
      delay(100);
    }
   initSD=sqrt((float)sum/500.0);
   avg = valSum/50;
}

void loop()
{
    bool appStatus = false;
    int sum=0;  
    
    for(int i=0;i<50;i++){
      int val = analogRead(pinAdc);
      int x= abs(val-avg);
      int xs=pow(x,2);
      sum+=xs;
      delay(100);
    }

    float stand_dev=sqrt((float)sum/500.0);

    if(stand_dev>(initSD+10)){
        appStatus=true;
    }
    if(appStatus){
      Serial.println("ON");
    }else{
      Serial.println("OFF");}
    
      
      }


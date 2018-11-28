
#include <ESP8266WiFi.h>
#include <EEPROM.h>
#include<ESP8266mDNS.h>
#include <PubSubClient.h>

//capteur
int Status = 12;  // Digital pin D6
int sensor = 13;  // Digital pin D7

int calibrationTime     = 30;
int pirState            = LOW;
int val                 = 0;

void setup() {
  pinMode(sensor, INPUT);
  pinMode(Status, OUTPUT);
  Serial.begin(115200);

  Serial.print("Calibration du détecteur (30 secondes) ");
  for(int i = 0; i < calibrationTime; i++){
    Serial.print(".");
    delay(1000);
  }
}

void loop(){
  val = digitalRead(sensor);
  Serial.println(val);

  if (val == HIGH) {
    delay(150);

    if (pirState == LOW) {
      Serial.println("Présence détéctée");
      pirState = HIGH;
      digitalWrite (Status, HIGH);
    }
  } else {
    delay(300);
    if (pirState == HIGH){
      Serial.println("Aucune présence détéctée");
      digitalWrite (Status, LOW);
      pirState = LOW;
    }
  }
}

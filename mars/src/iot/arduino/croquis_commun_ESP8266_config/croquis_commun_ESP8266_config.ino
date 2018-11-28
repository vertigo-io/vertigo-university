#include <ESP8266WiFi.h>
#include <EEPROM.h>

//structure pour stocker les infos
struct EEconf {
  char ssid[32];
  char password[64];
  char myhostname[32];
};

void setup() {
  // déclaration et initialisation
  EEconf myconf= {
    "Kliot",
    "Turinglab",
    "raspibase"
  };
  //seconde variable pour la relecture
  EEconf readconf;
  Serial.begin(115200);
  //initialisation EEPROM;
  EEPROM.begin(sizeof(myconf));
  //Enregistrement
  EEPROM.put(0, myconf);
  EEPROM.commit();

  //Relecture et affichage
  EEPROM.get(0, readconf);
  Serial.println("\n\n\n");
  Serial.println(readconf.ssid);
  Serial.println(readconf.password);
  Serial.println(readconf.myhostname);
}

void loop() {
  // put your main code here, to run repeatedly:

}

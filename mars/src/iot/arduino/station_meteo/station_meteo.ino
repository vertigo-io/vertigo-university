
#include <ESP8266WiFi.h>
#include <EEPROM.h>
#include<ESP8266mDNS.h>
#include <PubSubClient.h>

//capteur
int sense_Pin = 0; // sensor input at Analog pin A0
int value = 0;
int WET= 16; // Wet Indicator at Digital pin D0
int DRY= 2;  // Dry Indicator at Digital pin D4
//nom de la machine avec le broker (mDNS)
const char* mqtt_server = "raspibase.local";
#define MQTT_PORT 1883
//structure pour stocker les infos
struct EEconf {
  char ssid[32];
  char password[64];
  char myhostname[32];
}readconf;

//objet pour établir la connexion
WiFiClient espClient;
PubSubClient client(espClient);

// intervalle entre message
long lastMsg = 0;
//valeur à envoyer
byte val=0;

void setup_wifi() {
  //mode station
  WiFi.mode(WIFI_STA);
  Serial.println();
  Serial.print("Connexion");
  Serial.println(readconf.ssid);
  //co wifi
  WiFi.begin(readconf.ssid, readconf.password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  //affichage
  Serial.println("");
  Serial.println("Connexion wifi ok");
  Serial.println("Adresse IP: ");
  Serial.println(WiFi.localIP());

  //config mDNS
  WiFi.hostname(readconf.myhostname);
  if (!MDNS.begin(readconf.myhostname)){
    Serial.println("Erreur configuration mDNS !");
  }else {
    Serial.println("répondeur mDNS démarré");
    Serial.println(readconf.myhostname);
  }
}

void reconnect() {
  //connecté au broker ?
  while (!client.connected()){
    //non. on se connecte
    if (!client.connect(readconf.myhostname,"sondes","sondes2018")) {
      Serial.print("Erreur connexion au serveur MQTT, rc=");
      Serial.println(client.state());
      delay(5000);
      continue;
    }
    Serial.println("Connexion serveur MQTT réussie");
    //on s'abonne au topic
    client.subscribe("e/esp/led");
  }
}
void setup() {
  //config led
  pinMode(LED_BUILTIN, OUTPUT);
  //config moniteur serie
  Serial.begin(115200);
  Serial.println();
  Serial.print("MAC: ");
  Serial.println(WiFi.macAddress());

  //config EEPROM
  EEPROM.begin(sizeof(readconf));
  //lecture config
  EEPROM.get(0, readconf);
  //config wifi
  setup_wifi();
  //cinfig broker
  client.setServer(mqtt_server,MQTT_PORT);
  client.setCallback(callback);
  pinMode(WET, OUTPUT);
  pinMode(DRY, OUTPUT);
  delay(2000);
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Message [");
  Serial.print(topic);
  Serial.print("]");
  //affichage payload
  for (int i=0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();
  //le caractère '1' est-il le premier du payload ?
  if((char)payload[0] =='1') {
    //led on
    digitalWrite(LED_BUILTIN, LOW);
  }else {
    //led off
    digitalWrite(LED_BUILTIN, HIGH);
  }
}
void loop() {

  //conversion val
  char msg[16];
  //topic
  char topic[64];
  //connectes ?
  if (!client.connected()) {
    reconnect();
  }
  //gestion MQTT
  client.loop();
  //tempo
  long now = millis();
  if (now - lastMsg > 5000) {
    //wait 5s
    lastMsg = now;
   Serial.print("MOISTURE LEVEL : ");
   value= analogRead(sense_Pin);
   val++;
   //construction message
   sprintf(msg, "%hu", map(value, 1024, 0, 0, 100));
   //construction topic
   sprintf(topic, "e/esp2/moisture", readconf.myhostname);
   //pub sur un topic
   client.publish(topic, msg);
   Serial.println(map(value, 1024, 0, 0, 100));
}
}

//ESP for current arduino model using ESP for wifi
#include <ESP8266WiFi.h>
//Private data
#include "moisturizerWifiSecrets.h"
//Usage of display
#include <LiquidCrystal_I2C.h>

/*IMPORTANT!!
  TO PROGRAM/LOAD DATA INTO ESP8266
  USE PINS ON:  5,6,7
           OFF: 1,2,3,4,8

  AFTER LOADING SWITCH TO
      PINS ON:  5,6
           OFF: 1,2,3,4,7,8
*/


/*sensors setup*/
// Replace 0x27 with the correct address if different
LiquidCrystal_I2C lcd(0x27, 16, 2);

const int moisturePin = A0; // Moisture sensor input pin
const int relayPin = 3;     // Relay control pin

int moistureValue = 0;      // Variable to store the moisture value
int moisturePercent = 0;    // Variable to store the moisture percentage
/*sensors setup end*/


/*firebase setup*/
#include <Arduino.h>
#if defined(ESP32)
  #include <WiFi.h>
#elif defined(ESP8266)
  #include <ESP8266WiFi.h>
#endif
#include <Firebase_ESP_Client.h>

//Provide the token generation process info.
#include "addons/TokenHelper.h"
//Provide the RTDB payload printing info and other helper functions.
#include "addons/RTDBHelper.h"

//Define Firebase Data object
FirebaseData fbdo;

FirebaseAuth auth;
FirebaseConfig config;

unsigned long sendDataPrevMillis = 0;
int count = 0;
bool signupOK = false;
bool isValveOpen = true;
/*firebase setup end*/

void setup(){
  Serial.begin(115200);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED){
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  /* Assign the api key (required) */
  config.api_key = API_KEY;

  /* Assign the RTDB URL (required) */
  config.database_url = DATABASE_URL;

  /* Sign up */
  if (Firebase.signUp(&config, &auth, "", "")){
    Serial.println("ok");
    signupOK = true;
  }
  else{
    Serial.printf("%s\n", config.signer.signupError.message.c_str());
  }

  /* Assign the callback function for the long running token generation task */
  config.token_status_callback = tokenStatusCallback; //see addons/TokenHelper.h
  
  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);

  // lcd light on
  lcd.init();
  lcd.backlight();
  // relay mode setup
  pinMode(relayPin, OUTPUT);
}

void loop(){
  // read values from sensor
  // the lower the value the wetter the sensor
  // 500 - 400 is dry
  // 300 > x is under water
  moistureValue = analogRead(moisturePin);
  // Map the moistureValue to a percentage using a more detailed mapping
  if (moistureValue <= 300) {
    moisturePercent = 100;
  } else if (moistureValue >= 500) {
    moisturePercent = 0;
  } else {
    // Calculate the percentage manually for more precision
    moisturePercent = 100 - ((moistureValue - 300) * 100 / 200);
  }

  // Display moisture percentage on the LCD
  lcd.setCursor(1, 0);
  lcd.print("Vlhkost: ");
  lcd.print(moisturePercent);
  lcd.print("%");
  delay(1500);
  lcd.clear();

  // Open valve if moistureValue is high (dry), close if lower
  if (moistureValue > 400) {
    digitalWrite(relayPin, HIGH);
    lcd.print("Opening valve");
    delay(1000);
    lcd.clear();
  } else {
    digitalWrite(relayPin, LOW);
  }

  if (Firebase.ready() && signupOK && (millis() - sendDataPrevMillis > 15000 || sendDataPrevMillis == 0)){
    sendDataPrevMillis = millis();
    // Write an Int number on the database path test/int
    if (Firebase.RTDB.setInt(&fbdo, "moisture/percentage", moisturePercent)){
      Serial.println("PASSED");
      Serial.println("PATH: " + fbdo.dataPath());
      Serial.println("TYPE: " + fbdo.dataType());
    }
    else {
      Serial.println("FAILED");
      Serial.println("REASON: " + fbdo.errorReason());
    }
    
    // Write an Float number on the database path test/float
    if (Firebase.RTDB.setBool(&fbdo, "moisture/isValveOpen", isValveOpen)){
      Serial.println("PASSED");
      Serial.println("PATH: " + fbdo.dataPath());
      Serial.println("TYPE: " + fbdo.dataType());
    }
    else {
      Serial.println("FAILED");
      Serial.println("REASON: " + fbdo.errorReason());
    }
  }
}

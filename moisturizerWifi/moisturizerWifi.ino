#include <ESP8266WiFi.h>
// Private data
#include "moisturizerWifiSecrets.h"
// Usage of display
#include <LiquidCrystal_I2C.h>

/* sensors setup */
LiquidCrystal_I2C lcd(0x27, 16, 2);

int moisturePercent = -1;   // Variable to store the moisture percentage

/* firebase setup */
#include <Arduino.h>
#include <Firebase_ESP_Client.h>

// Provide the token generation process info.
#include "addons/TokenHelper.h"
// Provide the RTDB payload printing info and other helper functions.
#include "addons/RTDBHelper.h"

// Define Firebase Data object
FirebaseData fbdo;

FirebaseAuth auth;
FirebaseConfig config;

unsigned long sendDataPrevMillis = 0;
bool signupOK = false;
bool isValveOpen = false;

void setup() {
  Serial.begin(115200);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting to Wi-Fi");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(300);
  }
  Serial.println();
  Serial.print("Connected with IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();

  /* Assign the API key (required) */
  config.api_key = API_KEY;

  /* Assign the RTDB URL (required) */
  config.database_url = DATABASE_URL;

  /* Sign up */
  if (Firebase.signUp(&config, &auth, "", "")) {
    Serial.println("ok");
    signupOK = true;
  } else {
    Serial.printf("%s\n", config.signer.signupError.message.c_str());
  }

  /* Assign the callback function for the long-running token generation task */
  config.token_status_callback = tokenStatusCallback; // see addons/TokenHelper.h
  
  Firebase.begin(&config, &auth);
  Firebase.reconnectWiFi(true);
}

void loop() {
  // Continuously check if data is available from Arduino
  while (Serial.available()) {
    String receivedData = Serial.readStringUntil('\n');  // Read the incoming data until newline

    // Convert the received data to an integer
    int moistureValue = receivedData.toInt();
    if(moistureValue >= 50){
      isValveOpen = false;
    } else {
      isValveOpen = true;
    }
    // Validate the received data: should be between 0 and 100
    if (moistureValue >= 0 && moistureValue <= 100) {
      moisturePercent = moistureValue;
      Serial.println("Valid moisture data received: " + String(moisturePercent));
    } else {
      Serial.println("Invalid data received, ignoring.");
      moisturePercent = -1;  // Set to an invalid value if out of range
    }
  }

  // Send data to Firebase if it's valid
  if (moisturePercent >= 0 && Firebase.ready() && signupOK && (millis() - sendDataPrevMillis > 15000 || sendDataPrevMillis == 0)) {
    sendDataPrevMillis = millis();
    // Write the moisture percentage to Firebase
    if (Firebase.RTDB.setInt(&fbdo, "moisture/percentage", moisturePercent)) {
      Serial.println("Data sent to Firebase successfully");
    } else {
      Serial.println("Failed to send data to Firebase");
      Serial.println("Reason: " + fbdo.errorReason());
    }
    
    // Update the valve state in Firebase
    if (Firebase.RTDB.setBool(&fbdo, "moisture/isValveOpen", isValveOpen)) {
      Serial.println("Valve state updated in Firebase");
    } else {
      Serial.println("Failed to update valve state in Firebase");
      Serial.println("Reason: " + fbdo.errorReason());
    }
  }
}

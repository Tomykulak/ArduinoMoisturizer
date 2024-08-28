#include <Wire.h>
#include <LiquidCrystal_I2C.h>

// Replace 0x27 with the correct address if different
LiquidCrystal_I2C lcd(0x27, 16, 2);

const int moisturePin = A0; // Moisture sensor input pin
const int relayPin = 3;     // Relay control pin

int moistureValue = 0;      // Variable to store the moisture value
int moisturePercent = 0;    // Variable to store the moisture percentage

int dry = 450;
int wet = 300;

int getMoisturePercentage(int moistureValue, int wet, int dry) {
    int moisturePercent;

    if (moistureValue >= dry) {
        moisturePercent = 0;  // Completely dry
    } else if (moistureValue <= wet) {
        moisturePercent = 100;  // Completely wet
    } else {
        moisturePercent = map(moistureValue, wet, dry, 100, 0);
    }
    return moisturePercent;
}

void displayData(int moisturePercent){
  // Display moisture percentage on the LCD
  if(moisturePercent != -1){
    lcd.setCursor(1, 0);
    lcd.print("Moisture: ");
    lcd.print(moisturePercent);
    lcd.print("%");
    delay(1500);
    lcd.clear();

    // Open valve if moisturePercent is below 50%
    if (moisturePercent < 50) {
      digitalWrite(relayPin, HIGH);  // Open the valve
      lcd.print("Opening valve");
    } else {
      digitalWrite(relayPin, LOW);   // Close the valve
      lcd.print("Valve closed");
    }
    delay(1000);
    lcd.clear();
    } else {
      lcd.setCursor(1, 0);
      lcd.print("BAD DATA ERROR");
    }
    // add delay before removing message
    delay(1500);
    lcd.clear();
}

void setup() {
  Serial.begin(115200); // Start Serial communication with ESP8266
  // lcd light on
  lcd.init();
  lcd.backlight();

  // relay mode setup
  pinMode(relayPin, OUTPUT);
}


void loop() {
  moistureValue = analogRead(moisturePin);

  // Check if sensor gets data
  if (moistureValue < 0) {
    // Handle the error value
    Serial.println("Error: Failed to read from moisture sensor");
    moisturePercent = -1; // Assign a default error value
  } else {
    // Map moistureValue to a percentage
    moisturePercent = getMoisturePercentage(moistureValue, wet, dry);
    displayData(moisturePercent);
  }

  // Send moisture percentage to ESP8266
  Serial.println(moisturePercent);
  delay(1000);
}

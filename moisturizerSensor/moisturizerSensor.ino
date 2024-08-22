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

void setup() {
  Serial.begin(115200); // Start Serial communication with ESP8266
  // lcd light on
  lcd.init();
  lcd.backlight();

  // relay mode setup
  pinMode(relayPin, OUTPUT);
}

void loop() {
  // Read values from the sensor
  moistureValue = analogRead(moisturePin);

  // Map moistureValue to a percentage where:
  if (moistureValue >= dry) {
    moisturePercent = 0;  // Completely dry
  } else if (moistureValue <= wet) {
    moisturePercent = 100;  // Completely wet
  } else {
    moisturePercent = map(moistureValue, wet, dry, 100, 0);
  }

  // Display moisture percentage on the LCD
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

  // Send moisture percentage to ESP8266
  Serial.println(moisturePercent); // Send data to ESP8266
  delay(1000);  // Send data every second
}

#include <Wire.h>
#include <LiquidCrystal_I2C.h>

// Replace 0x27 with the correct address if different
LiquidCrystal_I2C lcd(0x27, 16, 2);

const int moisturePin = A0; // Moisture sensor input pin
const int relayPin = 3;     // Relay control pin

int moistureValue = 0;      // Variable to store the moisture value
int moisturePercent = 0;    // Variable to store the moisture percentage

void setup() {
  // lcd light on
  lcd.init();
  lcd.backlight();

  // relay mode setup
  pinMode(relayPin, OUTPUT);
}

void loop() {
  // read values from sensor
  // the lower the value the wetter the sensor
  // 500 - 400 is dry
  // 300 > x is under water
  moistureValue = analogRead(moisturePin);
  // lcd display printing values
  lcd.setCursor(1, 0);
  lcd.print("Vlhkost: ");
  lcd.print(moistureValue);
  delay(1500);
  lcd.clear();

  // open valve if moistureValue is high(dry), if lower close off valve
  if (moistureValue > 400){
    digitalWrite(relayPin, HIGH);
    lcd.print("Openning valve");
    delay(1000);
    lcd.clear();
  } else {
    digitalWrite(relayPin, LOW);
  }
}

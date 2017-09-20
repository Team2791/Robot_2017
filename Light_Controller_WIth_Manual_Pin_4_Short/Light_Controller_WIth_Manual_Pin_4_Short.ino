#include <Adafruit_NeoPixel.h>

//r6 close, r5 middle, 4 is down
/*
  @author Noah Page
  Operate 2 Adafruit NeoPixels for Team 2791's RGB light strips
*/
//4 High is red
//4 Low is blue

int PixelNum = 59;
int pin = 11;
int pin2 = 6;
int x = 0;
int y = 0;
int z = 0;
int p = 0;
int shootingCounter = 0;
bool AutonBool = false;
bool AutonBool2 = false;
bool TeleBool = false;
bool AutonBool3 = false;
bool GearBool = true;
Adafruit_NeoPixel stripA = Adafruit_NeoPixel(PixelNum, pin, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel stripB = Adafruit_NeoPixel(PixelNum, pin2, NEO_GRB + NEO_KHZ800);
void setup() {
  stripA.begin();
  stripB.begin();
  stripA.show();
  stripB.show();
  pinMode(4, INPUT);
  pinMode(8, INPUT);
  pinMode(13, INPUT);
}

void loop() {
  if (digitalRead(4) == HIGH && digitalRead(8) == HIGH && digitalRead(13) == HIGH) { //Climbing or Fuel Pick Up Green Lightning SHOULD BE GEAR DONE
    if (GearBool == true) {
      for (int x = 0; x <= PixelNum; x++) {
        stripA.setPixelColor(x, 255, 50, 0);
        stripB.setPixelColor(x, 255, 50, 0);
      }
      stripA.show();
      stripB.show();
      delay(200);
      GearBool = false;
    }
    else if (GearBool == false) {
      for (int x = 0; x <= PixelNum; x++) {
        stripA.setPixelColor(x, 0, 0, 0);
        stripB.setPixelColor(x, 0, 0, 0);
      }
      stripA.show();
      stripB.show();
      delay(200);
      GearBool = true;
    }
  }
  else if (digitalRead(4) == LOW && digitalRead(13) == HIGH && digitalRead(8) == HIGH) { //Gear Intake Down SWITCHED WITH AUTO RED SIDE SHOULD ALSO BE GEAR DONE
    if (GearBool == true) {
      for (int x = 0; x <= PixelNum; x++) {
        stripA.setPixelColor(x, 255, 50, 0);
        stripB.setPixelColor(x, 255, 50, 0);
      }
      stripA.show();
      stripB.show();
      delay(200);
      GearBool = false;
    }
    else if (GearBool == false) {
      for (int x = 0; x <= PixelNum; x++) {
        stripA.setPixelColor(x, 0, 0, 0);
        stripB.setPixelColor(x, 0, 0, 0);
      }
      stripA.show();
      stripB.show();
      delay(200);
      GearBool = true;
    }
  }
  else if (digitalRead(4) == HIGH && digitalRead(8) == HIGH && digitalRead(13) == LOW) { //Shooting YELLOW SETS OF 3 LEDS MOVING ACROSS FAST SHOULD BE RED TELE DONE
    if (AutonBool2 == false) {
      if (AutonBool == false) {
        for (int x = 0; x <= PixelNum; x++) {
          stripA.setPixelColor(x, 255, 0, 0);
          stripB.setPixelColor(x, 255, 0, 0);
          stripA.show();
          stripB.show();
          delay(25);
        }
      }
      if (AutonBool == true) {
        for (int x = PixelNum; x >= 0; x--) {
          stripA.setPixelColor(x, 0, 0, 0);
          stripB.setPixelColor(x, 0, 0, 0);
          stripA.show();
          stripB.show();
          delay(25);
        }
      }
    }
    if (AutonBool2 == true) {
      if (AutonBool == false) {
        for (int x = 0; x <= PixelNum; x++) {
          stripA.setPixelColor(x, 255, 255, 255);
          stripB.setPixelColor(x, 255, 255, 255);
          stripA.show();
          stripB.show();
          delay(25);
        }
      }
      if (AutonBool == true) {
        for (int x = PixelNum; x >= 0; x--) {
          stripA.setPixelColor(x, 0, 0, 0);
          stripB.setPixelColor(x, 0, 0, 0);
          stripA.show();
          stripB.show();
          delay(25);
        }
      }
    }
    if (AutonBool == false) {
      AutonBool = true;
    }
    else if (AutonBool == true) {
      AutonBool = false;
      AutonBool3 = true;
    }
    if (AutonBool3 == true) {
      if (AutonBool2 == false) {
        AutonBool2 = true;
      }
      else if (AutonBool2 == true) {
        AutonBool2 = false;
      }
      AutonBool3 = false;
    }
  }

  else if (digitalRead(4) == HIGH && digitalRead(8) == LOW && digitalRead(13) == LOW) { //Auto blue side BLUE AND WHITE ALTERNATING STROBE SHOULD BE DISABLED DONE
    rainbowCycle(20);
  }
  else if (digitalRead(4) == HIGH && digitalRead(8) == LOW && digitalRead(13) == HIGH) { //Auto red side WHITE AND RED ALTERNATING STROBE SWITCHED WITH GEAR SHOULD BE RED AUTO DONE
    if (TeleBool == false) {
      for (int x = 0; x <= PixelNum; x++) {
        if (x % 2 == 0) {
          stripA.setPixelColor(x, 255, 0, 0);
          stripB.setPixelColor(x, 255, 0, 0);
        }
        else {
          stripA.setPixelColor(x, 255, 255, 255);
          stripB.setPixelColor(x, 255, 255, 255);
        }
      }
      TeleBool = true;
      stripA.show();
      stripB.show();
      delay(200);
    }
    if (TeleBool == true) {
      for (int x = 0; x <= PixelNum; x++) {
        if (x % 2 != 0) {
          stripA.setPixelColor(x, 255, 0, 0);
          stripB.setPixelColor(x, 255, 0, 0);
        }
        else {
          stripA.setPixelColor(x, 255, 255, 255);
          stripB.setPixelColor(x, 255, 255, 255);
        }
      }
      TeleBool = false;
      stripA.show();
      stripB.show();
      delay(200);
    }
  }
  else if (digitalRead(4) == LOW && digitalRead(8) == HIGH && digitalRead(13) == LOW) { //Teleop blue side PULSING BLUE AND THEN PULSING WHITE //SHOULD BE BLUE TELE DONE
    if (AutonBool2 == false) {
      if (AutonBool == false) {
        for (int x = 0; x <= PixelNum; x++) {
          stripA.setPixelColor(x, 0, 0, 255);
          stripB.setPixelColor(x, 0, 0, 255);
          stripA.show();
          stripB.show();
          delay(25);
        }
      }
      if (AutonBool == true) {
        for (int x = PixelNum; x >= 0; x--) {
          stripA.setPixelColor(x, 0, 0, 0);
          stripB.setPixelColor(x, 0, 0, 0);
          stripA.show();
          stripB.show();
          delay(25);
        }
      }
    }
    if (AutonBool2 == true) {
      if (AutonBool == false) {
        for (int x = 0; x <= PixelNum; x++) {
          stripA.setPixelColor(x, 255, 255, 255);
          stripB.setPixelColor(x, 255, 255, 255);
          stripA.show();
          stripB.show();
          delay(25);
        }
      }
      if (AutonBool == true) {
        for (int x = PixelNum; x >= 0; x--) {
          stripA.setPixelColor(x, 0, 0, 0);
          stripB.setPixelColor(x, 0, 0, 0);
          stripA.show();
          stripB.show();
          delay(25);
        }
      }
    }
    if (AutonBool == false) {
      AutonBool = true;
    }
    else if (AutonBool == true) {
      AutonBool = false;
      AutonBool3 = true;
    }
    if (AutonBool3 == true) {
      if (AutonBool2 == false) {
        AutonBool2 = true;
      }
      else if (AutonBool2 == true) {
        AutonBool2 = false;
      }
      AutonBool3 = false;
    }
  }
  else if (digitalRead(4) == LOW && digitalRead(8) == LOW && digitalRead(13) == HIGH) { //Teleop red side PULSING RED AND THEN PULSING WHITE SHOULD BE BLUE AUTO DONE
    if (TeleBool == false) {
      for (int x = 0; x <= PixelNum; x++) {
        if (x % 2 == 0) {
          stripA.setPixelColor(x, 0, 0, 255);
          stripB.setPixelColor(x, 0, 0, 255);
        }
        else {
          stripA.setPixelColor(x, 255, 255, 255);
          stripB.setPixelColor(x, 255, 255, 255);
        }
      }
      TeleBool = true;
      stripA.show();
      stripB.show();
      delay(200);
    }
    if (TeleBool == true) {
      for (int x = 0; x <= PixelNum; x++) {
        if (x % 2 != 0) {
          stripA.setPixelColor(x, 0, 0, 255);
          stripB.setPixelColor(x, 0, 0, 255);
        }
        else {
          stripA.setPixelColor(x, 255, 255, 255);
          stripB.setPixelColor(x, 255, 255, 255);
        }
      }
      TeleBool = false;
      stripA.show();
      stripB.show();
      delay(200);
    }
  }

  else { //DIABLED
    rainbowCycle(20);
  }
}
uint32_t Wheel(byte WheelPos) { //Taken from Adafruit's example code
  WheelPos = 255 - WheelPos;
  if (WheelPos < 85) {
    return stripA.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if (WheelPos < 170) {
    WheelPos -= 85;
    return stripA.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return stripA.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}

void rainbowCycle(uint8_t wait) { //Taken from Adafruit's example code
  uint16_t i, j;
  for (j = 0; j < 256 * 5; j++) { // 5 cycles of all colors on wheel
    for (i = 0; i < stripA.numPixels(); i++) {
      stripA.setPixelColor(i, Wheel(((i * 256 / stripA.numPixels()) + j) & 255));
      stripB.setPixelColor(i, Wheel(((i * 256 / stripA.numPixels()) + j) & 255));
      if (digitalRead(8) == HIGH || digitalRead(13) == HIGH) {
        break;
      }
    }
    if (digitalRead(8) == HIGH || digitalRead(13) == HIGH) {
      break;
    }
    stripA.show();
    stripB.show();
    delay(wait);
  }
}

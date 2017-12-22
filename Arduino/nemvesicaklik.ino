#include<LiquidCrystal.h>
#include <dht.h>

#define dht_dpin 2 

LiquidCrystal lcd(8, 7, 6, 5, 4, 3);
dht DHT;
float h = 0;
float t = 0 ;
 
void setup(){
  Serial.begin(9600);
  lcd.begin(16,2);
  pinMode(13, OUTPUT);
  pinMode(12, OUTPUT);
  delay(300);
  //Serial.println("Nem ve Sıcaklık\n\n");
  delay(700);
}
 


void loop(){
  t = DHT.temperature;
  h = DHT.humidity;
 delay(800);
  DHT.read11(dht_dpin);


  lcd.home();
  lcd.print("Nem(%)=");
  lcd.print(h);
  lcd.setCursor(0,1);
  lcd.println("Sicaklik=");
  lcd.print(t);

  
    Serial.print("Nem(%)= ");
    Serial.println(h);
    //Serial.println("%  ");
    Serial.print("Sicaklik= ");
    Serial.print(t); 
    Serial.println("C  ");

    //Serial.print("\n");

    if (t<25)
      digitalWrite(13, HIGH);
    else if(t>=25 && t<=28)
       digitalWrite(13, LOW);
  else{
       digitalWrite(13, LOW);
      delay(1000);
      digitalWrite(13, HIGH);
      }

     if (h<40)
      digitalWrite(12, HIGH);
    else if(h>=40 && h<=50)
       digitalWrite(12, LOW);
  else{
       digitalWrite(12, LOW);
      delay(1000);
      digitalWrite(12, HIGH);
      }
}







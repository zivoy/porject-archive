#include <ESP8266WiFi.h>
#include <WiFiClientSecure.h>
#include <ESP8266WebServer.h>
#include <ESP8266HTTPClient.h>

#include <ArduinoJson.h>

const char *ssid = "Home internet";
const char *password = "password";

const char *host = "counter.shalit.name";
const int httpsPort = 443;
const char fingerprint[] PROGMEM = "85 E3 73 B7 E5 9E 3E 84 4D F1 ED 44 33 6B 78 DF 4B E8 6B 5F";


int contactFunction(String name, int change = 0){
        WiFiClientSecure httpsClient; //Declare object of class WiFiClient

        Serial.println(host);

        Serial.printf("Using fingerprint '%s'\n", fingerprint);
        httpsClient.setFingerprint(fingerprint);
        httpsClient.setTimeout(15000); // 15 Seconds
        // delay(1000);

        Serial.print("HTTPS Connecting");
        int r=0; //retry counter
        while((!httpsClient.connect(host, httpsPort)) && (r < 30)) {
                delay(100);
                Serial.print(".");
                r++;
        }
        if(r==30) {
                Serial.println("Connection failed");
                return -1;
        }
        Serial.println("Connected to web");


        String getData, Link;

        //POST Data
        Link = "/api";

        Serial.print("requesting URL: ");
        Serial.println(host);

        String PostData = "id="+name+"&change="+String(change);

        httpsClient.println("POST "+String(Link)+" HTTP/1.1");
        httpsClient.println("Host: "+String(host));
        httpsClient.println("Cache-Control: no-cache");
        httpsClient.println("Content-Type: application/x-www-form-urlencoded");
        httpsClient.println("Connection: close");
        httpsClient.print("Content-Length: ");
        httpsClient.println(PostData.length());
        httpsClient.println();
        httpsClient.println(PostData);


        Serial.println("request sent");

        while (httpsClient.connected()) {
                String line = httpsClient.readStringUntil('\n');
                if (line == "\r") {
                        Serial.println("headers received");
                        break;
                }
        }

        Serial.println("reply was:");
        String line;
        while(httpsClient.available()) {
                line = httpsClient.readStringUntil('\n'); //Read Line by Line
                Serial.println(line); //Print response
        }
        DynamicJsonDocument doc(1024);
        deserializeJson(doc, line);
        return max(int(doc["count"]),0);
}

void (* resetFunc) (void) = 0; //declare reset function @ address 0


void setup() {
        delay(1000);
        Serial.begin(115200);
        WiFi.mode(WIFI_OFF);  //Prevents reconnection issue (taking too long to connect)
        delay(1000);
        WiFi.mode(WIFI_STA);  //Only Station No AP, This line hides the viewing of ESP as wifi hotspot

        WiFi.begin(ssid, password); //Connect to your WiFi router
        Serial.println("");

        Serial.print("Connecting");
        // Wait for connection
        while (WiFi.status() != WL_CONNECTED) {
                delay(500);
                Serial.print(".");
        }

        //If connection successful show IP address in serial monitor
        Serial.println("");
        Serial.print("Connected to ");
        Serial.println(ssid);
        Serial.print("IP address: ");
        Serial.println(WiFi.localIP()); //IP address assigned to your ESP
}

void loop() {
        int returnVal;
        returnVal= contactFunction("swiming pool 1");
        if (returnVal==-1) {
                resetFunc();
        }
        Serial.print("Counter value: ");
        Serial.println(returnVal);
        delay(10000); //POST Data at every 2 seconds
}
//=======================================================================

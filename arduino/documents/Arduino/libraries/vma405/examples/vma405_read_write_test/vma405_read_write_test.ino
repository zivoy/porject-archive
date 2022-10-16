/*
 * File Name : RFID.pde
 * Creator : Dr.Leong (WWW.B2CQSHOP.COM)
 * Create Date: 2011.09.19
 * Modified by :
 * Modify Date:
 * Chinese comments translated using Google Translate by
 * Description : Mifare1 -> find cards - > Anti-collision - > Select Card - > reader interface
 */
// the sensor communicates using SPI, so include the library:
#include <SPI.h>
#include <RFID.h>

#define uchar unsigned char

/////////////////////////////////////////////////////////////////////
//set the pin
/////////////////////////////////////////////////////////////////////
#define CS_PIN 10
#define RST_PIN 5

RFID rfid(CS_PIN, RST_PIN);

//4 bytes card serial number , the first 5 bytes for the checksum byte
// uchar serNum[5];

uchar writeData[16] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100}; // Initialize 100 dollars
uchar moneyConsume = 18;                                                  // spending 18 yuan
uchar moneyAdd = 10;                                                      // recharge 10 yuan
                                                                          // Sector A password , 16 sectors , each sector password 6Byte
uchar sectorKeyA[16][16] = {
    {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF},
    {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF},
    //{0x19, 0x84, 0x07, 0x15, 0x76, 0x14},
    {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF},
};
uchar sectorNewKeyA[16][16] = {
    {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF},
    {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xff, 0x07, 0x80, 0x69, 0x19, 0x84, 0x07, 0x15, 0x76, 0x14},
    //you can set another ket , such as  " 0x19, 0x84, 0x07, 0x15, 0x76, 0x14 "
    //{0x19, 0x84, 0x07, 0x15, 0x76, 0x14, 0xff,0x07,0x80,0x69, 0x19,0x84,0x07,0x15,0x76,0x14},
    // but when loop, please set the  sectorKeyA, the same key, so that RFID module can read the card
    {0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xff, 0x07, 0x80, 0x69, 0x19, 0x33, 0x07, 0x15, 0x34, 0x14},
};

void setup()
{
  Serial.begin(9600); // RFID reader SOUT pin connected to Serial RX pin at 2400bps

  // start the SPI library:
  SPI.begin();

  rfid.init();

  Serial.println("Ready");
}

void loop()
{
  uchar i, tmp;
  uchar status;
  uchar str[MAX_LEN];
  uchar RC_size;
  uchar blockAddr; // select the operating block addresses 0 to 63

  // Find the card , back card type
  // status = rfid.MFRC522Request(PICC_REQIDL, str);
  if (rfid.isCard())
  {
    Serial.println("Find out a card ");
    Serial.print(rfid.lCard[0], BIN);
    Serial.print(" , ");
    Serial.print(rfid.lCard[1], BIN);
    Serial.println(" ");
  }

  if (rfid.readCardSerial())
  {
    Serial.println("The card's number is  : ");
    Serial.print(rfid.serNum[0], BIN);
    Serial.print(" , ");
    Serial.print(rfid.serNum[1], BIN);
    Serial.print(" , ");
    Serial.print(rfid.serNum[2], BIN);
    Serial.print(" , ");
    Serial.print(rfid.serNum[3], BIN);
    Serial.print(" , ");
    Serial.print(rfid.serNum[4], BIN);
    Serial.println(" ");
  }

  // Election card , return to card capacity
  RC_size = rfid.selectTag(rfid.serNum);
  if (RC_size != 0)
  {
    Serial.print("The size of the card is  :   ");
    Serial.print(RC_size, DEC);
    Serial.println(" K bits ");
  }
  /*
  // Registration Card
  blockAddr = 7;                                                                       // data block 7
  status = rfid.auth(PICC_AUTHENT1A, blockAddr, sectorKeyA[blockAddr / 4], rfid.serNum); // Certification
  if (status == MI_OK)
  {
    // Write data
    status = rfid.write(blockAddr, sectorNewKeyA[blockAddr / 4]);
    Serial.print("set the new card password, and can modify the data of the Sector ");
    Serial.print(blockAddr / 4, DEC);
    Serial.println(" : ");
    for (i = 0; i < 6; i++)
    {
      Serial.print(sectorNewKeyA[blockAddr / 4][i], HEX);
      Serial.print(" , ");
    }
    Serial.println(" ");
    blockAddr = blockAddr - 3;
    status = rfid.write(blockAddr, writeData);
    if (status == MI_OK)
    {
      Serial.println("You are B2CQSHOP VIP Member, The card has  $100 !");
    }
  }
  

  // Reader
  blockAddr = 7;                                                                          // data block 7
  status = rfid.auth(PICC_AUTHENT1A, blockAddr, sectorNewKeyA[blockAddr / 4], rfid.serNum); // Certification
  if (status == MI_OK)
  {
    // Read data
    blockAddr = blockAddr - 3;
    status = rfid.read(blockAddr, str);
    if (status == MI_OK)
    {
      Serial.println("Read from the card ,the data is : ");
      for (i = 0; i < 16; i++)
      {
        Serial.print(str[i], DEC);
        Serial.print(" , ");
      }
      Serial.println(" ");
    }
  }

  //  Spending
  blockAddr = 7;                                                                          // data block 7
  status = rfid.auth(PICC_AUTHENT1A, blockAddr, sectorNewKeyA[blockAddr / 4], rfid.serNum); // Certification
  if (status == MI_OK)
  {
    // Read data
    blockAddr = blockAddr - 3;
    status = rfid.read(blockAddr, str);
    if (status == MI_OK)
    {
      if (str[15] < moneyConsume)
      {
        Serial.println(" The money is not enough !");
      }
      else
      {
        str[15] = str[15] - moneyConsume;
        status = rfid.write(blockAddr, str);
        if (status == MI_OK)
        {
          Serial.print("You pay $18 for items in B2CQSHOP.COM . Now, Your money balance is :   $");
          Serial.print(str[15], DEC);
          Serial.println(" ");
        }
      }
    }
  }

  // Recharge
  blockAddr = 7;                                                                          // data block 7
  status = rfid.auth(PICC_AUTHENT1A, blockAddr, sectorNewKeyA[blockAddr / 4], rfid.serNum); // Certification
  if (status == MI_OK)
  {
    // Read data
    blockAddr = blockAddr - 3;
    status = rfid.read(blockAddr, str);
    if (status == MI_OK)
    {
      tmp = (int)(str[15] + moneyAdd);
      //Serial.println(tmp,DEC);
      if (tmp < (char)254)
      {
        Serial.println(" The money of the card can not be more than 255 !");
      }
      else
      {
        str[15] = str[15] + moneyAdd;
        status = rfid.write(blockAddr, str);
        if (status == MI_OK)
        {
          Serial.print("You add $10 to your card in B2CQSHOP.COM , Your money balance is :  $");
          Serial.print(str[15], DEC);
          Serial.println(" ");
        }
      }
    }
  }
  */
  // Serial.println(" ");
  rfid.halt(); // command card into hibernation
}

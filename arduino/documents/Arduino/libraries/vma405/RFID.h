/* RFID.h - Library to use ARDUINO RFID MODULE KIT 13.56 MHZ WITH TAGS SPI W AND R BY COOQROBOT.
 * Based on code Dr.Leong   ( WWW.B2CQSHOP.COM )
 * Created by Miguel Balboa (circuitito.com), Jan, 2012. 
 * Translated by zivoy 2021
 */
#ifndef RFID_h
#define RFID_h

#include <Arduino.h>
#include <SPI.h>



/******************************************************************************
 * Definitions
 ******************************************************************************/
#define MAX_LEN 16   // Maximum length of matrix ?list?

//MF522 command word
#define PCD_IDLE              0x00               // NO action; and cancel the command
#define PCD_AUTHENT           0x0E               // key authentication
#define PCD_RECEIVE           0x08               // data reception
#define PCD_TRANSMIT          0x04               // Send data
#define PCD_TRANSCEIVE        0x0C               // Send and receive data
#define PCD_RESETPHASE        0x0F               // reset
#define PCD_CALCCRC           0x03               // CRC calculate

//Mifare_One Mifare_One Card Mifare_One Command Word
#define PICC_REQIDL           0x26               // Antenna area is not trying to enter the idle state
#define PICC_REQALL           0x52               // All cards to find the antenna area
#define PICC_ANTICOLL         0x93               // anti-collision
#define PICC_SElECTTAG        0x93               // card selection
#define PICC_AUTHENT1A        0x60               // verification key A
#define PICC_AUTHENT1B        0x61               // verification key B
#define PICC_READ             0x30               // read block
#define PICC_WRITE            0xA0               // Write in the block 
#define PICC_DECREMENT        0xC0               // charge
#define PICC_INCREMENT        0xC1               // reload
#define PICC_RESTORE          0xC2               // Buffer block data transfer
#define PICC_TRANSFER         0xB0               // Save the data in the buffer
#define PICC_HALT             0x50               // inactivity

//MF522 Communication error code when it returned
#define MI_OK                 0
#define MI_NOTAGERR           1
#define MI_ERR                2

//------------------ MFRC522 registration---------------
//Page 0:Command and Status
#define     Reserved00            0x00    
#define     CommandReg            0x01    
#define     CommIEnReg            0x02    
#define     DivlEnReg             0x03    
#define     CommIrqReg            0x04    
#define     DivIrqReg             0x05
#define     ErrorReg              0x06    
#define     Status1Reg            0x07    
#define     Status2Reg            0x08    
#define     FIFODataReg           0x09
#define     FIFOLevelReg          0x0A
#define     WaterLevelReg         0x0B
#define     ControlReg            0x0C
#define     BitFramingReg         0x0D
#define     CollReg               0x0E
#define     Reserved01            0x0F
//Page 1:Command     
#define     Reserved10            0x10
#define     ModeReg               0x11
#define     TxModeReg             0x12
#define     RxModeReg             0x13
#define     TxControlReg          0x14
#define     TxAutoReg             0x15
#define     TxSelReg              0x16
#define     RxSelReg              0x17
#define     RxThresholdReg        0x18
#define     DemodReg              0x19
#define     Reserved11            0x1A
#define     Reserved12            0x1B
#define     MifareReg             0x1C
#define     Reserved13            0x1D
#define     Reserved14            0x1E
#define     SerialSpeedReg        0x1F
//Page 2:CFG    
#define     Reserved20            0x20  
#define     CRCResultRegM         0x21
#define     CRCResultRegL         0x22
#define     Reserved21            0x23
#define     ModWidthReg           0x24
#define     Reserved22            0x25
#define     RFCfgReg              0x26
#define     GsNReg                0x27
#define     CWGsPReg	          0x28
#define     ModGsPReg             0x29
#define     TModeReg              0x2A
#define     TPrescalerReg         0x2B
#define     TReloadRegH           0x2C
#define     TReloadRegL           0x2D
#define     TCounterValueRegH     0x2E
#define     TCounterValueRegL     0x2F
//Page 3:TestRegister     
#define     Reserved30            0x30
#define     TestSel1Reg           0x31
#define     TestSel2Reg           0x32
#define     TestPinEnReg          0x33
#define     TestPinValueReg       0x34
#define     TestBusReg            0x35
#define     AutoTestReg           0x36
#define     VersionReg            0x37
#define     AnalogTestReg         0x38
#define     TestDAC1Reg           0x39  
#define     TestDAC2Reg           0x3A   
#define     TestADCReg            0x3B   
#define     Reserved31            0x3C   
#define     Reserved32            0x3D   
#define     Reserved33            0x3E   
#define     Reserved34			  0x3F
//-----------------------------------------------

class RFID
{
  public:
    RFID(int chipSelectPin, int NRSTPD);
	
	bool isCard();
	bool readCardSerial();
	
    void init();
	void reset();
	void antennaOn(void);
	void antennaOff(void);
	void writeByte(unsigned char addr, unsigned char val);
	unsigned char readByte(unsigned char addr);
	void setBitMask(unsigned char reg, unsigned char mask);
	void clearBitMask(unsigned char reg, unsigned char mask);
	void calculateCRC(unsigned char *pIndata, unsigned char len, unsigned char *pOutData);
	unsigned char MFRC522Request(unsigned char reqMode, unsigned char *TagType);
	unsigned char MFRC522ToCard(unsigned char command, unsigned char *sendData, unsigned char sendLen, unsigned char *backData, unsigned int *backLen);
	unsigned char anticoll(unsigned char *serNum);
	unsigned char auth(unsigned char authMode, unsigned char BlockAddr, unsigned char *Sectorkey, unsigned char *serNum);
	unsigned char selectTag(unsigned char *serNum);
	unsigned char read(unsigned char blockAddr, unsigned char *recvData);
	unsigned char write(unsigned char blockAddr, unsigned char *writeData);
	void halt();
	
	unsigned char serNum[5];       // Constant to store the serial number read.
	unsigned char AserNum[5];      // Constant to store the serial number of the current section.
	
	unsigned char lCard[MAX_LEN];  // Constant to store the card type 

  private:
    int _chipSelectPin;
	int _NRSTPD;
	
};

#endif
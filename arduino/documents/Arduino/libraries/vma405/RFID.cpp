/*
 * RFID.cpp - Library to use ARDUINO RFID MODULE KIT 13.56 MHZ WITH TAGS SPI W AND R BY COOQROBOT.
 * Based on code Dr.Leong   ( WWW.B2CQSHOP.COM )
 * Created by Miguel Balboa, Jan, 2012. 
 * Released into the public domain.
 * translated by zivoy 2021
 */

/******************************************************************************
 * Includes
 ******************************************************************************/
#include <Arduino.h>
#include <RFID.h>

/******************************************************************************
 * User API
 ******************************************************************************/

/**
 * Construct RFID
 * int chipSelectPin RFID /ENABLE pin
 */
RFID::RFID(int chipSelectPin, int NRSTPD)
{
    _chipSelectPin = chipSelectPin;

    pinMode(_chipSelectPin, OUTPUT);   // Set digital as OUTPUT to connect it to the RFID /ENABLE pin
    digitalWrite(_chipSelectPin, LOW); // Activate the RFID reader

    pinMode(NRSTPD, OUTPUT); // Set digital pin, Not Reset and Power-down
    digitalWrite(NRSTPD, HIGH);
    _NRSTPD = NRSTPD;
}
/******************************************************************************
 * User API
 ******************************************************************************/

bool RFID::isCard()
{
    unsigned char status;
    unsigned char str[MAX_LEN];

    status = MFRC522Request(PICC_REQIDL, str);
    memcpy(lCard, str, MAX_LEN);

    if (status == MI_OK)
    {
        return true;
    }
    else
    {
        return false;
    }
}

bool RFID::readCardSerial()
{

    unsigned char status;
    unsigned char str[MAX_LEN];

    // Anti-collision, return 4-byte card serial number
    status = anticoll(str);
    memcpy(serNum, str, 5);

    if (status == MI_OK)
    {
        return true;
    }
    else
    {
        return false;
    }
}

/******************************************************************************
 * Dr.Leong   ( WWW.B2CQSHOP.COM )
 ******************************************************************************/

void RFID::init()
{
    digitalWrite(_NRSTPD, HIGH);

    reset();

    //Timer: TPrescaler*TreloadVal/6.78MHz = 24ms
    writeByte(TModeReg, 0x8D);      //Tauto=1; f(Timer) = 6.78MHz/TPreScaler
    writeByte(TPrescalerReg, 0x3E); //TModeReg[3..0] + TPrescalerReg
    writeByte(TReloadRegL, 30);
    writeByte(TReloadRegH, 0);

    writeByte(TxAutoReg, 0x40); //100%ASK
    writeByte(ModeReg, 0x3D);   // CRC valor inicial de 0x6363

    //ClearBitMask(Status2Reg, 0x08);	//MFCrypto1On=0
    //writeByte(RxSelReg, 0x86);		//RxWait = RxSelReg[5..0]
    //writeByte(RFCfgReg, 0x7F);   	//RxGain = 48dB

    antennaOn(); // Turn on the antenna
}

/*
 * Function Name : ResetMFRC522
 * Description : Reset RC522
 * Input: None
 * Return value: None
 */
void RFID::reset()
{
    writeByte(CommandReg, PCD_RESETPHASE);
}

/*
* Function Name : Write_MFRC5200
 * Description: MFRC522 of a register to write a byte of data
 * Input Parameters : addr - register address ; val - the value to be written
 * Return value: None
 */
void RFID::writeByte(unsigned char addr, unsigned char val)
{
    digitalWrite(_chipSelectPin, LOW);

    // Address Format :0XXXXXX0
    SPI.transfer((addr << 1) & 0x7E);
    SPI.transfer(val);

    digitalWrite(_chipSelectPin, HIGH);
}

/*
 * Function Name : AntennaOn
 * Description : Open antennas, each time you start or shut down the natural barrier between the transmitter should be at least 1ms interval
 * Input: None
 * Return value: None
 */
void RFID::antennaOn(void)
{
    unsigned char temp;

    temp = readByte(TxControlReg);
    if (!(temp & 0x03))
    {
        setBitMask(TxControlReg, 0x03);
    }
}

/*
 * Function Name : AntennaOff
 * Description : Close antennas, each time you start or shut down the natural barrier between the transmitter should be at least 1ms interval
 * Input: None
 * Return value: None
 * /
 */
void RFID::antennaOff(void)
{
    clearBitMask(TxControlReg, 0x03);
}

/*
 * Function name: ReadByte
 * Description: From the MFRC522 read a byte from a data register.
 * Input parameters : blockAddr - block address ; recvData - read block data
 * Return value: the successful return MI_OK
 */
unsigned char RFID::readByte(unsigned char addr)
{
    unsigned char val;

    digitalWrite(_chipSelectPin, LOW);

    // Address Format : 1XXXXXX0
    SPI.transfer(((addr << 1) & 0x7E) | 0x80);
    val = SPI.transfer(0x00);

    digitalWrite(_chipSelectPin, HIGH);

    return val;
}

/*
  * Function Name : SetBitMask
 * Description: Set RC522 register bit
 * Input parameters : reg - register address ; mask - set value
 * Return value: None
 */
void RFID::setBitMask(unsigned char reg, unsigned char mask)
{
    unsigned char tmp;
    tmp = readByte(reg);
    writeByte(reg, tmp | mask); // set bit mask
}

/*
 * Function Name : ClearBitMask
 * Description : clear RC522 register bit
 * Input parameters : reg - register address ; mask - clear bit value
 * Return value: None
 */
void RFID::clearBitMask(unsigned char reg, unsigned char mask)
{
    unsigned char tmp;
    tmp = readByte(reg);
    writeByte(reg, tmp & (~mask)); // clear bit mask
}

/*
 * Function Name : CalculateCRC
 * Description: CRC calculation with MF522
 * Input parameters : pIndata - To read the CRC data , len - the data length , pOutData - CRC calculation results
 * Return value: None
 */
void RFID::calculateCRC(unsigned char *pIndata, unsigned char len, unsigned char *pOutData)
{
    unsigned char i, n;

    clearBitMask(DivIrqReg, 0x04);  //CRCIrq = 0
    setBitMask(FIFOLevelReg, 0x80); //Clear FIFO pointer
    //Write_MFRC522(CommandReg, PCD_IDLE);

    //Write data to the FIFO
    for (i = 0; i < len; i++)
    {
        writeByte(FIFODataReg, *(pIndata + i));
    }
    writeByte(CommandReg, PCD_CALCCRC);

    // Wait for CRC calculation completion
    i = 0xFF;
    do
    {
        n = readByte(DivIrqReg);
        i--;
    } while ((i != 0) && !(n & 0x04)); //CRCIrq = 1

    //Read the CRC calculation
    pOutData[0] = readByte(CRCResultRegL);
    pOutData[1] = readByte(CRCResultRegM);
}

/*
 * Function Name : MFRC522_ToCard
 * Description : RC522 and ISO14443 card communication
 * Input Parameters : command - MF522 command word,
 * SendData - RC522 sent to the card via the data
 * SendLen - length of data sent
 * BackData - received the card returns data,
 * BackLen - return data bit length
 * Return value: the successful return MI_OK
 */
unsigned char RFID::MFRC522ToCard(unsigned char command, unsigned char *sendData, unsigned char sendLen, unsigned char *backData, unsigned int *backLen)
{
    unsigned char status = MI_ERR;
    unsigned char irqEn = 0x00;
    unsigned char waitIRq = 0x00;
    unsigned char lastBits;
    unsigned char n;
    unsigned int i;

    switch (command)
    {
    case PCD_AUTHENT: // Certification cards close
    {
        irqEn = 0x12;
        waitIRq = 0x10;
        break;
    }
    case PCD_TRANSCEIVE: //FIFO data transmission
    {
        irqEn = 0x77;
        waitIRq = 0x30;
        break;
    }
    default:
        break;
    }

    writeByte(CommIEnReg, irqEn | 0x80); // enable interrupt request
    clearBitMask(CommIrqReg, 0x80);         // Clear all interrupt request bits
    setBitMask(FIFOLevelReg, 0x80);         // FlushBuffer = 1, FIFO initialization

    writeByte(CommandReg, PCD_IDLE); // NO action; cancel the current command ? ? ?

    /// Write data to the FIFO
    for (i = 0; i < sendLen; i++)
    {
        writeByte(FIFODataReg, sendData[i]);
    }

    // Execute the command
    writeByte(CommandReg, command);
    if (command == PCD_TRANSCEIVE)
    {
        setBitMask(BitFramingReg, 0x80); //StartSend=1,transmission of data starts
    }

    // Pending receipt of data to complete
    i = 2000; // i according to the clock frequency adjustment , the operator M1 card maximum waiting time 25ms???
    do
    {
        //CommIrqReg[7..0]
        //Set1 TxIRq RxIRq IdleIRq HiAlerIRq LoAlertIRq ErrIRq TimerIRq
        n = readByte(CommIrqReg);
        i--;
    } while ((i != 0) && !(n & 0x01) && !(n & waitIRq));

    clearBitMask(BitFramingReg, 0x80); //StartSend=0

    if (i != 0)
    {
        if (!(readByte(ErrorReg) & 0x1B)) //BufferOvfl Collerr CRCErr ProtecolErr
        {
            status = MI_OK;
            if (n & irqEn & 0x01)
            {
                status = MI_NOTAGERR; //??
            }

            if (command == PCD_TRANSCEIVE)
            {
                n = readByte(FIFOLevelReg);
                lastBits = readByte(ControlReg) & 0x07;
                if (lastBits)
                {
                    *backLen = (n - 1) * 8 + lastBits;
                }
                else
                {
                    *backLen = n * 8;
                }

                if (n == 0)
                {
                    n = 1;
                }
                if (n > MAX_LEN)
                {
                    n = MAX_LEN;
                }

                // Read the received data in FIFO
                for (i = 0; i < n; i++)
                {
                    backData[i] = readByte(FIFODataReg);
                }
            }
        }
        else
        {
            status = MI_ERR;
        }
    }

    //SetBitMask(ControlReg,0x80);           //timer stops
    //Write_MFRC522(CommandReg, PCD_IDLE);

    return status;
}

/*
 * Function name: MFRC522_Request
 * Description: Find the cards, read the card type number.
 * Input parameters: reqMode - find the card mode,
 * Tagtype - return the card type
 * 0x4400 = Mifare_UltraLight
 * 0x0400 = Mifare_One (S50)
 * 0x0200 = Mifare_One (S70)
 * 0x0800 = Mifare_Pro (X)
 * 0x4403 = Mifare_DESFire
 * Return value: the return success MI_OK
 */
unsigned char RFID::MFRC522Request(unsigned char reqMode, unsigned char *TagType)
{
    unsigned char status;
    unsigned int backBits; //   Received data bits

    writeByte(BitFramingReg, 0x07); //TxLastBists = BitFramingReg[2..0]	???

    TagType[0] = reqMode;
    status = MFRC522ToCard(PCD_TRANSCEIVE, TagType, 1, TagType, &backBits);

    if ((status != MI_OK) || (backBits != 0x10))
    {
        status = MI_ERR;
    }

    return status;
}

/**
 * MFRC522Anticoll -> anticoll
 * Anti-collision detection, reading the serial number of the card from the card card
 * @param serNum - returns card number 4 bytes serial, first 5 bytes parity bytes
 * @return successful return MI_OK
 */
unsigned char RFID::anticoll(unsigned char *serNum)
{
    unsigned char status;
    unsigned char i;
    unsigned char serNumCheck = 0;
    unsigned int unLen;

    //ClearBitMask(Status2Reg, 0x08);		//TempSensclear
    //ClearBitMask(CollReg,0x80);			//ValuesAfterColl
    writeByte(BitFramingReg, 0x00); //TxLastBists = BitFramingReg[2..0]

    serNum[0] = PICC_ANTICOLL;
    serNum[1] = 0x20;
    status = MFRC522ToCard(PCD_TRANSCEIVE, serNum, 2, serNum, &unLen);

    if (status == MI_OK)
    {
        //?????? Check the serial number of the card
        for (i = 0; i < 4; i++)
        {
            serNumCheck ^= serNum[i];
        }
        if (serNumCheck != serNum[i])
        {
            status = MI_ERR;
        }
    }

    //SetBitMask(CollReg, 0x80);		//ValuesAfterColl=1

    return status;
}

/* 
 * MFRC522Auth -> auth
 * Verify card password
 * Input parameters: AuthMode - Password Authentication Mode
                 0x60 = A key authentication
                 0x61 = B key authentication
             BlockAddr-- address block
             Sectorkey-- sector password
             serNum - card serial number, 4-byte
 * MI_OK Return value: the successful return MI_OK
 */
unsigned char RFID::auth(unsigned char authMode, unsigned char BlockAddr, unsigned char *Sectorkey, unsigned char *serNum)
{
    unsigned char status;
    unsigned int recvBits;
    unsigned char i;
    unsigned char buff[12];

    // Validate instruction block address + sector + password + card serial number
    buff[0] = authMode;
    buff[1] = BlockAddr;
    for (i = 0; i < 6; i++)
    {
        buff[i + 2] = *(Sectorkey + i);
    }
    for (i = 0; i < 4; i++)
    {
        buff[i + 8] = *(serNum + i);
    }
    status = MFRC522ToCard(PCD_AUTHENT, buff, 12, buff, &recvBits);

    if ((status != MI_OK) || (!(readByte(Status2Reg) & 0x08)))
    {
        status = MI_ERR;
    }

    return status;
}

/*
 * Function Name : MFRC522_SelectTag
 * Description: election card , read the card memory capacity
 * Input parameters : serNum - Incoming card serial number
 * Return value: the successful return of card capacity
 */
unsigned char RFID::selectTag(unsigned char *serNum)
{
    unsigned char i;
    unsigned char status;
    unsigned char size;
    unsigned int recvBits;
    unsigned char buffer[9];

    //ClearBitMask(Status2Reg, 0x08);     //MFCrypto1On=0

    buffer[0] = PICC_SElECTTAG;
    buffer[1] = 0x70;
    for (i = 0; i < 5; i++)
    {
        buffer[i + 2] = *(serNum + i);
    }
    calculateCRC(buffer, 7, &buffer[7]); //??
    status = MFRC522ToCard(PCD_TRANSCEIVE, buffer, 9, buffer, &recvBits);

    if ((status == MI_OK) && (recvBits == 0x18))
    {
        size = buffer[0];
    }
    else
    {
        size = 0;
    }

    return size;
}

/*
 * MFRC522Read -> read
 * Description: Read block data
 * Input parameters: blockAddr - block address; recvData - read a data block.
 * Return value: the successful return MI_OK
 */
unsigned char RFID::read(unsigned char blockAddr, unsigned char *recvData)
{
    unsigned char status;
    unsigned int unLen;

    recvData[0] = PICC_READ;
    recvData[1] = blockAddr;
    calculateCRC(recvData, 2, &recvData[2]);
    status = MFRC522ToCard(PCD_TRANSCEIVE, recvData, 4, recvData, &unLen);

    if ((status != MI_OK) || (unLen != 0x90))
    {
        status = MI_ERR;
    }

    return status;
}

/*
 * MFRC522Write -> write
 * Block data writing
 * blockAddr - address of the block; WriteData - to write 16 bytes of the data block
 * Return value: the successful return MI_OK
 */
unsigned char RFID::write(unsigned char blockAddr, unsigned char *writeData)
{
    unsigned char status;
    unsigned int recvBits;
    unsigned char i;
    unsigned char buff[18];

    buff[0] = PICC_WRITE;
    buff[1] = blockAddr;
    calculateCRC(buff, 2, &buff[2]);
    status = MFRC522ToCard(PCD_TRANSCEIVE, buff, 4, buff, &recvBits);

    if ((status != MI_OK) || (recvBits != 4) || ((buff[0] & 0x0F) != 0x0A))
    {
        status = MI_ERR;
    }

    if (status == MI_OK)
    {
        for (i = 0; i < 16; i++) // Write 16Byte data to FIFO
        {
            buff[i] = *(writeData + i);
        }
        calculateCRC(buff, 16, &buff[16]);
        status = MFRC522ToCard(PCD_TRANSCEIVE, buff, 18, buff, &recvBits);

        if ((status != MI_OK) || (recvBits != 4) || ((buff[0] & 0x0F) != 0x0A))
        {
            status = MI_ERR;
        }
    }

    return status;
}

/*
* MFRC522Halt -> halt
 * Description : Command card into hibernation
 * Input parameters: None
 * Returned value: None
 */
void RFID::halt()
{
    unsigned char status;
    unsigned int unLen;
    unsigned char buff[4];

    buff[0] = PICC_HALT;
    buff[1] = 0;
    calculateCRC(buff, 2, &buff[2]);

    status = MFRC522ToCard(PCD_TRANSCEIVE, buff, 4, buff, &unLen);
}
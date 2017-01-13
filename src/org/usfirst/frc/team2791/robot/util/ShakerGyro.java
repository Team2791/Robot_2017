package org.usfirst.frc.team2791.util;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShakerGyro extends SensorBase implements Runnable {
    private static final double calibrationTime = 5.0;
    private static final int updateDelayMs = 1000 / 50; // run at 100 Hz
    public double initialAngle = 0.0;
    // constants from analog devices code
    @SuppressWarnings("unused")
    private byte ADXRS453_READ = (byte) (1 << 7);
    @SuppressWarnings("unused")
    private byte ADXRS453_WRITE = (1 << 6);
    private byte ADXRS453_SENSOR_DATA = (1 << 5);
    // constants from 830's code
    private byte PARITY_BIT = 1;
    private byte FIRST_BYTE_DATA = 0x3; // mask to find sensor data bits on
    // first byte: X X X X X X D D
    private char THIRD_BYTE_DATA = 0xFC; // mask to find sensor data bits on
    // third byte: D D D D D D X X
    private SPI m_spi;
    private Timer calibrationTimer;
    private double angle = 0;
    private double rateOffset = 0;
    private double last_update_time = -1;
    private double last_update_rate = 0;
    private boolean recalibrate = false;
    private boolean calibrated = false;

    public ShakerGyro(SPI.Port port) {
        m_spi = new SPI(port);
        m_spi.setClockRate(4000000); // set to 4 MHz because that's the rRio's
        // max, gyro can do 8 MHz
        m_spi.setMSBFirst();
        m_spi.setSampleDataOnRising();
        m_spi.setClockActiveHigh(); // set clock polarity low (yes I know it
        // says ActiveHigh)
        m_spi.setChipSelectActiveLow();

        calibrationTimer = new Timer();
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }

    // this method keeps the gyro angle updated, run at 100hz
    @Override
    public void run() {
        System.out.println("Gyro update thread started");
        recalibrate = true;
        try {
            while (true) {
                // first check if we need to run a calibrate loop
                if (recalibrate) {
                    calibrated = false;
                    recalibrate = false;
                    calibrate();
                    calibrated = true;
                } else {
                    update();
                }
                // delay so loop doesn't run crazy fast
                Thread.sleep(updateDelayMs);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double getRate() {
        byte[] data = ADXRS453_GetSensorData();
        double rate = assemble_sensor_data(data) / 80.0;
        if (rate > 301.0 || rate < -301.0) {
            System.out.println("Weird Rate: " + rate);
            System.out.println("Raw rate data: " + assemble_sensor_data(data));
            System.out.println("Hex response: " + byteArrayToHex(data));
        }
        // add in the offset calculated during calibration if it has been found
        if (calibrated)
            rate -= rateOffset;

//		System.out.print("Raw rate data: " + assemble_sensor_data(data));
//		System.out.println(" Hex response: " + byteArrayToHex(data));
        return rate;
    }

    public void update() {
        double fpgaTime = Timer.getFPGATimestamp();
        // ignore the first data since we don't have a time diff
        if (last_update_time != -1) {
            // gets the rate of change of angle and add that to the duration of
            // change
            double fpagTimeDiff = fpgaTime - last_update_time;
            angle += (getRate()-last_update_rate) * fpagTimeDiff * 0.5;//Trapezoidal Approximation to get change of angle
        }
        last_update_time = fpgaTime;
        last_update_rate = getRate();
    }

    public double getAngle() {
        return angle;
    }

    public boolean currentlyCalibrating() {
        return calibrationTimer.get() < calibrationTime;
    }

    // tell the gyro to recalibrate in parallel to calling thread
    public void recalibrate() {
        recalibrate = true;
    }

    private void calibrate() throws InterruptedException {
        System.out.println("Gyro calibrating");
        calibrationTimer.reset();
        calibrationTimer.start();
        // reset values then wait a few seconds to accumulate some offset
        rateOffset = 0;
        reset();
        // run the gyro normally for calibrationTime
        double time_spent = calibrationTimer.get();
        while (time_spent < calibrationTime) {
            time_spent = calibrationTimer.get();
            update();
            Thread.sleep(updateDelayMs);
        }
        // find the rate offset by dividing acumulated angle by the time spend
        // calibrating
        rateOffset = getAngle() / time_spent;
        // set the current angle to 0
        reset();
        System.out.println("Done calibrating. Rate offset = " + rateOffset);
        SmartDashboard.putNumber("Gyro rate offset", rateOffset);
    }

    /***************************************************************************/

    public void reset() {
        angle = 0;
    }

    /**
     * @return registerValue - The sensor data.
     * @brief Reads the sensor data.
     *******************************************************************************/

    public byte[] ADXRS453_GetSensorData() {
        byte[] dataBuffer = {0, 0, 0, 0};

        dataBuffer[0] = ADXRS453_SENSOR_DATA;
        SPI_transaction(dataBuffer, dataBuffer, 4);

        return dataBuffer;
    }

    int assemble_sensor_data(byte[] data) {
        /*
         * The data is formatted as a twos complement number with a scale factor
		 * of 80 LSB/°/sec. Therefore, the highest obtainable value for
		 * positive (clockwise) rotation is 0x7FFF (decimal +32,767), and the
		 * highest obtainable value for negative (counterclockwise) rotation is
		 * 0x8000 (decimal −32,768).
		 */
        // cast to short to make space for shifts
        // the 16 bits from the gyro are a 2's complement short
        // so we just cast it too a C++ short
        // the data is split across the output like this (MSB first): (D = data
        // bit, X = not data)
        // X X X X X X D D | D D D D D D D D | D D D D D D X X | X X X X X X X X

        int result = 0;
        result = data[0] & FIRST_BYTE_DATA; // remove first 6 bits keep last 2
        // shift bits 8 to the left to make room for the next byte
        result = result << 8;
        // or in bits from next byte of data
        result |= (data[1]);
        // shift another 6 bits to make room for last bit of data
        result = result << 6;
        // or in bits of last data
        result |= (data[2] & THIRD_BYTE_DATA) >> 2; // remove last 2 bits keep
        // first 6

        // check the sign bit to see if we need to invert the result
        // sign bit is the first of 16 so shifting 15 to the right will clear
        // everything but it
        if ((result >> 15) == 1) {
            // if negative number do bitwise negate to make positive 2s
            // compliment number
            // then set signbit again to make the number negative
            result = ~result & 0xFFFF; // negate bits and only keep last 16
            result = -result - 1;
        }

        return result;
    }

    private void SPI_transaction(byte[] inputBuffer, byte[] outputBuffer, int size) {
        check_parity(inputBuffer); // do parity bit things
        // System.out.println("Gyro hex command " +
        // byteArrayToHex(inputBuffer));
        m_spi.transaction(inputBuffer, outputBuffer, 4);
        // System.out.println("Gyro hex response " +
        // byteArrayToHex(outputBuffer));
    }

    void check_parity(byte[] command) {
        int num_bits = bits(command[0]) + bits(command[1]) + bits(command[2]) + bits(command[3]);
        if (num_bits % 2 == 0) {
            command[3] |= PARITY_BIT;
        }
    }

    int bits(byte val) {
        int n = 0;
        while (val != 0) {
            val &= val - 1;
            n += 1;
        }
        return n;
    }
}

// recomended startup secquence with error checking
// // assert the check bit
// byte[] commands = new byte[4];
// // send the command 0x20 00 00 03
// commands[0] = kDataRequest;
// commands[1] = 0x00;
// commands[2] = 0x00;
//// commands[3] = kAssertCheckBit;
// commands[3] = 0x00;

// byte[] transferBuffer = new byte[4];
// System.out.println("Gyro running init commands");
// SPI_transaction(commands, transferBuffer, 4);
//
// Thread.sleep(50); // delay to allow fault conditions to generate
// System.out.println("Clearing check bit ignore response");
// ADXRS453_GetSensorData();
//
// Thread.sleep(50); // allow fault conditions to clear
// System.out.println("chk response");
// ADXRS453_GetSensorData();
//
// Thread.sleep(1); // sequantial data delay
// System.out.println("chk response again");
// ADXRS453_GetSensorData();

// long adxrs453Id = 0;
// int status = 0;
// Thread.sleep(1); // sequantial data delay
// System.out.println("asking for status");
// adxrs453Id = ADXRS453_GetRegisterValue(ADXRS453_REG_PID);
// if((adxrs453Id >> 8) != 0x52)
// {
// status = -1;
// }
//
// SmartDashboard.putNumber("Gyro id value", adxrs453Id);
// SmartDashboard.putNumber("Gyro status", status);

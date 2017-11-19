#!/usr/bin/python

import smbus
import math
#socket library
import socket
import time

#Define host and port
HOST = "localhost"
PORT = 3055

# Power management registers
power_mgmt_1 = 0x6b
power_mgmt_2 = 0x6c

def read_byte(adr):
    return bus.read_byte_data(address, adr)

def read_word(adr):
    high = bus.read_byte_data(address, adr)
    low = bus.read_byte_data(address, adr+1)
    val = (high << 8) + low
    return val

def read_word_2c(adr):
    val = read_word(adr)
    if (val >= 0x8000):
        return -((65535 - val) + 1)
    else:
        return val

def dist(a,b):
    return math.sqrt((a*a)+(b*b))

def get_y_rotation(x,y,z):
    radians = math.atan2(z, dist(x,y))
    return -math.degrees(radians)

def get_x_rotation(x,y,z):
    radians = math.atan2(y, dist(x,z))
    return math.degrees(radians)

bus = smbus.SMBus(1) # or bus = smbus.SMBus(1) for Revision 2 boards
address = 0x68       # This is the address value read via the i2cdetect command

prevz = -0.3
class Data:
    prevz = -0.3

#def readSensor(prevz):
def readSensor():
    # Now wake the 6050 up as it starts in sleep mode
    bus.write_byte_data(address, power_mgmt_1, 0)

    accel_xout = read_word_2c(0x3b)
    accel_yout = read_word_2c(0x3d)
    accel_zout = read_word_2c(0x3f)
    
    accel_xout_scaled = round(accel_xout / 16384.0,2)
    accel_yout_scaled = round(accel_yout / 16384.0,2)
    accel_zout_scaled = round(accel_zout / 16384.0,2)
    
    print "accel_zout: ", ("%6.2f" % accel_zout_scaled),"x rotation: " , ("%6.2f" % get_x_rotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled)),"y rotation: ",("%6.2f" % get_y_rotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled))
    cur_x_rotation = get_x_rotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled)
    cur_y_rotation = get_y_rotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled)
  
##    if(accel_zout_scaled>0.5):
##        
##        return "right\n\r"
##    elif(accel_zout_scaled < -0.3):
##        return "left\n\r"
##    else:
    if(cur_x_rotation >= 40):
        return "hide\n\r"
    elif(cur_x_rotation <= -40):
        return "show\n\r"
    elif(cur_y_rotation >= 40):
        return "left\n\r"
    elif(cur_y_rotation <= -40):
        return "right\n\r"
    else :
        return "0\n\r"
##    if (accel_zout_scaled > 1):
##        if ((accel_zout_scaled - prevz) > 1.4 and (accel_zout_scaled > -0.4)):
##            prevz = accel_zout_scaled
##            return "right\n\r"
##    elif(accel_zout_scaled < -1.7):
##        if ((prevz - accel_zout_scaled) > 1.4 and (accel_zout_scaled < -0.2)):
##            prevz = accel_zout_scaled
##            return "left\n\r"
##    else:
##        prevz = accel_zout_scaled
##        return "0\n\r"
            
    #buffer = str(accel_xout_scaled) + " " + str(accel_yout_scaled) +" "+ str(accel_zout_scaled)+"\n\r"
    #pre_x_rotation = cur_x_rotation
    #pre_y_rotation = cur_y_rotation
    #print (buffer)
    
    #return buffer
##
##def activty(x,z):
##    state = 0
##    switch(state):
##        case 0:
##            pre_x = 0
##            cur_x = x
##            if(cur_x >= 50):
##                state = 1
##                pre_x = cur_x
##            elif(cur_x <= -40):
##                state = 2
##                pre_x = cur_x
##            break
##        
##        case 1:
##            if(cur_x - pre_x >= 50):
                
                
                
            
print "gyro data"
print "---------"

#gyro_xout = read_word_2c(0x43)
#gyro_yout = read_word_2c(0x45)
#gyro_zout = read_word_2c(0x47)


#print "gyro_xout: ", gyro_xout, " scaled: ", (gyro_xout / 131)
#print "gyro_yout: ", gyro_yout, " scaled: ", (gyro_yout / 131)
#print "gyro_zout: ", gyro_zout, " scaled: ", (gyro_zout / 131)

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print('Socket created')


try:
    s.bind((HOST, PORT))
except socket.error as err:
    print('Bind failed. Error Code : ' .format(err))
s.listen(10)
print("Socket Listening")
conn, addr = s.accept()
prevz = -0.3
while (True):
#if (True):
    #conn.send(readSensor().encode())
    #print("Message sent")
    #time.sleep(0.5)
    #s.close()
    if(readSensor() is not "0\n\r"):
        try:
            print(readSensor()) 
            conn.send(readSensor().encode())
            print("Message sent")
            time.sleep(0.1)
        except IOError:
            print("fail fail")
##            try:
##                s.close()
##            except IOError:
##                pass
##    
#print "x rotation: " , get_x_rotation(accel_xout_scaled, accel_yout_scaled, accel_zout_scaled)



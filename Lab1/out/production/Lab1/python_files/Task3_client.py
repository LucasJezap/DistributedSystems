import socket
import time

serverIP = "127.0.0.1"
serverPort = 9010
starting_number = 300

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
while True:
    # send
    msg_bytes = starting_number.to_bytes(4, byteorder='little')
    client.sendto(msg_bytes, (serverIP, serverPort))
    # receive
    buff, address = client.recvfrom(1024)
    starting_number = int.from_bytes(buff, byteorder='little')
    print(f"received msg: {starting_number}")
    starting_number += 1
    time.sleep(1)

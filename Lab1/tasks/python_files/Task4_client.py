import socket
import time

serverIP = "127.0.0.1"
serverPort = 9011
clientPort = 9013
msg = "Python Client says: Hello Java Server!"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.bind(('0.0.0.0', clientPort))
while True:
    # send
    client.sendto(bytes(msg.encode('utf-8')), (serverIP, serverPort))
    # receive
    buff, address = client.recvfrom(1024)
    print("received msg: " + str(buff, 'utf-8'))
    time.sleep(1)

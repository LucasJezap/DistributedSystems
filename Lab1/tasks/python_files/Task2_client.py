import socket

serverIP = "127.0.0.1"
serverPort = 9009
msg = "Python Client says: Żółta Gęś!"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
# send
client.sendto(bytes(msg.encode('utf-8')), (serverIP, serverPort))
# receive
buff, address = client.recvfrom(1024)
print("received msg: " + str(buff, 'utf-8'))

import datetime
import sys
from time import sleep

import grpc

import crypto_pb2
import crypto_pb2_grpc

currency_dict = {
    "BITCOIN": crypto_pb2.BITCOIN,
    "ETHEREUM": crypto_pb2.ETHEREUM,
    "LITECOIN": crypto_pb2.LITECOIN
}

reverse_currency_dict = {
    0: "Bitcoin",
    1: "Ethereum",
    2: "Litecoin"
}


def print_timestamp():
    return '[' + str(datetime.datetime.now())[:23] + '] '


def get_client_info():
    name = input(print_timestamp() + 'Provide your name: ')
    try:
        currencies = input(print_timestamp() + 'Provide currencies you want to follow (e.g. Bitcoin Litecoin): ')
        currencies = [c.upper() for c in currencies.split()]
        currencies = [currency_dict[c] for c in currencies]
    except KeyError:
        print(print_timestamp() + 'You have provided a currency that is not supported')
        sys.exit(1)

    return name, currencies


def init_grpc(client_n, currencies):
    client_obj = crypto_pb2.Request(name=client_n, currencies=currencies)
    channel = grpc.insecure_channel('localhost:55555')
    stub = crypto_pb2_grpc.MessagesStub(channel)
    return client_obj, stub


def get_notifications(stub, client_n):
    try:
        while True:
            try:
                iterator = stub.subscribe(client_n)
                for response in iterator:
                    print(print_timestamp() + reverse_currency_dict.get(response.currency).ljust(15) + " "
                          + str(response.price).ljust(25) + " "
                          + response.price_change.ljust(15))

            except grpc.RpcError as e:
                if e.details() == 'Stream removed' or e.code() == grpc.StatusCode.UNAVAILABLE:
                    print(print_timestamp() + 'Disconnected from server...')
                else:
                    print(print_timestamp() + str(e))
                    raise SystemExit
            sleep(1)
    except KeyboardInterrupt:
        pass


if __name__ == '__main__':
    print(print_timestamp() + 'Welcome in CryptoCurrency 1.0.')
    print(print_timestamp() + 'Provided currencies: Bitcoin, Ethereum, Litecoin.')
    client_name, subscribed_currencies = get_client_info()

    client, client_stub = init_grpc(client_name, subscribed_currencies)

    get_notifications(client_stub, client)

    print(print_timestamp() + 'Exiting CryptoCurrency 1.0.')

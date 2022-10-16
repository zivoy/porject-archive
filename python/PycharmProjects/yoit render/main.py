import socket
HOST = "127.0.0.1"  # localhost
PORT = 50008


def main():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.connect((HOST, PORT))
        data = s.recv(1024)
        print(data.decode("UTF8"))
        while True:
            i = input("> ")
            s.sendall(i.encode())
            data = s.recv(1024)
            print(data.decode('utf8'))
            if "#END#" in i:
                break


if __name__ == "__main__":
    main()

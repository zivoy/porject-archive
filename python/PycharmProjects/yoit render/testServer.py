import socket
HOST = ""  # localhost
PORT = 50008


def main():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((HOST, PORT))
        s.listen()
        conn, addr = s.accept()
        with conn:
            print('Connected by', addr)
            conn.send(f"connection accepted from {addr[0]}:{addr[1]}".encode())
            while True:
                data = conn.recv(1024)
                if b"#END#" in data:
                    break
                conn.sendall(f"> \"{data.decode('utf8')}\"".encode())


if __name__ == "__main__":
    main()

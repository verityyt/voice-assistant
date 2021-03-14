import socket
import speech_recognition as sr

HOST = "localhost"
PORT = 9090

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

def doListening():
    try:
        audio = r.listen(source)
        voice_input = str(r.recognize_google(audio, language="de_DE"))
        sock.sendall(("RECOGNIZED:" + voice_input + "\n").encode())
    except ConnectionResetError:
        sock.sendall(("CLIENT_DISCONNECTED\n").encode())
    except sr.UnknownValueError:
        sock.sendall(("COULD_NOT_UNDERSTAND\n").encode())


r = sr.Recognizer()
with sr.Microphone() as source:
    sock.sendall(("LISTENING_NOW\n").encode())

    while True:
        doListening()

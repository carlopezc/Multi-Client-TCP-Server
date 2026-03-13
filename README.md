# 🌐 Java TCP/IP Concurrent Servers

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Sockets](https://img.shields.io/badge/Network-Sockets-00599C?style=for-the-badge)
![Multithreading](https://img.shields.io/badge/Concurrency-Multithreading-121011?style=for-the-badge)

> A backend Java project demonstrating TCP/IP socket programming, multi-threading, and remote file management.

---

## 🎯 Project Overview

This project focuses on building robust client-server architectures using Java. The main objective is to handle multiple client connections simultaneously without blocking the main server thread, ensuring a smooth and responsive network application. The project culminates in a fully functional remote file server with state-based authentication.

## 🛠️ Programs Included

This repository contains two distinct server implementations, showcasing a progression in complexity:

### 1. The Concurrent Base Server
* **Multithreading:** Upgrades a standard sequential server to a concurrent architecture. 
* **Thread-per-client:** It uses Java's `Thread` class and `Runnable` interfaces to spawn a new thread for every incoming connection, allowing multiple clients to interact with the server simultaneously.
* **Core Networking:** Deep utilization of `ServerSocket` and `Socket` classes for reliable TCP communication.

### 2. The Authenticated Remote File Server
* **Authentication System:** The server requires a valid username and password session before granting access to its features.
* **State Machine Logic:** Operates on a strict state diagram (e.g., *Waiting for Connection -> Authenticating -> Ready for Commands -> Disconnected*) to ensure secure and logical request handling.
* **Remote Commands:** Once a client is authenticated, they can interact with the server's filesystem:
  * **List:** View the contents of the server's current directory.
  * **Read:** Request and display the contents of a specific file over the network.
  * **Exit:** Gracefully terminate the session and close the socket.
* **I/O Streams:** Extensive use of `DataInputStream`, `DataOutputStream`, `BufferedReader`, and `PrintWriter` for seamless data transmission.

## ⚙️ How to Compile & Run

*Note: You must start the Server application first before launching any Client instances.*

1.  Clone the repository:
    ```bash
    git clone [https://github.com/carlopezc/Java-Concurrent-Servers.git](https://github.com/carlopezc/Java-Concurrent-Servers.git)
    cd Java-Concurrent-Servers
    ```

2.  Compile the `.java` files:
    ```bash
    javac src/*.java
    ```

3.  **Start the Server** in one terminal:
    ```bash
    java -cp src ServerMain
    ```

4.  **Start one or more Clients** in separate terminals to test concurrency:
    ```bash
    java -cp src ClientMain
    ```

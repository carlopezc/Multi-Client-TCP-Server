# 🌐 Java TCP/IP Concurrent Servers

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Sockets](https://img.shields.io/badge/Network-Sockets-00599C?style=for-the-badge)
![Multithreading](https://img.shields.io/badge/Concurrency-Multithreading-121011?style=for-the-badge)

> A backend Java project featuring a suite of three TCP/IP server applications, demonstrating progression in socket programming, multi-threading, and remote file management.

---

## 🎯 Project Overview

This project focuses on building robust client-server architectures using Java. The main objective is to handle multiple client connections simultaneously without blocking the main server thread, ensuring a smooth and responsive network application. The repository includes three distinct server programs, culminating in a fully functional remote file server with state-based authentication.

## 🛠️ Programs Included

The repository is structured around three server implementations, each adding a layer of complexity:

### 1. Basic Concurrent Server
* **Foundation:** Upgrades a standard sequential server to a concurrent architecture.
* **Thread-per-client:** Uses Java's `Thread` class and `Runnable` interfaces to spawn a new thread for every incoming connection, allowing multiple clients to interact with the server simultaneously without bottlenecks.

### 2. Advanced Command Server
* **Complex I/O:** Builds upon the basic concurrent model to handle more complex data processing and continuous client-server message exchange.
* **Resource Management:** Ensures sockets and streams are properly managed and closed across multiple active threads to prevent resource leaks.

### 3. Authenticated Remote File Server
* **Authentication System:** The flagship implementation. The server requires a valid username and password session (e.g., `javier` / `secreta`) before granting access to its features.
* **State Machine Logic:** Operates on a strict state diagram (*Waiting for Connection -> Authenticating -> Ready for Commands -> Disconnected*) to ensure secure request handling.
* **Remote File System Access:** Once authenticated, clients can interact with the server's filesystem to:
  * **List:** View the contents of the server's current directory.
  * **Read:** Request and display the contents of a specific file over the network.
  * **Exit:** Gracefully terminate the session.

## ⚙️ How to Compile & Run

1.  Clone the repository:
    ```bash
    git clone [https://github.com/carlopezc/Java-Concurrent-Servers.git](https://github.com/carlopezc/Java-Concurrent-Servers.git)
    cd Java-Concurrent-Servers
    ```

2.  Compile the `.java` files (replace `program3` with the actual folder name):
    ```bash
    javac src/program3/*.java
    ```

3.  **Start the Server** in one terminal:
    ```bash
    java -cp src program3.ServerMain
    ```

4.  **Start one or more Clients** in separate terminals to test concurrency:
    ```bash
    java -cp src program3.ClientMain
    ```

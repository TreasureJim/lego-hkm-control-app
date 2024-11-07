package com.findingtreasure.phonependant

import java.io.InputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.Socket
import java.io.BufferedReader
import java.io.InputStreamReader

class NetworkManager {

    // This will store the connected socket once the connection is established
    private var connectedSocket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: OutputStream? = null

    // Method to initiate the connection on a background thread
    fun connectToAddress(address: String, port: Int) {
        // Run the network code in a background thread
        Thread {
            try {
                // Create a new socket and connect to the given address and port
                val socket = Socket(InetAddress.getByName(address), port)
                println("Connected to $address on port $port")

                // Store the socket once it's connected
                connectedSocket = socket

                // Setup input/output streams for the socket
                reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                writer = socket.getOutputStream()

                // Start reading from the socket in a background thread
                readFromSocket()

            } catch (e: Exception) {
                e.printStackTrace()
                println("Error connecting to $address on port $port: ${e.message}")
            }
        }.start()
    }

    // Method to retrieve the connected socket (if available)
    fun getSocket(): Socket? {
        return connectedSocket
    }

    // Method to continuously read data from the socket
    private fun readFromSocket() {
        try {
            val socket = connectedSocket
            if (socket != null && socket.isConnected) {
                // Read data from the socket's input stream in a loop
                var line: String?
                while (socket.isConnected && !socket.isInputShutdown) {
                    line = reader?.readLine()
                    if (line != null) {
                        // Process the incoming data
                        println("Received: $line")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error reading from socket: ${e.message}")
        }
    }

    // Method to send data to the socket
    fun sendData(data: String) {
        try {
            writer?.write(data.toByteArray())
            writer?.flush()
            println("Sent: $data")
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error sending data: ${e.message}")
        }
    }
}

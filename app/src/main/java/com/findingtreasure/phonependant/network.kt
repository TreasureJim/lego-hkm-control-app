package com.findingtreasure.phonependant

import java.io.InputStream
import java.net.Socket
import java.net.InetAddress

// Function to connect to an address and port
fun connectToAddress(address: String, port: Int): Socket? {
    return try {
        // Create and return a connected socket
        Socket(InetAddress.getByName(address), port).also {
            println("Connected to $address on port $port")
        }
    } catch (e: Exception) {
        println("Error connecting to $address on port $port: ${e.message}")
        null
    }
}

// Function to read data from the established connection
fun readFromConnection(socket: Socket, bufferSize: Int = 1024): ByteArray? {
    return try {
        // Create an input stream and read data into a byte array
        val inputStream: InputStream = socket.getInputStream()
        val buffer = ByteArray(bufferSize)
        val bytesRead = inputStream.read(buffer)

        if (bytesRead > 0) {
            buffer.copyOf(bytesRead)  // Return only the read portion of the buffer
        } else {
            println("No data available to read.")
            null
        }
    } catch (e: Exception) {
        println("Error reading from socket: ${e.message}")
        null
    }
}

package com.findingtreasure.comms

import java.io.OutputStream
import java.net.InetAddress
import java.net.Socket
import java.io.InputStream
import java.nio.ByteBuffer
import java.util.UUID

object NetworkManager {

    // This will store the connected socket once the connection is established
    private var connectedSocket: Socket? = null
    private var reader: InputStream? = null
    private var writer: OutputStream? = null

    // Method to initiate the connection on a background thread
    fun connectToAddress(address: String, port: Int) {
        if (connectedSocket != null) {
            println("Already connected")
            return
        }

        // Run the network code in a background thread
        Thread {
            try {
                // Create a new socket and connect to the given address and port
                val socket = Socket(InetAddress.getByName(address), port)
                println("Connected to $address on port $port")

                // Store the socket once it's connected
                connectedSocket = socket

                // Setup input/output streams for the socket
                reader = socket.getInputStream()
                writer = socket.getOutputStream()

                // Start reading from the socket in a background thread
//                readFromSocket()

            } catch (e: Exception) {
                e.printStackTrace()
                println("Error connecting to $address on port $port: ${e.message}")
            }
        }.start()
    }

   fun disconnect() {
       connectedSocket?.close()
       connectedSocket = null
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

                val sigId = ByteArray(1)
                while (socket.isConnected && !socket.isInputShutdown) {
                    if (reader?.read(sigId) != 1) {
                        println("[ERROR] Didn't read 1 byte")
                        continue
                    }

                    val numBytes = ProtocolHandler.numBytesToDecode(sigId[0].toInt())
                    val buf = ByteArray(numBytes)
                    if (reader?.read(sigId) != numBytes) {
                        println("[ERROR] Didn't read $numBytes bytes")
                        continue
                    }
                    ProtocolHandler.decode(buf)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error reading from socket: ${e.message}")
        }
    }

    // Method to send data to the socket
    fun sendData(data: ByteArray) {
        try {
            writer?.write(data)
            writer?.flush()
            println("Sent: $data")
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error sending data: ${e.message}")
        }
    }

    fun sendJogXYZ(uuid: UUID, x: Double, y: Double, z: Double) {
        val jog = MoveJog(uuidToByteArray(uuid), x, y, z, 0.0, 0.0, 0.0, 0.0)
        sendData(ProtocolHandler.encodeMoveJog(jog))
    }

    fun sendJogJoints(uuid: UUID, j1: Double, j2: Double, j3: Double, j4: Double) {
        val jog = MoveJog(uuidToByteArray(uuid), 0.0, 0.0, 0.0, j1, j2, j3, j4)
        sendData(ProtocolHandler.encodeMoveJog(jog))
    }
}

fun uuidToByteArray(uuid: UUID): ByteArray {
    val byteBuffer = ByteBuffer.allocate(16) // UUID is 16 bytes
    byteBuffer.putLong(uuid.mostSignificantBits)
    byteBuffer.putLong(uuid.leastSignificantBits)
    return byteBuffer.array()
}

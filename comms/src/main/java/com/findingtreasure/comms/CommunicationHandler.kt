package com.findingtreasure.comms

object CommunicationHandler {
    fun transmit(message: ByteArray) {
        /* TO IMPLEMENT: transmission logic */
    }

    fun receive() : ByteArray {
        /* TO IMPLEMENT: transmission logic */
        val message = "1234".toByteArray()

        return message
    }
}
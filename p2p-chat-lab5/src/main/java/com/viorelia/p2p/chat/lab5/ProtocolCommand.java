package com.viorelia.p2p.chat.lab5;

/**
 * Protocol command types according to the specification.
 */
public enum ProtocolCommand {
    HELLO,   // !hello <name> - initialize connection
    ACK,     // !ack - acknowledge connection establishment
    BYE,     // !bye - close connection
    BYEBYE,  // !byebye - close all connections and exit
    MESSAGE  // Regular message
}


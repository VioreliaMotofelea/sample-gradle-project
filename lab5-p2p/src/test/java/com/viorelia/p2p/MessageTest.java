package com.viorelia.p2p;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void raw_hello_ack_decode() {
        Message h = Message.decode("!hello Alice", false);
        assertEquals(Message.Type.HELLO, h.type());
        assertEquals("Alice", h.from());

        Message a = Message.decode("!ack", false);
        assertEquals(Message.Type.ACK, a.type());

        Message b = Message.decode("!bye", false);
        assertEquals(Message.Type.BYE, b.type());

        Message c = Message.decode("hi there", false);
        assertEquals(Message.Type.CHAT, c.type());
        assertEquals("hi there", c.text());
    }

    @Test
    void json_roundtrip() {
        Message m = Message.chat("Alice", "hello", true);
        String enc = m.encode(true);
        Message dec = Message.decode(enc, true);
        assertEquals(Message.Type.CHAT, dec.type());
        assertEquals("Alice", dec.from());
        assertEquals("hello", dec.text());
    }
}

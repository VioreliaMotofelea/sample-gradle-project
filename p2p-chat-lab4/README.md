# P2P Chat Lab 4

## Overview
Basic socket/messaging library usage implementation for peer-to-peer chat.

## Requirements Implementation

### Base Requirements (8.5 points)
- **Proper usage of ServerSocket/Socket** (2.5p) ✓
  - ServerSocket for accepting connections
  - Socket for client connections
  - Proper resource management with try-with-resources

- **Read/write messages** (6p) ✓
  - BufferedReader/BufferedWriter for text-based communication
  - Proper encoding/decoding with UTF-8
  - Message echo functionality

### Extra for Sockets (1.5 points) ✓
- Uses vanilla Java ServerSocket and Socket (not messaging library)
- Thread pool implementation for handling multiple connections
- Separate reader and sender threads

### Bonus Points (5 points) ✓
- **JSON format support** (--json flag)
  - Uses Jackson library for JSON encoding/decoding
  - Messages wrapped in JSON with timestamp
  
- **Protobuf format support** (--protobuf flag)
  - Custom protobuf-like encoding with length prefix and Base64
  - Demonstrates binary protocol support

## Usage

### Server Mode
```bash
# Raw string mode (default)
.\gradlew :p2p-chat-lab4:run --args="server 5000"

# JSON mode
.\gradlew :p2p-chat-lab4:run --args="server 5000 --json"

# Protobuf mode
.\gradlew :p2p-chat-lab4:run --args="server 5000 --protobuf"
```

### Client Mode
```bash
# Raw string mode (default)
.\gradlew :p2p-chat-lab4:run --args="client localhost 5000"

# JSON mode
.\gradlew :p2p-chat-lab4:run --args="client localhost 5000 --json"

# Protobuf mode
.\gradlew :p2p-chat-lab4:run --args="client localhost 5000 --protobuf"
```

## Architecture

### Components
1. **SocketServer** - Handles incoming connections using ServerSocket
2. **SocketClient** - Connects to a server using Socket
3. **MessageCodec** - Encodes/decodes messages in different formats
4. **MessageFormat** - Enum for supported formats (RAW, JSON, PROTOBUF)

### Thread Management
- Server uses ExecutorService with cached thread pool for handling multiple clients
- Client uses fixed thread pool with 2 threads:
  - Reader thread: Reads messages from server
  - Sender thread: Sends messages to server from stdin

## Features
- Multiple concurrent client connections
- Thread-safe message handling
- Support for multiple message formats
- Proper exception handling
- Graceful shutdown with resource cleanup


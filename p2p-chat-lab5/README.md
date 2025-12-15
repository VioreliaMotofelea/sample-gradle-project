# P2P Chat Lab 5

## Overview
Full peer-to-peer chat protocol implementation with concurrency, exception handling, and deadlock prevention.

## Protocol Specification

### Commands
- **`!hello <name>`** - Initialize a connection
  - First read message after sending should be `!ack`
  - If `!ack` is not received, connection is closed
  
- **`!ack`** - Acknowledge connection establishment
  - Sent by receiver after receiving `!hello`
  - Confirms successful connection establishment

- **`!bye`** - Close a specific connection

- **`!byebye`** - Close all connections and exit

### Protocol Flow
1. Peer A connects to Peer B
2. A sends: `!hello <A's name>`
3. B reads `!hello`, sends: `!ack`
4. A reads `!ack` (connection established)
5. Normal message exchange begins
6. Either peer can send `!bye` to close connection
7. `!byebye` closes all connections and exits

## Requirements Implementation

### Protocol Implementation (5 points) ✓
- Full implementation of all protocol commands
- Proper handshake mechanism
- Protocol validation and error handling

### Exceptions (2 points) ✓
- Handles IOException when connection closes unexpectedly
- Handles protocol errors (missing !ack, invalid commands)
- Graceful error recovery
- Proper exception handling when sender closes but reader still reads

### Concurrency (1.5 points) ✓
- Connection manager thread for accepting connections
- Separate message reader threads per connection
- Separate message sender threads per connection
- Thread-safe connection management using ConcurrentHashMap
- AtomicBoolean for connection state management

### Extra for Sockets (1.5 points) ✓
- Uses vanilla Java ServerSocket and Socket
- No external messaging libraries (optional JSON/Protobuf for encoding only)

### Bonus Points (10 points) ✓
- **Deadlock Prevention**
  - Separate ReentrantReadWriteLock for reading and writing operations
  - Ordered lock acquisition (read lock then write lock) to prevent deadlocks
  - Thread-safe connection closing from multiple threads
  - BlockingQueue for asynchronous message sending

- **JSON Support** (--json flag)
  - Messages encoded in JSON format with Jackson library
  
- **Protobuf Support** (--protobuf flag)
  - Custom protobuf-like encoding

## Usage

### Starting a Peer
```bash
# Raw string mode (default)
.\gradlew :p2p-chat-lab5:run --args="Alice 5000"

# JSON mode
.\gradlew :p2p-chat-lab5:run --args="Bob 5001 --json"

# Protobuf mode
.\gradlew :p2p-chat-lab5:run --args="Charlie 5002 --protobuf"
```

### Available Commands
Once started, you can use the following commands:

- **`connect <host> <port> <peer-name>`** - Connect to another peer
- **`send <peer-name> <message>`** - Send a message to a peer
- **`bye <peer-name>`** - Close connection to a specific peer
- **`byebye`** - Close all connections and exit
- **`list`** - List all active connections
- **`quit`** - Exit the application

### Example Session
```bash
# Terminal 1
.\gradlew :p2p-chat-lab5:run --args="Alice 5000"

# Terminal 2
.\gradlew :p2p-chat-lab5:run --args="Bob 5001"

# In Bob's terminal
> connect localhost 5000 Alice
[Connecting] to localhost:5000 as Alice
[Connected] to Alice
[Event] Connection established with Alice

# In Alice's terminal
[Event] Connection established with Bob

# In Bob's terminal
> send Alice Hello from Bob!
[Sent] to Alice: Hello from Bob!

# In Alice's terminal
[Bob] Hello from Bob!

# In Alice's terminal
> send Bob Hello from Alice!
[Sent] to Bob: Hello from Alice!

# In Bob's terminal
[Alice] Hello from Alice!
```

## Architecture

### Components

1. **P2PChatLab5** - Main application class
   - Handles user input
   - Manages connection manager lifecycle

2. **ConnectionManager** - Manages all peer connections
   - Accepts incoming connections
   - Establishes outgoing connections
   - Thread for accepting connections
   - Manages connection lifecycle

3. **PeerConnection** - Represents a single peer connection
   - Thread-safe read/write operations
   - Deadlock prevention using read-write locks
   - Message queue for asynchronous sending
   - Proper connection closing from multiple threads

4. **ProtocolParser** - Parses protocol commands
   - Validates protocol messages
   - Formats protocol commands

5. **MessageCodec** - Encodes/decodes messages
   - Supports RAW, JSON, and Protobuf formats

### Concurrency Model

#### Thread Pools
- **ExecutorService (cached thread pool)** - For connection handling and message processing
- **Connection Manager Thread** - Accepts incoming connections
- **Message Reader Threads** - One per connection, reads incoming messages
- **Message Sender Threads** - One per connection, flushes queued messages

#### Deadlock Prevention
1. **Separate Locks**: Read and write operations use separate ReentrantReadWriteLock instances
2. **Ordered Lock Acquisition**: Always acquire read lock before write lock in close()
3. **Atomic State Management**: AtomicBoolean for connection state
4. **Thread-Safe Collections**: ConcurrentHashMap for connection storage
5. **BlockingQueue**: Asynchronous message sending to avoid blocking

#### Exception Handling
- Handles IOException when connections close unexpectedly
- Catches exceptions in reader/writer threads independently
- Graceful connection cleanup on errors
- Protocol validation with proper error messages

## Features

- ✅ Full protocol implementation
- ✅ Thread-safe connection management
- ✅ Deadlock prevention
- ✅ Exception handling
- ✅ Multiple concurrent connections
- ✅ Graceful shutdown
- ✅ Protocol validation
- ✅ Support for multiple message formats (RAW, JSON, Protobuf)
- ✅ Asynchronous message sending
- ✅ Proper resource cleanup

## Testing

### Test Connection Establishment
1. Start two peers on different ports
2. Connect one to the other using `connect` command
3. Verify handshake completes with `!hello` and `!ack`
4. Verify bidirectional messaging works

### Test Protocol Errors
1. Try connecting without proper `!hello`
2. Verify connection is closed with error message

### Test Concurrency
1. Connect multiple peers simultaneously
2. Send messages from multiple threads
3. Verify no deadlocks occur

### Test Exception Handling
1. Close connection from one side
2. Verify other side handles disconnection gracefully
3. Verify reader thread exits cleanly


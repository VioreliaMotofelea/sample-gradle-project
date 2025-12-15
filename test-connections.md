# Testing Peer-to-Peer Connections

## Lab4: Client-Server Connection Test

### Manual Test Steps:

1. **Terminal 1 - Start Server:**
   ```powershell
   .\gradlew :lab4-p2p:run --args="server 5000"
   ```

2. **Terminal 2 - Connect Client:**
   ```powershell
   .\gradlew :lab4-p2p:run --args="client localhost 5000"
   ```

3. **In Terminal 2, type a message and press Enter**
   - You should see the message echoed back from the server
   - Example: Type "Hello Lab4" and you should see "echo: Hello Lab4"

4. **Test JSON mode:**
   - Server: `.\gradlew :lab4-p2p:run --args="server 5000 --json"`
   - Client: `.\gradlew :lab4-p2p:run --args="client localhost 5000 --json"`

## Lab5: Peer-to-Peer Connection Test

### Manual Test Steps:

1. **Terminal 1 - Start Peer Alice:**
   ```powershell
   .\gradlew :lab5-p2p:run --args="--name Alice --port 5000"
   ```

2. **Terminal 2 - Start Peer Bob:**
   ```powershell
   .\gradlew :lab5-p2p:run --args="--name Bob --port 5001"
   ```

3. **In Terminal 1 (Alice), connect to Bob:**
   ```
   !connect localhost 5001
   ```

4. **Verify connection:**
   - You should see messages like "[mgr] peer registered: Bob" in Alice's terminal
   - You should see "[mgr] peer registered: Alice" in Bob's terminal

5. **Send messages:**
   - In Alice's terminal: `Bob: Hello from Alice!`
   - In Bob's terminal: `Alice: Hello from Bob!`
   - Messages should appear in the other peer's terminal

6. **Test JSON mode:**
   - Alice: `.\gradlew :lab5-p2p:run --args="--name Alice --port 5000 --json"`
   - Bob: `.\gradlew :lab5-p2p:run --args="--name Bob --port 5001 --json"`

7. **Close connection:**
   - In either terminal: `!bye Bob` (or `!bye Alice`)
   - Or exit both: `!byebye`

## Expected Behavior

### Lab4:
- Server listens on specified port
- Client connects and can send messages
- Server echoes messages back
- Both JSON and plain text modes work

### Lab5:
- Peers can connect to each other using `!connect`
- Handshake (HELLO/ACK) completes successfully
- Messages can be sent between peers using `peerName: message` format
- Connections can be closed with `!bye` or `!byebye`
- Both JSON and plain text modes work

## Potential Issues to Check

1. **Port conflicts:** Make sure ports 5000, 5001 are not in use
2. **Firewall:** Windows Firewall might block connections
3. **Handshake timing:** If connections fail, check for race conditions in handshake
4. **Message encoding:** Verify JSON mode works correctly


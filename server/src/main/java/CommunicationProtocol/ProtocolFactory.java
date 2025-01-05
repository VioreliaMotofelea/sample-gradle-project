package CommunicationProtocol;

public class ProtocolFactory {
    public static Protocol getProtocol(String type){
        switch (type.toLowerCase())
        {
            case "json":
                return new JsonProtocol();
            case "xml":
                return new XMLProtocol();
            default:
                return new StringProtocol();
        }
    }
}

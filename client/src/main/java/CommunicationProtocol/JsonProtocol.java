package CommunicationProtocol;

import org.json.JSONObject;

public class JsonProtocol implements Protocol{
    @Override
    public String input(String input) {
        JSONObject json =  new JSONObject();
        json.append("message", input);
        return json.toString();
    }

    @Override
    public String output(String output) {
        JSONObject json = new JSONObject(output);
        return json.get("message").toString();
    }
}

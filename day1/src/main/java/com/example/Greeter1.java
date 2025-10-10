package com.example;

/**
 * This is a class.
 */
public class Greeter1 {

    /**
     * This method greets the person.
     * @param someone the person to greet
     * @return the greeting message
     * @throws Exception if the person is not valid
     */
    public String greet(String someone) {
        try {
            return String.format("Hello, %s!", someone);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}

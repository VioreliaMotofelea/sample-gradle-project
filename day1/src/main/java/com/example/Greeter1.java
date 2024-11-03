package com.example;

/**
 * This is a class.
 */
public class Greeter1 {

    /**
     * This is a constructor.
     */
    public Greeter1() {
    }

    /**
     * Greets the specified person by returning a greeting message.
     *
     * @param someone the name of a person
     * @return greeting string or null if an exception occurs
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

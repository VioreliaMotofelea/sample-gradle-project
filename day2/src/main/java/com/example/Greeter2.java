package com.example;

/**
 * This is a class.
 */
public class Greeter2 {

    /**
     * This is a constructor.
     */
    public Greeter2() {

    }

    /**
     * Greets the specified person by returning a greeting message.
     *
     * @param someone the name of a person
     * @return greeting string
     */
    public String greet(String someone) {
        return String.format("Hello, %s!", someone);
    }
}

package com.example;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.example.Greeter1;

public class TestGreeter1 {

    public Greeter1 greeter1;

    @Before
    public void setup() {
        greeter1 = new Greeter1();
    }

    @Test
    public void greetShouldIncludeTheOneBeingGreeted() {
        String someone = "World";

        assertThat(greeter1.greet(someone), containsString(someone));
    }

    @Test
    public void greetShouldIncludeGreetingPhrase() {
        String someone = "World";

        assertThat(greeter1.greet(someone).length(), is(greaterThan(someone.length())));
    }
}

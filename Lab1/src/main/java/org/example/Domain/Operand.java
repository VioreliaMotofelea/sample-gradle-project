package org.example.Domain;

public class Operand extends Operation{
    private Float value = null;

    public Operand() {
    }

    public Operand(float value) {
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}

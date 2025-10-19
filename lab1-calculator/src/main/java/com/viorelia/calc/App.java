package com.viorelia.calc;

public class App {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: app <op> <a> [b] or sqrt <x>");
            System.exit(1);
        }
        Calculator c = new Calculator();
        String op = args[0];
        try {
            if ("sqrt".equals(op)) {
                double x = Double.parseDouble(args[1]);
                System.out.println(c.sqrt(x));
                return;
            }
            double a = Double.parseDouble(args[1]);
            double b = Double.parseDouble(args[2]);
            switch (op) {
                case "add": System.out.println(c.add(a,b)); break;
                case "sub": System.out.println(c.sub(a,b)); break;
                case "mul": System.out.println(c.mul(a,b)); break;
                case "div": System.out.println(c.div(a,b)); break;
                case "min": System.out.println(c.min(a,b)); break;
                case "max": System.out.println(c.max(a,b)); break;
                default: System.err.println("Unknown op: " + op);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(2);
        }
    }
}

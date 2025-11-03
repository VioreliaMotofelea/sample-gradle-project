package com.viorelia.repo;

public class ManualTest {
    public static void main(String[] args) {
        InMemoryRepository<Order> repo = new HashSetBasedRepository<>();

        Order o1 = new Order(1, 100, 2);
        repo.add(o1);
        System.out.println(repo.contains(o1)); // true

        repo.remove(o1);
        System.out.println(repo.contains(o1)); // false

        // =================================================================================

        InMemoryRepository<String> r = new ArrayListBasedRepository<>();
        r.add("x");
        r.add("x"); // duplicate
        System.out.println(r.contains("x")); // true
        r.remove("x");
        System.out.println(r.contains("x")); // true
    }
}

package tech.ada.threads.pool;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolAnimals {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        Animal[] animals = {
            new Animal("Cachorro", "Au au"),
            new Animal("Gato", "Miau"),
            new Animal("Pato", "Quack"),
            new Animal("Leão", "Rugido"),
            new Animal("Vaca", "Muu"),
            new Animal("Galinha", "Cocoricó")
        };

        Arrays.stream(animals).forEach(animal -> {
            pool.execute(animal::fazerBarulho);
        });

        pool.shutdown();
    }

}

class Animal {
    private String nome;
    private String som;

    public Animal(String nome, String som) {
        this.nome = nome;
        this.som = som;
    }

    public void fazerBarulho() {
        System.out.println(nome + ": " + som);
    }
}
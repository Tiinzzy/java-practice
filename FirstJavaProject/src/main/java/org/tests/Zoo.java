package org.tests;


import java.util.ArrayList;
import java.util.List;

public class Zoo {
    final int MAX_WORKING_DAYS = 2 * 365;
    final int MAX_NO_OF_RENOVATION = 50;

    Location zooLocation = new Location(1);
    Location temporaryZooLocation = new Location(100);
    List<Cage> cages = null;
    List<Animal> animals = null;

    void putAnimalToProperCage(Animal a) {

    }

    void buyZooAnimals() {
        animals = new ArrayList<>();

        animals.add(new Lion("Chuck", 100));
        animals.add(new Lion("Diana", 140));
        animals.add(new Lion("Sunny", 80));
        animals.add(new Snake("Hiss", 50, 3));

        animals.forEach(this::putAnimalToProperCage);
    }

    void buyCages() {
        cages = new ArrayList<>();
        cages.add(new HardIronCage());
        cages.add(new GlassyCage());
    }

    void moveAnimalService(Location from, Location to) {
        animals.forEach(a -> a.move(from, to));
    }

    void renovateZoo() {
        System.out.println("Reinforce all the cages.");
        System.out.println("Clean up cages.");
        System.out.println("Paint cages.");
    }

    void sellTicketAndGiveServiceForDays(int days) {
        System.out.printf("Selling tickets and giving service to public for %d days\n", days);
    }

    void washAllAnimals() {
        animals.forEach(Animal::takeAShower);
    }

    void showAnimalFeature(Animal a) {
        String animalType = a.getClass().getName();
        if (animalType.equals("Lion")) {
            Lion l = (Lion) a;
            l.roar();
            l.jump();
        } else if (animalType.equals("Snake")) {
            ((Snake) a).bite();
        }
    }

    public static void main(String[] args) {
        Zoo z = new Zoo();
        z.buyCages();
        z.buyZooAnimals();

        for (int r = 0; r < z.MAX_NO_OF_RENOVATION; r++) {
            z.sellTicketAndGiveServiceForDays(z.MAX_WORKING_DAYS);
            z.moveAnimalService(z.zooLocation, z.temporaryZooLocation);
            z.renovateZoo();
            z.washAllAnimals();
            z.moveAnimalService(z.temporaryZooLocation, z.zooLocation);
        }
    }
}

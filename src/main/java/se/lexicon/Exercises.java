package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;


import java.time.LocalDate;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message){
        System.out.println(message);
        //Write your code here
        Predicate<Person> firstNameErik = person -> person.getFirstName().equalsIgnoreCase("Erik");
        List<Person> personList = storage.findMany(firstNameErik);
        personList.forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message){
        System.out.println(message);
        //Write your code here

        storage.findMany(person -> person.getGender() == Gender.FEMALE).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message){
        System.out.println(message);
        //Write your code here

        Predicate<Person> bornAfter = person -> person.getBirthDate().isAfter(LocalDate.parse("2020-01-01"))
                || person.getBirthDate().equals(LocalDate.parse("2020-01-01"));
        storage.findMany(bornAfter).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message){
        System.out.println(message);
        //Write your code here

        System.out.println(storage.findOne(person -> person.getId()==123));

        System.out.println("----------------------");

    }

    /*
        5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message){
        System.out.println(message);
        //Write your code here

        Predicate<Person> matchId456 = person -> person.getId() == 456;
        Function<Person, String> personString = person -> "Name: " + person.getFirstName() + " "
                                                         + person.getLastName() + " born "
                                                         + person.getBirthDate();

        System.out.println(storage.findOneAndMapToString(matchId456, personString));

        System.out.println("----------------------");
    }

    /*
        6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
     */
    public static void exercise6(String message){
        System.out.println(message);
        //Write your code here


        Predicate<Person> maleAndStartWithE = person -> person.getGender() == Gender.MALE && person.getFirstName().startsWith("E");
        Function<Person, String> eachPeopleToString = person -> person.toString();
        storage.findManyAndMapEachToString(maleAndStartWithE, eachPeopleToString).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message){
        System.out.println(message);
        //Write your code here

        Predicate<Person> ageBelowTen = person -> (LocalDate.now().getYear() - person.getBirthDate().getYear()) < 10;
        Function<Person, String> manyPeopleToString = person -> person.getFirstName() + " "
                                                                + person.getLastName() + " "
                                                                + (LocalDate.now().getYear() - person.getBirthDate().getYear())
                                                                + " years";
        storage.findManyAndMapEachToString(ageBelowTen, manyPeopleToString).forEach(System.out::println);


        System.out.println("----------------------");
    }

    /*
        8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message){
        System.out.println(message);
        //Write your code here

        Predicate<Person> firstNameUlf = person -> person.getFirstName().equals("Ulf");
        Consumer<Person> print = person -> System.out.println(person.toString());
        storage.findAndDo(firstNameUlf, print);

        System.out.println("----------------------");
    }

    /*
        9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message){
        System.out.println(message);
        //Write your code here

        Predicate<Person> lastNameContainFirstName = person -> person.getLastName().toUpperCase().contains
                (person.getFirstName().toUpperCase());
        storage.findAndDo(lastNameContainFirstName, person -> System.out.println(person.toString()));

        System.out.println("----------------------");
    }

    /*
        10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message){
        System.out.println(message);
        //Write your code here

        Predicate<Person> palindrome = person -> person.getFirstName().equalsIgnoreCase
                (new StringBuilder(person.getFirstName()).reverse().toString());
        Consumer<Person> printFullName = person ->
                System.out.println(person.getFirstName() + " " + person.getLastName());
        storage.findAndDo(palindrome, printFullName);

        System.out.println("----------------------");
    }

    /*
        11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message){
        System.out.println(message);
        //Write your code here

        Predicate<Person> firstNameStartWithA = person -> person.getFirstName().startsWith("A");
        Comparator<Person> byBirthDate = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getBirthDate().compareTo(o2.getBirthDate());
            }
        };
        storage.findAndSort(firstNameStartWithA, byBirthDate).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message){
        System.out.println(message);
        //Write your code here

        Predicate<Person> bornBefore1950 = person -> person.getBirthDate().isBefore
                (LocalDate.of(1950,01,01));

        Comparator<Person> lateToEarly = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o2.getBirthDate().compareTo(o1.getBirthDate());
            }
        };
        storage.findAndSort(bornBefore1950, lateToEarly).forEach(System.out::println);

        System.out.println("----------------------");
    }

    /*
        13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message){
        System.out.println(message);
        //Write your code here

        Comparator<Person> sort = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {

                int temp = o1.getLastName().compareTo(o2.getLastName());
                if (temp==0){
                    temp = o1.getFirstName().compareTo(o2.getFirstName());
                    if (temp == 0){
                        temp = o1.getBirthDate().compareTo(o2.getBirthDate());
                    }
                }
                return temp;
            }
        };
        storage.findAndSort(sort).forEach(System.out::println);

        System.out.println("----------------------");
    }
}

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        Predicate<Person> personPredicate = (o1) -> o1.getAge() < 18;

        Predicate<Person> conscriptPredicate = (o1) ->
                o1.getAge() >= 18 && o1.getAge() < 27 && o1.getSex().equals(Sex.MAN);

        Predicate<Person> hardWorkingPredicate = Main::hardWorkingPeople;

        System.out.println("Количество несовершеннолетних: " + persons.stream().filter(personPredicate).count());

        List<String> personConscript = persons.stream().filter(conscriptPredicate)
                .map(o1 -> o1.getFamily())
                .collect(Collectors.toList());

        //System.out.println(personConscript);

        List<Person> hardWorkingPerson = persons.stream().filter(hardWorkingPredicate)
                .filter(o1 -> o1.getEducation().equals(Education.HIGHER))
                .sorted(Comparator.comparing(Person::getFamily))
                .collect(Collectors.toList());

        //System.out.println(hardWorkingPerson);
    }

    public static boolean hardWorkingPeople(Person person) {
        if (person.getSex().equals(Sex.MAN)) {
            if (person.getAge() >= 18 && person.getAge() < 65) {
                return true;
            }
        } else {
            if (person.getAge() >= 18 && person.getAge() < 60) {
                return true;
            }
        }
        return false;
    }

}

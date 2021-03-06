package com.heedi.modernjavainaction.optional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalMain {

    public static void main(String[] args) {
        Person person = new Person(new Car(new Insurance("in")), 10);
        Person nullablePerson = new Person(new Car(null), 40);

        System.out.println(getCarInsuranceName(Optional.of(person)));
        System.out.println(getCarInsuranceName(Optional.of(nullablePerson)));
    }

    public static String getCarInsuranceName(Optional<Person> person) {
        return person
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

    public static List<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                .map(Person::getCar)
                .map(optCar -> optCar.flatMap(Car::getInsurance))
                .map(optIns -> optIns.map(Insurance::getName))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    public static Optional<Insurance> nullSafeFindCheapestInsurance(Optional<Person> person, Optional<Car> car) {
        if (person.isPresent() && car.isPresent()) {
            return Optional.of(findCheapestInsurance(person.get(), car.get()));
        } else {
            return Optional.empty();
        }
    }

    public static Optional<Insurance> nullSafeFindCheapestInsuranceUnWrap(Optional<Person> person, Optional<Car> car) {
        // person.map을 실행했다면 Optional<Optional<Insurance>>를 반환해야 할 것이다.
        return person.flatMap(p -> car.map(c -> findCheapestInsurance(p, c)));
    }

    private static Insurance findCheapestInsurance(Person p, Car c) {
        Insurance cheapestInsurance = new Insurance("cheap");

        return cheapestInsurance;
    }

    private static void isCambridgeInsurance(Insurance insurance) {
        Optional.ofNullable(insurance)
                .filter(i -> "CambridgeInsurance".equals(i.getName()))
                .ifPresent(x -> System.out.println("Ok"));
    }

    public static String getMinAgeCarInsuranceName(Optional<Person> person, int minAge) {
        return person
                .filter(p -> p.getAge() >= minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");
    }

    // 중첩 Optional이 생성되어 컴파일 실패
//    public String getCarInsuranceName(Person person) {
//        Optional<Person> optPersion = Optional.of(person);
//        Optional<String> name = optPersion.map(Person::getCar)
//                .map(Car::getInsurance)
//                .map(Insurance::getName);
//
//        return name.orElse("");
//    }

}

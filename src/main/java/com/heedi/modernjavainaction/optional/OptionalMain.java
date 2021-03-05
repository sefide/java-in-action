package com.heedi.modernjavainaction.optional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalMain {

    public static void main(String[] args) {
        Person person = new Person(new Car(new Insurance("in")));
        Person nullablePerson = new Person(new Car(null));

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

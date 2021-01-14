package com.example.demo;

import com.example.demo.model.Person;
import com.example.demo.repository.CityJobCount;
import com.example.demo.repository.JobCount;
import com.example.demo.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@Sql({"/person.sql"})
class PersonRepositoryTests {

    @Autowired
    private PersonRepository personRepo;


    @Test
    void checkAge() {
        Person person = personRepo.getOne(1L);
        System.out.println(person.getAge());
        assertThat(person.getAge() > 0).isTrue();
    }


    @Test
    void countByJob() {
        List<JobCount> result = personRepo.countByJob();
        result.forEach(System.out::println);
        assertThat(result).isSortedAccordingTo(Comparator.comparing(JobCount::getCount).reversed());
    }

    @Test
    void findTopJobs() {
        int top = 5;
        List<JobCount> top5Jobs = personRepo.findTopJobs(PageRequest.of(0, top));
        top5Jobs.forEach(System.out::println);
        assertThat(top5Jobs).hasSize(top).
                isSortedAccordingTo(Comparator.comparing(JobCount::getCount).reversed());
    }

    @Test
    void countJobsInCity() {
        List<CityJobCount> result = personRepo.countJobsInCity();
        assertThat(result.size()).isPositive();
    }

}
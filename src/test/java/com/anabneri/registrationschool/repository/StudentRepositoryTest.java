package com.anabneri.registrationschool.repository;

import com.anabneri.registrationschool.model.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    StudentRepository repository;

    @Test
    @DisplayName("Should return true when exists an student with a registration already created.")
    public void returnTrueWhenRegistrationExists() {

        String registration = "123";
        Student student = Student.builder().studentName("Ana Neri").dateOfRegistration("10/10/2021").registration(registration).build();
        entityManager.persist(student);

        boolean exists = repository.existsByRegistration(registration);

        assertThat(exists).isTrue();
    }


    @Test
    @DisplayName("Should return false when doesn't exists an student with a registration already created.")
    public void returnFalseWhenRegistrationDoestExists() {

        String registration = "123";

        boolean exists = repository.existsByRegistration(registration);

        assertThat(exists).isFalse();
    }

}

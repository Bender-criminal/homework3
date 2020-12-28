package ru.digitalhabbits.homework3.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.digitalhabbits.homework3.model.DepartmentInfo;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "person")
public class Person {

    @Id
    private Integer id;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(length = 80)
    private String middleName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column
    private Integer age;

    @OneToOne
    @JoinColumn(name = "departmentID")
    private DepartmentInfo department;
}

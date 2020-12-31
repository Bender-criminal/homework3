package ru.digitalhabbits.homework3.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PersonRequest {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer age;
    private DepartmentInfo departmentInfo;
}

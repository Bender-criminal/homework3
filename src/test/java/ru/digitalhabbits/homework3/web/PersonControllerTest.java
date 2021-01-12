package ru.digitalhabbits.homework3.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.digitalhabbits.homework3.model.DepartmentInfo;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;
import ru.digitalhabbits.homework3.service.PersonService;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    private PersonResponse personResponse1 = new PersonResponse()
            .setId(100500)
            .setFullName("Pupkin")
            .setAge(30)
            .setDepartment(new DepartmentInfo()
                .setId(666)
                .setName("IT"));
    private PersonResponse personResponse2 = new PersonResponse()
            .setId(555)
            .setFullName("Ivanov")
            .setAge(40)
            .setDepartment(new DepartmentInfo()
                .setId(666)
                .setName("IT"));
    private List<PersonResponse> personResponses = new ArrayList<>(Arrays.asList(personResponse1, personResponse2));

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void persons() throws Exception {

        Mockito.when(personService.findAllPersons()).thenReturn(personResponses);

        mockMvc.perform(
                get("/api/v1/persons/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{id: 100500, fullName: Pupkin, age: 30}, {id: 555, fullName: Ivanov, age: 40}]"));
    }

    @Test
    void person() throws Exception {

        Mockito.when(personService.getPerson(anyInt())).thenReturn(personResponse1);

        mockMvc.perform(
                get("/api/v1/persons/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{id: 100500, fullName: Pupkin, age: 30}"));
    }

    @Test
    void createPerson() throws Exception {

        Mockito.when(personService.createPerson(any(PersonRequest.class))).thenReturn(100500);

        mockMvc.perform(post("/api/v1/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Vasiliy\", \"middleName\":\"Ivanovich\",\"lastName\":\"Pupkin\", \"age\":\"30\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void updatePerson() throws Exception {

        Mockito.when(personService.updatePerson(anyInt(), any(PersonRequest.class))).thenReturn(personResponse1);

        mockMvc.perform(patch("/api/v1/persons/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"lastName\":\"Putin\", \"age\":\"65\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{id:100500, fullName:Pupkin, age: 30}"));
    }

    @Test
    void deletePerson() throws Exception {

        mockMvc.perform(delete("/api/v1/persons/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(personService, times(1)).deletePerson(anyInt());
    }
}
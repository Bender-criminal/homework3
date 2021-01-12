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
import ru.digitalhabbits.homework3.model.DepartmentRequest;
import ru.digitalhabbits.homework3.model.DepartmentResponse;
import ru.digitalhabbits.homework3.model.DepartmentShortResponse;
import ru.digitalhabbits.homework3.service.DepartmentService;

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
@WebMvcTest(controllers = DepartmentController.class)
class DepartmentControllerTest {

    private DepartmentResponse departmentResponse = new DepartmentResponse()
            .setId(1)
            .setName("IT")
            .setClosed(false);

    private List<DepartmentShortResponse> responses = new ArrayList<>(Arrays.asList(
            new DepartmentShortResponse().setId(1).setName("IT"),
            new DepartmentShortResponse().setId(2).setName("Buh")));

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void departments() throws Exception {

        Mockito.when(departmentService.findAllDepartments()).thenReturn(responses);

        mockMvc.perform(
                get("/api/v1/departments/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{id: 1, name: IT}, {id: 2, name: Buh}]"));
    }

    @Test
    void department() throws Exception {

        Mockito.when(departmentService.getDepartment(anyInt())).thenReturn(departmentResponse);

        mockMvc.perform(get("/api/v1/departments/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{id:1, name: IT, closed: false}"));
    }

    @Test
    void createDepartment() throws Exception {

        Mockito.when(departmentService.createDepartment(any(DepartmentRequest.class))).thenReturn(666);

        mockMvc.perform(post("/api/v1/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"IT\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void updateDepartment() throws Exception {

        Mockito.when(departmentService.updateDepartment(anyInt(), any(DepartmentRequest.class))).thenReturn(departmentResponse);

        mockMvc.perform(patch("/api/v1/departments/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"IT\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{id:1, name:IT, closed: false}"));
    }

    @Test
    void deleteDepartment() throws Exception {

        mockMvc.perform(delete("/api/v1/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(departmentService, times(1)).deleteDepartment(anyInt());
    }

    @Test
    void addPersonToDepartment() throws Exception {

        mockMvc.perform(post("/api/v1/departments/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(departmentService, times(1)).addPersonToDepartment(anyInt(), anyInt());
    }

    @Test
    void removePersonToDepartment() throws Exception {

        mockMvc.perform(delete("/api/v1/departments/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(departmentService, times(1)).removePersonToDepartment(anyInt(), anyInt());
    }

    @Test
    void closeDepartment() throws Exception {

        mockMvc.perform(post("/api/v1/departments/1/close")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(departmentService, times(1)).closeDepartment(anyInt());
    }
}
package com.classbox.crud.application.tests;

import com.classbox.crud.application.controllers.PersonController;
import com.classbox.crud.application.repositories.PersonRepository;
import com.classbox.crud.application.services.SimpleFileUploadService;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerUnitTest {

    @MockBean
    private PersonRepository personRepository;

    @MockBean
    private SimpleFileUploadService simpleFileUploadService;

    @Autowired
    PersonController personController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPersonControllerInjected_thenCorrect() throws Exception {
        assertThat(personController).isNotNull();
    }

    @Test
    public void whenGetHttpRequestToSearchPersons_thenCorrectViewName() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/searchpersons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void whenGetHttpRequestToSearchPersonsAndCriterioDniAndValueRequestParams_thenCorrectViewName() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/searchpersons")
                        .param("criterio", "dni")
                        .param("valor", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void whenGetHttpRequestToSearchPersonsAndCriterioEdadAndValueRequestParams_thenCorrectViewName() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/searchpersons")
                        .param("criterio", "edad")
                        .param("valor", "30"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void whenGetHttpRequestToSearchPersonsAndCriterioNombreAndValueRequestParams_thenCorrectViewName() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/searchpersons")
                        .param("criterio", "nombre")
                        .param("valor", "john"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void whenGetHttpRequestToSearchPersons_thenCorrectModelAttributeType() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/searchpersons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("persons", new ArrayList<>()));
    }

    @Test
    public void whenGetHttpRequestToAddPerson_thenCorrectViewName() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/addperson"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("addperson"));
    }

    @Test
    public void whenPosttHttpRequestToAddPerson_thenCorrectViewName() throws Exception {
        MockMultipartFile image = new MockMultipartFile("imagen", "imagen.txt", "text/plain", "text".getBytes());
        this.mockMvc
                .perform(MockMvcRequestBuilders.multipart("/addperson")
                        .file(image)
                        .param("dni", "1")
                        .param("nombre", "john")
                        .param("apellido", "Smith")
                        .param("edad", "40")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void whenPosttHttpRequestToAddPerson_thenCorrectModelAttributeType() throws Exception {
        MockMultipartFile image = new MockMultipartFile("imagen", "imagen.txt", "text/plain", "text".getBytes());
        this.mockMvc
                .perform(MockMvcRequestBuilders.multipart("/addperson")
                        .file(image)
                        .param("dni", "1")
                        .param("nombre", "john")
                        .param("apellido", "Smith")
                        .param("edad", "40")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("persons", new ArrayList<>()));
    }
}

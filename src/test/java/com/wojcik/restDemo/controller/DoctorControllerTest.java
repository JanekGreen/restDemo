package com.wojcik.restDemo.controller;

import com.wojcik.restDemo.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({"/doctor_test.sql"})
@SpringBootTest
public class DoctorControllerTest {

    private MockMvc mockMvc;

    @Resource
    private DoctorRepository doctorRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void shouldGetDoctor() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("http://localost/doctors/doctor/12")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String expected = "{\"id\":12,\"name\":\"Tomasz\",\"surname\":\"Kolodziej\"}";
        assertThat(result.getResponse().getContentAsString(), is(expected));
    }

    @Test
    public void shouldNotFindDoctor() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localost/doctors/doctor/99")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldSaveDoctorViaEndpoint() throws Exception {
        String content = "{\"name\":\"Jan\",\"surname\":\"Mlynarczyk\"}";
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.POST, "http://localhost/doctors/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk());

        assertThat(doctorRepository.findAll().stream()
                .anyMatch(d -> d.getSurname().equals("Mlynarczyk")), is(true));
    }

    @Test
    public void shouldEditDoctorViaEndpoint() throws Exception {
        String content = "{\"name\":\"Jan\",\"surname\":\"Mlynarczyk\"}";
        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.PUT, "http://localost/doctors/doctor/12")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isOk());

        assertThat(doctorRepository.findAll().stream()
                .anyMatch(d -> d.getSurname().equals("Mlynarczyk")), is(true));
        assertThat(doctorRepository.findAll().stream()
                .anyMatch(d -> d.getSurname().equals("Kolodziej")), is(false));
    }

    @Test
    public void shouldGetAllDoctors() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost/doctors/")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String expected = "[{\"id\":11,\"name\":\"Pawel\",\"surname\":\"Wojcik\"},{\"id\":12,\"name\":\"Tomasz\",\"surname\":\"Kolodziej\"}" +
                ",{\"id\":13,\"name\":\"Aleksandra\",\"surname\":\"Kowalska\"}]";
        assertThat(result.getResponse().getContentAsString(), is(expected));
    }

    @Test
    public void shouldGetAllDoctorsWithComments() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("http://localost/doctors/withComments")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        String expected = "[{\"doctorDto\":{\"id\":11,\"name\":\"Pawel\",\"surname\":\"Wojcik\"},\"comments\":[{\"id\":14,\"comment\":\"git\"}]}," +
                "{\"doctorDto\":{\"id\":12,\"name\":\"Tomasz\",\"surname\":\"Kolodziej\"},\"comments\":[{\"id\":15,\"comment\":\"wspaniale\"},{\"id\":16,\"comment\":\"Elegancko\"}]},{\"doctorDto\":{\"id\":13,\"name\":\"Aleksandra\",\"surname\":\"Kowalska\"},\"comments\":[]}]";
        assertThat(result.getResponse().getContentAsString(), is(expected));
    }

    @Test
    public void shouldDeleteDoctorViaEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localost/doctors/doctor/12")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("http://localost/doctors/doctor/12")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

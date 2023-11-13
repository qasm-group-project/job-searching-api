package uk.ac.le.qasm.job.searching.api.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import uk.ac.le.qasm.job.searching.api.entity.JobSeekerAccount;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class JobSeekerACControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreate() throws Exception{
        Gson gson = new Gson();

        JobSeekerAccount jobSeekerAccount = new JobSeekerAccount();

        jobSeekerAccount.setGender("male");
        jobSeekerAccount.setEmail("test@gmail.com");
        jobSeekerAccount.setFirstname("John");
        jobSeekerAccount.setLastname("QAQ");
        jobSeekerAccount.setPhone("444455555");
        jobSeekerAccount.setAc_username("TTTTYYYY");
        jobSeekerAccount.setAc_password("password");

        String jsonString = gson.toJson(jobSeekerAccount);

        mockMvc.perform(post("http://localhost:8181/seekers")
                .content(jsonString.getBytes())
                .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

}
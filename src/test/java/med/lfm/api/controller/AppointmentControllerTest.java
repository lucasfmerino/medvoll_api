package med.lfm.api.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import med.lfm.api.domain.appointment.AppointmentDetailsDTO;
import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;
import med.lfm.api.domain.appointment.Schedule;
import med.lfm.api.domain.doctor.Specialism;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AppointmentSchedulingDTO> appointmentSchedulingJson;

    @Autowired
    private JacksonTester<AppointmentDetailsDTO> appointmentDetailsJson;

    @MockBean
    private Schedule schedule;

    @Test
    @DisplayName("Should return http code 400 for invalid information")
    @WithMockUser
    void appointmentScheduling01() throws Exception {
        var response = mvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return http code 200 for valid information")
    @WithMockUser
    void appointmentScheduling02() throws Exception {
        var date = LocalDateTime.now().plusHours(1);
        var specialism = Specialism.CARDIOLOGIA;

        var appointmentDetails = new AppointmentDetailsDTO(null, 4l, 5l, date);

        when(schedule.toSchedule(any())).thenReturn(appointmentDetails);

        var response = mvc
                .perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentSchedulingJson.write(new AppointmentSchedulingDTO(4l, 5l, date, specialism))
                                .getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var responseJson = appointmentDetailsJson.write(appointmentDetails)
                .getJson();

        assertThat(response.getContentAsString()).isEqualTo(responseJson);
    }

}

package med.lfm.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

import med.lfm.api.domain.address.Address;
import med.lfm.api.domain.address.AddressDTO;
import med.lfm.api.domain.doctor.Doctor;
import med.lfm.api.domain.doctor.DoctorRepository;
import med.lfm.api.domain.doctor.MedicalDetailsDTO;
import med.lfm.api.domain.doctor.MedicalRegistrationDTO;
import med.lfm.api.domain.doctor.Specialism;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DoctorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<MedicalRegistrationDTO> medicalRegistrationJson;

    @Autowired
    private JacksonTester<MedicalDetailsDTO> medicalDetailsJson;

    @MockBean
    private DoctorRepository repository;

    @Test
    @DisplayName("Should return http code 400 when information is invalid")
    @WithMockUser
    void register01() throws Exception {
        var response = mvc
                .perform(post("/medicos"))
                .andReturn().getResponse();

        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Should return http code 200 when information is valid")
    @WithMockUser
    void register02() throws Exception {
        var medicalRegistration = new MedicalRegistrationDTO(
                "Medico",
                "medico@voll.med",
                "61999999999",
                "123456",
                Specialism.CARDIOLOGIA,
                addressData());

        when(repository.save(any())).thenReturn(new Doctor(medicalRegistration));

        var response = mvc
                .perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(medicalRegistrationJson.write(medicalRegistration).getJson()))
                .andReturn().getResponse();

        var medicalDetails = new MedicalDetailsDTO(
                null,
                medicalRegistration.nome(),
                medicalRegistration.email(),
                medicalRegistration.crm(),
                medicalRegistration.telefone(),
                medicalRegistration.especialidade(),
                new Address(medicalRegistration.endereco()));
        var jsonResponse = medicalDetailsJson.write(medicalDetails).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonResponse);
    }

    private AddressDTO addressData() {
        return new AddressDTO(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }
}

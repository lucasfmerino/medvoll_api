package med.lfm.api.domain.doctor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.lfm.api.domain.address.AddressDTO;
import med.lfm.api.domain.appointment.Appointment;
import med.lfm.api.domain.patient.Patient;
import med.lfm.api.domain.patient.PatientRegistrationDTO;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("It should return null when the only registered doctor is not available on the date")
    void choseRaondomDoctorOnDate01() {
        // given or arrange
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Medico", "medico@voll.med", "123456", Specialism.CARDIOLOGIA);
        var patient = registerPatient("Paciente", "paciente@email.com", "00000000000");
        registerAppointment(doctor, patient, nextMondayAt10);

        // when or act
        var freeDoctor = doctorRepository.choseRaondomDoctorOnDate(Specialism.CARDIOLOGIA, nextMondayAt10);

        // then or assert
        assertThat(freeDoctor).isNull();
    }


    @Test
    @DisplayName("Should return the doctor when he is available on the date")
    void choseRaondomDoctorOnDate02() {

        // given or arrange
        var nextMondayAt10 = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Medico", "medico@voll.med", "123456", Specialism.CARDIOLOGIA);

        // when or act
        var freeDoctor = doctorRepository.choseRaondomDoctorOnDate(Specialism.CARDIOLOGIA, nextMondayAt10);

        // then or assert
        assertThat(freeDoctor).isEqualTo(doctor);
    }


    private void registerAppointment(Doctor medico, Patient paciente, LocalDateTime data) {
        em.persist(new Appointment(null, medico, paciente, data, null));
    }

    private Doctor registerDoctor(String nome, String email, String crm, Specialism especialidade) {
        var medico = new Doctor(doctorData(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }

    private Patient registerPatient(String nome, String email, String cpf) {
        var paciente = new Patient(patientData(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }

    private MedicalRegistrationDTO doctorData(String nome, String email, String crm, Specialism especialidade) {
        return new MedicalRegistrationDTO(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                addressData());
    }

    private PatientRegistrationDTO patientData(String nome, String email, String cpf) {
        return new PatientRegistrationDTO(
                nome,
                email,
                "61999999999",
                cpf,
                addressData());
    }

    private AddressDTO addressData() {
        return new AddressDTO(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                "",
                null);
    }
}

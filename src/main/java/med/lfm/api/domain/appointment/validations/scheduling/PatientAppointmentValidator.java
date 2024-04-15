package med.lfm.api.domain.appointment.validations.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.lfm.api.domain.ValidationException;
import med.lfm.api.domain.appointment.AppointmentRepository;
import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;

@Component
public class PatientAppointmentValidator implements AppointmentValidator {
    
    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentSchedulingDTO data) {
        var firstTime = data.data().withHour(7);
        var lastTime = data.data().withHour(18);
        var patientHasAnAppointmentOnTheDay = repository.existsByPacienteIdAndDataBetween(data.idPaciente(), firstTime, lastTime);

        if (patientHasAnAppointmentOnTheDay) {
            throw new ValidationException("Paciente já possui uma consulta agendada nesse dia.");
        }
    }
}

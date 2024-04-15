package med.lfm.api.domain.appointment.validations;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.lfm.api.domain.ValidationException;
import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;

@Component
public class AdvanceTimeValidator implements AppointmentValidator {

    public void validate(AppointmentSchedulingDTO data) {

        var appointmentDate = data.data();
        var now = LocalDateTime.now();
        var differenceInMinutes = Duration.between(now, appointmentDate).toMinutes();

        if (differenceInMinutes < 30) {
            throw new ValidationException("Consulta deve ser agendada com antecedência mínima de 30 minutos.");
        }
    }
}

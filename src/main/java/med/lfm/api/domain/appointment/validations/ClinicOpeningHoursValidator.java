package med.lfm.api.domain.appointment.validations;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import med.lfm.api.domain.ValidationException;
import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;

@Component
public class ClinicOpeningHoursValidator implements AppointmentValidator {

    public void validate(AppointmentSchedulingDTO data) {

        var appointmentDate = data.data();

        var sunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeTheClinicOpens = appointmentDate.getHour() < 7;
        var afterTheClinicCloses = appointmentDate.getHour() > 18;

        if (sunday || beforeTheClinicOpens || afterTheClinicCloses) {
            throw new ValidationException("Consulta fora do horário de funcionamento da clínica.");
        }
    }
}

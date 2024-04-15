package med.lfm.api.domain.appointment.validations.cancellation;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.lfm.api.domain.ValidationException;
import med.lfm.api.domain.appointment.AppointmentRepository;
import med.lfm.api.domain.appointment.CancelingAppointmentDTO;

@Component("CancelationAdvanceTimeValidator")
public class AdvanceTimeValidator implements AppointmentCancellationValidator {
    
    @Autowired
    private AppointmentRepository repository;

    public void validate(CancelingAppointmentDTO data) {
        var appointment = repository.getReferenceById(data.idConsulta());
        var now = LocalDateTime.now();
        var differenceInHours = Duration.between(now, appointment.getData()).toHours();

        if (differenceInHours < 24) {
            throw new ValidationException("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }
    }
}

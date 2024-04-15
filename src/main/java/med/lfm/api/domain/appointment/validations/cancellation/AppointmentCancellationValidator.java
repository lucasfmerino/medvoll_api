package med.lfm.api.domain.appointment.validations.cancellation;

import med.lfm.api.domain.appointment.CancelingAppointmentDTO;

public interface AppointmentCancellationValidator {
    
    void validate (CancelingAppointmentDTO dados);
}

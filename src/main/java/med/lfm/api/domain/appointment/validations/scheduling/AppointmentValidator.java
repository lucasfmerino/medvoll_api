package med.lfm.api.domain.appointment.validations.scheduling;

import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;

public interface AppointmentValidator {

    void validate(AppointmentSchedulingDTO data);
}
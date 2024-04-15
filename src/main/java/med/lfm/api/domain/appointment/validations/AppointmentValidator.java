package med.lfm.api.domain.appointment.validations;

import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;

public interface AppointmentValidator {

    void validate(AppointmentSchedulingDTO data);
}
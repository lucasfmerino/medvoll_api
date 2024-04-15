package med.lfm.api.domain.appointment.validations.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.lfm.api.domain.ValidationException;
import med.lfm.api.domain.appointment.AppointmentRepository;
import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;

@Component
public class DoctorUnavailableOnTimeValidator implements AppointmentValidator {
    
    @Autowired
    private AppointmentRepository repository;

    public void validate(AppointmentSchedulingDTO data) {
        var unavailableDoctor = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(data.idMedico(), data.data());
        if (unavailableDoctor) {
            throw new ValidationException("O médico selecionado já possuí outra consulta agendada nesse mesmo horário.");
        }
    }
}

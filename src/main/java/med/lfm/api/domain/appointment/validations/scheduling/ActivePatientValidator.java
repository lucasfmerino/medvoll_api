package med.lfm.api.domain.appointment.validations.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.lfm.api.domain.ValidationException;
import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;
import med.lfm.api.domain.patient.PatientRepository;

@Component
public class ActivePatientValidator implements AppointmentValidator {
    
    @Autowired
    private PatientRepository repository;

    public void validate(AppointmentSchedulingDTO data) {
        var activePatient = repository.findAtivoById(data.idPaciente());
        if (!activePatient) {
            throw new ValidationException("Consulta não pode ser agendada para um paciente excluído.");
        }
    }
}

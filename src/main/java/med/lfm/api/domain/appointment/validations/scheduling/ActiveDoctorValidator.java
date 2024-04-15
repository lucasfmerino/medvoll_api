package med.lfm.api.domain.appointment.validations.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.lfm.api.domain.ValidationException;
import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;
import med.lfm.api.domain.doctor.DoctorRepository;

@Component
public class ActiveDoctorValidator implements AppointmentValidator {
    
    @Autowired
    private DoctorRepository repository;

    public void validate(AppointmentSchedulingDTO data) {
        if (data.idMedico() == null) {
            return;
        }

        var activeDoctor = repository.findAtivoById(data.idMedico());
        if (!activeDoctor) {
            throw new ValidationException("Consulta não pode ser agendada com médico inativo");
        }
    }
}

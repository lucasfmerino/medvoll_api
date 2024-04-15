package med.lfm.api.domain.appointment;

import java.time.LocalDateTime;

public record AppointmentDetailsDTO(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {

    public AppointmentDetailsDTO(Appointment consulta) {
        this(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getData());
    }
}
package med.lfm.api.domain.appointment;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record AppointmentSchedulingDTO(
        Long idMedico,

        @NotNull Long idPaciente,

        @NotNull @Future LocalDateTime data) {
}
package med.lfm.api.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record CancelingAppointmentDTO(
    @NotNull
    Long idConsulta,

    @NotNull
    CancellationReason motivo) {
}
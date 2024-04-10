package med.lfm.api.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.lfm.api.address.AddressDTO;

public record PatientUpdateDTO(
        @NotNull Long id,
        String nome,
        String telefone,
        @Valid AddressDTO endereco) {
}

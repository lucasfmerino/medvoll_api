package med.lfm.api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import med.lfm.api.domain.address.AddressDTO;

public record MedicalUpdateDTO(
        @NotNull Long id,
        String nome,
        String telefone,
        AddressDTO endereco) {
}
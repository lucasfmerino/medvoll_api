package med.lfm.api.doctor;

import jakarta.validation.constraints.NotNull;
import med.lfm.api.address.AddressDTO;

public record MedicalUpdateDTO(
        @NotNull Long id,
        String nome,
        String telefone,
        AddressDTO endereco) {
}
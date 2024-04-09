package med.lfm.api.doctor;

import med.lfm.api.address.AddressDTO;

public record MedicalRegistrationDTO(String nome, String email, String orm, Specialism especialidade, AddressDTO endereco) {
}
package med.lfm.api.domain.doctor;

import med.lfm.api.domain.address.Address;

public record MedicalDetailsDTO(Long id, String nome, String email, String crm, String telefone,
        Specialism especialidade, Address endereco) {
    public MedicalDetailsDTO (Doctor doctor) {
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getTelefone(), doctor.getEspecialidade(), doctor.getEndereco());
    }
}

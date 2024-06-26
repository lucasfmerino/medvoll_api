package med.lfm.api.domain.patient;

public record PatientListingDTO(Long id, String nome, String email, String cpf) {
    public PatientListingDTO(Patient patient) {
        this(patient.getId(), patient.getNome(), patient.getEmail(), patient.getCpf());
    }
}

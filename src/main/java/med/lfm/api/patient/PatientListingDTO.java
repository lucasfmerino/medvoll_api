package med.lfm.api.patient;

public record PatientListingDTO(String nome, String email, String cpf) {
    public PatientListingDTO(Patient patient) {
        this(patient.getNome(), patient.getEmail(), patient.getCpf());
    }
}

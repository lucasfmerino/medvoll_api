package med.lfm.api.domain.doctor;

public record DoctorListingDTO(Long id, String nome, String email, String crm, Specialism especialidade) {
    
    public DoctorListingDTO(Doctor doctor) {
        this(doctor.getId(), doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getEspecialidade());
    }
}

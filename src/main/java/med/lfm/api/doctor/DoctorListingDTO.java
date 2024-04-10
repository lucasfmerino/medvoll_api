package med.lfm.api.doctor;

public record DoctorListingDTO(String nome, String email, String crm, Specialism especialidade) {
    
    public DoctorListingDTO(Doctor doctor) {
        this(doctor.getNome(), doctor.getEmail(), doctor.getCrm(), doctor.getEspecialidade());
    }
}

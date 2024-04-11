package med.lfm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.lfm.api.patient.Patient;
import med.lfm.api.patient.PatientDetailsDTO;
import med.lfm.api.patient.PatientListingDTO;
import med.lfm.api.patient.PatientRegistrationDTO;
import med.lfm.api.patient.PatientRepository;
import med.lfm.api.patient.PatientUpdateDTO;

@RestController
@RequestMapping("pacientes")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(
            @RequestBody @Valid PatientRegistrationDTO data,
            UriComponentsBuilder uriBuilder) {
        var patient = new Patient(data);
        repository.save(patient);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(patient.getId()).toUri();
        return ResponseEntity.created(uri).body(new PatientDetailsDTO(patient));
    }

    @GetMapping
    public ResponseEntity<Page<PatientListingDTO>> getPatients(
            @PageableDefault(size = 10, sort = { "nome" }) Pageable pagination) {
        var page = repository.findAllByAtivoTrue(pagination).map(PatientListingDTO::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        return ResponseEntity.ok(new PatientDetailsDTO(patient));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<?> updatePatient(@RequestBody @Valid PatientUpdateDTO data) {
        var patient = repository.getReferenceById(data.id());
        patient.updateData(data);
        return ResponseEntity.ok(new PatientDetailsDTO(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletePatient(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        patient.softDelete();
        return ResponseEntity.noContent().build();
    }
}

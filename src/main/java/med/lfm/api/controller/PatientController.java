package med.lfm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.lfm.api.patient.Patient;
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
    public void register(@RequestBody @Valid PatientRegistrationDTO data) {
        repository.save(new Patient(data));
    }

    @GetMapping
    public Page<PatientListingDTO> getPatients(@PageableDefault(size = 10, sort = {"nome"}) Pageable pagination) {
        return repository.findAllByAtivoTrue(pagination).map(PatientListingDTO::new);
    }

    @PutMapping
    @Transactional
    public void updatePatient(@RequestBody @Valid PatientUpdateDTO data) {
        var patient = repository.getReferenceById(data.id());
        patient.updateData(data);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void deleteDoctor(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        patient.softDelete();
    }
}

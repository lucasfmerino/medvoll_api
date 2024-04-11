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
import med.lfm.api.doctor.Doctor;
import med.lfm.api.doctor.DoctorListingDTO;
import med.lfm.api.doctor.DoctorRepository;
import med.lfm.api.doctor.MedicalDetailsDTO;
import med.lfm.api.doctor.MedicalRegistrationDTO;
import med.lfm.api.doctor.MedicalUpdateDTO;

@RestController
@RequestMapping("medicos")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> register(@RequestBody @Valid MedicalRegistrationDTO data, UriComponentsBuilder uriBuilder) {
        
        var doctor = new Doctor(data);
        repository.save(doctor);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new MedicalDetailsDTO(doctor));
    }

/* 
    SEM PAGINAÇÃO
    @GetMapping
    public List<DoctorListingDTO> getDoctors() {
        return repository.findAll().stream().map(DoctorListingDTO::new).toList();
    }
 */

/* 
    LISTAR TODOS OS MÉDICOS
    @GetMapping
    public Page<DoctorListingDTO> getDoctors(@PageableDefault(size = 10, sort = {"nome"}) Pageable pagination) {
        return repository.findAll(pagination).map(DoctorListingDTO::new);
    }
 */


// LISTAR MEDICOS ATIVOS
    @GetMapping
    public ResponseEntity<Page<DoctorListingDTO>> getDoctors(@PageableDefault(size = 10, sort = {"nome"}) Pageable pagination) {
        var page =  repository.findAllByAtivoTrue(pagination).map(DoctorListingDTO::new);
        return ResponseEntity.ok(page);
    }


    @PutMapping
    @Transactional
    public ResponseEntity<?> updateDoctor(@RequestBody @Valid MedicalUpdateDTO data) {
        var doctor = repository.getReferenceById(data.id());
        doctor.updateData(data);
        return ResponseEntity.ok(new MedicalDetailsDTO(doctor));
    }


    // DELETE PERMANENTE
/*     
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteDoctor(@PathVariable Long id) {
        repository.deleteById(id);
    }
     */


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteDoctor(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        doctor.softDelete();
        return ResponseEntity.noContent().build();
    }

    

}

package med.lfm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.lfm.api.doctor.Doctor;
import med.lfm.api.doctor.DoctorListingDTO;
import med.lfm.api.doctor.DoctorRepository;
import med.lfm.api.doctor.MedicalRegistrationDTO;

@RestController
@RequestMapping("medicos")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid MedicalRegistrationDTO data) {
        repository.save(new Doctor(data));
    }

/* 
    SEM PAGINAÇÃO
    @GetMapping
    public List<DoctorListingDTO> getDoctors() {
        return repository.findAll().stream().map(DoctorListingDTO::new).toList();
    }
 */

    @GetMapping
    public Page<DoctorListingDTO> getDoctors(@PageableDefault(size = 10, sort = {"nome"}) Pageable pagination) {
        return repository.findAll(pagination).map(DoctorListingDTO::new);
    }

}

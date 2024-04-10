package med.lfm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.lfm.api.doctor.Doctor;
import med.lfm.api.doctor.DoctorRepository;
import med.lfm.api.doctor.MedicalRegistrationDTO;

@RestController
@RequestMapping("medicos")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    public void register(@RequestBody MedicalRegistrationDTO data) {
        repository.save(new Doctor(data));
    }
}

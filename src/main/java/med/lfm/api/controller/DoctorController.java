package med.lfm.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.lfm.api.doctor.MedicalRegistrationDTO;

@RestController
@RequestMapping("medicos")
public class DoctorController {

    @PostMapping
    public void register(@RequestBody MedicalRegistrationDTO data) {
        System.out.println(data);
    }
}

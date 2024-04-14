package med.lfm.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.lfm.api.domain.appointment.AppointmentDetailsDTO;
import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;

@RestController
@RequestMapping("consultas")
public class AppointmentController {

    @PostMapping
    @Transactional
    public ResponseEntity<?> appointmentScheduling(@RequestBody @Valid AppointmentSchedulingDTO data) {
        System.out.println(data);
        return ResponseEntity.ok(new AppointmentDetailsDTO(null, null, null, null));
    }
    
}

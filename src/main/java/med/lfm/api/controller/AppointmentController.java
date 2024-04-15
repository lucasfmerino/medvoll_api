package med.lfm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.lfm.api.domain.appointment.AppointmentSchedulingDTO;
import med.lfm.api.domain.appointment.CancelingAppointmentDTO;
import med.lfm.api.domain.appointment.Schedule;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key") 
public class AppointmentController {

    @Autowired
    private Schedule schedule;

    @PostMapping
    @Transactional
    public ResponseEntity<?> appointmentScheduling(@RequestBody @Valid AppointmentSchedulingDTO data) {
        var dto = schedule.toSchedule(data);
        return ResponseEntity.ok(dto);
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity<?> cancel(@RequestBody @Valid CancelingAppointmentDTO data) {
        schedule.cancel(data);
        return ResponseEntity.noContent().build();
    }

}

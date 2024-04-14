package med.lfm.api.domain.appointment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.lfm.api.domain.ValidationException;
import med.lfm.api.domain.doctor.Doctor;
import med.lfm.api.domain.doctor.DoctorRepository;
import med.lfm.api.domain.patient.PatientRepository;

@Service
public class Schedule {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public void toSchedule(AppointmentSchedulingDTO data) {

        if (!patientRepository.existsById(data.idPaciente())) {
            throw new ValidationException("Paciente não encontrado - ID inexistente");
        }

        if (data.idMedico() != null && !doctorRepository.existsById(data.idMedico())) {
            throw new ValidationException("Médico não encontrado - ID inexistente");
        }

        var patient = patientRepository.getReferenceById(data.idPaciente());
        var doctor = chooseDoctor(data);
        var appointment = new Appointment(null, doctor , patient, data.data(), null);

        appointmentRepository.save(appointment);

    }


    private Doctor chooseDoctor(AppointmentSchedulingDTO data) {
        if (data.idMedico() != null) {
            return doctorRepository.getReferenceById(data.idMedico());
        }

        if (data.especialidade() == null) {
            throw new ValidationException("Especialidade é obrigatória quando o médico não for escolhido.");
        }

        return doctorRepository.choseRaondomDoctorOnDate(data.especialidade(), data.data());
    }


    public void cancel(CancelingAppointmentDTO data) {
        if (!appointmentRepository.existsById(data.idConsulta())) {
            throw new ValidationException("Id da consulta informado não existe!");
        }
    
        var consulta = appointmentRepository.getReferenceById(data.idConsulta());
        consulta.cancel(data.motivo());
    }
}

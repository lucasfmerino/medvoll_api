package med.lfm.api.domain.appointment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.lfm.api.domain.ValidationException;
import med.lfm.api.domain.appointment.validations.cancellation.AppointmentCancellationValidator;
import med.lfm.api.domain.appointment.validations.scheduling.AppointmentValidator;
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

    @Autowired
    private List<AppointmentValidator> validators;

    @Autowired
    private List<AppointmentCancellationValidator> validatorsCancellation;

    public AppointmentDetailsDTO toSchedule(AppointmentSchedulingDTO data) {

        if (!patientRepository.existsById(data.idPaciente())) {
            throw new ValidationException("Paciente não encontrado - ID inexistente");
        }

        if (data.idMedico() != null && !doctorRepository.existsById(data.idMedico())) {
            throw new ValidationException("Médico não encontrado - ID inexistente");
        }

        validators.forEach(v -> v.validate(data));

        var patient = patientRepository.getReferenceById(data.idPaciente());
        var doctor = chooseDoctor(data);

        if (doctor == null) {
            throw new ValidationException("Não existe méidco disponível nessa data.");
        }

        var appointment = new Appointment(null, doctor, patient, data.data(), null);

        appointmentRepository.save(appointment);

        return new AppointmentDetailsDTO(appointment);

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

        validatorsCancellation.forEach(v -> v.validate(data));

        var consulta = appointmentRepository.getReferenceById(data.idConsulta());
        consulta.cancel(data.motivo());
    }
}

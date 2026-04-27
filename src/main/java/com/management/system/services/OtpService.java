package com.management.system.services;

import com.management.system.dto.OtpDTO;
import com.management.system.entities.Employee;
import com.management.system.entities.OTP;
import com.management.system.repositories.EmployeeRepository;
import com.management.system.repositories.OtpRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;


@EnableAsync
@Service
@RequiredArgsConstructor
public class OtpService {


    private final EmailService emailService;
    private final OtpRepository otpRepository;
    private final EmployeeRepository employeeRepository;
    private final SpringTemplateEngine templateEngine;



    public String issueOrRefreshOtp(OtpDTO otpDTO) {
        Employee emp = employeeRepository.findById(otpDTO.getId()).orElseThrow(()->new EntityNotFoundException("Employee Not Found"));
        OTP otp = otpRepository.findByEmployeeEmployeeId(otpDTO.getId()).orElseGet(()->{
            OTP newOtp = new OTP();
            newOtp.setEmployee(emp);
            return newOtp;

        });
        String otpCode = generateVerificationCode();
        otp.setOtp(otpCode);
        otp.setOtp_expiration(LocalDateTime.now().plusMinutes(15));
        otpRepository.save(otp);

        return otpCode;

        }


        @Async
        public void sendVerificationCode(String email , String verificationCode) throws MessagingException {

        String subject = "Email Verification";
        Context context = new Context();
        context.setVariable("VerificationCode",verificationCode);

        String htmlContent = templateEngine.process("OtpEmailTemplate", context);

        emailService.sendEmail(email, subject,htmlContent);


        }




    public void resendVerificationCode(String email) throws MessagingException {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();
            if (existingEmployee.isEnabled()) {
                throw new RuntimeException("Account is Already Verified");
            }
            OtpDTO otpDTO = new OtpDTO();
            otpDTO.setId(existingEmployee.getEmployeeId());
            String code = issueOrRefreshOtp(otpDTO);
            sendVerificationCode(existingEmployee.getEmail(), code);
        } else {
            throw new RuntimeException("User not Found");
        }
    }



    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000)+100000;
        return String.valueOf(code);
    }
}

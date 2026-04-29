package com.management.system.auth;

import com.management.system.auth.Dto.AuthenticationRequestDTO;
import com.management.system.auth.Dto.AuthenticationResponseDTO;
import com.management.system.dto.Otp.VerifyUserDTO;
import com.management.system.entities.Employee;
import com.management.system.entities.OTP;
import com.management.system.repositories.EmployeeRepository;
import com.management.system.repositories.OtpRepository;
import com.management.system.security.JwtUtil;
import com.management.system.services.Impl.OtpService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final OtpRepository otpRepository;
    private final OtpService otpService;


    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) throws MessagingException {
        var employee = employeeRepository.findByEmail(request.getEmail()).orElseThrow();

        if (!employee.isEnabled()) {
            otpService.resendVerificationCode(employee.getEmail());
            throw new DisabledException("User is not verified. Verification code sent to email: " + employee.getEmail());
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );

        var Jwt = jwtUtil.generateToken(employee);
        return AuthenticationResponseDTO.builder()
                .username(employee.getUsername())
                .role(employee.getRole())
                .department(employee.getDepartment())
                .token(Jwt)
                .build();
    }



    public void verifyUser(VerifyUserDTO dto) {
        Optional<Employee> employee = employeeRepository.findByEmail(dto.getEmail());
        if (employee.isPresent()) {
            Employee existingEmployee = employee.get();

            Optional<OTP> otp = otpRepository.findByEmployeeEmployeeId(existingEmployee.getEmployeeId());
            if (otp.isPresent()) {
                OTP existingOtp = otp.get();

                if (existingOtp.getOtp_expiration().isBefore(LocalDateTime.now())) {
                    throw new RuntimeException("Invalid verification code");
                }
                if (existingOtp.getOtp().equals(dto.getEmailVerificationCode())) {
                    existingEmployee.setEnabled(true);
                    existingOtp.setOtp(null);
                    existingOtp.setOtp_expiration(null);
                    otpRepository.save(existingOtp);
                    employeeRepository.save(existingEmployee);
                } else {
                    throw new RuntimeException("Invalid verification code");
                }
            }
            else {
                throw new RuntimeException("No verification code found");
            }
        }
        else {
            throw new RuntimeException("User not found");
        }
    }


}

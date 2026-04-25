package com.management.system.auth;

import com.management.system.dto.ResendVerificationCodeDTO;
import com.management.system.dto.VerifyUserDTO;
import com.management.system.services.OtpService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationService service;
    private final OtpService otpService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request){
        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyUserDTO request){
        try{
            service.verifyUser(request);
            return ResponseEntity.ok("Account Verified Successfully");
        }
        catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")

    public ResponseEntity<?> resend(@RequestBody ResendVerificationCodeDTO request){
        try{
            otpService.resendVerificationCode(request.getEmail());
            return ResponseEntity.ok("Verification Resend Successfully");
        }
        catch(RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }

    }

}

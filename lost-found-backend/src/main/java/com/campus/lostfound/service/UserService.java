package com.campus.lostfound.service;

import com.campus.lostfound.dto.LoginRequest;
import com.campus.lostfound.dto.LoginResponse;
import com.campus.lostfound.dto.RegisterRequest;
import com.campus.lostfound.entity.User;
import com.campus.lostfound.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.campus.lostfound.util.JwtUtil;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request) {
        if (userRepository.existsByStudentNo(request.studentNo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "该学号已经注册"
            );
        }

        User user = new User();
        user.setStudentNo(request.studentNo());
        user.setUsername(request.username());
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        user.setPhone(request.phone());
        user.setRole("USER");
        user.setStatus((byte) 1);

        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByStudentNo(request.studentNo())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "学号或密码错误"
                ));

        if (!passwordEncoder.matches(
                request.password(),
                user.getPasswordHash()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "学号或密码错误"
            );
        }

        if (user.getStatus() == null || user.getStatus() == 0) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "该账号已被禁用"
            );
        }

        String token = jwtUtil.generateToken(user);

        return new LoginResponse(
        token,
        "Bearer",
        user.getId(),
        user.getStudentNo(),
        user.getUsername(),
        user.getRole()
        );
    }
}
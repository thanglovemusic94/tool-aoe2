package com.backend.controllers;


import com.backend.models.ERole;
import com.backend.models.Magt;
import com.backend.models.StatusMagt;
import com.backend.models.User;
import com.backend.payload.request.LoginRequest;
import com.backend.payload.request.SignupRequest;
import com.backend.payload.response.JwtResponse;
import com.backend.payload.response.MessageResponse;
import com.backend.repository.MagtRepository;
import com.backend.repository.UserRepository;
import com.backend.security.jwt.JwtUtils;
import com.backend.security.services.AuthenticationFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class AuthController {
  @Autowired
  AuthenticationFacade authenticationFacade;

  @Autowired
  UserRepository userRepository;

  @Autowired
  private MagtRepository magtRepository;
//  @Autowired
//  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    authenticationFacade.setAuthentication(loginRequest.getInGame(), loginRequest.getPassword());
    String jwt = jwtUtils.generateJwtToken();
    return ResponseEntity.ok(new JwtResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    String inGame = signUpRequest.getInGame().toLowerCase();
    Boolean checkMgt = magtRepository.existsByCode(signUpRequest.getMaGT());
    Boolean checkMgtStatus = magtRepository.existsByCodeAndStatus_HieuLuc(signUpRequest.getMaGT());
    Magt magt = magtRepository.findByCode(signUpRequest.getMaGT());

    if (checkMgt == false){
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Mã giới thiệu không đúng, vui lòng liên hệ với admin nhóm"));
    }

    if (checkMgtStatus == false){
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Mã giới thiệu đã hết hiệu lực , vui lòng liên hệ với admin nhóm"));
    }

    if (userRepository.existsByinGame(inGame)) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Lỗi: Tên In Game đã tồn tại, vui lòng đăng kí bằng tên In Game Khác"));
    }


//    User user = new User(signUpRequest.getHovaten(), signUpRequest.getInGame(), signUpRequest.getNickZalo(),
//            signUpRequest.getSdt(), signUpRequest.getNickFb(), encoder.encode(signUpRequest.getPassword()));

    User user = new User(signUpRequest.getHovaten(), signUpRequest.getInGame(), signUpRequest.getNickZalo(),
            signUpRequest.getSdt(), signUpRequest.getNickFb(), signUpRequest.getPassword());

    String strRoles = signUpRequest.getRole();

    if (strRoles != null){
      if (strRoles.equals(ERole.ROLE_ADMIN)){
        user.setRoles(ERole.ROLE_ADMIN);
      }else {
        user.setRoles(ERole.ROLE_USER);
      }
    }else {
      user.setRoles(ERole.ROLE_USER);
    }

    User newUser = userRepository.save(user);

    magt.setStatus(StatusMagt.HET_HIEU_LUC);
    magt.setUser(newUser);
    magt.setNote(newUser.getInGame() + " đã dùng ");

    magtRepository.save(magt);

    return ResponseEntity.ok(new MessageResponse("Đăng kí tài khoản thành công, vui lòng đăng nhập lại"));
  }
}

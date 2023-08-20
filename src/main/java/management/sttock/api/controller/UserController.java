package management.sttock.api.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import management.sttock.api.request.user.FindIdRequestDto;
import management.sttock.api.request.user.SigninRequestDto;
import management.sttock.api.request.user.SignupRequestDto;
import management.sttock.api.response.user.SigninResponseDto;
import management.sttock.api.response.user.SignupResponseDto;
import management.sttock.common.auth.local.TokenProvider;
import management.sttock.db.entity.User;
import management.sttock.api.sevice.UserService;
import management.sttock.db.entity.RefreshToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

//    @ApiOperation(value = "회원가입", notes = "아이디 중복 확인")
//    @PostMapping("/signup")
//    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
//        try {
//            Long id = userService.join(signupRequestDto.toEntity());
//
//            SignupResponseDto signupResponseDto = new SignupResponseDto();
//            signupResponseDto.setId(id);
//
//            return ResponseEntity.status(HttpStatus.OK).body(signupResponseDto);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//
//    }

    @ApiOperation("로그인")
    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDto> signin(@Valid @RequestBody SigninRequestDto signinRequestDto) {
        System.out.println("--------");
        //사용자 인증
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signinRequestDto.getNickName(), signinRequestDto.getPassword());
        System.out.println("============");
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            String token = tokenProvider.createToken(authentication);

            User user = userService.findUseridforLogin(signinRequestDto.getNickName());
            RefreshToken refreshToken = tokenProvider.createRefreshToken(user, authentication);

            SigninResponseDto signinResponseDto = new SigninResponseDto(user.getName(), token, refreshToken);
            return ResponseEntity.status(HttpStatus.OK).body(signinResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

//    @ApiOperation("아이디 찾기")
//    @PostMapping("/findId")
//    public String findUseridByEmail(@RequestBody FindIdRequestDto findIdRequestDto) {
//        return userService.findEmail(findIdRequestDto.getEmail());
//    }
//
//    //임시비밀번호 로직으로 변경
////    @ApiOperation("임시 비밀번호")
//
//    @ApiOperation("회원탈퇴")
//    @DeleteMapping("/users/{userId}")
//    public ResponseEntity<?> withdraw(@PathVariable String userId) {
//        try {
//            /**인증권한 확인 로직 추가**/
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//            userService.deleteUser(userId);
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("회원 탈퇴에 성공했습니다.");
//    }
//
//    @ApiOperation("회원정보 수정")
//    @PutMapping("/users/{userId}")
//    public ResponseEntity<User> changeUserInfo (@PathVariable String userId) {
//        User findUser = userService.findUserid(userId);
//        return ResponseEntity.status(HttpStatus.OK).body(findUser);
//
//    }
//
//    @ApiOperation("유저 프로필 조회")
//    @GetMapping("/users/{userId}/profile")
//    public ResponseEntity<User> getUserInfo(@PathVariable String userId) {
//        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserid(userId));
//    }

    //로그아웃 로직 추가(refreshToken 삭제로직 추가)
}
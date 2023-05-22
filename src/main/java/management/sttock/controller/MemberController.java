package management.sttock.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import management.sttock.config.jwt.JwtFilter;
import management.sttock.config.jwt.TokenProvider;
import management.sttock.domain.Member;
import management.sttock.dto.SigninRequestDto;
import management.sttock.dto.SigninResponseDto;
import management.sttock.dto.SignupRequestDto;
import management.sttock.dto.SignupResponseDto;
import management.sttock.sevice.MemberService;
import org.springframework.http.HttpHeaders;
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
public class MemberController {
    private final MemberService memberService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @ApiOperation(value = "회원가입", notes = "아이디 중복 확인")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        try {
            Long id = memberService.join(signupRequestDto.toEntity());

            SignupResponseDto signupResponseDto = new SignupResponseDto();
            signupResponseDto.setId(id);

            return ResponseEntity.status(HttpStatus.OK).body(signupResponseDto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @ApiOperation("로그인")
    @PostMapping("/signin")
    public ResponseEntity<SigninResponseDto> signin(@Valid @RequestBody SigninRequestDto signinRequestDto) {
        //사용자 인증
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signinRequestDto.getUserId(), signinRequestDto.getPassword());
        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            //jwt 토큰 생성
            String token = tokenProvider.createToken(authentication);

            Member member = memberService.findUserid(signinRequestDto.getUserId());
            //응답 dto 생성
            SigninResponseDto signinResponseDto = new SigninResponseDto();
            signinResponseDto.setUserId(member.getUserId());
            signinResponseDto.setEmail(member.getEmail());
            signinResponseDto.setName(member.getName());
            signinResponseDto.setPhoneNumber(member.getPhoneNumber());
            signinResponseDto.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(signinResponseDto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation("아이디 찾기")
    @PostMapping("/findId")
    public String findUseridByEmail(String email) {
        Member findMember = memberService.findEmail(email);
        return findMember.getUserId();
    }

    //임시비밀번호 로직으로 변경
//    @ApiOperation("비밀번호 찾기")
//    @PostMapping("/findPassword")
//    public String findUserPassword (String userId, String email) {
//        Member findMember = memberService.findEmail(email);
//        return findMember.getUserPassword();
//    }

    @ApiOperation("회원탈퇴")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> withdraw(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(memberService.deleteMember(userId));

    }
    @ApiOperation("회원정보 수정")
    @PutMapping("/users/{userId}")
    public ResponseEntity<Member> changeMemberInfo (@PathVariable String userId) {
        Member findMember = memberService.findUserid(userId);
        return ResponseEntity.status(HttpStatus.OK).body(findMember);

    }

    @ApiOperation("유저 프로필 조회")
    @GetMapping("/users/{userId}/profile")
    public ResponseEntity<Member> getMemberInfo(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findUserid(userId));
    }

}

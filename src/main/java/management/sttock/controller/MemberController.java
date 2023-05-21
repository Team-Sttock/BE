package management.sttock.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import management.sttock.config.jwt.JwtFilter;
import management.sttock.config.jwt.TokenProvider;
import management.sttock.domain.Member;
import management.sttock.dto.SigninRequestDto;
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
    public ResponseEntity<Long> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
            return ResponseEntity.status(HttpStatus.OK).body(memberService.join(signupRequestDto.toEntity()));
    }

    @ApiOperation("로그인")
    @PostMapping("/signin")
    public ResponseEntity<SignupResponseDto> signin(@Valid @RequestBody SigninRequestDto signinRequestDto) {
        //사용자 인증
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(signinRequestDto.getUserId(), signinRequestDto.getPassword());

        Authentication authentication =
                authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //jwt 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        //httpheader에 jwt 토큰 포함
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer" + jwt);

        //응답 dto 생성
        Member member = memberService.findUserid(signinRequestDto.getUserId());
        return new ResponseEntity<>(new SignupResponseDto(member, jwt), httpHeaders, HttpStatus.OK);

    }

    @ApiOperation("로그아웃")
    @PostMapping("/users/logout")
    public void logout() {

    }


//    @ApiOperation("아이디 찾기")
//
//    @ApiOperation("비밀번호 찾기")

    @ApiOperation("회원탈퇴")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<String> withdraw(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(memberService.deleteMember(userId));

    }
//    @ApiOperation("회원정보 수정")

    @ApiOperation("유저 프로필 조회")
    @GetMapping("/users/{userId}/profile")
    public ResponseEntity<Member> getUserInfo(@PathVariable String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findUserid(userId));
    }

}

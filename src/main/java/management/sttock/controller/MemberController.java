package management.sttock.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import management.sttock.config.jwt.TokenProvider;
import management.sttock.domain.Member;
import management.sttock.memberDto.*;
import management.sttock.sevice.MemberService;
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
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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
            String token = tokenProvider.createToken(authentication);
            Member member = memberService.findUseridforLogin(signinRequestDto.getUserId());
            SigninResponseDto signinResponseDto = new SigninResponseDto();
            signinResponseDto.setName(member.getName());
            signinResponseDto.setToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(signinResponseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @ApiOperation("아이디 찾기")
    @PostMapping("/findId")
    public String findUseridByEmail(@RequestBody FindIdRequestDto findIdRequestDto) {
        return memberService.findEmail(findIdRequestDto.getEmail());
    }

    //임시비밀번호 로직으로 변경
//    @ApiOperation("임시 비밀번호")

    @ApiOperation("회원탈퇴")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> withdraw(@PathVariable String userId) {
        try {
            /**인증권한 확인 로직 추가**/
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
            memberService.deleteMember(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("회원 탈퇴에 성공했습니다.");
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

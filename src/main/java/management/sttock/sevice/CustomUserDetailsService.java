package management.sttock.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.domain.Member;
import management.sttock.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        String findUser = memberRepository.findByUserIdForDetailService(username)
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username));

        Member findMember = memberRepository.findOneByUserIdForLong(username);
        if(findMember == null) {
            throw new UsernameNotFoundException("없어용.");
        }

//        //암호화된 비밀번호 가져오기
//        String storedPassword = findMember.getUserPassword();
//        System.out.println("0000000");
//        if(!passwordEncoder.matches(userpassword, storedPassword)) {
//            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
//        }
//        System.out.println("11111");
        List<GrantedAuthority> authorities = Collections.emptyList();
//        System.out.println("22222");
        return new org.springframework.security.core.userdetails.User(findMember.getUserId(), findMember.getUserPassword(),authorities);
    }

}

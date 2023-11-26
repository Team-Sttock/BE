package management.sttock.common.auth.local;

import lombok.RequiredArgsConstructor;
import management.sttock.db.entity.User;
import management.sttock.db.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLoginId(loginId);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("일치하는 회원이 없습니다.");
        }
        List<GrantedAuthority> authorities = Collections.emptyList();//일단 권한 정보 비워둠
        return new CustomUserDetails(user.get().getLoginId(), user.get().getPassword(), authorities);
    }
}
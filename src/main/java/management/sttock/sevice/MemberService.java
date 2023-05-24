package management.sttock.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.domain.Member;
import management.sttock.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(Member member) {
        validationDupliateMember(member);//중복 회원 검증

        member.encodePassword(passwordEncoder);
        memberRepository.save(member);

        return member.getId();
    }

    //아이디, 이메일 중복 검사
    private void validationDupliateMember(Member member) {
        List<Member> findSameId = memberRepository.findByUserIdForList(member.getUserId());
        List<Member> findSameEmail = memberRepository.findByEmail(member.getEmail());
        if (!findSameId.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        } else if (!findSameEmail.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    /**
     * 조회
     */
    //회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    //회원 pk로 조회
    public Member findMember(Long id) {
        return memberRepository.findOne(id);
    }

    //회원아이디로 조회
    public Member findUserid(String userId) {
        return memberRepository.findOneByUserId(userId);
    }

    public Member findUseridforLogin(String userId) {
        return memberRepository.findOneByUserIdForLong(userId);
    }

    //이메일로 회원아이디 찾기
    public String findEmail(String email) {
        return memberRepository.findOneByEmail(email).getUserId();
    }

    //회원 삭제
    @Transactional
    public String deleteMember(String userId) {
        Member findMember = memberRepository.findOneByUserId(userId);
        Optional<Member> memberOptional = Optional.ofNullable(findMember);

        if (memberOptional.isPresent()) {
            String findId = findMember.getUserId();
            int deleteCount = memberRepository.delete(findId);
            return "delete success";
        } else {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

}

package management.sttock.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.domain.Member;
import management.sttock.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long join(Member member) {
        validationDupliateMember(member);//중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //아이디, 이메일 중복 검사
    private void validationDupliateMember(Member member) {
        List<Member> findSameId = memberRepository.findById(member.getId());
        List<Member> findSameEmail = memberRepository.findByEmail(member.getEmail());
        if (!findSameId.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        } else if (!findSameEmail.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 이메일입니다.");
        }
    }

    //회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
    public Member findMember(Long id) {
        return memberRepository.findOne(id);
    }

    //로그인, 로그아웃, 아이디 찾기, 비번 찾기
}

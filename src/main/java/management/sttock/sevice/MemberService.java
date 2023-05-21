package management.sttock.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.config.jwt.TokenProvider;
import management.sttock.domain.Member;
import management.sttock.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    private TokenProvider tokenProvider;

    @Transactional
    public Long join(Member member) {
        validationDupliateMember(member);//중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //아이디, 이메일 중복 검사
    private void validationDupliateMember(Member member) {
        List<Member> findSameId = memberRepository.findByUserId(member.getUserId());
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
    public Member findMember(Long userId) {
        return memberRepository.findOne(userId);
    }

    //회원아이디로 조회
    public Member findUserid(String userId) {
        return memberRepository.findOneByUserId(userId);
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


    //로그아웃, 아이디 찾기, 비번 찾기
}

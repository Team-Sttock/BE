package management.sttock.repository;

import lombok.RequiredArgsConstructor;
import management.sttock.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager em;

    //회원 저장
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    //회원 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email",email)
                .getResultList();
    }
    public List<Member> findByUserId(String userId) {
        return em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    public Member findOneByUserId(String userId) {
        return em.find(Member.class, userId);
    }
    public Member findOneByEmail(String email) {
        return em.find(Member.class, email);
    }

    public int delete(String userId) {
        return em.createQuery("delete from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}

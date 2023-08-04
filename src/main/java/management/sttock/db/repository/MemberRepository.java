package management.sttock.db.repository;

import lombok.RequiredArgsConstructor;
import management.sttock.db.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

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
    public List<Member> findByUserIdForList(String userId) {
        return em.createQuery("select m from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    //CustomUserDetailsService만 사용
    public Optional<String> findByUserIdForDetailService(String userId) {
        String jpql = "SELECT m.userId FROM Member m WHERE m.userId = :userId";
        TypedQuery<String> query = em.createQuery(jpql, String.class);
        query.setParameter("userId", userId);
        try {
            String findUserId = query.getSingleResult();
            return Optional.of(findUserId);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public Member findOneByUserIdForLong(String userId) {
        String jpql = "SELECT m FROM Member m WHERE m.userId = :userId";
        TypedQuery<Member> query = em.createQuery(jpql, Member.class);
        query.setParameter("userId", userId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Member findOneByUserId(String userId) {
        return em.find(Member.class, userId);
    }
    public Member findOneByEmail(String email) {
        String jpql = "SELECT m FROM Member m WHERE m.email = :email";
        TypedQuery<Member> query = em.createQuery(jpql, Member.class);
        return query.setParameter("email", email)
                .getSingleResult();
    }

    public int delete(String userId) {
        return em.createQuery("delete from Member m where m.userId = :userId", Member.class)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}

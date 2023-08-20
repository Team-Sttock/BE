package management.sttock.db.repository;

import lombok.RequiredArgsConstructor;
import management.sttock.db.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private final EntityManager em;

    //회원 저장
    public Long save(User user) {
        em.persist(user);
        return user.getId();
    }

    //회원 조회
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select m from User m", User.class)
                .getResultList();
    }

    public List<User> findByEmail(String email) {
        return em.createQuery("select u from User u where u.email = :email", User.class)
                .setParameter("email",email)
                .getResultList();
    }
    public List<User> findByUserIdForList(String userId) {
        return em.createQuery("select u from User u where u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    //CustomUserDetailsService만 사용
    public Optional<String> findByUserIdForDetailService(String userId) {
        String jpql = "SELECT u.userId FROM User u WHERE u.userId = :userId";
        TypedQuery<String> query = em.createQuery(jpql, String.class);
        query.setParameter("userId", userId);
        try {
            String findUserId = query.getSingleResult();
            return Optional.of(findUserId);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    public User findOneByUserIdForLong(String userId) {
        String jpql = "SELECT u FROM User u WHERE u.userId = :userId";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        query.setParameter("userId", userId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findOneByUserId(String userId) {
        return em.find(User.class, userId);
    }
    public User findOneByEmail(String email) {
        String jpql = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> query = em.createQuery(jpql, User.class);
        return query.setParameter("email", email)
                .getSingleResult();
    }

    public int delete(String userId) {
        return em.createQuery("delete from User u where u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .executeUpdate();
    }
}

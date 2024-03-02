package management.sttock.api.db.entity.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import management.sttock.api.db.entity.User;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@MappedSuperclass
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedBy
    @Column(name = "crt_by")
    private Long crtBy;              //생성자

    @CreatedDate
    @Column(updatable = false, name = "crt_dt")
    private LocalDateTime crtDt;        //생성일자

    @LastModifiedBy
    @Column(name = "mod_by")
    private Long modBy;              //수정자

    @LastModifiedDate
    @Column(name = "mod_dt")
    private LocalDateTime modDt;        //수정일자

    @Column(name = "del_by")
    private Long delBy;              //삭제자

    @Column(name = "del_dt")
    private LocalDateTime delDt;        //삭제일자

    @Column(name = "use_yn")
    private String useYn;               //사용 여부

    @Column(name = "del_yn")
    private String delYn;               //삭제 여부

    public void setModBy(User user){
        this.modBy = user.getId();
    }

    public void setCrtBy(User user){
        this.crtBy = user.getId();
    }

    public void setDelBy(User user){
        this.delBy = user.getId();
    }

    public void setDelDt(){
        this.delDt = LocalDateTime.now();
    }



    @PrePersist
    public void setDefaultDelYn() {
        if (this.delYn == null) {
            this.delYn = "N";
        }
    }
}
package ru.luttsev.contractors.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * Сущность контрагента в БД
 * @author Yuri Luttsev
 */
@Entity
@Table(name = "contractor")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@NamedEntityGraph(name = "contractor-entity-graph", attributeNodes = {
        @NamedAttributeNode("country"),
        @NamedAttributeNode("industry"),
        @NamedAttributeNode("orgForm")
})
public class Contractor {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "name")
    private String name;

    @Column(name = "name_full")
    private String fullName;

    @Column(name = "inn")
    private String inn;

    @Column(name = "ogrn")
    private String ogrn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country", referencedColumnName = "id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "industry", referencedColumnName = "id")
    private Industry industry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_form", referencedColumnName = "id")
    private OrgForm orgForm;

    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "create_date", insertable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "create_user_id", insertable = false, updatable = false)
    @CreatedBy
    private String createUserId;

    @Column(name = "modify_user_id")
    @LastModifiedBy
    private String modifyUserId;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

}

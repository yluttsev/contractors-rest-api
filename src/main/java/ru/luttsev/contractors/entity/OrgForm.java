package ru.luttsev.contractors.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность формы организации в БД
 * @author Yuri Luttsev
 */
@Entity
@Table(name = "org_form")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrgForm {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "org_form_id_seq")
    @SequenceGenerator(name = "org_form_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

}

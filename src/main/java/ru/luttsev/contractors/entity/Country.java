package ru.luttsev.contractors.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность страны в БД
 * @author Yuri Luttsev
 */
@Entity
@Table(name = "country")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Country {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

}

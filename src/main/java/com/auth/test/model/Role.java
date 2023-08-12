package com.auth.test.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "security", name = "roles")
@Getter
@Setter
public class Role extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_generator")
    @SequenceGenerator(name = "roles_id_generator", sequenceName = "roles_seq_id", allocationSize = 1)
    private String id;
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    List<User> users;
}

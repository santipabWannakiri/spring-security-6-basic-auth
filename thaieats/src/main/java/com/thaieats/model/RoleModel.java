package com.thaieats.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role_table")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="role_id")
    private int id;

    private String name;
}

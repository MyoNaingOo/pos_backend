package com.mno.business.product.entity;

import com.mno.business.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    private String name;
    private String img;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String description;
    private LocalDateTime time;

}

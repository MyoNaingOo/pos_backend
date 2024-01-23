package com.mno.business.user.entity;




import jakarta.persistence.*;
import lombok.*;



@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Data
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    public User user;


}


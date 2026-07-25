package com.onedayworker.auth.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "identity_roles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "identity_id",
                        "role_id"
                })
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdentityRole extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identity_id", nullable = false)
    private Identity identity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

}

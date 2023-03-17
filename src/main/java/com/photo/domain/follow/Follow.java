package com.photo.domain.follow;

import com.photo.domain.BaseTimeEntity;
import com.photo.domain.user.User;
import lombok.*;
import javax.persistence.*;



@NoArgsConstructor
@Getter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "follow_uk",
                        columnNames = {"from_user_id", "to_user_id"}
                )
        }
)
public class Follow extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

}

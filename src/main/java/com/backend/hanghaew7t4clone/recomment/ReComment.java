package com.backend.hanghaew7t4clone.recomment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recomment;

    

}

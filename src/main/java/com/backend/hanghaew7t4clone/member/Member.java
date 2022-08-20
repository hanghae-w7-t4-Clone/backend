package com.backend.hanghaew7t4clone.member;

import com.backend.hanghaew7t4clone.shared.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Objects;


@Entity
@NoArgsConstructor
@Getter
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String phoneNum;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String content;



    @Column(nullable = false)
    private String profilePhoto;


    public Member(String email, String phoneNum, String name, String nickname, String password, String content, String profilePhoto) {
        this.id = getId();
        this.email = email;
        this.phoneNum = phoneNum;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.content = content;
        this.profilePhoto = profilePhoto;
    }

    @Builder
    public Member(String email, String name, String nickname, String password, String phoneNum) {
        this.id = getId();
        this.email = email;
        this.phoneNum =phoneNum;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.content = " ";
        this.profilePhoto =" ";
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || Hibernate.getClass(this)!= Hibernate.getClass(o)){
            return false;
        }
        Member member = (Member) o;
        return id != null && Objects.equals(id,member.id);
    }

    @Override
    public int hashCode(){
        return getClass().hashCode();
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password){
        return passwordEncoder.matches(password,this.password);
    }


}

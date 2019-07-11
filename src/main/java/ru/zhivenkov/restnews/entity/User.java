package ru.zhivenkov.restnews.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name="users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    public User() {

    }

    public User(@NotNull String fio) {
        this.fio = fio;
    }

    @Column(name = "fio")
    @NotNull
    private String fio;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                '}';
    }
}

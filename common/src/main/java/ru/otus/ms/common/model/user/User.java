package ru.otus.ms.common.model.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class User {
    @EqualsAndHashCode.Exclude
    private String id;
    private String lastName;
    private String firstName;
    private String email;
    @EqualsAndHashCode.Exclude
    private boolean active;

    public User(String id, String lastName, String firstName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.active = true;
    }

}

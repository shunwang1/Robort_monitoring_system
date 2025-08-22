package cn.iua.caas.robotdatahub.Entity.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer Id;
    private String password;
    private String username;
    private String role;
}

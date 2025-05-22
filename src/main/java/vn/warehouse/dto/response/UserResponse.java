package vn.warehouse.dto.response;

import lombok.*;
import vn.warehouse.model.enumuration.Role;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private Role role;
}

package vn.warehouse.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import vn.warehouse.model.enumuration.Role;

@Getter
public class UserRequest {
    @NotBlank(message = "Username là bắt buộc")
    @Size(min = 3, max = 50, message = "Username phải từ 3 đến 50 ký tự")
    private String username;

    @NotBlank(message = "Mật khẩu là bắt buộc")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    @NotBlank(message = "Email là bắt buộc")
    @Email(message = "Email không hợp lệ")
    private String email;

    @Size(max = 15, message = "Số điện thoại không được vượt quá 15 ký tự")
    private String phone;

    @NotNull(message = "Vai trò là bắt buộc")
    private Role role;
}

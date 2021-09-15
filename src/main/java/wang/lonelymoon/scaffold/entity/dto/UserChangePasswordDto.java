package wang.lonelymoon.scaffold.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordDto {

    /**
     * 用户名
     */
    private String username;

    /**
     * 旧密码
     */
    private String oriPassword;

    /**
     * 新密码
     */
    private String newPassword;

}

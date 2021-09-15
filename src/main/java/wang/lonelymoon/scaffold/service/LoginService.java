package wang.lonelymoon.scaffold.service;

import wang.lonelymoon.scaffold.entity.dto.UserChangePasswordDto;
import wang.lonelymoon.scaffold.entity.dto.UserLoginDto;
import wang.lonelymoon.scaffold.entity.dto.UserLogoutDto;
import wang.lonelymoon.scaffold.entity.dto.UserRegisterDto;

/**
 * 登陆、注册功能
 */
public interface LoginService {

    void Login(UserLoginDto userLoginDto);

    void Register(UserRegisterDto userRegisterDto);

    void Logout(UserLogoutDto userLogoutDto);

    void changePassword(UserChangePasswordDto userChangePasswordDto);
}

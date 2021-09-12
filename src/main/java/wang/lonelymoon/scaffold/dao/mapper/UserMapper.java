package wang.lonelymoon.scaffold.dao.mapper;

import wang.lonelymoon.scaffold.entity.User;

public interface UserMapper {
    User findById(Long id);
}

package wang.lonelymoon.scaffold.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.lonelymoon.scaffold.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
}

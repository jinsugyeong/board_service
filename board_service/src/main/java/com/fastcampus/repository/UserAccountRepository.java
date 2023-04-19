package com.fastcampus.repository;

import com.fastcampus.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, String>{

}

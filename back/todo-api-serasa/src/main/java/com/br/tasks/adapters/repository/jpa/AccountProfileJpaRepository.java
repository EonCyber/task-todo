package com.br.tasks.adapters.repository.jpa;

import com.br.tasks.domain.entity.AccountProfile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface AccountProfileJpaRepository extends JpaRepository<AccountProfile, Long> {

    Optional<AccountProfile> findByEmail(String email);
}

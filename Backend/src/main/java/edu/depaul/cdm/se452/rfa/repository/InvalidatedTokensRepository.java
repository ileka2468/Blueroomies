package edu.depaul.cdm.se452.rfa.repository;

import edu.depaul.cdm.se452.rfa.entity.Invalidatedtoken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokensRepository extends JpaRepository<Invalidatedtoken, Integer> {
    public Invalidatedtoken findByTokenHash(String tokenHash);
}

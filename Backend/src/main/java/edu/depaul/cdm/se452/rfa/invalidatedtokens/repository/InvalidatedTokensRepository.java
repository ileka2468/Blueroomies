package edu.depaul.cdm.se452.rfa.invalidatedtokens.repository;

import edu.depaul.cdm.se452.rfa.invalidatedtokens.entity.Invalidatedtoken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokensRepository extends JpaRepository<Invalidatedtoken, Integer> {
    public Invalidatedtoken findByTokenHash(String tokenHash);
}

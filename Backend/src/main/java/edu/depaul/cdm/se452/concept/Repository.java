package edu.depaul.cdm.se452.concept;

import java.util.Optional;

public interface Repository<ClassTemplate, KeyTemplate> {
    /**
     * Saves a value to a repository
     * @param value
     * @return
     */
    public ClassTemplate save(ClassTemplate value);

    /**
     * Find the object based on unique key
     * @param id
     * @return
     */
    public Optional<ClassTemplate> findById(KeyTemplate id);

    /**
     * @return count of items in repository
     */
    public int count();
}

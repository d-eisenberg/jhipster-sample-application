package com.broscorp.service.impl;

import com.broscorp.service.UsersService;
import com.broscorp.domain.Users;
import com.broscorp.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Users}.
 */
@Service
@Transactional
public class UsersServiceImpl implements UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersServiceImpl.class);

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Save a users.
     *
     * @param users the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Users save(Users users) {
        log.debug("Request to save Users : {}", users);
        return usersRepository.save(users);
    }

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Users> findAll() {
        log.debug("Request to get all Users");
        return usersRepository.findAll();
    }

    /**
     * Get one users by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Users> findOne(Long id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id);
    }

    /**
     * Delete the users by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
    }
}

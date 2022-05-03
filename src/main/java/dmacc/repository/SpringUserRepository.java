package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import dmacc.beans.UserInformation;

/**
 * @author jword - jord
 * CIS175 - Spring - 2022
 * Apr 16, 2022
 */
@Repository
public interface SpringUserRepository extends JpaRepository<UserInformation, Long>{

}


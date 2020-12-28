package com.hashedin.repositories;

import com.hashedin.entities.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Groups,Long> {

    Groups findByIdAndDeletedFalse(Long groupId);
}

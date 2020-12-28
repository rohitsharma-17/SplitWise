package com.hashedin.repositories;

import com.hashedin.entities.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bills,Long> {

    Bills findByIdAndDeletedFalse(Long billId);

    Bills findByIdAndGroupIdAndDeletedFalse(Long billId, Long groupId);

    List<Bills> findByGroupIdAndDeletedFalse(Long groupId);

}

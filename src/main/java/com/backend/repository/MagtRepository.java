package com.backend.repository;

import com.backend.models.Magt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;



public interface MagtRepository extends JpaRepository<Magt, Long> {

    boolean existsByCode(String code);
    @Query("select count(m)>0 from Magt m  where m.code like :code and m.status = com.backend.models.StatusMagt.HIEU_LUC")
    boolean existsByCodeAndStatus_HieuLuc(String code);

    Magt findByCode(String code);

    @Modifying
    @Transactional
    @Query(value = "delete from Magt m where m.user.id = :user_id")
    void deleteAllByUser_Id(Long user_id);
}

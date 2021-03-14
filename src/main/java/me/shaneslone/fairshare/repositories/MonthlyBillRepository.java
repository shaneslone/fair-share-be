package me.shaneslone.fairshare.repositories;

import me.shaneslone.fairshare.models.MonthlyBill;
import org.springframework.data.repository.CrudRepository;

public interface MonthlyBillRepository extends CrudRepository<MonthlyBill, Long> {
}

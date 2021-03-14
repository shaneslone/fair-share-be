package me.shaneslone.fairshare.repositories;

import me.shaneslone.fairshare.models.Bill;
import org.springframework.data.repository.CrudRepository;

public interface BillRepository extends CrudRepository<Bill, Long> {
}

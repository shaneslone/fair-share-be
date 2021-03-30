package me.shaneslone.fairshare.repositories;

import me.shaneslone.fairshare.models.Household;
import org.springframework.data.repository.CrudRepository;

public interface HouseholdRepository extends CrudRepository<Household, Long> {
    Household findHouseholdByHouseholdKey(String key);
}

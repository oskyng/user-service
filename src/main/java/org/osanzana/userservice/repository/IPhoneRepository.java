package org.osanzana.userservice.repository;

import org.osanzana.userservice.model.Phone;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IPhoneRepository extends CrudRepository<Phone, UUID> {
}

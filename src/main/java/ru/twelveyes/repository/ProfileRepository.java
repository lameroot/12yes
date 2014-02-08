package ru.twelveyes.repository;

import org.springframework.data.repository.CrudRepository;
import ru.twelveyes.domain.Profile;

/**
 * Created by lameroot on 28.12.13.
 */
public interface ProfileRepository extends CrudRepository<Profile,Integer> {
}

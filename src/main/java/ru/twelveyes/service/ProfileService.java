package ru.twelveyes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.twelveyes.domain.Profile;
import ru.twelveyes.repository.ProfileRepository;

import javax.annotation.Resource;

/**
 * Created by lameroot on 28.12.13.
 */
@Service
public class ProfileService {

    @Resource
    private ProfileRepository profileRepository;

    public Profile create() {
        return null;
    }

    public Profile follow(Profile me, Profile target) {
        if ( null == me ) return null;
        if ( null == target ) return me;
        me.follow(target);
        profileRepository.save(target);
        return me;
    }
}

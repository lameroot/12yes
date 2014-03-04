package ru.twelveyes.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.twelveyes.domain.Activity;
import ru.twelveyes.repository.ActivityRepository;
import ru.twelveyes.util.TranslitUtil;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Krainov
 * Date: 27.02.14
 * Time: 16:35
 */
@Service
public class ActivityService<A extends Activity> extends AbstractService<A>{

    @Resource
    private ActivityRepository<A> activityRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Pattern pattern = Pattern.compile("(\\d+$)");

    public A findByIndex(String index) {
        return activityRepository.findByUniqueIndex(index);
    }

    public A create(A activity, String title, String uniqueIndex, Activity...parents) {
        if (StringUtils.isBlank(title) ) return null;
        activity.setTitle(title);
        String index = TranslitUtil.toTranslit(title.toLowerCase());
        if ( StringUtils.isNotBlank(uniqueIndex) ) index = uniqueIndex;
        activity.setUniqueIndex(findUniqueIndex(index));
        for (Activity parent : parents) {
            activity.addParent(parent);
        }
        logger.debug("Try save activity with uniqueIndex: {}",uniqueIndex);
        activityRepository.save((A) activity);
        logger.debug("Activity {} successfully saved",activity);
        return (A)activity;
    }

    public A read(Long id) {
        return activityRepository.findOne(id);
    }

    public A update(A activity) {
        if ( null == activity ) return null;
        return activityRepository.save(activity);
    }

    public void delete(A activity) {
        if ( null == activity ) return;
        activityRepository.delete(activity);
    }

    public String findUniqueIndex(String propertyValue) {
        int count = 0;
        if ( null != findByIndex(propertyValue) ) {
            Matcher matcher = pattern.matcher(propertyValue);
            if ( matcher.find() ) count = Integer.parseInt(matcher.group(0));
            propertyValue = findUniqueIndex(propertyValue + count++);
        }
        return propertyValue;
    }

}

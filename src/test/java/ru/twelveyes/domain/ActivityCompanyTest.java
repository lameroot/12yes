package ru.twelveyes.domain;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

/**
 * Created by lameroot on 23.01.14.
 */
public class ActivityCompanyTest extends AbstractDomainTest {

    @Test
    public void testCreateActivity() {
        //root activities
        Activity sport = createActivity("sport",null);
        activityRepository.save(sport);
        assertNotNull(sport.getId());

        Activity culture = createActivity("culture",null);
        activityRepository.save(culture);
        assertNotNull(culture.getId());
        //end root

        //first level activities
        Activity dance = createActivity("dance",sport);
        activityRepository.save(dance);
        assertNotNull(dance);

        Activity skiing = createActivity("skiing",sport);
        activityRepository.save(skiing);
        assertNotNull(skiing);
        //end first level

        //second level activities
        Activity tango = createActivity("tango",dance);
        activityRepository.save(tango);

        Activity tangoChild = createActivity("tangoChild",tango);
        activityRepository.save(tangoChild);

        Activity breakDance = createActivity("breakDance",dance);
        activityRepository.save(breakDance);

        Activity snowboard = createActivity("snowboard",skiing);
        activityRepository.save(snowboard);

        Activity ski = createActivity("ski",skiing);
        activityRepository.save(ski);
        //end second level

        //company level
        Company domTango = createCompany("domTango",tango);
        companyRepository.save(domTango);

        Company domSuperTango = createCompany("domSuperTango",tango);
        companyRepository.save(domSuperTango);

        Company breakSchool = createCompany("breakSchool",breakDance);
        companyRepository.save(breakSchool);

        Company snowSchool = createCompany("snowSchool",snowboard);
        companyRepository.save(snowSchool);

        Company snowSchool2 = createCompany("snowSchool2",snowboard);
        companyRepository.save(snowSchool2);

        Company skiSchool = createCompany("skiSchool",ski);
        companyRepository.save(skiSchool);

        Company skiSchool2 = createCompany("skiSchool2",ski);
        companyRepository.save(skiSchool2);

        Company skiSchool3 = createCompany("skiSchool3",ski);
        companyRepository.save(skiSchool3);

        Company skiAndSnowboardSchool = createCompany("skiAndSnowboardSchool",ski);
        skiAndSnowboardSchool.addParent(snowboard);
        companyRepository.save(skiAndSnowboardSchool);
        //end company level

        //start add followers
        Profile profile1 = createProfile("profile1@mail.ru","password1");
        profileRepository.save(profile1);

        Profile profile2 = createProfile("profile2@mail.ru","password2");
        profileRepository.save(profile2);

        Profile profile3 = createProfile("profile3@mail.ru","password3");
        profileRepository.save(profile3);

        Profile profile4 = createProfile("profile4@mail.ru","password4");
        profileRepository.save(profile4);

        Profile profile5 = createProfile("profile5@mail.ru","password5");
        profileRepository.save(profile5);

        profileService.follow(profile2,profile1);
        profileService.follow(profile1,profile2);
        profileService.follow(profile3,profile2);
        profileService.follow(profile4,profile2);
        profileService.follow(profile5,profile1);
        //end profiles

        //test profiles
        Profile profileFound = profileRepository.findByPropertyValue("email","profile1@mail.ru");
        assertNotNull(profileFound);
        System.out.println(profileFound);
        for (Profile follower : profileFound.getFollowers()) {
            System.out.println("follower_ = " + profileRepository.findById(follower.getId()));
        }
        Profile profileFound2 = profileRepository.findByPropertyValue("email","profile2@mail.ru");
        assertNotNull(profileFound2);
        System.out.println(profileFound2);
        for (Profile follower : profileFound2.getFollowers()) {
            System.out.println("follower2_ = " + profileRepository.findById(follower.getId()));
        }
        //test end profiles

        //tests activities
        /*
        Activity activityFound = activityRepository.findByPropertyValue("title","tango");
        assertNotNull(activityFound);

        System.out.println("parent = " + activityFound.getParents());
        System.out.println("child = " + activityFound.getChild());
        for (Activity parent : activityFound.getParents()) {
            System.out.println("parent_ = " + activityRepository.findOne(parent.getId()));
        }
        for (Activity child : activityFound.getChild()) {
            System.out.println("child_ = " + activityRepository.findOne(child.getId()));
        }
        Company companyFound = companyRepository.findByPropertyValue("title","domTango");
        assertNotNull(companyFound);

        for (Activity parent : companyFound.getParents()) {
            System.out.println("_parent = " + activityRepository.findOne(parent.getId()));
        }
        for (Activity child : companyFound.getChild()) {
            System.out.println("_child = " + activityRepository.findOne(child.getId()));
        }
        */
        //end tests

    }
}

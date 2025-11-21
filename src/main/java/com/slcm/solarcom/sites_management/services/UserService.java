package com.slcm.solarcom.sites_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slcm.sites_management.domain.Profile;
import com.slcm.sites_management.domain.User;
import com.slcm.solarcom.sites_management.repo.ProfileRepository;
import com.slcm.solarcom.sites_management.repo.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public void createUserWithProfile() {
        Profile profile = new Profile();
        profile.setBio("This is a bio");
        profileRepository.save(profile);

        User user = new User();
        user.setLogin("John Doe");
        user.setProfile(profile);
        userRepository.save(user);
    }

    public User getUser(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
    public boolean loginExists(String login) {
        return userRepository.findByLogin(login).isPresent();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}


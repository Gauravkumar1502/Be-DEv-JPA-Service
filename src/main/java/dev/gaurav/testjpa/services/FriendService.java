package dev.gaurav.testjpa.services;

import dev.gaurav.testjpa.repositorys.FriendRepository;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
    private final FriendRepository friendRepository;
    FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }
}
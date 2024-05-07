package com.izertis.SimpleShared.primaryAdapter.controller;


import com.izertis.SimpleShared.core.domain.exception.EmptyFriendListException;
import com.izertis.SimpleShared.core.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/friend")
    public String viewFriends(Model modelo) throws EmptyFriendListException {
        modelo.addAttribute("friends", friendService.findFriends());
        return "friend";
    }
}


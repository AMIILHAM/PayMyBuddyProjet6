package com.demos.paymybuddy.web;

import com.demos.paymybuddy.domain.CustomUser;
import com.demos.paymybuddy.dto.CustomUserDto;
import com.demos.paymybuddy.mapper.CustomUserMapper;
import com.demos.paymybuddy.service.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final CustomUserService userService;
    private final CustomUserMapper userMapper;

    @GetMapping("/contact")
    public String contactPage(HttpSession session, Model model) {
        CustomUserDto userDto = this.userService.getConnectedUser(session);

        model.addAttribute("pageName", "Contact");
        model.addAttribute("user", userDto);
        model.addAttribute("connectionList", userDto.getFriendsList());

        return "contact";
    }

    @PostMapping("/contact/add")
    public String addConnexion(@RequestParam String email,HttpSession session, Model model){
        if(this.userService.isExistByEmail(email)){
            CustomUserDto userDto = this.userService.getConnectedUser(session);
            this.userService.addFriend(userDto.getId(), email);
        }
        return "redirect:/contact";
    }

    @DeleteMapping("/contact/deleteContact/{id}")
    public String deleteContact(@PathVariable Long id,HttpSession session, Model model){
        CustomUserDto userDto = this.userService.getConnectedUser(session);
        List<CustomUser> filteredFriendsList = userDto.getFriendsList()
                .stream()
                .filter(friend -> !Objects.equals(friend.getId(), id))
                .collect(Collectors.toList());

        userDto.setFriendsList(filteredFriendsList);
        this.userService.save(userDto);

        return "redirect:/contact";
    }
}

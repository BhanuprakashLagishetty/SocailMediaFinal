package com.example.SocailMedia.Controller;

import com.example.SocailMedia.Entity.User;
import com.example.SocailMedia.Models.FriendModel;
import com.example.SocailMedia.Models.UserModel;
import com.example.SocailMedia.Service.GroupService;
import com.example.SocailMedia.Service.UserService;
import com.example.SocailMedia.validator.GroupValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;
    public int GroupId1=0;
    public int UserId1=0;
    @Autowired
    GroupValidator groupValidator;

    @Autowired
    UserService userService;
    @RequestMapping("/CreateGroup")
    public String CreateGroup()
    {
        System.out.println("Group Method is called");
        return "CreateGroup";
    }

    @RequestMapping("/SaveGroup")
    public String SaveGroup(@Valid FriendModel friendModel, Model model, BindingResult bindingResult){
        System.out.println("this is the frindmodel name"+ friendModel.getFname());
        groupValidator.validate(friendModel,bindingResult);
        System.out.println(bindingResult);
        if(bindingResult.hasErrors())
        {
            System.out.println("this is my page");
            return "CreateGroup";
        }
        groupService.createGroup(friendModel);
        return "index";
    }
    @RequestMapping("/viewAllGroups")
    public String viewAllGroups(Model model)
    {
        List<FriendModel> frm= groupService.viewAllGroups();
        model.addAttribute("friends",frm);
        return "viewAllGroups";
    }
    @RequestMapping("/viewGroupMembers")
    public String viewGroupMembers(int groupId,Model model)
    {
        GroupId1=groupId;
        Set<UserModel> um=groupService.viewGroupMembers(groupId);
        model.addAttribute("members",um);
        return "viewGroupMembers";
    }
    @RequestMapping("/ShowallExistingUsers")
    public String ShowallExistingUsers(Model model,int groupId)
    {
        GroupId1=groupId;
        List<User>u=userService.display();
        model.addAttribute("all",u);
        return "ShowallExistingUsers";
    }
    @RequestMapping("/AddToGroup")
    public String AddToGroup(int userId,Model model)
    {
        UserId1=userId;
        System.out.println("Group id"+GroupId1);
        groupService.addUserToGroup(userId,GroupId1);
        return "redirect:/viewAllGroups";
    }
    @RequestMapping("/removeMember")
    public String removeMember(int eid)
    {
        groupService.removeMember(eid,GroupId1);
        return "redirect:/viewAllGroups";
    }
    @RequestMapping("/removeGroup")
    public String removeGroup(int GroupId)
    {
        groupService.removeGroup(GroupId);
        return "redirect:/viewAllGroups";
    }



}

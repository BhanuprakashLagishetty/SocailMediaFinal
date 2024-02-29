package com.example.SocailMedia.Controller;

import com.example.SocailMedia.Entity.Post;
import com.example.SocailMedia.Entity.User;
import com.example.SocailMedia.Entity.UserProfile;
import com.example.SocailMedia.Models.*;
import com.example.SocailMedia.Service.UserService;
import com.example.SocailMedia.validator.GroupValidator;
import com.example.SocailMedia.validator.UserValidator;
import com.oracle.wls.shaded.org.apache.xpath.operations.Mod;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class HomeController {
    public int UserId1=0;
    public int GroupId1=0;
    public int G_UserId=0;
    public String userName1;
    public String Password1;
    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;

    @Autowired
    private GroupValidator groupValidator;
    @RequestMapping("/")
    public String home(){
        return "homepage";
    }


    @RequestMapping("/Register")
    public String Register()
    {
        return "addUser";
    }
    @RequestMapping("/login")
    public String Login()
    {
        return "login";
    }

    @RequestMapping("/CheckLogin")

    public String CheckLogin(String userName,String Password, Model model)
    {

        int result=userService.CheckLogin(userName,Password);
        System.out.println(result);
        if(result!=0)
        {
            userName1=userName;
            G_UserId=result;
            System.out.println("USER ID IS"+G_UserId);
            return "index";
        }
        else{
            String message="PLEASE LOGIN CORRECTLY";
            model.addAttribute("message",message);
            return "ShowLoginResults";
        }
    }

    //adding user
    @RequestMapping("/addUser")
    public String addUser()
    {
        return "addUser";
    }
    @RequestMapping("/SaveUser")
    public String SaveUser(@Valid UserModel userModel,Model model, BindingResult bindingResult)
    {
        System.out.println(bindingResult);
        userValidator.validate(userModel,bindingResult);
        if(bindingResult.hasErrors())
        {
            System.out.println("bhanu");
            return "addUser";
        }
        userValidator.validate(userModel,bindingResult);

        userService.addUser(userModel);
        List<User>u=userService.display();
        model.addAttribute("all",u);
        System.out.println("USER IloginD IS"+G_UserId);
        model.addAttribute("loginUserId",G_UserId);


        return "redirect:/";
    }
    @RequestMapping("/viewUsers")
    public String viewUsers(Model model)
    {
        List<User>u=userService.display();
        model.addAttribute("all",u);
        model.addAttribute("loginUserId",G_UserId);
        System.out.println("THE USER IS"+G_UserId);
        return "viewEmployees";

    }
    @RequestMapping("/removeemployee")
    public String removeemployee(int eid)
    {
        userService.remove(eid);
        return "redirect:/viewUsers";
    }
    @RequestMapping("/SearchEmployee")
    public String SearchEmployee( int eid,Model model)
    {
        System.out.println("METHOD CALLED");
        System.out.println(eid);
        User e2=userService.userProfile(eid);
        UserId1=e2.getUserId();
//        System.out.println(e2.getEname());
        model.addAttribute("user",e2);
        return "singleEmployee";
    }
    @RequestMapping("/Addposts")
    public String Addposts(int userId)
    {
        UserId1=userId;
        return "addPost";
    }

    @RequestMapping("/SavePost")
    public String SavePost(Post post,Model model)
    {
        userService.savepost(post,UserId1);
        List<User>u=userService.display();
        model.addAttribute("all",u);

        model.addAttribute("loginUserId",G_UserId);
        return "viewEmployees";
    }

    @RequestMapping("/ViewPosts")
    public String ViewPosts(int userId,Model model)
    {
        UserId1=userId;
        List<Post>postList=userService.viewPost(userId);
        model.addAttribute("post",postList);
        model.addAttribute("userId",userId);
        return "ViewPosts";
    }











}

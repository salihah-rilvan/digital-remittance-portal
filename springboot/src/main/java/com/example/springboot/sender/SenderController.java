package com.example.springboot.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.springboot.ssot.Ssot;
import com.example.springboot.ssot.SsotService;
import com.example.springboot.ssotReceiverMap.SsotReceiverMapService;
import com.example.springboot.ssotSenderMap.SsotSenderMapService;
import com.example.springboot.transaction.Transaction;
import com.example.springboot.transaction.TransactionService;


@RestController 
public class SenderController {
    private final SenderService senderService;

    @Autowired
    SsotService ssotService;

    @Autowired
    SsotSenderMapService ssotSenderMapService;

    @Autowired
    SsotReceiverMapService ssotReceiverMapService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SenderController(SenderService senderService) {
        this.senderService = senderService;
    }

    // Get login page
    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.setViewName("login");
        return model;
    }

    // Redirecting user when accessing root URL
    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public ModelAndView blank(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        HttpSession session = request.getSession(false);

        // if no session is created, redirect to login page
        if (session == null){
            model.setViewName("redirect:/login");
        }else{
            // if there is no attribute senderid in session, redirect to admin page, else redirect to home page
            if (session.getAttribute("senderid") == null){
                model.setViewName("redirect:/admin");
            }else{
                model.setViewName("redirect:/home");
            }
        }

        return model;
    }

    // Get signup page
    @RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView model = new ModelAndView();
        Sender sender = new Sender();
        model.addObject("sender", sender);
        model.setViewName("signup");
        return model;
    }

    // Processing signup request (for sender only)
    @RequestMapping(value = { "/signup" }, method = RequestMethod.POST)
    public ModelAndView createUser(@Validated Sender sender, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        Optional<Sender> senderExists = senderService.findSenderByEmail(sender.getEmail());

        // Check if the restering sender is existed in the database or share the same email as Admin
        if (senderExists.isPresent() || inMemoryUserDetailsManager.userExists(sender.getEmail())) {
            model.addObject("msg2", "This email already exists!");
            bindingResult.rejectValue("email", "error.user", "This email already exists!");
        }
        if (bindingResult.hasErrors()) {
            model.addObject("sender", new Sender());
            model.setViewName("signup");
        } else {
            senderService.addNewSender(sender);
            model.addObject("msg", "User has been registered successfully!");
            model.addObject("sender", new Sender());
            model.setViewName("signup");
        }

        return model;
    }

    // Get home page
    @RequestMapping(value = { "/home" }, method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request) {
        // If user is admin, redirect to /admin
        if (request.isUserInRole("ROLE_ADMIN")) {
            return new ModelAndView("redirect:/admin");
        }

        // Get the authorised sender
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Sender> senderExists = senderService.findSenderByEmail(auth.getName());

        Sender s = senderExists.get();
        String msg = s.getCompany() ; // + s.getId()

        // Start session and add senderid to session
        HttpSession session = request.getSession();
        session.setAttribute("senderid", s.getId());

        long senderId = s.getId();
        ArrayList<Transaction> t = transactionService.getTransactionBySender(senderId);  

        // Get the number of field mappings and ssots to alert sender to submit/update field mappings
        if (session.getAttribute("noOfFields") == null){
            List<Ssot> ssots = ssotService.findAll();
            HashMap<Long, String> prev = ssotSenderMapService.getSenderField(senderId);          
            session.setAttribute("noOfFields", prev.size());
            session.setAttribute("noOfSsots", ssots.size());    

        }

        // Send objects to front end
        model.addObject("allTransactions", t);        
        model.addObject("msg", msg);
        model.setViewName("home");
        return model;
    }

    // Get setting page
    @RequestMapping(value = { "/settings" }, method = RequestMethod.GET)
    public ModelAndView settings(HttpSession session) {
        ModelAndView model = new ModelAndView();
        model.setViewName("settings");
        return model;
    }
    
    // Delete sender
    @DeleteMapping(path = "{senderId}")
    public void deleteSender(@PathVariable("senderId") Long senderId) {
        senderService.deleteSender(senderId);
    }

    // Update password for user
    @RequestMapping(value = { "/password_user" }, method = RequestMethod.POST)
    public ModelAndView updateSenderPassword(@RequestParam("email") String email ,@RequestParam("curPassword") String password,@RequestParam("newPassword") String newPassword, HttpSession session) {
        ModelAndView model = new ModelAndView();
        long senderId = (Long) session.getAttribute("senderid");
        Sender sender = senderService.findSenderById(senderId).get();

        // Check if the entered email and password matched with the record in database
        if (sender.getEmail().equals(email) && bCryptPasswordEncoder.matches(password, sender.getPassword())){
            if (newPassword != null && newPassword.length() > 0){

                // update password
                senderService.updateSenderPassword(senderId, newPassword);
                model.addObject("msg", "Changes saved");
            }else{
                model.addObject("msg2", "Please enter a new password");

            }

        }else{
            model.addObject("msg2", "Wrong email or password!");
        }

        model.setViewName("settings");
        return model;
    }

    // update password for admin
    @RequestMapping(value = { "/password_admin" }, method = RequestMethod.POST)
    public ModelAndView updateAdminPassword(@RequestParam("email") String email ,@RequestParam("curPassword") String password,@RequestParam("newPassword") String newPassword) {
        ModelAndView model = new ModelAndView();

        // Check if the entered email and password match the record stored in memory
        // only 1 admin stored in memory
        try {
            UserDetails user = inMemoryUserDetailsManager.loadUserByUsername(email);
            if (bCryptPasswordEncoder.matches(password, user.getPassword())){
                if (newPassword != null && newPassword.length() > 0){
                    UserDetails u = inMemoryUserDetailsManager.updatePassword(user, bCryptPasswordEncoder.encode(newPassword));    
                    model.addObject("msg", "Changes saved");
    
                }else{
                    model.addObject("msg2", "Please enter a new password");

                }
            }else{
                model.addObject("msg2", "The current password is wrong!");
            }

            model.setViewName("adminSettings");
            return model;

        } catch (UsernameNotFoundException e){
            model.addObject("msg2", "Your email is incorrect");
            model.setViewName("adminSettings");
            return model;

        }
     
    }

    // Logout and destroy session, user is redirected to login page
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login"; 
    }

    // get submit field name page
    @RequestMapping(value = { "/submitNew" }, method = RequestMethod.GET)
    public ModelAndView submitNew() {
        ModelAndView model = new ModelAndView();
        model.setViewName("submitNew");
        return model;
    }

}

package com.shetu.bookstore.controller;

import com.shetu.bookstore.domain.*;
import com.shetu.bookstore.domain.security.PasswordResetToken;
import com.shetu.bookstore.domain.security.Role;
import com.shetu.bookstore.domain.security.UserRole;
import com.shetu.bookstore.service.BookService;
import com.shetu.bookstore.service.UserPaymentService;
import com.shetu.bookstore.service.UserService;
import com.shetu.bookstore.service.impl.UserSecurityService;
import com.shetu.bookstore.utility.MailConstructor;
import com.shetu.bookstore.utility.SecurityUtility;
import com.shetu.bookstore.utility.USConstants;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.*;

@Controller
public class HomeController {
    //Field injectin : MailSender, MailConstructor,UserService,UserServiceSecurity
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserPaymentService userPaymentService;

    //index
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    //login
    @RequestMapping("/login")
    public String login(Model model) {
        //if classActiveLogin is true then
        model.addAttribute("classActiveLogin", true);
        //sent to the myAccount page
        return "myAccount";
    }

    //forgetpassword
    @RequestMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request,
                                 @ModelAttribute("email") String email, Model model) {
        model.addAttribute("classActiveForgetPassword", true);


        //Get the usr from user service
        User user = userService.findByEmail(email);

        //logic :if does not exists
        if (user == null) {
            model.addAttribute("emailNotExist", true);
            return "myAccount";
        }
        //logic :if exists
        //generate a random password
        String password = SecurityUtility.randomPassword();
        //encypt the pasword
        String encrytedPassword = SecurityUtility.passwordEncoder().encode(password);
        //set the password
        user.setPassword(encrytedPassword);
        //save it
        userService.save(user);

        //generate a random token and send it to the email
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        //url and mail
        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
        mailSender.send(newEmail);
        model.addAttribute("forgetPasswordEmailSent", true);
        return "myAccount";
    }

    //post the newUser
    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUserPost(
            HttpServletRequest request,
            @ModelAttribute("email") String userEmail,
            @ModelAttribute("username") String username,
            Model model
    ) throws Exception {
        //add the valude true or false
        model.addAttribute("classActiveNewAccount", true);
        //add the userEmail
        model.addAttribute("email", userEmail);
        //add the username
        model.addAttribute("username", username);

        //add the logic
        if (userService.findByUsername(username) != null) {
            //create an object and send the data using model
            model.addAttribute("usernameExists", true);
            return "myAccount";
        }

        if (userService.findByEmail(userEmail) != null) {
            //create an object
            model.addAttribute("emailExists", true);
            return "myAccount";
        }

        //if username or email does not exist then create a new User and his role, token

        //User
        User user = new User();
        //set the username
        user.setUsername(username);
        //set the email
        user.setEmail(userEmail);
        //set the encrypted password
        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);

        //Role
        Role role = new Role();
        //set the role id = 1
        role.setRoleId(1);
        //set the role as user
        role.setName("ROLE_USER");
        //set the userRoles
        Set<UserRole> userRoles = new HashSet<>();
        //add the user and role to the UserRole class
        userRoles.add(new UserRole(user, role));
        //now create new user using the userservice object
        userService.createUser(user, userRoles);

        //Token
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        //send to the mail
        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);
        mailSender.send(email);
        model.addAttribute("emailSent", "true");
        return "myAccount";
    }

    //myProfile
    @RequestMapping("/myProfile")
    public String myProfile(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
//        model.addAttribute("orderList",user.getOrderList());

        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping", userShipping);

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("listOfShippingAddresses", true);

        List<String> stateList = USConstants.listOfUSStatesCode;
        Collections.sort(stateList);
        model.addAttribute("stateList", stateList);
        model.addAttribute("classActiveEdit", true);
        return "myProfile";
    }

    //listOfCreditCards
    @RequestMapping("/listOfCreditCards")
    public String listOfCreditCards(Model model, Principal principal, HttpServletRequest request) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
//        model.addAttribute("orderList",user.orderList());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);
        return "myProfile";
    }


    //listofShippingAddresses
    @RequestMapping("/listOfShippingAddresses")
    public String listOfShippingAddresses(Model model, Principal principal, HttpServletRequest request) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
//        model.addAttribute("orderList",user.orderList());

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "myProfile";
    }

    //add New Credit Card
    @RequestMapping("/addNewCreditCard")
    public String addNewCreditCard(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("addNewCreditCard", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        UserBilling userBilling = new UserBilling();
        UserPayment userPayment = new UserPayment();

        model.addAttribute("userBilling", userBilling);
        model.addAttribute("userPayment", userPayment);

        List<String> stateList = USConstants.listOfUSStatesCode;
        Collections.sort(stateList);
        model.addAttribute("stateList", stateList);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());

        return "myProfile";
    }

    //post: addNewCreditCard
    @RequestMapping(value = "/addNewCreditCard", method = RequestMethod.POST)
    public String addNewCreditCard(@ModelAttribute("userPayment") UserPayment userPayment,
                                   @ModelAttribute("userBilling") UserBilling userBilling, Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        //call the method to update
        userService.updateUserBilling(userBilling, userPayment, user);
        //send to the myProfile page
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "myProfile";

    }

    /////////update credit card
    @RequestMapping("/updateCreditCard")
    public String updateCreditCard(@ModelAttribute("id") Long creditCardId, Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.findById(creditCardId);

        if (user.getId() != userPayment.getUser().getId()) {
            return "badRequestPage";
        } else {
            model.addAttribute("user", user);
            UserBilling userBilling = userPayment.getUserBilling();
            model.addAttribute("userPayment", userPayment);
            model.addAttribute("userBilling", userBilling);

            List<String> stateList = USConstants.listOfUSStatesCode;
            Collections.sort(stateList);
            model.addAttribute("stateList", stateList);

            model.addAttribute("addNewCreditCard", true);
            model.addAttribute("classActiveBilling", true);
            model.addAttribute("listOfShippingAddresses", true);

            model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("userShippingList", user.getUserShippingList());
            return "myProfile";
        }

    }

    //////////////////////////////////// Remove Credit card
    @RequestMapping("/removeCreditCard")
    public String removeCreditCard(Model model, Principal principal, @ModelAttribute("id") Long creditCardId){
        User user = userService.findByUsername(principal.getName());
        UserPayment userPayment = userPaymentService.findById(creditCardId);

        if (user.getId() != userPayment.getUser().getId()) {
            return "badRequestPage";
        } else {
            model.addAttribute("user", user);
          userPaymentService.removeById(creditCardId);


            model.addAttribute("listOfCreditCards", true);
            model.addAttribute("classActiveBilling", true);
            model.addAttribute("listOfShippingAddresses", true);

            model.addAttribute("userPaymentList", user.getUserPaymentList());
            model.addAttribute("userShippingList", user.getUserShippingList());

            return "myProfile";
        }
    }

    ////////////////setDefaultMapping = Post
    @RequestMapping(value = "/setDefaultPayment",method = RequestMethod.POST)
    public String setDefaultPayment(Model model,Principal principal,@ModelAttribute("defaultUserPaymentId") Long defaultPaymentId){
        //create user and user payment
        User user = userService.findByUsername(principal.getName());
        userService.setUserDefaultPayment(defaultPaymentId,user);

        model.addAttribute("user", true);
        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());

        return "myProfile";
    }

    //add New shipping Address
    @RequestMapping("/addNewShippingAddress")
    public String addNewShippingAddress(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("classActiveShipping", true);
        model.addAttribute("listOfCreditCards", true);


        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping", userShipping);

        List<String> stateList = USConstants.listOfUSStatesCode;
        Collections.sort(stateList);
        model.addAttribute("stateList", stateList);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());

        return "myProfile";
    }


    //show the new User
    @RequestMapping("/newUser")
    public String newUser(Locale local, @RequestParam("token") String token, Model model) {
        //Make an object of the PasswordResetToken
        PasswordResetToken passToken = userService.getPasswordResetToken(token);

        //logic: if passToken is null
        if (passToken == null) {
            String message = "Invalid Token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }

        //if not null
        //Get the user from the token
        User user = passToken.getUser();
        String username = user.getUsername();
        //Use the security : spring security core
        UserDetails userDetails = userSecurityService.loadUserByUsername(username);
        //use authentication : spring security core
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
        //security context holder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        model.addAttribute("user", user);
        model.addAttribute("classActiveEdit", true);
        return "myProfile";
    }

    //add Bookshelf mapping
    @RequestMapping("/bookshelf")
    public String bookshelf(Model model) {
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "bookshelf";
    }

    //add Bookdetail
    @RequestMapping("/bookDetail")
    public String bookDetail(@PathParam("id") Long id, Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        Book book = bookService.findBookByBookId(id);
        model.addAttribute("book", book);
        List<Integer> qtyList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        model.addAttribute("qtyList", qtyList);
        model.addAttribute("qty", 1);
        return "bookDetail";
    }
}

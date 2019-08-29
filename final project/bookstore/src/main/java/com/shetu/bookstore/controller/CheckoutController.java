package com.shetu.bookstore.controller;

import com.shetu.bookstore.domain.*;
import com.shetu.bookstore.service.*;
import com.shetu.bookstore.utility.MailConstructor;
import com.shetu.bookstore.utility.USConstants;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
public class CheckoutController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShippingAddressService shippingAddressService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BillingAddressService billingAddressService;

    @Autowired
    private UserShippingService userShippingService;

    @Autowired
    private UserPaymentService userPaymentService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MailConstructor mailConstructor;

    private ShippingAddress shippingAddress = new ShippingAddress();
    private BillingAddress billingAddress = new BillingAddress();
    private Payment payment = new Payment();

    @RequestMapping("/checkout")
    public String checkout(Model model, Principal principal, @RequestParam("id") Long cartId,
                           @RequestParam(value = "missingRequiredField", required = false) boolean missingRequiredField) {
        //create a user to find the authorization
        User user = userService.findByUsername(principal.getName());

        //logic
        if (cartId != user.getShoppingCart().getId()) {
            return "badRequestPage";
        }
        //now find the cartItem list using cartItemService
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

        //logic : cartItemSize check
        if (cartItemList.size() == 0) {
            model.addAttribute("emptyCart", true);
            return "forward:/shoppingCart/cart";
        }

        //forEach loop
        for (CartItem cartItem : cartItemList) {
            //logic: if stockNumber < qty
            if (cartItem.getBook().getIntStockNumber() < cartItem.getQty()) {
                model.addAttribute("notEnoughStock", true);
                return "forward:/shoppingCart/cart";
            }
        }

        /////list : usershipping , userPayment
        List<UserShipping> userShippingList = user.getUserShippingList();
        List<UserPayment> userPaymentList = user.getUserPaymentList();

        //send to the template
        model.addAttribute("userShippingList", userShippingList);
        model.addAttribute("userPaymentList", userPaymentList);

        //logic; check for user payment
        if (userPaymentList.size() == 0) {
            model.addAttribute("emptyPaymentList", true);
        } else {
            model.addAttribute("emptyPaymentList", false);
        }

        //logic; check for user Shipping
        if (userShippingList.size() == 0) {
            model.addAttribute("emptyShippingList", true);
        } else {
            model.addAttribute("emptyShippingList", false);
        }

        //add to the shopping cart
        ShoppingCart shoppingCart = user.getShoppingCart();

        //for loop: user shipping
        for (UserShipping userShipping : userShippingList) {
            //logic: check default Shipping
            if (userShipping.isUserShippingDefault()) {
                shippingAddressService.setByUserShipping(userShipping, shippingAddress);
            }
        }

        //for loop: user payment
        for (UserPayment userPayment : userPaymentList) {
            if (userPayment.isDefaultPayment()) {
                paymentService.setByUserPayment(userPayment, payment);
                billingAddressService.setByUserBilling(userPayment.getUserBilling(), billingAddress);
            }
        }

        model.addAttribute("shippingAddress", shippingAddress);
        model.addAttribute("payment", payment);
        model.addAttribute("billingAddress", billingAddress);
        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", user.getShoppingCart());

        List<String> stateList = USConstants.listOfUSStatesCode;
        Collections.sort(stateList);
        model.addAttribute("stateList", stateList);

        model.addAttribute("classActiveShipping", true);

        //logic : for required field
        if (missingRequiredField) {
            model.addAttribute("missingRequiredField", true);
        }

        return "checkout";
    }

    //////////////////////////////////////////check out post mapping
    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkoutPost(
            @ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
            @ModelAttribute("billingAddress") BillingAddress billingAddress,
            @ModelAttribute("payment") Payment payment,
            @ModelAttribute("billingSameAsShipping") String billingSameAsShipping,
            @ModelAttribute("shippingMethod") String shippingMethod,
            Model model, Principal principal
    ) {
        //create the shopping cart
        ShoppingCart shoppingCart = userService.findByUsername(principal.getName()).getShoppingCart();
        ///create a list
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        model.addAttribute("cartItemList", cartItemList);

        //logic : check the billing address same as shipping
        if (billingSameAsShipping.equals("true")) {
            billingAddress.setBillingAddressName(shippingAddress.getShippingAddressName());
            billingAddress.setBillingAddressStreet1(shippingAddress.getShippingAddressStreet1());
            billingAddress.setBillingAddressStreet2(shippingAddress.getShippingAddressStreet2());
            billingAddress.setBillingAddressCity(shippingAddress.getShippingAddressCity());
            billingAddress.setBillingAddressState(shippingAddress.getShippingAddressState());
            billingAddress.setBillingAddressCountry(shippingAddress.getShippingAddressCountry());
            billingAddress.setBillingAddressZipcode(shippingAddress.getShippingAddressZipcode());
        }

        //logic: shipping Address empty
        if (shippingAddress.getShippingAddressStreet1().isEmpty() ||
                shippingAddress.getShippingAddressCity().isEmpty() ||
                shippingAddress.getShippingAddressState().isEmpty() ||
                shippingAddress.getShippingAddressName().isEmpty() ||
                shippingAddress.getShippingAddressZipcode().isEmpty() ||
                payment.getCardNumber().isEmpty() ||
                payment.getCvc() == 0 ||
                billingAddress.getBillingAddressStreet1().isEmpty() ||
                billingAddress.getBillingAddressCity().isEmpty() ||
                billingAddress.getBillingAddressState().isEmpty() ||
                billingAddress.getBillingAddressName().isEmpty() ||
                billingAddress.getBillingAddressZipcode().isEmpty()

        ) {
            return "redirect:/checkout?id="+shoppingCart.getId()+"&missingRequiredField=true";
        }

            ///create User
            User user = userService.findByUsername(principal.getName());

            //Create Order
            Order order = orderService.createOrder(shoppingCart,shippingAddress,billingAddress,payment,shippingMethod,user);
            //send mail
            mailSender.send(mailConstructor.constructOrderConfirmationEmail(user,order, Locale.ENGLISH));

            ///clear the shopping cart
            shoppingCartService.clearShoppingCart(shoppingCart);

            ///date
            LocalDate today = LocalDate.now();
            LocalDate estimatedDeliveryDate;

            //logic for sending in date
            if(shippingMethod.equals("groundShipping")){
                estimatedDeliveryDate = today.plusDays(5);
            }else {
                estimatedDeliveryDate = today.plusDays(3);
            }

            ///////////////////
            model.addAttribute("estimatedDeliveryDate",estimatedDeliveryDate);
            return "orderSubmittedPage";
        }



    /////////////////////////// set Shipping Address
    @RequestMapping("/setShippingAddress")
    public String setShippingAddress(@RequestParam("userShippingId") Long userShippingId, Principal principal, Model model) {
        //create a user
        User user = userService.findByUsername(principal.getName());

        //create User Shipping Service
        UserShipping userShipping = userShippingService.findById(userShippingId);

        //logic
        if (userShipping.getUser().getId() != user.getId()) {
            return "badRequestPage";
        } else {
            //Set User Shiping Address
            shippingAddressService.setByUserShipping(userShipping, shippingAddress);

            //get the data using list
            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

            model.addAttribute("shippingAddress", shippingAddress);
            model.addAttribute("payment", payment);
            model.addAttribute("billingAddress", billingAddress);
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("shoppingCart", user.getShoppingCart());

            //////////Create the list
            List<String> stateList = USConstants.listOfUSStatesCode;
            Collections.sort(stateList);
            model.addAttribute("stateList", stateList);

            ////Create User Shipping List and User Payment List
            List<UserShipping> userShippingList = user.getUserShippingList();
            List<UserPayment> userPaymentList = user.getUserPaymentList();

            ///add these attribute
            model.addAttribute("userShippingList", userShippingList);
            model.addAttribute("userPaymentList", userPaymentList);

            model.addAttribute("shippingAddress", shippingAddress);

            model.addAttribute("classActiveShipping", true);

            //logic; check for user payment
            if (userPaymentList.size() == 0) {
                model.addAttribute("emptyPaymentList", true);
            } else {
                model.addAttribute("emptyPaymentList", false);
            }

            //shipping address will always be available
            model.addAttribute("emptyShippingList", false);


            return "checkout";
        }


    }

    /////////////////////////////////////////Set Payment Method
    @RequestMapping("/setPaymentMethod")
    public String setPaymentMethod(@RequestParam("userPaymentId") Long userPaymentId,
                                   Principal principal, Model model) {
        //create a user
        User user = userService.findByUsername(principal.getName());
        //create a userPayment
        UserPayment userPayment = userPaymentService.findById(userPaymentId);
        //create a UserBilling
        UserBilling userBilling = userPayment.getUserBilling();

        ///logic: check the user Payement and user Billing
        if (userPayment.getUser().getId() != user.getId()) {
            return "badRequestPage";
        } else {
            paymentService.setByUserPayment(userPayment, payment);

            //get the data using list
            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

            //Check the billing Address
            billingAddressService.setByUserBilling(userBilling, billingAddress);

            model.addAttribute("shippingAddress", shippingAddress);
            model.addAttribute("payment", payment);
            model.addAttribute("billingAddress", billingAddress);
            model.addAttribute("cartItemList", cartItemList);
            model.addAttribute("shoppingCart", user.getShoppingCart());

            //////////Create the list
            List<String> stateList = USConstants.listOfUSStatesCode;
            Collections.sort(stateList);
            model.addAttribute("stateList", stateList);

            ////Create User Shipping List and User Payment List
            List<UserShipping> userShippingList = user.getUserShippingList();
            List<UserPayment> userPaymentList = user.getUserPaymentList();

            ///add these attribute
            model.addAttribute("userShippingList", userShippingList);
            model.addAttribute("userPaymentList", userPaymentList);

            model.addAttribute("shippingAddress", shippingAddress);

            model.addAttribute("classActivePayment", true);

            //Payment will always be available

            model.addAttribute("emptyPaymentList", false);


            //logic; check for user Shipping
            if (userShippingList.size() == 0) {
                model.addAttribute("emptyShippingList", true);
            } else {
                model.addAttribute("emptyShippingList", false);
            }

            return "checkout";

        }

    }
}

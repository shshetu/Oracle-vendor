package com.shetu.bookstore.controller;

import com.shetu.bookstore.domain.Book;
import com.shetu.bookstore.domain.CartItem;
import com.shetu.bookstore.domain.ShoppingCart;
import com.shetu.bookstore.domain.User;
import com.shetu.bookstore.service.BookService;
import com.shetu.bookstore.service.CartItemService;
import com.shetu.bookstore.service.ShoppingCartService;
import com.shetu.bookstore.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private BookService bookService;

    @RequestMapping("/cart")
    public String shoppingCart(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);
        return "shoppingCart";
    }

    @RequestMapping("/addItem")
    public String addItem(Model model, Principal principal,
                          @ModelAttribute("book") Book book,
                          @ModelAttribute("qty") String qty) {
        User user = userService.findByUsername(principal.getName());
        book = bookService.findBookByBookId(book.getId());

        if(Integer.parseInt(qty) > book.getIntStockNumber()){
            model.addAttribute("notEnoughStock",true);
            return "forward:/bookDetail?id="+book.getId();
        }

        CartItem cartItem = cartItemService.addBookToCartItem(book,user,Integer.parseInt(qty));
        model.addAttribute("addBookSuccess",true);

        return "forward:/bookDetail?id="+book.getId();
    }

    //////////////update Cart item
    @RequestMapping("/updateCartItem")
    public String updateShoppingCart(Model model,Principal principal,
                                     @ModelAttribute("id") Long cartItemId,
                                     @ModelAttribute("qty") int qty){
        //create the object of cartItem
        CartItem cartItem = cartItemService.findById(cartItemId);
        //set the quantity
        cartItem.setQty(qty);
        //update teh cartItem
        cartItemService.updateCartItem(cartItem);

        return "forward:/shoppingCart/cart";
    }

    //////////////////////////Remove Cart Item
    @RequestMapping("/removeItem")
    public String removeItem(@RequestParam("id") Long id){
        cartItemService.removeCartItem(cartItemService.findById(id));
        return "forward:/shoppingCart/cart";
    }

}

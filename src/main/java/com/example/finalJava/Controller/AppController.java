package com.example.finalJava.Controller;

import com.example.finalJava.Repository.UserRepository;
import com.example.finalJava.Service.ICartService;
import com.example.finalJava.Service.IUserService;
import com.example.finalJava.Service.ProductService;
import com.example.finalJava.dto.CartInfo;
import com.example.finalJava.dto.CartItem;
import com.example.finalJava.dto.Product;
import com.example.finalJava.dto.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProductService ProductService;

    @Autowired
    private ICartService cartService;

    @Autowired
    private IUserService userService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/home")
    public String viewHomePage(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user);
        }
        CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");
        List<CartItem> cartItems = cartService.getItems(cartInfo);
        if (cartItems != null) {
            model.addAttribute("quantity", cartItems.size());
        }
        return "index";
    }

    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register-form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        userRepo.save(user);
        return "redirect:";
    }

    @GetMapping("/product")
    public String showProductList(Model model) {
        List<Product> productList = ProductService.getAllProduct();
        model.addAttribute("productList", productList);
        return "shop-grid";
    }

    @GetMapping("/product/{id}")
    public String getProductById(@PathVariable int id, Model model) {
        Product product = ProductService.getProductById(id);
        model.addAttribute("product", product);
        return "shop-details";
    }

    @GetMapping("/shopping-cart")
    public String showShoppingCartPage(Model model) {
        CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");
        List<CartItem> cartItems = cartService.getItems(cartInfo);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.getCartTotal(cartItems));
        model.addAttribute("quantity", cartItems.size());
        return "shoping-cart";
    }

    @GetMapping("/shop-details")
    public String showShoppingDetailPage() {
        return "shop-details";
    }

    @GetMapping("/shop-grid")
    public String showShoppingGridPage(Model model) {
        CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");
        List<CartItem> cartItems = cartService.getItems(cartInfo);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", cartService.getCartTotal(cartItems));
        model.addAttribute("quantity", cartItems.size());
        return "shop-grid";
    }

    @PostMapping("/add-to-cart/{productId}")
    public String addToCart(@PathVariable Integer productId) {
        CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");
        if (cartInfo == null) {
            cartInfo = new CartInfo();
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null) {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                cartInfo.setUser(user);
            }
        }
        cartService.addToCart(cartInfo, productId);
        request.getSession().setAttribute("myCart", cartInfo);
        return "redirect:/shop-grid";
    }

    @GetMapping("/checkout")
    public String checkout() {
        CartInfo cartInfo = (CartInfo) request.getSession().getAttribute("myCart");
        if (cartInfo == null) {
            return "redirect:/home";
        }
        cartService.checkout(cartInfo);
        request.getSession().setAttribute("myCart", new CartInfo());
        return "redirect:/home";
    }
}
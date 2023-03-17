package com.siyuchen.skiswap.controller;


import com.siyuchen.skiswap.dto.ProductDTO;
import com.siyuchen.skiswap.exception.ProductNotFoundException;
import com.siyuchen.skiswap.model.Category;
import com.siyuchen.skiswap.model.Product;
import com.siyuchen.skiswap.service.CategoryService;
import com.siyuchen.skiswap.service.ProductService;
import com.siyuchen.skiswap.service.UserService;
import com.siyuchen.skiswap.utility.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author Siyu Chen
 *  This class is to handle the product request from the frontend.
 */
@Controller
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    private String productIdForUpdate;

    /**
     * This method is to retrieve all the ski products from database and send the data to frontend.
     * @param model
     * @return render skis.html file
     */
    @GetMapping("/skis")
    private String loadSkiProducts(Model model){
        List<ProductDTO> skiProducts = productService.getAllProductByCategory("SKI");
        model.addAttribute("productDtos", skiProducts);
        log.info("Ski product displayed");
        return "skis";
    }

    /**
     * This method is to retrieve all the snowboard products from database and send the data to frontend.
     * @param model
     * @return render snowboards.html file
     */
    @GetMapping("/snowboards")
    private String loadSnowboardProducts(Model model){
        List<ProductDTO> snowboardProduct = productService.getAllProductByCategory("SNOWBOARD");
        model.addAttribute("productDtos", snowboardProduct);
        log.info("Snowboard product displayed");
        return "snowboards";
    }

    /**
     * This method is to retrieve all the snow essential products from database and send the data to frontend.
     * @param model
     * @return render essentials.html file
     */
    @GetMapping("/snowEssentials")
    private String loadEssentialProducts(Model model){
        List<ProductDTO> essentialProduct = productService.getAllProductByCategory("ESSENTIAL");
        model.addAttribute("productDtos", essentialProduct);
        log.info("Snow Essential product displayed");
        return "essentials";
    }

    /**
     * This method is to get all the products for ADMIN
     * @param model
     * @return render product_list.html
     */
    @GetMapping("/allProducts")
    public String getAllProducts(Model model){
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product_list";
    }

    /**
     * This method is to retrieve all the product listed by current user
     * @param model
     * @return render all products posted by this user on product_list.html
     */
    @GetMapping("/myList")
    public String getMyProductList(Model model, Authentication authentication)  {
        String userEmail = authentication.getName();
        Set<Product> products = productService.findProductsByUserEmail(userEmail);
//        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product_list";
    }

    /**
     * This method is for processing user's request to create the product
     * @param model
     * @return render product_form.html
     */
    @GetMapping("/myList/save")
    public String newProduct(Model model){
        List<Category> categoryList = categoryService.getAllCategories();
        // the data from the empty form will be bind with this new userDTO
        model.addAttribute("productDto", new ProductDTO());
        model.addAttribute("categories", categoryList);
        return "product_form";
    }

    /**
     * This method is for processing user's request to create product or update an existing product.
     * If the private field productIdForUpdate is not null or is not empty, meaning that an existing product needs to
     * be updated.
     * If the private field productIdForUpdate has a UUID, pass the value to the productId of productDTO, and update
     * product information in Service based on productDTO. Clear the value in productIdForUpdate.
     * @param productDTO
     * @param bindingResult
     * @param redirectAttributes
     * @param authentication
     * @return redirected back to /myList.
     */
    @PostMapping("/myList/save/processing")
    public String createProductProcess(@Valid @ModelAttribute("productDto") ProductDTO productDTO,
                                       BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                       Authentication authentication,
                                       @RequestParam("image")MultipartFile multipartFile) throws IOException {
        // if it is to create a new product
        if (productIdForUpdate== null || productIdForUpdate.equals("")){
            if (multipartFile.isEmpty()){
                log.warn("Wrong attempt");
                redirectAttributes.addFlashAttribute("message", "Please upload an image!");
                return "redirect:/myList/save";
            }
            // get the name of current file
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String userEmail = authentication.getName();
            productDTO.setUserEmail(userEmail);
            productDTO.setImage(fileName);
            log.info("Create new product in database");
            Product product = productService.createProduct(productDTO);
            // update image into product-image folder
            String uploadDir = "product-images/" + product.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

            redirectAttributes.addFlashAttribute("message", "Item added successfully!");

        } else{
            if (multipartFile.isEmpty()){
                // for update, if there is no image uploaded, remain the previous file name
                Product product = productService.findProductByProductId(productIdForUpdate);
                productDTO.setImage(product.getImage());
            } else {
                // update image into product-image folder
                String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                productDTO.setImage(fileName);
                // upload image into corresponding folder
                Product product = productService.findProductByProductId(productIdForUpdate);
                String uploadDir = "product-images/" + product.getId();
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
            log.info("Update product information in database");
            productDTO.setProductId(productIdForUpdate);
            productService.updateProductInfo(productDTO);
            redirectAttributes.addFlashAttribute("message", "Item updated successfully!");
            productIdForUpdate ="";

        }
        return "redirect:/myList";
    }

    /**
     * This method is to handle the delete request sent from admin
     * @param id
     * @param model
     * @param redirectAttributes
     * @return redirect to the url requesting getting all products for admin
     */
    @RequestMapping("/allProducts/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes){
        productService.deleteProductById(id);
        redirectAttributes.addFlashAttribute("message", "The product has been deleted successfully");
        return "redirect:/allProducts";
    }
    /**
     * This method is to handle the delete request sent from USER only
     * @param id
     * @param model
     * @param redirectAttributes
     * @return redirect to the url requesting getting the products associated with current user
     */
    @RequestMapping("/myList/delete/{id}")
    public String deleteMyProduct(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes){
        productService.deleteProductById(id);
        redirectAttributes.addFlashAttribute("message", "The product has been deleted successfully");
        return "redirect:/myList";
    }

    /**
     * This method is for User to mark the item they want to sell is available or not.
     * @param id
     * @return redirect to the myList mapping
     */
    @RequestMapping("/myList/status/{id}")
    public String updateInStockById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes)
            throws ProductNotFoundException {
        try{
            productService.updateProductAvailabilityById(id);
        } catch (ProductNotFoundException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/myList";
    }

    /**
     * This method is to process the editing product information regarding to the product identified by its ID.
     * If product exist, pass the product.productID to the private field productIdForUpdate. And populate data
     * on the product_form
     * If product doesn't exist, throw productNotFoundException.
     * @param id
     * @param model
     * @param redirectAttributes
     * @return
     * @throws ProductNotFoundException
     */
    @GetMapping("/myList/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes)
            throws ProductNotFoundException {
        try{
            Product product = productService.findProductById(id);
            ProductDTO productDTO = productService.getProductDTOByProduct(product);
//            System.out.println("=========================productDto id " + productDTO.getProductId());
            productIdForUpdate = productDTO.getProductId();
            model.addAttribute("productDto", productDTO);
            List<Category> categoryList = categoryService.getAllCategories();
            model.addAttribute("categories", categoryList);
            return "product_form";
        } catch (ProductNotFoundException e){
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/myList";
        }
    }
}

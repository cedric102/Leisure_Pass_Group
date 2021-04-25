package com.assignment.lpg3.controller;

import java.io.IOException;
import java.util.List;

import com.assignment.lpg3.models.data.ProductDao;

import com.assignment.lpg3.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("/")
public class ProductController {

    // Get the Product Service Singleton
    @Autowired
    private ProductService productService;

    /**
     * Basic test with dummy data
     * Populate a dummy data and return the result to the View
     * It can be  used to prove that the persistence layer works as expected
     * 
     * @return ModelAndView with the dummy data to be listed in the View
     * @throws IOException
     */
    @GetMapping("/")
    public ModelAndView Root() throws IOException {
        ModelAndView mv = new ModelAndView();

        ProductDao entityDao = new ProductDao(
            1 ,
            "Name" ,
            "Description" ,
            "Category Id" ,
            "Creation Date" ,
            "Update Date" ,
            "Last Purchased Date" );

        mv.setViewName("view");
        mv.addObject("articleList", entityDao);

        return mv;
    }
    
    /**
     * Populate the data from the products.csv.
     * Save the populated data in the persistence layer
     * 
     * @return ModelAndView with the productsList to be listed in the View
     * @throws IOException
     */
    @GetMapping("pop")
    public ModelAndView Populate() throws IOException {

        ModelAndView mv = new ModelAndView();

        List<ProductDao> productist = productService.Pop();

        mv.setViewName("view");
        mv.addObject("articleList" , productist);
        return mv;
    }

    /**
     * Sort the data from the persistence layer.
     * 
     * @return ModelAndView with the productsList to be listed in the View
     * @throws IOException
     */
    @GetMapping("/sort")
    public ModelAndView Sort() throws IOException {

        ModelAndView mv = new ModelAndView();

        List<ProductDao> innerList = productService.Sort();

        mv.setViewName("view");
        mv.addObject("articleList" , innerList);
        return mv;
    }

    /**
     * Visualize the content of the persistence layer.
     * 
     * @return ModelAndView with the productsList to be listed in the View
     * @throws IOException
     */
    @GetMapping("/view")
    public ModelAndView View() throws IOException {

        ModelAndView mv = new ModelAndView();

        List<ProductDao> innerList = productService.View();

        mv.setViewName("view");
        mv.addObject("articleList" , innerList);
        return mv;
    }

    /**
     * Clear the persistence layer.
     * 
     * @return ModelAndView with the productsList to be listed in the View ( Empty )
     * @throws IOException
     */
    @GetMapping("/clear")
    public ModelAndView Clear() throws IOException {

        ModelAndView mv = new ModelAndView();

        List<ProductDao> innerList = productService.Clear();

        mv.setViewName("view");
        mv.addObject("articleList" , innerList);
        return mv;
    }
}

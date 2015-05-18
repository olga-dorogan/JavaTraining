package com.custom;

import com.custom.repository.ProductRepository;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olga on 18.05.15.
 */
@WebServlet("product/list")
public class ProductController extends HttpServlet {

    private static final String TEMPLATE_PRODUCT_LIST = "product_list";
    private static final String ATTR_PRODUCTS = "prods";
    @Inject
    private ProductRepository productRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setPrefix("/WEB-INF/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(360000L);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        WebContext ctx = new WebContext(req, resp, getServletContext(), req.getLocale());
        ctx.setVariable(ATTR_PRODUCTS, productRepository.findAll());

        templateEngine.process(TEMPLATE_PRODUCT_LIST, ctx, resp.getWriter());
        resp.setContentType("text/html;charset=UTF-8");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 1000);
    }
}

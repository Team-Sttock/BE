package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import management.sttock.api.sevice.ProductService;
import management.sttock.common.define.ApiPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiPath.V1_PRODUCTS)
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;
}

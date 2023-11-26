package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import management.sttock.api.dto.message.SuccessMessage;
import management.sttock.api.dto.product.ProductRequest;
import management.sttock.api.sevice.ProductService;
import management.sttock.api.sevice.StockService;
import management.sttock.common.define.ApiPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(ApiPath.V1_PRODUCTS)
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private StockService stockService;
    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(defaultValue = "0", value = "page") int page,
                                         @RequestParam(defaultValue = "10", value = "size") int size,
                                         @RequestParam(defaultValue = "lasted", value = "sorted") String sortBy,
                                         @RequestParam(value = "category", required = false) String category,
                                         Authentication authentication){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return new ResponseEntity<>(stockService.getUserProducts(pageable, category, authentication), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> insertProduct(@RequestBody ProductRequest productRequest, Authentication authentication){
        stockService.insertStock(productRequest, authentication);
        return new ResponseEntity<>(new SuccessMessage(), HttpStatus.OK);
    }

}

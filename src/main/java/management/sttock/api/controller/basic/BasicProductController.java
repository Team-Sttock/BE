package management.sttock.api.controller.basic;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import management.sttock.api.sevice.ProductService;
import management.sttock.common.define.ApiPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(ApiPath.V1_BASIC_PRODUCTS)
@RequiredArgsConstructor
public class BasicProductController {
    @Autowired
    private ProductService productService;

    @Operation(description = "기본 product list 반환 api")
    @GetMapping
    public ResponseEntity<?> getBasicProducts(@RequestParam(value = "like", required = false) String like, HttpServletRequest request){
        return new ResponseEntity<>(productService.selectBasicProducts(like), HttpStatus.OK);
    }

}

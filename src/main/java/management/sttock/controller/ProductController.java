package management.sttock.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import management.sttock.sevice.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/products")
public class ProductController {
    private final ProductService productService;

//    @ApiOperation("전체 상품 조회")
//    @GetMapping("/")
//
//
//    @ApiOperation("특정 상품 조회")
//    @GetMapping("/{productId}")
//
//    @ApiOperation("자주 이용하는 상품 조회")
//    @GetMapping("/{frequently-used-products}")
//
//    @ApiOperation("상품 생성")
//    @PostMapping("/")
//
//    @ApiOperation("상품 수정")
//    @PutMapping("/{productId}")
//
//    @ApiOperation("상품 삭제")
//    @DeleteMapping("/{productId}")
}

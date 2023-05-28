package management.sttock.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import management.sttock.config.jwt.TokenProvider;
import management.sttock.domain.Product;
import management.sttock.productDto.AProductResponseDto;
import management.sttock.productDto.UpdateProductRequestDto;
import management.sttock.productDto.CreateProductRequestDto;
import management.sttock.productDto.TotalProductResponseDto;
import management.sttock.sevice.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/products")
public class ProductController {
    private final ProductService productService;
    private final TokenProvider tokenProvider;

    @ApiOperation("상품 생성")
    @PostMapping("")
    public ResponseEntity createProduct(@Valid @RequestHeader("Authorization") String token,
                                        @RequestBody CreateProductRequestDto createProductRequestDto) {
        String sub = tokenProvider.getUserIdFromToken(token.substring(7));
        try {
            productService.create(sub, createProductRequestDto);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ApiOperation("상품 수정")
    @PutMapping("/{productId}")
    public ResponseEntity updateProduct(@RequestHeader("Authorization") String token, @PathVariable Long productId,
                                        @RequestBody UpdateProductRequestDto updateProductRequestDto) {
        String sub = tokenProvider.getUserIdFromToken(token.substring(7));
        try {
            productService.update(sub, updateProductRequestDto.toEntity());

            return ResponseEntity.status(HttpStatus.OK).body(UpdateProductRequestDto.builder().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
//
//    @ApiOperation("상품 삭제")
//    @DeleteMapping("/{productId}")
//    public ResponseEntity deleteProduct(@RequestHeader("Authorization") String token,
//                                        @PathVariable Long productId) {
//        String sub = tokenProvider.getUserIdFromToken(token.substring(7));
//        try {
//            Product findProduct = productService.find(sub, productId);
//            productService.delete(productId);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
//
//    @ApiOperation("전체 상품 조회")
//    @GetMapping("")
//    public ResponseEntity<TotalProductResponseDto> totalProduct(@RequestHeader("Authorization") String token) {
//        String sub = tokenProvider.getUserIdFromToken(token.substring(7));
//        try {
//            productService.findProducts(sub);
//            TotalProductResponseDto totalProductResponseDto = new TotalProductResponseDto();
//            totalProductResponseDto
//        }
//
//    }
//
//
//    @ApiOperation("특정 상품 조회")
//    @GetMapping("/{productId}")
//    public ResponseEntity<AProductResponseDto> aProduct(@RequestHeader("Authorization") String token,
//                                                        @PathVariable Long productId) {
//        String sub = tokenProvider.getUserIdFromToken(token.substring(7));
//        try {
//            Product findProduct = productService.find(sub, productId);
//
//            AProductResponseDto aProductResponseDto = new AProductResponseDto();
//            aProductResponseDto.setCategory(findProduct.getCategory());
//            aProductResponseDto.setName(findProduct.getName());
//            aProductResponseDto.setDiscription(findProduct.getDescription());
//            aProductResponseDto.setPurchaseDate(findProduct.getPurchaseDate());
//            aProductResponseDto.setPurchaseAmount(findProduct.getPurchaseAmount());
//            aProductResponseDto.setExpectedPurchaseDate(findProduct.getExpectedPurchaseDate());
//            aProductResponseDto.setPurchaseStatus(findProduct.getPurchaseStatus());
//            aProductResponseDto.setRegularDate(findProduct.getRegularDate());
//            aProductResponseDto.setRegularCapacity(findProduct.getRegularCapacity());
//
//            return ResponseEntity.status(HttpStatus.OK).body(aProductResponseDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//    }
//
//    @ApiOperation("자주 이용하는 상품 조회")
//    @GetMapping("/{frequently-used-products}")
//
 //   @ApiOperation("카테고리별 조회")
//    @GetMapping("/{categoryId}")

}

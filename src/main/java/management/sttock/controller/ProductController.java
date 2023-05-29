package management.sttock.controller;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import management.sttock.config.jwt.TokenProvider;
import management.sttock.domain.Product;
import management.sttock.productDto.UpdateProductRequestDto;
import management.sttock.productDto.CreateProductRequestDto;
import management.sttock.productDto.TotalProductResponseDto;
import management.sttock.sevice.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
        Product findProduct = productService.find(sub, productId);
        if (findProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            productService.update(sub, updateProductRequestDto.toEntity(), productId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ApiOperation("상품 삭제")
    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(@RequestHeader("Authorization") String token,
                                        @PathVariable Long productId) {
        String sub = tokenProvider.getUserIdFromToken(token.substring(7));
        Product findProductByProductId = productService.find(sub, productId);
        if(findProductByProductId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            Product findProduct = productService.find(sub, productId);
            productService.delete(productId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @ApiOperation("상품 조회")
    @GetMapping("")
    public ResponseEntity<Result<List<TotalProductResponseDto>>> totalProduct(@RequestHeader("Authorization") String token,
                                                @RequestParam(value = "category", required = false) String category,
                                                @RequestParam(value = "sort", required = false) String sort) {
        String sub = tokenProvider.getUserIdFromToken(token.substring(7));
        if (sub == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            List<Product> findProducts = productService.findProducts(sub, category, sort);
            List<TotalProductResponseDto> totalProductResponseDto = findProducts.stream()
                    .map(TotalProductResponseDto::new)
                    .collect(Collectors.toList());

            Result<List<TotalProductResponseDto>> result = new Result<>(totalProductResponseDto.size(), totalProductResponseDto);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}

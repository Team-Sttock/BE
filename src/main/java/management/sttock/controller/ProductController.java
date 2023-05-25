package management.sttock.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import management.sttock.config.jwt.TokenProvider;
import management.sttock.domain.Member;
import management.sttock.domain.Product;
import management.sttock.productDto.CreateProductRequestDto;
import management.sttock.sevice.MemberService;
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
    private final MemberService memberService;

//    @ApiOperation("상품 생성")
//    @PostMapping("")
//    public ResponseEntity createProduct(@Valid @RequestHeader("Authorization") String token,
//                                        @RequestBody CreateProductRequestDto createProductRequestDto) {
//        String sub = tokenProvider.getUserIdFromToken(token.substring(7));
//        Member findMember = memberService.findUserid(sub);
//        if (findMember != null) {
//            productService.create(createProductRequestDto);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//    }


//    @ApiOperation("상품 수정")
//    @PutMapping("/{productId}")
//
//    @ApiOperation("상품 삭제")
//    @DeleteMapping("/{productId}")

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

}

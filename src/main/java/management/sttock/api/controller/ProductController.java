package management.sttock.api.controller;

import lombok.RequiredArgsConstructor;
import management.sttock.api.dto.message.SuccessMessage;
import management.sttock.api.dto.product.ProductDetailInfo;
import management.sttock.api.dto.product.ProductRequest;
import management.sttock.api.dto.stock.BasicStockInfo;
import management.sttock.api.sevice.ProductService;
import management.sttock.api.sevice.StockService;
import management.sttock.common.define.ApiPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
}

package management.sttock.api.sevice.Impl;

import lombok.RequiredArgsConstructor;
import management.sttock.api.dto.product.ProductRequest;
import management.sttock.api.dto.stock.BasicStockInfo;
import management.sttock.api.sevice.CommonCodeService;
import management.sttock.api.sevice.StockService;
import management.sttock.db.entity.CommonCode;
import management.sttock.db.entity.Product;
import management.sttock.db.entity.StockMaster;
import management.sttock.db.entity.User;
import management.sttock.db.entity.enums.CommonCodeType;
import management.sttock.db.repository.*;
import management.sttock.support.error.ApiException;
import management.sttock.support.error.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {
    @Autowired
    private CustomStockRepository customStockRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockMasterRepository stockMasterRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CommonCodeRepository commonCodeRepository;

    @Override
    public void insertStock(ProductRequest productRequest, Authentication authentication){
        User user = userRepository.findByLoginId(authentication.getName()).get();
        Product product = productRepository.findById(productRequest.getProdId()).orElseThrow(()->new ApiException(ErrorType.SERVER_ERROR));
        CommonCode stateCode = commonCodeRepository.findByCode(CommonCodeType.in_use.name());
        stockMasterRepository.save(productRequest.toEntity( product, user, stateCode));
    }

    @Override
    public Page<BasicStockInfo> getUserProducts(Pageable pageable, String category, Authentication authentication){
        User user = userRepository.findByLoginId(authentication.getName()).get();
        Page<StockMaster> userStockList =  stockMasterRepository.findByUser(user, pageable);
        return userStockList.map(o -> new BasicStockInfo(o));
    }

}

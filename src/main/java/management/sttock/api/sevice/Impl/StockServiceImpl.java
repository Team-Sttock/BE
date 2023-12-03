package management.sttock.api.sevice.Impl;

import lombok.RequiredArgsConstructor;
import management.sttock.api.dto.product.ProductDetailInfo;
import management.sttock.api.dto.product.ProductRequest;
import management.sttock.api.dto.stock.BasicStockInfo;
import management.sttock.api.sevice.StockService;
import management.sttock.db.entity.*;
import management.sttock.db.entity.enums.CommonCodeType;
import management.sttock.db.repository.*;
import management.sttock.support.error.ApiException;
import management.sttock.support.error.ErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
        Product product = productRepository.findById(productRequest.getProdId()).get();
        CommonCode stateCode = commonCodeRepository.findByCode(CommonCodeType.in_use.name());
        stockMasterRepository.save(productRequest.toEntity(product, user, stateCode));
    }

    @Override
    public Page<BasicStockInfo> getUserProducts(Pageable pageable, String category, Authentication authentication){
        User user = userRepository.findByLoginId(authentication.getName()).get();
        Page<StockMaster> userStockList =  stockMasterRepository.findByUser(user, pageable);
        return userStockList.map(o -> new BasicStockInfo(o));
    }

    /**
     * 제품 페이징을 위한 기준
     * @param sortBy
     * @return
     */
    @Override
    public Sort getSortedBy(String sortBy){
        if (sortBy == "lasted"){
            return Sort.by(Sort.Direction.DESC, "crtDt");
        } else if (sortBy == "oldest") {
            return Sort.by(Sort.Direction.ASC, "crtDt");
        } else{
            return Sort.by(Sort.Direction.ASC, "cycleEndDt");
        }
    }

    /**
     * 제품 상세 반환
     * @param id
     * @return
     */
    @Override
    public ProductDetailInfo getProductDetail(Long id, Authentication authentication) {
        User user = userRepository.findByLoginId(authentication.getName()).get();
         StockMaster sm = stockMasterRepository.findById(id).get();
         if(sm.getUser() != user){
             throw new RuntimeException("올바르지 않은 요청입니다");
         }
         return new ProductDetailInfo(sm, getExpectedDays(sm));
    }

    /**
     * StockMaster 에서 반환받은 값으로 사용 날짜 남은 일수 계산하는 함수
     */
    private int getExpectedDays(StockMaster sm){
        Integer sumUseAmount = sm.getStockDetailList().stream().mapToInt(StockDetail::getUseAmount)
                .sum();
        Integer remainingAmount = sm.getBuyQty() * sm.getBasicAmount() - sumUseAmount;
        return (int) Math.ceil(remainingAmount / (sm.getHeadCount() + sm.getBasicUsage()));
    }

}

package management.sttock.api.sevice.Impl;

import lombok.RequiredArgsConstructor;
import management.sttock.api.db.entity.Product;
import management.sttock.api.db.entity.StockDetail;
import management.sttock.api.db.entity.StockMaster;
import management.sttock.api.db.entity.User;
import management.sttock.api.db.entity.enums.State;
import management.sttock.api.db.repository.CustomStockRepository;
import management.sttock.api.db.repository.ProductRepository;
import management.sttock.api.db.repository.StockMasterRepository;
import management.sttock.api.db.repository.UserRepository;
import management.sttock.api.dto.product.ProductDetailInfo;
import management.sttock.api.dto.product.ProductRequest;
import management.sttock.api.dto.stock.BasicStockInfo;
import management.sttock.api.sevice.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * 유저 상품 insert
     * @param productRequest
     * @param authentication
     */
    @Override
    @Transactional
    public void insertStock(ProductRequest productRequest, Authentication authentication){
//        User user = userRepository.findByLoginId(authentication.getName()).get(); // Todo
        User user = userRepository.findById(productRequest.getUserId()).get();
        Product product = productRepository.findById(productRequest.getProdId()).get();
        stockMasterRepository.save(productRequest.toEntity(product, user));
        return;
    }

    /**
     * 유저 상품 업데이트
     * @param productRequest
     * @param smId
     * @param authentication
     * @return
     */

    @Override
    @Transactional
    public Long update(ProductRequest productRequest, Long smId, Authentication authentication){
        // Todo: auth 추가
        User user = userRepository.findById(productRequest.getUserId()).get();
        StockMaster stock = stockMasterRepository.findById(smId).orElseThrow(() -> new RuntimeException("존재하지 않는 stockMaster id"));
        stock.update(productRequest);
        return stockMasterRepository.save(stock).getStockMasterId();
    }

    /**
     * 유저 상품 사용 중지 업데이트
     * @param smId
     * @param authentication
     * @return
     */
    @Override
    @Transactional
    public void stopUsing(Long smId, Authentication authentication) {
        StockMaster stockMaster = stockMasterRepository.findById(smId).get();
        stockMaster.changeState(State.STOP);
        stockMasterRepository.save(stockMaster);
        return;
    }


    /**
     * 제품 상세 반환
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDetailInfo getProductDetail(Long id, Authentication authentication, Long userId) {
//        User user = userRepository.findByLoginId(authentication.getName()).get(); Todo: auth 추가
         User user = userRepository.findById(userId).get();
         StockMaster sm = stockMasterRepository.findById(id).get();
         if(sm.getUser() != user){
             throw new RuntimeException("올바르지 않은 요청입니다");
         }
         return new ProductDetailInfo(sm, getExpectedDays(sm));
    }



    /**
     * 유저가 추가한 상품 리스트 반환
     *
     * @param pageable
     * @param category
     * @param startDt
     * @param endDt
     * @param authentication
     * @param userId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BasicStockInfo> getUserProducts(Pageable pageable, String category, LocalDateTime startDt, LocalDateTime endDt, Authentication authentication, Long userId){
//        User user = userRepository.findByLoginId(authentication.getName()).get();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 userID"));
        List<StockMaster> userStockList =  stockMasterRepository.findByUser(user);
        List<BasicStockInfo> list = userStockList.stream().map(BasicStockInfo::new).collect(Collectors.toList());
        return new PageImpl<>(list, pageable, list.size());
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
     * StockMaster 에서 반환받은 값으로 사용 날짜 남은 일수 계산하는 함수
     */
    private int getExpectedDays(StockMaster sm){
        Integer sumUseAmount = sm.getStockDetailList().stream().mapToInt(StockDetail::getUseAmount)
                .sum();
        Integer remainingAmount = sm.getBuyQty() * sm.getBasicAmount() - sumUseAmount;
        return (int) Math.ceil(remainingAmount / (sm.getHeadCount() + sm.getBasicUsage()));
    }

}

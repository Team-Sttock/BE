package management.sttock.api.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.db.entity.Member;
import management.sttock.db.entity.Product;
import management.sttock.api.request.product.CreateProductRequestDto;
import management.sttock.db.repository.MemberRepository;
import management.sttock.db.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(String userId, CreateProductRequestDto createProductRequestDto) {
        Member findMember = memberRepository.findOneByUserIdForLong(userId);
        if(findMember != null) {
            Product product = createProductRequestDto.toEntity();
            product.setMember(findMember);
            productRepository.save(product);
        } else {
            throw new IllegalStateException("회원을 찾을 수 없습니다.");
        }
    }

    public Product find(String userId, Long productId) {
        Member findMember = memberRepository.findOneByUserIdForLong(userId);
        if(findMember != null) {
            return productRepository.findOne(userId, productId);
        } else {
            throw new IllegalStateException("상품을 찾을 수 없습니다.");
        }
    }
    public List<Product> findProducts(String userId, String category, String sortMethod) {
        System.out.println(userId);
        Member findMember = memberRepository.findOneByUserIdForLong(userId);
        if(sortMethod == null) {
            sortMethod = "최신 등록순";
        }
        if (findMember != null) {
            if (category != null && sortMethod.equals("최신 등록순")) { //카테고리별&최신등록순
                List<Product> findByCategoryByRecent = productRepository.findProductsByCategoryByRecent(userId, category);
                return findByCategoryByRecent;
            } else if(category != null && sortMethod.equals("소진 임박순")) { //카테고리별&소집임박순
                List<Product> findByCategoryByShortTerm = productRepository.findProductsByCategoryByShort(userId, category);
                return findByCategoryByShortTerm;
            } else if (category == null && sortMethod.equals("최신 등록순")) { //전체&최신등록순
                List<Product> findByRecent = productRepository.findAllProductsByRecent(userId);
                return findByRecent;
            } else { //전체&소집임박순
                List<Product> findByShortTerm = productRepository.findAllProductsByShort(userId);
                return findByShortTerm;
            }
        } else {
            throw new IllegalStateException("회원을 찾을 수 없습니다.");
        }
    }
    @Transactional
    public void update(String userId, Product product, Long productId) {
        Product findProduct = productRepository.findOne(userId, productId);
        if (findProduct != null) {

            findProduct.setCategory(product.getCategory());
            findProduct.setName(product.getName());
            findProduct.setDescription(product.getDescription());
            findProduct.setPurchaseDate(product.getPurchaseDate());
            findProduct.setPurchaseAmount(product.getPurchaseAmount());
            findProduct.setExpectedDate(product.getExpectedDate());
            findProduct.setRegularCapacity(product.getRegularCapacity());

        } else {
            throw new IllegalStateException("회원을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void delete(Long productId) {
        productRepository.deleteOne(productId);
    }
}

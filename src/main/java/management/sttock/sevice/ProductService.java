package management.sttock.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.domain.Member;
import management.sttock.domain.Product;
import management.sttock.productDto.CreateProductRequestDto;
import management.sttock.productDto.UpdateProductRequestDto;
import management.sttock.repository.MemberRepository;
import management.sttock.repository.ProductRepository;
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
            throw new IllegalStateException("회원을 찾을 수 없습니다.");
        }
    }
    public List<Product> findProducts(String userId) {
        Member findMember = memberRepository.findOneByUserIdForLong(userId);
        if (findMember != null) {
            return productRepository.findAllProducts(userId);
        } else {
            throw new IllegalStateException("회원을 찾을 수 없습니다.");
        }
    }
    @Transactional
    public void update(String userId, Product product) {
        Product findProduct = productRepository.findOne(userId, product.getId());
        if (findProduct != null) {

            findProduct.setCategory(product.getCategory());
            findProduct.setName(product.getName());
            findProduct.setDescription(product.getDescription());
            findProduct.setPurchaseDate(product.getPurchaseDate());
            findProduct.setPurchaseAmount(product.getPurchaseAmount());
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

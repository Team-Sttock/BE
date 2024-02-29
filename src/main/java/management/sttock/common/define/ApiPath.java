package management.sttock.common.define;

import management.sttock.api.sevice.ProductService;
import management.sttock.db.entity.Product;

public class ApiPath extends CorePath{
    /* defalut */
    private static final String V1 = "/api/v1";
    private static final String BASIC = "/basic";
    private static final String AUTH = "/auth";

    /* user */
    private static final String USER = "/user";
    public static final String V1_USER = V1 + USER;
    public static final String SIGN_UP = "/signup";
    public static final String V1_AUTH = V1 + AUTH;

    /* product */
    public static final String PRODUCTS = "/products";
    public static final String V1_PRODUCTS = V1 + PRODUCTS ;
    public static final String V1_BASIC_PRODUCTS = V1 + BASIC + PRODUCTS;

    /* user product */
    public static final String V1_USER_PRODUCTS = V1 + USER + PRODUCTS;

    /* category */
    public static final String CATEGORY = "/categories";
    public static final String V1_CATEGORY = V1 + CATEGORY;
    public static final String V1_BASIC_CATEGORY = V1 + BASIC + CATEGORY;

    /* stock */
    public static final String STOCK = "/stock";
    public static final String V1_STOCK = V1 + STOCK;

}

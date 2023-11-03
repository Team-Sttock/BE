package management.sttock.api.controller;

import lombok.extern.slf4j.Slf4j;
import management.sttock.support.error.ApiException;
import management.sttock.support.error.ErrorType;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public void throwError(ApiException apiException, Exception e){
        log.info("/error 요청");
        throw new ApiException(ErrorType.UNAUTHENTICATED_STATUS);
    }
}

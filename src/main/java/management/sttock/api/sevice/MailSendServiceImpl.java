package management.sttock.api.sevice;

import lombok.RequiredArgsConstructor;
import management.sttock.common.exception.ServerException;
import management.sttock.common.exception.ValidateException;
import management.sttock.db.entity.VerificationCode;
import management.sttock.db.repository.VerificationCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailSendServiceImpl implements MailSendService {
    private final JavaMailSenderImpl javaMailSender;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*";
    private static final String ALL_CHARACTERS = CHARACTERS + DIGITS + SPECIAL_CHARACTERS;
    private static final int LENGTH = 10;

    @Override
    public int sendAuthNumber(String email) {
        int authNumber = makeTempNumber();
        buildMail(authNumber, email);
        VerificationCode verificationCode = new VerificationCode(email, authNumber,false);
        verificationCodeRepository.save(verificationCode);
        return authNumber;
    }

    @Override
    public void checkAuthNumber(String email, int authNumber) {
        VerificationCode verificationCode = verificationCodeRepository.findById(email).get();
        int expectedAuthNumber = verificationCode.getAuthNumber();
        boolean isNotMatchAuthCode = expectedAuthNumber != authNumber;
        if (isNotMatchAuthCode) {
            throw new ValidateException(HttpStatus.BAD_REQUEST, "인증 번호가 잘못되었습니다.");
        }
        verificationCode.setVerificationStatus(true);
        verificationCodeRepository.save(verificationCode);
    }

    @Override
    public void checkVerificationStatus(String email) {
        boolean verificationStatus = verificationCodeRepository.findById(email)
                .map(VerificationCode::isVerificationStatus)
                .orElse(false);

        if(!verificationStatus) {
            throw new ValidateException(HttpStatus.BAD_REQUEST, "이메일 인증 후 다시 회원가입을 시도해주세요");
        }
    }

    @Override
    public String sendTempPassword(String email) {
        String tempPassword = makeTempPassword();
        buildTempPasswordMail(tempPassword, email);
        return passwordEncoder.encode(tempPassword);
    }

    @Override
    public String makeTempPassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder();

        passwordBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        passwordBuilder.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordBuilder.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        for (int i = 3; i < LENGTH; i++) {
            passwordBuilder.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }
        return passwordBuilder.toString();
    }

    @Override
    public int makeTempNumber() {
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;
        return checkNum;
    }

    @Override
    public void buildMail(int checkNumber, String email) {
        String title = "[스똑] 메일 인증 코드 발송 ";
        String content = "이메일 인증코드"+
                "<br><br>" +
                "인증번호는 " + checkNumber + " 입니다." +
                "<br><br>" +
                "해당 인증번호를 인증번호 확인한에 기입하여 주시기 바랍니다.";
        sendEmailForAuth(title, email, content);
    }

    @Override
    public void buildTempPasswordMail(String tempPassword, String email) {
        String title = "[스똑] 임시 비밀번호 발송 ";
        String content = "임시 비밀번호 "+
                "<br><br>" +
                "임시 비밀번호는 " + tempPassword + " 입니다." +
                "<br><br>" +
                "해당 비밀번호를 사용하여 로그인을 시도해 주시기 바랍니다.";
        sendMailForPassword(title, email, content);
    }

    @Override
    public void sendEmailForAuth(String title, String email, String content) {
        try {
            sendMail(title, email, content);
        } catch (MessagingException e) {
            throw new ServerException("인증번호 전송 실패");
        }
    }

    @Override
    public void sendMailForPassword(String title, String email, String content) {
        try {
            sendMail(title, email, content);
        } catch (MessagingException e) {
            throw new ServerException("임시 비밀번호 전송 실패");
        }
    }

    private void sendMail(String title, String email, String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
        messageHelper.setTo(email);
        messageHelper.setSubject(title);
        messageHelper.setText(content, true);
        javaMailSender.send(mimeMessage);
    }
}
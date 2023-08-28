package management.sttock.api.sevice;

public interface MailSendService {
    int sendAuthNumber(String email);
    int makeTempNumber();
    void sendEmailForAuth(String title, String email, String content);
    void checkAuthNumber(String email, int authNumber);
    String sendTempPassword(String email);
    String makeTempPassword();
    void buildTempPasswordMail(String tempPassword, String email);
    void sendMailForPassword(String title, String email, String content);
    void buildMail(int checkNumber,String email);
}

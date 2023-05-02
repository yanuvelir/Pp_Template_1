package CreateAccount_Page.En;

public class TestConfig {
    public static String EMAIL_ADDRESS;
    public static String PASSWORD;
    public static String SIGN_UP_PAGE;
    public static String ELEMENT_FOR_TEST;


    static {
        SIGN_UP_PAGE = "https://dex-trade.com/sign-up";
        EMAIL_ADDRESS = "yanautomte.st.6@gmail.com";
        PASSWORD = "Vinnitsa-2022";

        ELEMENT_FOR_TEST = "(//div[@class='auth-container'])[3]";
    }
}

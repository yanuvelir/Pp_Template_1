package CreateAccount_Page.En;

import org.openqa.selenium.WebElement;

import java.io.IOException;

public class Create_Account_pg_Email_and_Password_Form extends By_Element_Test_1_En {

//    @Parameters({"CreateAccount_page_Test1", "CreateAccount_Test_1_var", "Difference_T1_En", "MockFile_T1_En", "RealFile_T1_En"})
//    @Test(groups = {"test1"}, testName = "En_Test_1_By_Element")
    public static void checkInputFields_inputsAreFilledIn_PpNotChanges(WebElement email, WebElement password, String CreateAccount_page_Test1, String CreateAccount_Test_1_var, String Difference_T1_En,
                                                                            String MockFile_T1_En, String RealFile_T1_En) throws InterruptedException, IOException {


        email.sendKeys("yanautomte.st.6@gmail.com");
        password.sendKeys("Vinnitsa-2022");
    }

}



package CreateAccount_Page.En;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import static Main.TestNg.driver;

public class Input_Form {

    @Parameters({"Difference_T1_En", "MockFile_T1_En", "RealFile_T1_En"})
    @Test(groups = {"test1"}, testName = "En_Test_1_By_Element")
    public static void inputFormTest(String Difference_T1_En,
                         String MockFile_T1_En, String RealFile_T1_En) throws InterruptedException, IOException {

        System.out.println("*** Pixel_Perfect By_Element Test_1 folder=Screenshots***");
        // Set up a WebDriverWait instance with a timeout (in seconds)
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

        // Переход на начальную страницу
//        driver.get(CreateAccount_page_Test1);
        driver.get(Input_Form_TestConfig.SIGN_UP_PAGE);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("sign-link")));

        Thread.sleep(1000);
        // Find elements - Logo and password"
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys(Input_Form_TestConfig.EMAIL_ADDRESS);
//        email.sendKeys(s);
//        Create_Account_pg_Email_and_Password_Form createAccInput = new Create_Account_pg_Email_and_Password_Form();
//        createAccInput.checkInputFields_inputsAreFilledIn_PpNotChanges()
       WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(Input_Form_TestConfig.PASSWORD);
        WebElement eyeButton = driver.findElement(By.xpath("//button[@data-test-id=\"viewBox\"]"));
        eyeButton.click();
        Thread.sleep(1500);

//        WebElement inputWrapper = driver.findElement(By.xpath(CreateAccount_Test_1_var));//"input-wrap"
        WebElement inputWrapper = driver.findElement(By.xpath(Input_Form_TestConfig.ELEMENT_FOR_TEST));//"input-wrap"

        try {
            File screenshotFile = inputWrapper.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File
                    (RealFile_T1_En);
            Files.move(screenshotFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            if (!destinationFile.exists()) {
                destinationFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("*** File exists ***");
        }

        BufferedImage image1 = ImageIO.read(new File(MockFile_T1_En));
        BufferedImage image2 = ImageIO.read(new File(RealFile_T1_En));

        // Resize the images to the same size for comparison
        int width = Math.min(image1.getWidth(), image2.getWidth());
        int height = Math.min(image1.getHeight(), image2.getHeight());
        image1 = scaleImage(image1, width, height);
        image2 = scaleImage(image2, width, height);

        // Convert the images to grayscale
        BufferedImage grayscaleImage1 = toGrayscale(image1);
        BufferedImage grayscaleImage2 = toGrayscale(image2);

        // Calculate the average brightness of each pixel in the grayscale images
        double averageBrightness1 = calculateAverageBrightness(grayscaleImage1);
        double averageBrightness2 = calculateAverageBrightness(grayscaleImage2);

        // Calculate the difference in average brightness between the two images
        double brightnessDifference = Math.abs(averageBrightness1 - averageBrightness2);

        // Print the result
        if (brightnessDifference < 0.005) {
            System.out.println("The two images are similar.");
        } else {
            System.out.println("The two images are not similar.");

            // Get the RGB values of each pixel in the two images
            int[] pixels1 = new int[width * height];
            int[] pixels2 = new int[width * height];
            PixelGrabber grabber1 = new PixelGrabber(image1, 0, 0, width, height, pixels1, 0, width);
            PixelGrabber grabber2 = new PixelGrabber(image2, 0, 0, width, height, pixels2, 0, width);
            try {
                grabber1.grabPixels();
                grabber2.grabPixels();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Create a new image to highlight the difference
            BufferedImage differenceImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = differenceImage.createGraphics();
            graphics.drawImage(image1, 0, 0, null);

            // Compare the RGB values of each pixel in the two images
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int index = y * width + x;
                    if (pixels1[index] != pixels2[index]) {
                        graphics.setColor(Color.RED);
                        graphics.drawRect(x - 2, y - 2, 1, 1); // highlight the area around the different pixel
                    }
                }
            }
            graphics.dispose();

            // Save the difference image to a file
            File differenceFile = new File(Difference_T1_En);
            ImageIO.write(differenceImage, "png", differenceFile);

            // Print a message indicating where the difference was found
            System.out.println("Difference found. See the file " + differenceFile.getAbsolutePath() + " for details.");
        }
        Assert.assertTrue(brightnessDifference < 0.005, "*** Images are not similar ***");
        Thread.sleep(500);
//        driver.quit();
    }

    private static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, image.getType());
        scaledImage.getGraphics().drawImage(image, 0, 0, width, height, null);
        return scaledImage;
    }

    private static BufferedImage toGrayscale(BufferedImage image) {
        BufferedImage grayscaleImage = new BufferedImage(image.getWidth(),
                image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        grayscaleImage.getGraphics().drawImage(image, 0, 0, null);
        return grayscaleImage;
    }

    private static double calculateAverageBrightness(BufferedImage image) {
        double sum = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                double brightness = (color.getRed() + color.getGreen() + color.getBlue()) / 3.0;
                sum += brightness;
            }
        }
        return sum / (image.getWidth() * image.getHeight());

    }

}



package CreateAccount_Page.En;

import org.openqa.selenium.*;
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

public class By_Page_Test_2_1_En {

    @Parameters({"CreateAccount_page", "CreateAccount_Test_2_var", "Difference_T2_1_En", "MockFile_T2_1_En", "RealFile_T2_1_En"})
    @Test(groups = {"test1"}, testName = "RealFile_T2_1_En")
    public void mainCode(String CreateAccount_page, String CreateAccount_Test_2_var, String Difference_T2_1_En,
                         String MockFile_T2_1_En, String RealFile_T2_1_En) throws InterruptedException, IOException {

        System.out.println("*** Pixel_Perfect By_Element folder=Screenshots1***");
        // Set up a WebDriverWait instance with a timeout (in seconds)
           WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // 10 seconds timeout

        // Переход на начальную страницу
        driver.get("https://dex-trade.com/sign-up");

        WebElement element = wait.until
                (ExpectedConditions.visibilityOfElementLocated(By.className("sign-link")));

        Thread.sleep(2000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0)");

        try {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationFile = new File
                    (RealFile_T2_1_En);
            Files.move(screenshotFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            if (!destinationFile.exists()) {
                destinationFile.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("*** File exists ***");
        }

        BufferedImage image1 = ImageIO.read(new File(MockFile_T2_1_En));
        BufferedImage image2 = ImageIO.read(new File(RealFile_T2_1_En));

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
        if (brightnessDifference < 0.0005) {
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
            File differenceFile = new File(Difference_T2_1_En);
            ImageIO.write(differenceImage, "png", differenceFile);

            // Print a message indicating where the difference was found
            System.out.println("Difference found. See the file " + differenceFile.getAbsolutePath() + " for details.");
        }
        Assert.assertTrue(brightnessDifference < 0.0005, "*** Images are not similar ***");
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



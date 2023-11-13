import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModel;
import model.ImageModelImpl;
import model.Pixel;
import model.PixelInterface;
import view.ImageView;
import view.ImageViewImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * A JUnit test to test SimpleController.
 * SimpleController is a main controller class which creates MVC object
 * and performs various tasks.
 */
public class SimpleControllerTest {

  /**
   * Given private method will return an array list of the pixels
   * of the image stored in the given file path.
   *
   * @param filePath file path of the image which needs to be referred
   * @return an array list of the image pixels
   */
  private List<ArrayList<PixelInterface>> getImagePixels(String filePath) {
    Scanner sc;
    List<ArrayList<PixelInterface>> pixels = new ArrayList<ArrayList<PixelInterface>>();
    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      return null;
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (!s.isEmpty() && s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;
    token = sc.next();


    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    for (int i = 0; i < height; i++) {
      pixels.add(new ArrayList<PixelInterface>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        PixelInterface pixel = new Pixel(r, g, b);
        pixels.get(i).add(j, pixel);
      }
    }
    return pixels;
  }

  /**
   * Given private method will check if provided array list of the pixels are same or not.
   *
   * @param storedPixels        an arraylist of the pixels of the stored image
   * @param originalImagePixels an arraylist of the pixels of the original image
   * @return true if both the arraylists are same else false
   */
  private boolean assertPixels(List<ArrayList<PixelInterface>> storedPixels, List<ArrayList<PixelInterface>> originalImagePixels) {
    if (originalImagePixels == null) {
      return false;
    }
    for (int i = 0; i < originalImagePixels.size(); i++) {
      for (int j = 0; j < originalImagePixels.get(0).size(); j++) {
        int r = originalImagePixels.get(i).get(j).getR();
        int g = originalImagePixels.get(i).get(j).getG();
        int b = originalImagePixels.get(i).get(j).getB();
        if (r != storedPixels.get(i).get(j).getR() || g != storedPixels.get(i).get(j).getG() && b != storedPixels.get(i).get(j).getB()) {
          return false;
        }
      }
    }
    return (originalImagePixels.size() == storedPixels.size());
  }

  /**
   * Given private method will check if the file stored in given path contains same pixels as stored.
   *
   * @param storedPixels an arraylist of the pixels of the stored image
   * @param path         path of the image which needs to be referred
   * @return true if image contains same pixels else false
   */
  private boolean assertImage(List<ArrayList<PixelInterface>> storedPixels, String path) {
    List<ArrayList<PixelInterface>> originalImagePixels = getImagePixels(path);
    return assertPixels(storedPixels, originalImagePixels);
  }

  /**
   * Given private method will check if file stored in provided paths are the same or not.
   *
   * @param originalImagePath image path of the original image
   * @param savedImagePath    image path of the saved image
   * @return true if both the images in the provided path are the same else false
   * @throws IOException throws input output exception
   */
  private boolean assertImageFiles(String originalImagePath, String savedImagePath) throws IOException {
    List<ArrayList<PixelInterface>> originalImagePixels = getImagePixels(originalImagePath);
    List<ArrayList<PixelInterface>> savedImagePixels = getImagePixels(savedImagePath);

    return assertPixels(savedImagePixels, originalImagePixels);
  }

  /**
   * Check load command executed successfully or not.
   */
  @Test
  public void loadImage() {

    String input = "load res/owl.ppm owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    List<ArrayList<PixelInterface>> pixels = model.getImagePixels("owl");
    assertTrue(assertImage(pixels, "res/owl.ppm"));


  }

  /**
   * JUnit test to check if image successfully loaded and saved using load and save command respectively.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void loadSaveImage() throws IOException {

    String input = "load res/owl.ppm owl\r\nsave test/images/owl.ppm owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl.ppm", "test/images/owl.ppm"));

  }

  /**
   * JUnit test to check if image loaded, brighten the loaded image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void loadBrightenSaveImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nbrighten 10 owl owl-brighter\r\nsave test/images/owl-brighter.ppm owl-brighter\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-brighter.ppm", "test/images/owl-brighter.ppm"));
  }

  /**
   * JUnit test to check if image loaded, brighten the loaded image and then save it successfully.
   * If command passed is case-sensitive.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void testCaseSensitivityOfCommand() throws IOException {
    String input = "LOad res/owl.ppm owl\r\nbriGHten 10 owl owl-brighter\r\nSAVE test/images/owl-brighter.ppm owl-brighter\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-brighter.ppm", "test/images/owl-brighter.ppm"));
  }

  /**
   * JUnit test to check if image loaded, darken the loaded image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void loadDarkenSaveImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nbrighten -50 owl owl-darken\r\nsave test/images/owl-darken.ppm owl-darken\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-darken.ppm", "test/images/owl-darken.ppm"));
  }

  /**
   * JUnit test to check if image loaded, flipping vertically and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void loadVerticalFlipSaveImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nvertical-flip owl owl-vertical\r\nsave test/images/owl-vertical.ppm owl-vertical\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    System.out.println(out.toString());
    assertTrue(assertImageFiles("res/owl-vertical.ppm", "test/images/owl-vertical.ppm"));
  }

  /**
   * JUnit test to check if image loaded, flipping horizontally and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void loadHorizontalFlipSaveImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nhorizontal-flip owl owl-horizontal\r\nsave test/images/owl-horizontal.ppm owl-horizontal\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-horizontal.ppm", "test/images/owl-horizontal.ppm"));
  }

  /**
   * JUnit test to check if image loaded, convert to red greyscale image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleRedImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale red-component owl owl-red\r\nsave test/images/owl-red.ppm owl-red\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-red.ppm", "test/images/owl-red.ppm"));
  }

  /**
   * JUnit test to check if image loaded, convert to green greyscale image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleGreenImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale green-component owl owl-green\r\nsave test/images/owl-green.ppm owl-green\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-green.ppm", "test/images/owl-green.ppm"));
  }

  /**
   * JUnit test to check if image loaded, convert to blue greyscale image and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleBlueImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale blue-component owl owl-blue\r\nsave test/images/owl-blue.ppm owl-blue\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-blue.ppm", "test/images/owl-blue.ppm"));
  }

  /**
   * JUnit test to check if image loaded, convert to greyscale image based on value and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleValueImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale value-component owl owl-greyscale-value\r\nsave test/images/owl-greyscale-value.ppm owl-greyscale-value\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-greyscale-value.ppm", "test/images/owl-greyscale-value.ppm"));
  }

  /**
   * JUnit test to check if image loaded, convert to greyscale image based on luma and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleLumaImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale luma-component owl owl-greyscale-luma\r\nsave test/images/owl-greyscale-luma.ppm owl-greyscale-luma\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-greyscale-luma.ppm", "test/images/owl-greyscale-luma.ppm"));
  }

  /**
   * JUnit test to check if image loaded, convert to greyscale image based on intensity and then save it successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void greyscaleIntensityImage() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale intensity-component owl owl-greyscale-intensity\r\nsave test/images/owl-greyscale-intensity.ppm owl-greyscale-intensity\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-greyscale-intensity.ppm", "test/images/owl-greyscale-intensity.ppm"));
  }

  /**
   * JUnit test to check if split an image in to three greyscale image based on
   * red, green and blue channels works successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void rgbSplitImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nrgb-split owl owl-red owl-green owl-blue"
            + "\r\nsave test/images/owl-red.ppm owl-red\n" +
            "save test/images/owl-green.ppm owl-green\n" +
            "save test/images/owl-blue.ppm owl-blue\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-red.ppm", "test/images/owl-red.ppm"));
    assertTrue(assertImageFiles("res/owl-green.ppm", "test/images/owl-green.ppm"));
    assertTrue(assertImageFiles("res/owl-blue.ppm", "test/images/owl-blue.ppm"));
  }

  /**
   * JUnit test to check if combining of r,g,b greyscale image into one color image works successfully.
   *
   * @throws IOException throws input output exception
   */
  @Test
  public void rgbCombine() throws IOException {
    String input = "load res/owl.ppm owl\r\nrgb-split owl owl-red owl-green owl-blue"
            + "\r\nrgb-combine owl-red-tint owl-red owl-green owl-blue\r\nsave test/images/owl-red-tint.ppm owl-red-tint\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-red-tint.ppm", "test/images/owl-red-tint.ppm"));
  }

  /**
   * JUnit test to check if error throws when command passed on not existing file name.
   */
  @Test
  public void fileNameNotStored() {
    //owl-horizontal is not stored anywhere in the program so it will return error while saving the image.
    String input = "load res/owl.ppm owl\r\nvertical-flip owl owl-vertical\r\nsave test/images/owl-vertical.ppm owl-horizontal\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();

    assertEquals("Filename passed is not loaded :save test/images/owl-vertical.ppm owl-horizontal" + System.lineSeparator(), out.toString());

  }

  /**
   * JUnit test to check if error throws when command passed is not valid.
   */
  @Test
  public void invalidCommandName() {
    //owl-horizontal is not stored anywhere in the program so it will return error while saving the image.
    String input = "loaded res/owl.ppm owl";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();

    assertEquals("Invalid command name passed :loaded res/owl.ppm owl" + System.lineSeparator(), out.toString());

  }

  /**
   * JUnit test to check if command works correctly even if multiple space
   * is passed.
   */
  @Test
  public void multipleSpaceInCommand() {

    String input = "load          test/images/owl.ppm       owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    List<ArrayList<PixelInterface>> pixels = model.getImagePixels("owl");
    assertTrue(assertImage(pixels, "test/images/owl.ppm"));
  }

  /**
   * JUnit test to check if error throws when command contains more
   * parameters than expected.
   */
  @Test
  public void moreParametersInCommand() {

    String input = "load test/images/owl.ppm owl koala\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Invalid arguments passed :load test/images/owl.ppm owl koala"
            + System.lineSeparator(), out.toString());

  }

  /**
   * JUnit test to check if error throws when command contains less
   * parameters than expected.
   */
  @Test
  public void lessParametersInCommand() {

    String input = "load test/images/owl.ppm\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Invalid arguments passed :load test/images/owl.ppm"
            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if error throws when only command is passed
   * without parameters.
   */
  @Test
  public void onlyCommandPassed() {

    String input = "load\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Invalid arguments passed :load"
            + System.lineSeparator(), out.toString());

  }

  /**
   * JUnit test to check if error throws when image
   * doesn't exist on provided path.
   */
  @Test
  public void invalidImageFormat() {

    String input = "load res/images/koala.png owl\r\nexit";
//    String input = "load test/images/owl.ppm owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("File not found :load res/images/koala.png owl"
            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if error throws when PPM image
   * is empty.
   */
  @Test
  public void emptyPPMImage() {

    String input = "load res/owl-empty.ppm owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("File passed is empty :load res/owl-empty.ppm owl"
            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if error throws when image
   * is png.
   */
  @Test
  public void loadPNGImage() {

    String input = "load res/owl-png.png owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Invalid PPM file: plain RAW file should begin with P3"
            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if error throws when PPM image
   * doesn't exist on file path.
   */
  @Test
  public void imageDontExistOnPath() {

    String input = "load test/images/koala.ppm owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("File not found :load test/images/koala.ppm owl", out.toString().trim());
  }

  /**
   * JUnit test to check if image brighten by decimal increment.
   */
  @Test
  public void brightenImageDecimalIncrement() throws IOException {
    String input = "load res/owl.ppm owl\r\nbrighten 33.3 owl owl-brighter\r\nsave test/images/owl-brighterDecimal.ppm owl-brighter\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-brighterDecimal.ppm", "test/images/owl-brighterDecimal.ppm"));
  }

  /**
   * JUnit test to check if image darken by below lower constraint.
   */
  @Test
  public void brightenDarkenBelowLowerConstraint() throws IOException {
    String input = "load res/owl.ppm owl\r\nbrighten -300 owl owl-darken2\r\nsave test/images/owl-darken2.ppm owl-darken2\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-darken2.ppm", "test/images/owl-darken2.ppm"));
  }

  /**
   * JUnit test to check if image brighten by highest constraint.
   */
  @Test
  public void brightenAboveHighestConstraint() throws IOException {
    String input = "load res/owl.ppm owl\r\nbrighten 300 owl owl-brighten2\r\nsave test/images/owl-brighten2.ppm owl-brighten2\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertTrue(assertImageFiles("res/owl-brighten2.ppm", "test/images/owl-brighten2.ppm"));
  }

  /**
   * JUnit test to check if image flip works.
   */
  @Test
  public void loadVerticalHorizontalFlipSaveImage() throws IOException {
    String input = "load res/owl.ppm owl\r\nvertical-flip owl owl-vertical\n" +
            "horizontal-flip owl-vertical owl-vertical-horizontal" +
            "\r\nsave test/images/owl-vertical-horizontal.ppm owl-vertical-horizontal\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    System.out.println(out.toString());
    assertTrue(assertImageFiles("res/owl-vertical-horizontal.ppm", "test/images/owl-vertical-horizontal.ppm"));
  }

  /**
   * JUnit test to check if error throws when Invalid value
   * component passed.
   */
  @Test
  public void loadInvalidValueComponent() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale block-component owl owl-greyscale-value\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Invalid value component passed :greyscale block-component owl owl-greyscale-value"
            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if combinations of command works.
   */
  @Test
  public void loadRedLumaSave() throws IOException {
    String input = "load res/owl.ppm owl\r\ngreyscale red-component owl owl-greyscale-red\r\n"
            + "greyscale luma-component owl-greyscale-red owl-greyscale-red-luma\r\n" +
            "save test/images/owl-greyscale-red-luma.ppm owl-greyscale-red-luma\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();

    assertTrue(assertImageFiles("res/owl-greyscale-red-luma.ppm", "test/images/owl-greyscale-red-luma.ppm"));
  }

  /**
   * JUnit test to check if error throws when save image
   * without load.
   */
  @Test
  public void saveWithoutLoad() throws IOException {
    String input = "save test/images/owl-greyscale-red-luma.ppm owl-greayscale-red-luma\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();


    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Filename passed is not loaded :save test/images/owl-greyscale-red-luma.ppm owl-greayscale-red-luma"

            + System.lineSeparator(), out.toString());
  }

  /**
   * JUnit test to check if run script file works.
   */
  @Test
  public void runScriptFile() throws IOException {
    String input = "run command-script.txt\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    OutputStream out = new ByteArrayOutputStream();

    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
  }
}
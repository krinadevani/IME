import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import controller.ImageController;
import controller.ImageControllerImpl;
import model.COMPONENTS;
import model.ImageModel;
import model.PixelInterface;
import view.ImageView;
import view.ImageViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * A JUnit test class to test controller in isolation.
 */
public class ImageControllerImplTest {

  /**
   * JUnit mock test to check if image successfully loaded.
   */
  @Test
  public void testLoad() {
    String input = "load test/images/owl.ppm owl\r\nexit";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ImageModel model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Recieved test/images/owl.ppm and owl", sb.toString());
  }

  /**
   * JUnit mock test to check if image saved as expected.
   */
  @Test
  public void testSave() {
    String input = "save test/images/owl.ppm owl";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ImageModel model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Recieved test/images/owl.ppm and owl", sb.toString());
  }

  /**
   * JUnit mock test to check if greyscale image converted as expected.
   */
  @Test
  public void testVisualizeComponent() {
    String input = "greyscale red-component owl owl-greyscale";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ImageModel model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Recieved RED , owl and owl-greyscale", sb.toString());
  }

  /**
   * JUnit mock test to check if flipping image horizontally works as expected.
   */
  @Test
  public void testFlipHorizontal() {
    String input = "horizontal-flip owl owl-horizontal";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ImageModel model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Recieved owl and owl-horizontal", sb.toString());
  }

  /**
   * JUnit mock test to check if flipping image vertically works as expected.
   */
  @Test
  public void testFlipVertical() {
    String input = "vertical-flip owl owl-vertical";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ImageModel model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Recieved owl and owl-vertical", sb.toString());
  }

  /**
   * JUnit mock test to check if brightening image works as expected.
   */
  @Test
  public void testBrighter() {
    String input = "brighten 10 owl owl-brighter";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ImageModel model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Recieved 10,owl and owl-brighter", sb.toString());
  }

  /**
   * JUnit mock test to check if splitting image into greyscale images works as expected.
   */
  @Test
  public void splitRGB() {
    String input = "rgb-split owl owl-red owl-green owl-blue";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ImageModel model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Recieved owl , owl-red , owl-red and owl-blue", sb.toString());
  }

  /**
   * JUnit mock test to check if combining greyscale images into a color image works as expected.
   */
  @Test
  public void combineRGB() {
    String input = "rgb-combine owl-red-tint owl-red owl-green owl-blue";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ImageModel model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    assertEquals("Recieved owl-red-tint , owl-red , owl-red and owl-blue", sb.toString());
  }

  /**
   * JUnit mock test to check if getpixels method works as expected.
   */
  @Test
  public void testGetPixels() {
    String input = "";
    InputStream in = new ByteArrayInputStream(input.getBytes());


    OutputStream out = new ByteArrayOutputStream();

    StringBuilder sb = new StringBuilder();
    ImageModel model = new MockModel(sb);
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, in, out);
    controller.go();
    model.getImagePixels("owl");
    assertEquals("Recieved owl", sb.toString());
  }

  /**
   * A custom model class created to test controller in the isolation.
   */
  public static class MockModel implements ImageModel {

    private final StringBuilder sb;

    /**
     * A constructor to initialize a mock model.
     *
     * @param sb a string builder to manage exception logs
     */
    public MockModel(StringBuilder sb) {
      this.sb = sb;
    }

    @Override
    public void load(String path, String fileName) throws FileNotFoundException, IllegalArgumentException {
      sb.append("Recieved " + path + " and " + fileName);
    }

    @Override
    public void save(String path, String fileName) throws IOException {
      sb.append("Recieved " + path + " and " + fileName);
    }

    @Override
    public void visualizeComponent(COMPONENTS COMPONENTS, String fileName, String destFileName) {
      sb.append("Recieved " + COMPONENTS + " , " + fileName + " and " + destFileName);
    }

    @Override
    public void flipHorizontally(String fileName, String destFileName) {
      sb.append("Recieved " + fileName + " and " + destFileName);
    }

    @Override
    public void flipVertically(String fileName, String destFileName) {
      sb.append("Recieved " + fileName + " and " + destFileName);
    }

    @Override
    public void brightness(int intensity, String fileName, String destFileName) {
      sb.append("Recieved " + intensity + "," + fileName + " and " + destFileName);
    }

    @Override
    public void splitRGB(String fileName, String redFileName, String greenFileName, String blueFileName) {
      sb.append("Recieved " + fileName + " , " + redFileName + " , " + redFileName + " and " + blueFileName);
    }

    @Override
    public void combineRGB(String destFileName, String redFileName, String greenFileName, String blueFileName) {
      sb.append("Recieved " + destFileName + " , " + redFileName + " , " + redFileName + " and " + blueFileName);
    }

    @Override
    public ArrayList<ArrayList<PixelInterface>> getImagePixels(String filename) {
      sb.append("Recieved " + filename);
      return null;
    }
  }

}
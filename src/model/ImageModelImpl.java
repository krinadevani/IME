package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.ImageUtilsImpl;

/**
 * Given implementation represents a model class.
 * It contains all the business logic of image processing and manipulation.
 * <p>
 * Given Image model class will contains various image processing logics
 * like load, save image, flip an image, convert it to greyscale image
 * based on various value components, combine and  split image etc.
 * </p>
 * Also, it will use generalised methods from the abstract class.
 */
public class ImageModelImpl implements ImageModel {

  private final Map<String, Image> imageCollection;
  ImageUtilsImpl imageUtilsImpl = new ImageUtilsImpl();
  private Image img;

  /**
   * Given constructor will initialize image collection hashmap
   * and error logs string builder.
   */
  public ImageModelImpl() {
    imageCollection = new HashMap<>();
  }

  /**
   * Load file specified on the given path in the hashmap with given filename.
   *
   * @param path     file path provided to load the file
   * @param fileName filename referred henceforth in the program
   */
  @Override
  public void load(String path, String fileName) throws FileNotFoundException, IllegalArgumentException {
    this.img = imageUtilsImpl.readPPMImage(path);
    imageCollection.put(fileName, img);
  }

  /**
   * Save file in the specified path.
   *
   * @param path     provided path where file needs to be saved
   * @param fileName filename of the file needs to be saved
   * @throws IOException throws input output exception
   */
  @Override
  public void save(String path, String fileName) throws IOException {
    img.saveImage(path, imageCollection.get(fileName));
  }

  /**
   * Given method creates greyscale image of the provided image based on the component given
   * and refer to it henceforth in the program by the given destination name.
   * <p>
   * Here, COMPONENTS is a enum type.
   * It contains various value component like Red, Green, Blue, Value, Intensity and luma.
   * </p>
   *
   * @param component    enum value of the channels of the pixel
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  @Override
  public void visualizeComponent(COMPONENTS component, String fileName, String destFileName) {
    Image image = imageCollection.get(fileName).cloneImage().visualizeComponent(component);
    imageCollection.put(destFileName, image);
  }

  /**
   * Given method flip image horizontally
   * and refer to it henceforth in the program by the given destination name..
   *
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  @Override
  public void flipHorizontally(String fileName, String destFileName) {
    Image image = imageCollection.get(fileName).cloneImage().flipHorizontally();
    imageCollection.put(destFileName, image);
  }

  /**
   * Given method flip image vertically
   * and refer to it henceforth in the program by the given destination name..
   *
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  @Override
  public void flipVertically(String fileName, String destFileName) {
    Image image = imageCollection.get(fileName).cloneImage().flipVertically();
    imageCollection.put(destFileName, image);
  }

  /**
   * Given method increase or decrease the brightness of the image
   * and refer to it henceforth in the program by the given destination name.
   * <p>
   * A positive integer value of the intensity will brighter the image
   * and a negative integer value of the intensity will darker the image.
   * </p>
   *
   * @param intensity    integer value of increment
   * @param fileName     filename of the file needs to be referred
   * @param destFileName filename referred henceforth in the program
   */
  @Override
  public void brightness(int intensity, String fileName, String destFileName) {
    Image image = imageCollection.get(fileName).cloneImage().brightness(intensity);
    imageCollection.put(destFileName, image);
  }

  /**
   * Given method split image in the greyscale image of red, green and blue component
   * and refer to it in the program by provided redFileName, greenFileName and
   * blueFileName respectively.
   *
   * @param fileName      filename of the file needs to be referred
   * @param redFileName   red-component filename referred henceforth in the program
   * @param greenFileName green-component filename referred henceforth in the program
   * @param blueFileName  blue-component filename referred henceforth in the program
   */
  @Override
  public void splitRGB(String fileName, String redFileName, String greenFileName, String blueFileName) {
    visualizeComponent(COMPONENTS.RED, fileName, redFileName);
    visualizeComponent(COMPONENTS.GREEN, fileName, greenFileName);
    visualizeComponent(COMPONENTS.BLUE, fileName, blueFileName);
  }

  /**
   * Given method will combine red, green and blue greyscale image into one image.
   *
   * @param destFileName  filename referred henceforth in the program
   * @param redFileName   red-component filename of the file needs to be referred
   * @param greenFileName green-component filename of the file needs to be referred
   * @param blueFileName  blue-component filename of the file needs to be referred
   */
  @Override
  public void combineRGB(String destFileName, String redFileName, String greenFileName, String blueFileName) {
    Image redImage = imageCollection.get(redFileName);
    Image greenImage = imageCollection.get(greenFileName);
    Image blueImage = imageCollection.get(blueFileName);

    Image image = redImage.cloneImage().combineRGB(greenImage, blueImage);
    imageCollection.put(destFileName, image);

  }

  /**
   * Given method will return array list of the image pixels specified in the filename.
   *
   * @param filename filename of the image which needs to be used
   * @return an array list of the pixels of the image
   */
  @Override
  public List<ArrayList<PixelInterface>> getImagePixels(String filename) {
    return imageCollection.get(filename).getPixels();
  }

}

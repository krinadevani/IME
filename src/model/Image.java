package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Given interface contains summary of the abstract image class.
 * <p>
 * Given generalized interface is further implemented by AbstractImage
 * which will extends by PPM class.
 * </p>
 * <p>
 * Given abstract class contains generalized image processing and manipulation
 * methods.
 * It also contains methods to fetch width, height, maxvalue and pixels of an
 * image.
 * </p>
 */
public interface Image {

  /**
   * Given method will get width of the given image.
   *
   * @return width of the image
   */
  int getWidth();

  /**
   * Given method will get height of the given image.
   *
   * @return height of the image
   */
  int getHeight();

  /**
   * Given method will get maximum value of the given image.
   *
   * @return maxVal of the image
   */
  int getMaxVal();

  /**
   * Given method will get array of the pixels of the given image.
   *
   * @return pixel arrays of the image
   */
  List<ArrayList<PixelInterface>> getPixels();

  /**
   * Given method will get the pixel at the given position (row,col) of the image.
   *
   * @return pixel at the given position.
   */
  PixelInterface getPixel(int row, int col);

  /**
   * Given method will create a clone image of the given image.
   *
   * @return copy of the given image
   */
  Image cloneImage();

  /**
   * Save image provided in the specified path.
   *
   * @param path  provided path where file needs to be saved
   * @param image image which needs to be saved
   * @throws IOException throws input output exception
   */
  void saveImage(String path, Image image) throws IOException;

  /**
   * Given method will create a greyscale image based on the component passed.
   *
   * @param component enum value of the channels of the pixel
   * @return created greyscale image
   */
  Image visualizeComponent(COMPONENTS component);

  /**
   * Given method will flip the image horizontally.
   *
   * @return horizontally flipped image
   */
  Image flipHorizontally();

  /**
   * Given method will flip the image vertically.
   *
   * @return vertically flipped image
   */
  Image flipVertically();

  /**
   * Given method manage the brightness of the given image.
   * It will brighter or darker the image based on the intensity.
   * <p>
   * A positive integer value of the intensity will brighter the image
   * and a negative integer value of the intensity will darker the image.
   * </p>
   *
   * @param intensity provided intensity to manage the brightness
   * @return created brighter or darker image
   */
  Image brightness(int intensity);

  /**
   * Given method split image in the greyscale image of red, green and blue component
   * and refer to it in the program by provided redFileName, greenFileName and
   * blueFileName respectively.
   *
   * @param fileName      filename of the file needs to be referred
   * @param redFileName   red-component filename referred henceforth in the program
   * @param greenFileName green-component filename referred henceforth in the program
   * @param blueFileName  blue-component filename referred henceforth in the program
   * @return list of the created red, green and blue images
   */
  List<Image> splitRGB(String fileName, String redFileName, String greenFileName, String blueFileName);

  /**
   * Given method will combine image with the provided images.
   *
   * @param greenImage green-component image
   * @param blueImage  blue-component image
   * @return combined image created by combining provided images with the given image
   */
  Image combineRGB(Image greenImage, Image blueImage);
}

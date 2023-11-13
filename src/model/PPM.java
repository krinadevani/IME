package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.ImageUtilsImpl;

/**
 * Given Class represents a PPM image file.
 * <p>
 * Given class performs various processing and manipulations
 * on the PPM file like load, save, clone, flip an image, manage the brightness,
 * create a greyscale image based on value component provided, split and combine
 * an image.
 * It extends abstract class named AbstractImage.
 * </p>
 */
public class PPM extends AbstractImage {


  /**
   * Given constructor will inherit the width, height, maxVal
   * and arraylist of the pixels.
   *
   * @param width  width of the ppm file
   * @param height height of the ppm file
   * @param maxVal maximum value of the ppm file
   * @param pixels arraylist of pixels of the given ppm
   */
  public PPM(int width, int height, int maxVal, List<ArrayList<PixelInterface>> pixels) {
    super(width, height, maxVal, pixels);
  }

  /**
   * Given method will create a clone image of the given image.
   *
   * @return copy of the given image
   */
  @Override
  public Image cloneImage() {
    return new PPM(width, height, maxVal, makeCopyOfArray(pixels));
  }

  /**
   * Save image provided in the specified path.
   *
   * @param path  provided path where file needs to be saved
   * @param image image which needs to be saved
   * @throws IOException throws input output exception
   */
  @Override
  public void saveImage(String path, Image image) throws IOException {
    ImageUtilsImpl imageUtils = new ImageUtilsImpl();
    imageUtils.savePPMImage(path, image);

  }

  /**
   * Given abstract helper method will return a created object of the image
   * using width, height, maxVal and pixels.
   *
   * @param width  width of the image
   * @param height height of the image
   * @param maxVal maxVal of the image
   * @param pixels arraylist of the pixels of the image
   * @return created object of the image
   */
  @Override
  protected Image getImageObject(int width, int height, int maxVal, List<ArrayList<PixelInterface>> pixels) {
    return new PPM(width, height, maxVal, pixels);
  }

}

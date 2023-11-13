package utils;

import java.io.FileNotFoundException;
import java.io.IOException;

import model.Image;

/**
 * Given interface summarize the utility class for the image processing.
 * It contains method overview of read and write images.
 */
public interface ImageUtils {

  /**
   * Given method will read the ppm image from the specified path
   * and return a new image object.
   * <p>
   * It reads image in pixels format.
   * Also, save their width, height and maxvalue.
   * </p>
   *
   * @param path file path provided to read the file
   * @return image object create by reading provided file
   */
  Image readPPMImage(String path) throws FileNotFoundException, IllegalArgumentException;

  /**
   * Save image provided in the specified path.
   *
   * @param path  provided path where file needs to be saved
   * @param image image which needs to be saved
   * @throws IOException throws input output exception
   */
  void savePPMImage(String path, Image image) throws IOException;
}

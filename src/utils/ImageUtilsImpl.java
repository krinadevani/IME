package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Image;
import model.PPM;
import model.Pixel;
import model.PixelInterface;

/**
 * Utility class for the image processing.
 * This helper class will help to process various kinds of
 * operations on the image like save and read.
 */
public class ImageUtilsImpl implements ImageUtils {

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
  @Override
  public Image readPPMImage(String path) throws FileNotFoundException, IllegalArgumentException {
    Scanner sc;
    List<ArrayList<PixelInterface>> pixels = new ArrayList<ArrayList<PixelInterface>>();
    try {
      sc = new Scanner(new FileInputStream(path));
    } catch (FileNotFoundException e) {
      throw new FileNotFoundException();
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

    if (!token.equals("P3")) {
      throw new IllegalArgumentException();
    }
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
    return new PPM(width, height, maxValue, pixels);
  }

  /**
   * Save image provided in the specified path.
   *
   * @param path  provided path where file needs to be saved
   * @param image image which needs to be saved
   * @throws IOException throws input output exception
   */
  @Override
  public void savePPMImage(String path, Image image) throws IOException {

    Path filePath = Paths.get(path);
    File directory = new File(filePath.getParent().toString());
    if (!directory.exists()) {
      directory.mkdir();
    }

    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
    writer.write("P3");
    writer.newLine();
    writer.write(image.getWidth() + " " + image.getHeight());
    writer.newLine();
    writer.write(String.valueOf(image.getMaxVal()));
    for (int row = 0; row < image.getHeight(); row++) {
      for (int column = 0; column < image.getWidth(); column++) {
        writer.newLine();
        writer.write(String.valueOf(image.getPixel(row, column).getR()));
        writer.newLine();
        writer.write(String.valueOf(image.getPixel(row, column).getG()));
        writer.newLine();
        writer.write(String.valueOf(image.getPixel(row, column).getB()));
      }
    }
    writer.flush();
    writer.close();
  }

}

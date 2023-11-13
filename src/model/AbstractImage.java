package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Given class represents an abstract class of the image
 * and implements Image interface.
 * <p>
 * Given abstract class will further extended by PPM class.
 * </p>
 * <p>
 * Given abstract class contains generalized image processing and manipulation
 * methods like save and clone image, flip an image, manage the brightness,
 * create a greyscale image based on value component provided, split and combine
 * an image.
 * It also contains methods to fetch width, height, maxvalue and pixels of an
 * image.
 * </p>
 */
public abstract class AbstractImage implements Image {

  protected final int width;
  protected final int height;
  protected final int maxVal;
  protected final List<ArrayList<PixelInterface>> pixels;

  /**
   * Given constructor will initialize width, height, maxVal and
   * pixel array list of the given image.
   *
   * @param width  provided value of the width
   * @param height provided value of the height
   * @param maxVal provided value of the maxVal
   * @param pixels provided array list of the pixels
   */
  public AbstractImage(int width, int height, int maxVal, List<ArrayList<PixelInterface>> pixels) {
    this.width = width;
    this.height = height;
    this.maxVal = maxVal;
    this.pixels = pixels;
  }

  /**
   * Given helper method will get value of the pixel on bases of channel passed.
   *
   * @param pixel      given pixel object
   * @param COMPONENTS enum value of the channels of the pixel
   * @return integer value to be set
   */
  protected static int getValueBasedOnChannel(PixelInterface pixel, COMPONENTS COMPONENTS) {
    int setValue = 0;

    switch (COMPONENTS) {
      case RED:
        setValue = pixel.getR();
        break;
      case GREEN:
        setValue = pixel.getG();
        break;
      case BLUE:
        setValue = pixel.getB();
        break;
      case VALUE:
        setValue = pixel.getValue();
        break;
      case LUMA:
        setValue = pixel.getLuma();
        break;
      case INTENSITY:
        setValue = pixel.getIntensity();
        break;
    }

    return setValue;
  }

  /**
   * Given method will get width of the given image.
   *
   * @return width of the image
   */
  @Override
  public int getWidth() {
    return width;
  }

  /**
   * Given method will get height of the given image.
   *
   * @return height of the image
   */
  @Override
  public int getHeight() {
    return height;
  }

  /**
   * Given method will get maximum value of the given image.
   *
   * @return maxVal of the image
   */
  @Override
  public int getMaxVal() {
    return maxVal;
  }

  /**
   * Given method will get array of the pixels of the given image.
   *
   * @return pixel arrays of the image
   */
  @Override
  public List<ArrayList<PixelInterface>> getPixels() {
    return pixels;
  }

  /**
   * Given method will get the pixel at the given position (row,col) of the image.
   *
   * @return pixel at the given position.
   */
  @Override
  public PixelInterface getPixel(int row, int col) {
    return pixels.get(row).get(col);
  }

  /**
   * Given method will create a clone image of the given image.
   *
   * @return copy of the given image
   */
  @Override
  public abstract Image cloneImage();

  /**
   * Save image provided in the specified path.
   *
   * @param path  provided path where file needs to be saved
   * @param image image which needs to be saved
   * @throws IOException throws input output exception
   */
  @Override
  public abstract void saveImage(String path, Image image) throws IOException;

  /**
   * Given method will create a greyscale image based on the component passed.
   *
   * @param component enum value of the channels of the pixel
   * @return created greyscale image
   */
  @Override
  public Image visualizeComponent(COMPONENTS component) {
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        PixelInterface tempPixel = getPixel(row, col);

        int setValue = getValueBasedOnChannel(tempPixel, component);
        tempPixel.setR(setValue);
        tempPixel.setG(setValue);
        tempPixel.setB(setValue);
      }
    }
    return getImageObject(width, height, maxVal, pixels);
  }

  /**
   * Given method will flip the image horizontally.
   *
   * @return horizontally flipped image
   */
  @Override
  public Image flipHorizontally() {
    for (int i = 0; i < getHeight(); i++) {
      Collections.reverse(getPixels().get(i));
    }
    return getImageObject(width, height, maxVal, pixels);
  }

  /**
   * Given method will flip the image vertically.
   *
   * @return vertically flipped image
   */
  @Override
  public Image flipVertically() {
    int height = getHeight();
    for (int i = 0; i < height / 2; i++) {
      ArrayList<PixelInterface> temp = new ArrayList<>(getPixels().get(i));
      ArrayList<PixelInterface> temp2 = getPixels().get(i);
      ArrayList<PixelInterface> temp3 = getPixels().get(height - i - 1);
      ArrayList<PixelInterface> temp4 = new ArrayList<>(getPixels().get(height - i - 1));

      temp2.clear();
      temp2.addAll(temp4);

      temp3.clear();
      temp3.addAll(temp);
    }
    return getImageObject(width, height, maxVal, pixels);
  }

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
  @Override
  public Image brightness(int intensity) {
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        PixelInterface tempPixel = getPixel(row, col);

        int redVal = Math.max(0, Math.min(255, tempPixel.getR() + intensity));
        int blueVal = Math.max(0, Math.min(255, tempPixel.getB() + intensity));
        int greenVal = Math.max(0, Math.min(255, tempPixel.getG() + intensity));

        tempPixel.setR(redVal);
        tempPixel.setB(blueVal);
        tempPixel.setG(greenVal);
      }
    }
    return getImageObject(width, height, maxVal, pixels);
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
   * @return list of the created red, green and blue images
   */
  @Override
  public List<Image> splitRGB(String fileName, String redFileName, String greenFileName, String blueFileName) {
    return null;
  }

  /**
   * Given method will combine image with the provided images.
   *
   * @param greenImage green-component image
   * @param blueImage  blue-component image
   * @return combined image created by combining provided images with the given image
   */
  @Override
  public Image combineRGB(Image greenImage, Image blueImage) {
    List<ArrayList<PixelInterface>> imagePixels = new ArrayList<ArrayList<PixelInterface>>();

    for (int row = 0; row < getHeight(); row++) {
      imagePixels.add(new ArrayList<PixelInterface>());
      for (int col = 0; col < getWidth(); col++) {
        int r = getPixel(row, col).getR();
        int g = greenImage.getPixel(row, col).getG();
        int b = blueImage.getPixel(row, col).getB();

        imagePixels.get(row).add(col, new Pixel(r, g, b));
      }
    }

    return getImageObject(getWidth(), getHeight(), getMaxVal(), imagePixels);
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
  protected abstract Image getImageObject(int width, int height, int maxVal, List<ArrayList<PixelInterface>> pixels);

  /**
   * Given helper method will create a deep copying the arraylist of the pixels.
   *
   * @param pixels arraylist of the pixels which needs to be copy
   * @return a cloned arraylist of the pixels
   */
  protected List<ArrayList<PixelInterface>> makeCopyOfArray(List<ArrayList<PixelInterface>> pixels) {
    List<ArrayList<PixelInterface>> newArray = new ArrayList<ArrayList<PixelInterface>>();
    for (ArrayList<PixelInterface> p : pixels) {
      ArrayList<PixelInterface> na = new ArrayList<>();
      for (PixelInterface pm : p) {
        na.add(new Pixel(pm.getR(), pm.getG(), pm.getB()));
      }
      newArray.add(na);
    }
    return newArray;
  }
}

package model;

/**
 * Given class represents entity class of pixel object.
 * <p>
 * An image is a sequence of pixels.
 * Each pixel has a position in the image and a color.
 * Color of a pixel is represented by breaking it into individual components in R,G,B.
 * </p>
 * It contains get and set method for R, G, B value
 * and get method for value, intensity and luma.
 */
public class Pixel implements PixelInterface {

  private int R, G, B;

  /**
   * Given constructor will initialize the R, G, B values.
   *
   * @param R provided value of red component
   * @param G provided value of green component
   * @param B provided value of blue component
   */
  public Pixel(int R, int G, int B) {
    this.R = R;
    this.G = G;
    this.B = B;
  }

  /**
   * Get red component of the pixel.
   *
   * @return int value of red component
   */
  @Override
  public int getR() {
    return R;
  }


  /**
   * Set red component of the pixel.
   *
   * @param r provided red component value
   */
  @Override
  public void setR(int r) {
    R = r;
  }

  /**
   * Get green component of the pixel.
   *
   * @return int value of green component
   */
  @Override
  public int getG() {
    return G;
  }

  /**
   * Set green component of the pixel.
   *
   * @param g provided green component value
   */
  @Override
  public void setG(int g) {
    G = g;
  }

  /**
   * Get blue component of the pixel.
   *
   * @return int value of blue component
   */
  @Override
  public int getB() {
    return B;
  }

  /**
   * Set blue component of the pixel.
   *
   * @param b provided blue component value
   */
  @Override
  public void setB(int b) {
    B = b;
  }

  /**
   * Get value component of the pixel.
   *
   * @return int value of value component
   */
  @Override
  public int getValue() {
    return Math.max(B, (Math.max(R, G)));
  }

  /**
   * Get intensity component of the pixel.
   *
   * @return int value of intensity component
   */
  @Override
  public int getIntensity() {
    return (R + G + B) / 3;
  }

  /**
   * Get luma component of the pixel.
   *
   * @return int value of luma component
   */
  @Override
  public int getLuma() {
    return (int) (0.2126 * R + 0.7152 * G + 0.0722 * B);
  }

}

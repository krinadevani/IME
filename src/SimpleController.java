import controller.ImageController;
import controller.ImageControllerImpl;
import model.ImageModel;
import model.ImageModelImpl;
import view.ImageView;
import view.ImageViewImpl;

/**
 * Given controller class is a main class.
 * In this class we create object of MVC (model, view, controller)
 * and pass control to the Image controller.
 */
public class SimpleController {

  /**
   * Given method will create object of model and view
   * and pass it to controller.
   * Then relinquishes control to the controller (by calling its go method).
   *
   * @param args an array of string
   */
  public static void main(String[] args) {
    ImageModel model = new ImageModelImpl();
    ImageView view = new ImageViewImpl();
    ImageController controller = new ImageControllerImpl(model, view, System.in, System.out);
    controller.go();
  }

}

package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.COMPONENTS;
import model.ImageModel;
import view.ImageView;

/**
 * Given implementation is a controller class.
 *
 * <p>
 * Image controller act as a mediator between ImageView
 * and ImageModel.
 * It took commands from the ImageView and processCommand
 * using logic defined in ImageModel.
 * It remove dependency between Model and View.
 * </p>
 */
public class ImageControllerImpl implements ImageController {
  private final ImageModel model;
  private final ImageView view;
  private InputStream in;

  private final OutputStream out;


  /**
   * A constructor will initialize Image model, Image view and input stream.
   *
   * @param model provided Image model
   * @param view  provided Image view
   * @param in    provided Input stream
   * @param out   provided Output stream
   */
  public ImageControllerImpl(ImageModel model, ImageView view, InputStream in, OutputStream out) {
    this.model = model;
    this.view = view;
    this.in = in;
    this.out = out;
  }

  /**
   * Given method ask controller to start taking
   * command line inputs in iterative manner.
   * It will stop taking inputs once it receives
   * 'exit' command.
   */
  @Override
  public void go() {
    String input = "";
    Scanner sc = new Scanner(this.in);
    StringBuilder sb = new StringBuilder();

    while (sc.hasNextLine()) {
      input = sc.nextLine();
      if (input.equals("exit")) {
        break;
      }
      processCommand(input);
    }
  }

  /**
   * Given method get extension from the filename.
   *
   * @param fileName filename provided
   * @return get extension from the filename
   */
  private String getExtension(String fileName) {

    int i = fileName.lastIndexOf('.');
    if (i > 0) {
      return fileName.substring(i + 1);
    }
    return null;
  }


  /**
   * Given method will process commands provided.
   * It uses business logic from the Image model according to
   * the commands provided.
   *
   * @param commandLine the command provided.
   */
  @Override
  public void processCommand(String commandLine) {
    try {
      String[] context = commandLine.trim().replaceAll(" +", " ").split(" ");
      String command = context[0];

      switch (command.toLowerCase()) {
        case "load":
          if (context.length != 3) {
            view.displayOutput(this.out, "Invalid arguments passed :" + commandLine + System.lineSeparator());
            break;

          }
          model.load(context[1], context[2]);

          break;
        case "save":
          if (context.length != 3) {
            view.displayOutput(this.out, "Invalid arguments passed :" + commandLine + System.lineSeparator());
            break;
          }
          model.save(context[1], context[2]);
          break;
        case "greyscale":
          if (context.length != 4) {
            view.displayOutput(this.out, "Invalid arguments passed :" + commandLine + System.lineSeparator());
            break;
          }
          String component = context[1].toLowerCase();
          switch (component) {
            case "red-component":
              model.visualizeComponent(COMPONENTS.RED, context[2], context[3]);
              break;
            case "green-component":
              model.visualizeComponent(COMPONENTS.GREEN, context[2], context[3]);
              break;
            case "blue-component":
              model.visualizeComponent(COMPONENTS.BLUE, context[2], context[3]);
              break;
            case "value-component":
              model.visualizeComponent(COMPONENTS.VALUE, context[2], context[3]);
              break;
            case "intensity-component":
              model.visualizeComponent(COMPONENTS.INTENSITY, context[2], context[3]);
              break;
            case "luma-component":
              model.visualizeComponent(COMPONENTS.LUMA, context[2], context[3]);
              break;
            default:
              view.displayOutput(this.out, "Invalid value component passed :" + commandLine + System.lineSeparator());
              break;
          }
          break;
        case "horizontal-flip":
          if (context.length != 3) {
            view.displayOutput(this.out, "Invalid arguments passed :" + commandLine + System.lineSeparator());
            break;
          }
          model.flipHorizontally(context[1], context[2]);
          break;
        case "vertical-flip":
          if (context.length != 3) {
            view.displayOutput(this.out, "Invalid arguments passed :" + commandLine + System.lineSeparator());

            break;
          }
          model.flipVertically(context[1], context[2]);
          break;
        case "brighten":
          if (context.length != 4) {
            view.displayOutput(this.out, "Invalid arguments passed :" + commandLine + System.lineSeparator());

            break;
          }
          model.brightness((int) Double.parseDouble(context[1]), context[2], context[3]);
          break;
        case "rgb-split":
          if (context.length != 5) {
            view.displayOutput(this.out, "Invalid arguments passed :" + commandLine + System.lineSeparator());

            break;
          }
          model.splitRGB(context[1], context[2], context[3], context[4]);
          break;
        case "rgb-combine":
          if (context.length != 5) {
            view.displayOutput(this.out, "Invalid arguments passed :" + commandLine + System.lineSeparator());

            break;
          }
          model.combineRGB(context[1], context[2], context[3], context[4]);
          break;
        case "run":
          if (context.length != 2) {
            view.displayOutput(this.out, "Invalid arguments passed :" + commandLine + System.lineSeparator());
            break;
          }
          List<String> inputCommands = takeInputFile(new FileInputStream(context[1]));
          for (String e : inputCommands) {
            processCommand(e);
          }
          break;
        case "exit":
          if (context.length != 1) {
            view.displayOutput(this.out, "Invalid arguments passed :" + commandLine + System.lineSeparator());
            break;
          }
          break;
        default:
          view.displayOutput(this.out, "Invalid command name passed :" + commandLine + System.lineSeparator());
          break;
      }

    } catch (IllegalArgumentException ex) {
      view.displayOutput(this.out, "Invalid PPM file: plain RAW file should begin with P3" + System.lineSeparator());
    } catch (FileNotFoundException ex) {
      view.displayOutput(this.out, "File not found :" + commandLine + System.lineSeparator());
    } catch (NullPointerException ex) {
      view.displayOutput(this.out, "Filename passed is not loaded :" + commandLine + System.lineSeparator());
    } catch (NoSuchElementException ex) {
      view.displayOutput(this.out, "File passed is empty :" + commandLine + System.lineSeparator());
    } catch (Exception ex) {
      view.displayOutput(this.out, commandLine + System.lineSeparator());
    }
  }

  /**
   * Given method is a helper method which works on a script file.
   * Script file is a collection of commands. user can provide the file
   * using run command.
   *
   * @param in represents input stream of bytes
   * @return list of commands passed in the script file
   */

  private List<String> takeInputFile(InputStream in) {
    this.in = in;
    Scanner sc = new Scanner(this.in);
    List<String> inputCommands = new ArrayList<String>();
    while (sc.hasNextLine()) {
      String input = sc.nextLine();
      if (!input.isEmpty() && input.charAt(0) != '#') {

        inputCommands.add(input);
      }
    }
    return inputCommands;


  }

}

# IME: Image Manipulation and Enhancement

Overview
------------------

**IME is an image processing application. It helps to perform image manipulation and enhancement
effects such as,**

1. Visualizing components
2. Image flipping - Horizontally or Vertically
3. Brightening or darkening an image
4. Converting color image to greyscale image based on various channels such as red, green, blue,
   value, intensity and luma.
5. Split the image into 3 greyscale images based on red, green and blue channels.
6. Combining 3 greyscale images based on red, green and blue channels into one color image.

## Implementation

IME is based on the MVC architecture which follows Model-View-Controller (MVC) design pattern.
In which,
Model contains the business logic for image processing.
Controller acts as a mediator between view and model. It takes commands from the view and processes
the command using logic developed in the model.
View is the user interaction layer which displays the output of the proceed command.


### Models

**1. ImageModel** :

It is an interface which represents an image model. This interface contains a summary of the
business logic performed on the image model.

**2. ImageModelImpl** :

It represents a model class which implements ImageModel interface. It contains methods of image
processing and manipulation such as loading the image, saving the image, flipping an image
horizontally or vertically, converting an image to a greyscale image, splitting an image into 3
greyscale images and combining 3 greyscale images into one color image.    
It also handles imageCollection. **imageCollection** is a hashmap which contains filename as a key
and Image object as a value. It will add and update the values according to the commands given.

**3. PixelInterface** :

It is an interface which represents Pixel entity. An image is a sequence of pixels. Each pixel has a
position in the image and a color.

**4. Pixel** :

This class represents entity class of pixel and implements PixelInterface interface. It contains
various methods performed on the pixels such as, get and set methods for R, G, B value. Also, get
methods for value, intensity and luma.

**5. Image** :

This interface contains summary of the generalized image processing and manipulation methods. It
also contains methods to fetch width, height, maxvalue and pixels of an image.

**6. AbstractImage** :

This class represents an abstract class of the image and implements Image interface. It also
contains generalized business logic for image processing and manipulation. It will be further
extended by the PPM class.

**7. PPM** :

This Class represents a PPM image file and it also extends AbstractImage abstract class. The PPM
format is a simple, text-based file format to store images. It contains a dump of the red, green and
blue values of each pixel, row-wise. This class override various processing and manipulation methods
on the PPM file.

**8. COMPONENTS** :

This Enum class COMPONENTS represent various channels of the pixels such as red, green, blue, value,
intensity and luma.

### Controller

**1. ImageController** :

It is a controller interface which contains summary of the operations performed in the Image
controller. Image controller act as a mediator between ImageView and ImageModel.

**2. ImageControllerImpl** :

This implementation is a controller class which implements ImageController interface. It took
commands from the terminal or script-file. It calls logic methods of the model according to the
command provided and sent appropriate output to the view. Hence, It removes dependency between model
and view.

### View

**1. ImageView** :

This interface contains summary of the methods used to interact with users. This is an interaction
layer of MVC.

**2. ImageViewImpl** :

It is a view class which implements ImageView interface. This class will contain the methods
implemented for user interaction.
It takes proceed output from the controller and displays it in the terminal.

### Utils

**1. ImageUtils** :

This interface contains summary of the helper utility class. It contains an overview of the save and
read image methods.

**2. ImageUtilsImpl** :

This utility helper class is used for image processing and implements ImageUtils interface. It
contains methods like read and save image.

### SimpleController

This main controller class will create a new object of model and view classes and then pass these
objects to the controller. Then relinquishes control to the controller (by calling its go method).

## Ways to input commands

There are two ways where user can pass the commands and manipulate the image.

**1. Command-line** :

User can use console to enter commands in the program. To use this way one need to use *
*SimpleController** which contains a main method. This method will get inputs using the scanner one
by one in iterative manner and send it to the ImageControllerImpl for the further processing. The
iteration will stop once the method receives **exit** command as a input. It also displays
appropriate output to the terminal once the command proceed.

**2. Script-file** :

User can send a sequence of script commands using a script file. One need to pass script file using
command **run script-file** where script-file is the path of the script file.

**For Example** : To run the script file named **script-file** in the given code. One needs to
follow steps given below:

1. Run main method of SimpleController.
2. enter **run script-file** command to the console where script-file is the path of the command
   file.
3. enter **exit** if wants to exit from the console.
4. enter the valid command if wants to perform actions further.

## Contributing

1. Create your feature branch: `git checkout -b new-branch-name`
2. Commit your changes: `git commit -am 'Add some feature'`
3. Push to the branch: `git push origin new-branch-name`
4. Submit a pull request

## Contributors

- Krina Devani
- Sarthak Kagliwal

## License

PPM file used in the application is created by us and we are authorizing it to use further in this
project.




  

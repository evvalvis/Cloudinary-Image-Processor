package gr.cs.aueb.distributed.systems.util.image;

import java.io.File;

public class ImageTest {

  public static void main(String[] args) {
    // Note : if you upload the same image again it gets another url, so if you wanna test that
    // everything works :
    // 1) Run it as it is
    // 2) Check the printed link -> should be blank
    // 3) Check the printed link by replacing the image name with renamedTest.png
    // Should be blank as well
    // Even thought i don't think we are going to delete images anyway
    String image = "C:\\Users\\Evan\\Desktop\\meme.jpg";
    String result =
        CloudinaryImageManager.getInstance().uploadImage(new File(image), "meme1", "png", "memez");
    System.out.println(result);
    CloudinaryImageManager.getInstance().renameImage("meme1", "renameTest");
    // above link should not work (should show a blank image) should be replaced with renameTest.png
    // now we delete the renamed image
    CloudinaryImageManager.getInstance().deleteImage("renameTest");
    // now the image link to renameTest.png will be blank as well
  }

}

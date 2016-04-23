package gr.cs.aueb.distributed.systems.util.image;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryImageManager {

  private Cloudinary cloudinary;

  /**
   * Private constructor
   */
  private CloudinaryImageManager() {
    cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "djbfchwia", "api_key",
        "753318975694996", "api_secret", "Tm49nL39U-0I6N7LNw9yHgUBxTs"));
  }

  @SuppressWarnings("rawtypes")
  /**
   * Upload an image
   *
   * @param image - the image file
   * @param name - the name of the image that will be shown in the url
   * @param imageType - image type : png, jpg or w/e
   * @param tags - image tags since we are talking about a social network
   * @return the url of the uploaded image or null if the input file does not exist
   */
  public String uploadImage(File image, String name, String imageType, String... tags) {
    if (!image.exists())
      return null;
    Map uploadParameters = ObjectUtils.asMap("public_id", name, "transformation",
        new Transformation().fetchFormat(imageType), "tags", Arrays.asList(tags), "invalidate",
        true);
    Map uploadResult = null;
    try {
      uploadResult = cloudinary.uploader().upload(image, uploadParameters);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return (String) uploadResult.get("secure_url");
  }

  /**
   * Renames an uploaded image
   *
   * @param oldName - uploaded image's current name
   * @param newName - uploaded images' new name
   */
  public void renameImage(String oldName, String newName) {
    try {
      cloudinary.uploader().rename(oldName, newName, ObjectUtils.asMap("overwrite", true));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Delete an uploaded image
   *
   * @param imageID
   */
  public void deleteImage(String imageID) {
    if (imageID == null)
      return;
    try {
      cloudinary.uploader().destroy(imageID, ObjectUtils.asMap("invalidate", true));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private static class SingletonHolder {
    private static final CloudinaryImageManager INSTANCE = new CloudinaryImageManager();
  }

  /**
   * Get the only instance of this object. Since it is a manager used my many threads we want a solo
   * synchronized instance
   *
   * @return
   */
  public static synchronized CloudinaryImageManager getInstance() {
    return SingletonHolder.INSTANCE;
  }
}

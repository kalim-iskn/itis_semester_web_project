package ru.kpfu.itis.iskander.models;

import ru.kpfu.itis.iskander.classes.PhotoRemover;
import ru.kpfu.itis.iskander.classes.PhotoUploader;
import ru.kpfu.itis.iskander.database.DataBaseConnection;
import ru.kpfu.itis.iskander.exceptions.NoSuchRecordIntoTableException;
import ru.kpfu.itis.iskander.exceptions.UploadPhotoInvalidException;
import ru.kpfu.itis.iskander.repositories.SaleAnnouncementRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UpdateAddAnnouncementModel {

    /**
     * @param req (HttpServletRequest with parameters)
     * @param announcementId (-1 if it is announcement adding, else - editing)
     * @return list of errors (success if list is empty)
     * @throws IOException (IOException)
     * @throws ServletException (ServletException)
     */
    public static ArrayList<String> execute(HttpServletRequest req, int announcementId, int extraPicturesCount) throws IOException, ServletException {
        ArrayList<String> errors = CheckAnnouncementParamsModel.check(req);
        if (errors.isEmpty()) {
            if (req.getPart("main_picture") != null) {
                String mainPicture = "";
                String mainPictureFromDB = null;
                StringBuilder pictures = new StringBuilder();
                if (announcementId != -1) {
                    try {
                        SaleAnnouncementRepository repository = new SaleAnnouncementRepository();
                        mainPictureFromDB = repository.getMainPicture(announcementId);
                        mainPicture = mainPictureFromDB;
                    } catch (SQLException | ClassNotFoundException | NoSuchRecordIntoTableException e) {
                        errors.add("Проблемы с базой данных");
                        return errors;
                    }
                }

                String appPath = req.getServletContext().getRealPath("");

                try {
                    PhotoUploader uploader = new PhotoUploader(appPath);
                    Part mainPicturePart = req.getPart("main_picture");
                    if (announcementId == -1 || !Paths.get(mainPicturePart.getSubmittedFileName()).getFileName().toString().equals(""))
                        mainPicture = uploader.upload(req.getPart("main_picture"));
                    for (Part part : req.getParts()) {
                        if (part.getName().equals("pictures[]")) {
                            if (!Paths.get(part.getSubmittedFileName()).getFileName().toString().equals("")) {
                                if (extraPicturesCount == 5) {
                                    errors.add("Вы можете загрузить только 5 дополнительных фотографий");
                                    return errors;
                                }
                                pictures.append(uploader.upload(part)).append(";");
                                extraPicturesCount++;
                            }
                        }
                    }
                } catch (UploadPhotoInvalidException e) {
                    errors.add("Фотография может иметь разрешение только .png, .jpg, ,jpeg. Максимальный размер 3 мегабайта");
                    return errors;
                }
                try {
                    DataBaseConnection dbConnection = new DataBaseConnection();
                    String query;
                    if (announcementId == -1)
                        query =
                            "INSERT INTO announcements (city, category, name, description, main_picture, pictures, is_new, price, author_id) " +
                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    else
                        query =
                            "UPDATE announcements SET city = ?, category = ?, name = ?, description = ?, main_picture = ?," +
                                    "pictures = CONCAT(pictures, ?), is_new = ?, price = ? WHERE author_id = ? AND id = ?";

                    PreparedStatement statement =
                            dbConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                    statement.setString(1, req.getParameter("city"));
                    statement.setString(2, req.getParameter("category"));
                    statement.setString(3, req.getParameter("name"));
                    statement.setString(4, req.getParameter("description"));
                    statement.setString(5, mainPicture);
                    statement.setString(6, pictures.toString());
                    statement.setString(7, req.getParameter("is_new"));
                    statement.setInt(8, Integer.parseInt(req.getParameter("price")));
                    statement.setInt(9, (int) req.getAttribute("authorizedUserId"));
                    if (announcementId != -1)
                        statement.setInt(10, announcementId);

                    if (statement.executeUpdate() != 1)
                        errors.add("Проблемы с базой данных");
                    if (mainPictureFromDB != null && !mainPicture.equals(mainPictureFromDB)) {
                        PhotoRemover photoRemover = new PhotoRemover(appPath);
                        photoRemover.deletePhoto(mainPictureFromDB);
                    }

                    dbConnection.close();
                } catch (SQLException | ClassNotFoundException exception) {
                    errors.add("Проблемы с базой данных");
                }
            } else {
                errors.add("Загрузите главную фотографию");
            }
        }
        return errors;
    }

}

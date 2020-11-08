package ru.kpfu.itis.iskander.classes;

import java.sql.Timestamp;

public class SaleAnnouncement {

    private int id;
    private int authorId;
    private String name;
    private String description;
    private int price;
    private String city;
    private String category;
    private boolean isNew;
    private String mainPicture;
    private String pictures;
    private String[] picturesArray;
    private Timestamp createdAt;

    public SaleAnnouncement(int id, int authorId, String name, String description, Timestamp createdAt, String city,
                            String category, boolean isNew, String mainPicture, String pictures, int price) {
        this.id = id;
        this.authorId = authorId;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.city = city;
        this.category = category;
        this.isNew = isNew;
        this.mainPicture = mainPicture;
        this.pictures = pictures;
        this.picturesArray = pictures.split(";");
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getFormattedCreatedAt() {
        return createdAt.toString().split(" ")[0];
    }

    public String getCity() {
        return city;
    }

    public String getCategory() {
        return category;
    }

    public boolean isNew() {
        return isNew;
    }

    public String getMainPicture() {
        return mainPicture;
    }

    public String getPictures() {
        return pictures;
    }

    public String[] getPicturesArray() {
        return picturesArray;
    }

    public int getPrice() {
        return price;
    }
}

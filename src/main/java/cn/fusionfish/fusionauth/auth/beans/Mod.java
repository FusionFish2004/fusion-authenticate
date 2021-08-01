package cn.fusionfish.fusionauth.auth.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 代表一个FabricMod的JavaBean
 */
public class Mod {
    @SerializedName("id")
    private final String id;

    @SerializedName("name")
    private final String name;

    @SerializedName("description")
    private final String description;

    @SerializedName("authors")
    private final List<String> authors;

    public Mod(String id, String name, String description, List<String> authors) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.authors = authors;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String toLine() {
        return
            "Mod : " + getName() + "\n" +
            " - ID : " + getId() + "\n" +
            " - Description : " + getDescription();
    }
}

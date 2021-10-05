package es.neifi.myfinance.shared.domain;

import com.google.common.collect.ImmutableMap;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadContent extends File {

    private String id;
    private String uploadedOn;
    private String name;

    private final Map<String, Object> metadata = new HashMap<>();


    public UploadContent(String pathname) {
        super(pathname);
    }


    public void putMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }

    public UploadContent withId(String id) {
        this.id = id;
        putMetadata("id", id);
        return this;
    }

    public UploadContent withUploadedOn(String uploadedOn) {
        this.uploadedOn = uploadedOn;
        putMetadata("uploadedOn", uploadedOn);
        return this;
    }

    public UploadContent withName(String name) {
        this.name = name;
        putMetadata("name", name);
        return this;
    }

    public String id() {
        return id;
    }

    public String uploadedOn() {
        return uploadedOn;
    }

    public String name() {
        return name;
    }

    public ImmutableMap<String, Object> getMetadata() {
        if (isValidContent()) {

            return ImmutableMap.copyOf(metadata);
        }

        throw new IllegalArgumentException("Ensure metadata is not empty, has id, uploadedOn or name as keys.");
    }

    private boolean isValidContent() {
        return !metadata.isEmpty()
                && metadata.containsKey("id")
                && metadata.containsKey("uploadedOn")
                && metadata.containsKey("name");
    }
}

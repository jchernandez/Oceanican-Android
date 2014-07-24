package com.oceanican.android.facebook;

import java.io.Serializable;

public class FacebookItem implements Serializable {

    private String title;
    private String alternet;
    private String updated;
    private String content;
    String fbPicture ="https://graph.facebook.com/oceanican/picture?type=large";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlternet() {
        return alternet;
    }

    public void setAlternet(String alternet) {
        this.alternet = alternet;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

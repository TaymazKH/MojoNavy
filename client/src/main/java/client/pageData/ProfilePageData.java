package client.pageData;

import client.util.PageType;
import shared.model.User;

public class ProfilePageData implements PageData {
    private final User user;

    public ProfilePageData(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public PageType getPageType() {
        return PageType.profile;
    }
}

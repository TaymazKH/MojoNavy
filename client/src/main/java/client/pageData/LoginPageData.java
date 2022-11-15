package client.pageData;

import client.util.PageType;

public class LoginPageData implements PageData {
    private final int message;

    public LoginPageData(int message) {
        this.message = message;
    }

    public int getMessage() {
        return message;
    }

    @Override
    public PageType getPageType() {
        return PageType.login;
    }
}

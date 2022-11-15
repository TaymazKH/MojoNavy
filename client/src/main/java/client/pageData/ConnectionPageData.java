package client.pageData;

import client.util.PageType;

public class ConnectionPageData implements PageData {
    @Override
    public PageType getPageType() {
        return PageType.connection;
    }
}

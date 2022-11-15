package shared.request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.response.Response;

@JsonTypeName("clickRequest")
public class ClickRequest extends Request {
    private int x,y;

    public ClickRequest(){}
    public ClickRequest(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleClickRequest(this);
    }
}

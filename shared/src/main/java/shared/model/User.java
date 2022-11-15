package shared.model;

import java.util.Objects;

public class User {
    private String username,password;
    private long id;
    private int winCount,loseCount;

    public User(){}
    public User(String username, String password, long id) {
        this.username = username;
        this.password = password;
        this.id = id;
        winCount=0;
        loseCount=0;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public long getId() {
        return id;
    }
    public int getWinCount() {
        return winCount;
    }
    public int getLoseCount() {
        return loseCount;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }
    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

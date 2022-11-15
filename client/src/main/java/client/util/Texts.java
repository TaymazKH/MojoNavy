package client.util;

public class Texts {
    private static final Config config = Config.getConfig("texts");
    private static final String
            noAccount = config.getProperty(String.class,"noAccount"),
            wrongPassword = config.getProperty(String.class,"wrongPassword"),
            alreadyLoggedIn = config.getProperty(String.class, "alreadyLoggedIn"),
            usernameTaken = config.getProperty(String.class, "usernameTaken"),
            boardSetup = config.getProperty(String.class, "boardSetup"),
            ready = config.getProperty(String.class, "ready"),
            yourTurn = config.getProperty(String.class, "yourTurn"),
            opponentsTurn = config.getProperty(String.class, "opponentsTurn"),
            youWon = config.getProperty(String.class, "youWon"),
            youLost = config.getProperty(String.class, "youLost"),
            joinedWhenEnded = config.getProperty(String.class, "joinedWhenEnded"),
            firstsTurn = config.getProperty(String.class, "firstsTurn"),
            secondsTurn = config.getProperty(String.class, "secondsTurn"),
            firstWon = config.getProperty(String.class, "firstWon"),
            secondWon = config.getProperty(String.class, "secondWon"),
            wrongInputHeader = config.getProperty(String.class, "wrongInputHeader"),
            loadingProblemHeader = config.getProperty(String.class, "loadingProblemHeader"),
            CouldNotLoadProfile = config.getProperty(String.class, "couldNotLoadProfile"),
            playerTitle = config.getProperty(String.class, "playerTitle"),
            totalPointsTitle = config.getProperty(String.class, "totalPointsTitle"),
            winsTitle = config.getProperty(String.class, "winsTitle"),
            defeatsTitle = config.getProperty(String.class, "defeatsTitle"),
            opponentTitle = config.getProperty(String.class, "opponentTitle"),
            emptyPlayerName = config.getProperty(String.class, "emptyPlayerName");

    public static Config getConfig() {
        return config;
    }

    public static String getNoAccount() {
        return noAccount;
    }

    public static String getWrongPassword() {
        return wrongPassword;
    }

    public static String getAlreadyLoggedIn() {
        return alreadyLoggedIn;
    }

    public static String getUsernameTaken() {
        return usernameTaken;
    }

    public static String getBoardSetup() {
        return boardSetup;
    }

    public static String getReady() {
        return ready;
    }

    public static String getYourTurn() {
        return yourTurn;
    }

    public static String getOpponentsTurn() {
        return opponentsTurn;
    }

    public static String getYouWon() {
        return youWon;
    }

    public static String getYouLost() {
        return youLost;
    }

    public static String getJoinedWhenEnded() {
        return joinedWhenEnded;
    }

    public static String getFirstsTurn() {
        return firstsTurn;
    }

    public static String getSecondsTurn() {
        return secondsTurn;
    }

    public static String getFirstWon() {
        return firstWon;
    }

    public static String getSecondWon() {
        return secondWon;
    }

    public static String getWrongInputHeader() {
        return wrongInputHeader;
    }

    public static String getLoadingProblemHeader() {
        return loadingProblemHeader;
    }

    public static String getCouldNotLoadProfile() {
        return CouldNotLoadProfile;
    }

    public static String getPlayerTitle() {
        return playerTitle;
    }

    public static String getTotalPointsTitle() {
        return totalPointsTitle;
    }

    public static String getWinsTitle() {
        return winsTitle;
    }

    public static String getDefeatsTitle() {
        return defeatsTitle;
    }

    public static String getOpponentTitle() {
        return opponentTitle;
    }

    public static String getEmptyPlayerName() {
        return emptyPlayerName;
    }
}

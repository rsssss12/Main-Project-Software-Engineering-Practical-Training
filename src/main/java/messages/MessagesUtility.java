package messages;

import gamelogic.Player;

import java.util.List;

/**
 * The class MessagesUtility contains methods to customize the messages
 */
public class MessagesUtility {

    /**
     * The method customizes the username in the message
     *
     * @param clientID : int ID of client
     * @param players  : List with all Players
     * @param message  : String message to customize
     * @return String : customized message
     */
    public static String replaceUsername(int clientID, List<Player> players, String message) {
        StringBuilder username = new StringBuilder();

        for (Player player : players) {
            if (clientID == player.getClientID()) {
                username.append(player.getUsername());
                username.append(" (").append(player.getClientID()).append(")");
                break;
            }
        }

        return message.replace("$$username", username.toString());
    }

    /**
     * The method customizes the number in the message
     *
     * @param number  : int number
     * @param message : String message to customize
     * @return String : customized message
     */
    public static String replaceNumber(int number, String message) {
        String numberString = Integer.toString(number);

        return message.replace("$$number", numberString);
    }

    /**
     * The method customizes the card in the message
     *
     * @param card    : String card
     * @param message : String message to customize
     * @return String : customized message
     */
    public static String replaceCard(String card, String message) {


        return message.replace("$$card", card);
    }

    /**
     * The method customizes the position in the message
     *
     * @param x       : int x coordinate
     * @param y       : int y coordinate
     * @param message : String message to customize
     * @return String : customized message
     */
    public static String replacePosition(int x, int y, String message) {
        String xString = Integer.toString(x);
        String yString = Integer.toString(y);

        String position = message.replace("$$x", xString);
        return position.replace("$$y", yString);
    }

    /**
     * The method customizes the card list in the message
     *
     * @param cards   : List<String> cards
     * @param message : String message to customize
     * @return String : customized message
     */
    public static String replaceCards(List<String> cards, String message) {
        String toString = cards.toString();
        String cardsString = toString.substring(1, toString.length() - 1);

        return message.replace("$$cards", cardsString);
    }

    /**
     * The method customizes the usernames in the message
     *
     * @param clientIDs : List<Integer> IDs of clients
     * @param players   : List<Player> players
     * @param message   : String message to customize
     * @return String : customized message
     */
    public static String replaceUsernames(List<Integer> clientIDs, List<Player> players, String message) {
        StringBuilder usernames = new StringBuilder();


        for (Player player : players) {
            if (clientIDs.contains(player.getClientID())) {
                usernames.append(player.getUsername());
                usernames.append(" (").append(player.getClientID()).append(")");

                if (players.indexOf(player) == players.size() - 2) {
                    usernames.append(" and ");
                } else if (players.indexOf(player) == players.size() - 1) {
//                    usernames.append(" ");
                } else {
                    usernames.append(", ");
                }

            }
        }
        return message.replace("$$usernames", usernames.toString());
    }


}

package com.buckshot.Manager;

import com.buckshot.Core.User;

import java.util.ArrayList;

public class AsciiArt {
    public static String start = "--------------------------------------------------------------------------------\n" +
            "                                                                                \n" +
            "              ✧･ﾟ: *✧･ﾟ:* 　Let's play Buckshot Roulette!　 *:･ﾟ✧*:･ﾟ✧       \n" +
            "                                                                                \n" +
            "                                %%%%%%%%%%%%%                                   \n" +
            "                              %%%% %*+++++*%%*+-=+%                             \n" +
            "                           %**      ***+++**%%%%**++*                           \n" +
            "                          *+*       **+-=+*%       %++%                         \n" +
            "                          **%       *=--=**%        %%+%                        \n" +
            "                         *++**%%%%*+-----+**%%%%   %*%**                        \n" +
            "%%                       ***++++=--==---=++**++++++**++*                        \n" +
            "%%%%                       %%%%%%%%**++++++++++*%%* %%**%                       \n" +
            "%%%%%%                  %% %%%%*%**% %****%%  %***% %%%*                   %%%%%\n" +
            "**%%%%                   %% %%% %%*++*%%%%%**%%*% %    *                   %%%%*\n" +
            "*%*%%%                   %% %*%   %*******+**%  % %%    %               %%%%%%**\n" +
            "*%*%%                      %++*         %          %    %               %%%%%%**\n" +
            "+**%%                      %%** %%  % %               %                  %%%%%%%\n" +
            "+*%%%                        % %%%  *%%%           %%                       %%%%\n" +
            "**%%                                %%%% %    %*==                        %%%%%%\n" +
            "%%%                                          *=+--%%%++******%             %%%%%\n" +
            "%%%                         %*++%%%%%%%%****===+-=%*++**+++%                 %%%\n" +
            "%%%%%%%%                 %*+++******+*%***+=+*++==%***++***%               %    \n" +
            "*****%%%%               +===+******%*%%%%%*+=***+=+*+=++%                 %%   %\n" +
            "******%%%%%%%%%    %%  *--=-=*%%%%         **+***%+==+%*+%%%%%%             %%%%\n" +
            "************%%%%%%%%%  *=-==+*% %%%%%       %%%%%     %%*%%%*%%%%%%       %%%%%%\n" +
            "***************%%%%*%   %*****%**%%%%%%%%            %%%%%%***%%%%%%%  %%%%%%%%%\n" +
            "                                                                                \n" +
            "                                                                                \n" +
            "--------------------------------------------------------------------------------\n";

    public static void printCenteredStringPretty(String input, int margin) {
        int totalWidth = 80;  // 전체 너비
        String base = "*﹥   %s   ﹤*";
        String formatted = String.format(base, input);

        int padding = (totalWidth - formatted.length()) / 2 - margin;
        String output = " ".repeat(Math.max(0, padding)) + formatted;  // 중앙 정렬

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(output);
        System.out.println("--------------------------------------------------------------------------------");
    }

    public static void printCenteredString(String input, int margin) {
        int totalWidth = 80;
        int padding = (totalWidth-input.length()) / 2 - margin;  // 패딩 계산
        String output = " ".repeat(padding) +input;
        System.out.print(output);
    }

    public static String printState(GameManager gm){
        String base =
                "--------------------------------------------------------------------------------\n" +
                "player 1                                                               player 2\n" +
                "--------------------------------------------------------------------------------\n" +
                "|  1           |  2           |                  |  1           |  2           |\n" +
                "|  %s      |  %s      |                  |  %s      |  %s      |\n" +
                "--------------------------------------------------------------------------------\n" +
                "|  3           |  4           |                  |  3           |  4           |\n" +
                "|  %s      |  %s      |                  |  %s      |  %s      |\n" +
                "--------------------------------------------------------------------------------\n" +
                "%s                                                                    %s\n";
        String formatted = getGameInfo(gm, base);
        System.out.println(formatted);
        return formatted;
    }

    private static String getGameInfo(GameManager gm, String base) {
        User p1 = gm.getP1();
        User p2 = gm.getP2();
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i< 4; i++){
            if(i>= p1.getItems().size()){
                items.add("      ");
            }else {
                items.add(p1.getItems().get(i).getName());
            }
        }
        for (int i = 0; i< 4; i++){
            if(i>= p2.getItems().size()){
                items.add("      ");
            }else {
                items.add(p2.getItems().get(i).getName());
            }
        }
        String p1Health = "";
        String p2Health = "";
        for (int i = 0; i<6; i++){
            if(i< p1.getHealth()){
                p1Health +="◼";
            }else{
                p1Health += "□";
            }
        }
        for (int i = 0; i<6; i++){
            if(i<6- p2.getHealth()){
                p2Health +="□";
            }else{
                p2Health += "◼";
            }
        }

        String formatted = String.format(base, items.get(0), items.get(1), items.get(4),items.get(5), items.get(2), items.get(3), items.get(6), items.get(7), p1Health, p2Health);
        return formatted;
    }

    public static void printBang(){
        String bang = "                                   ____  \",\n" +
                "                                __,-~~/~    `---.\",\n" +
                "                              _/_,---(      ,    )\",\n" +
                "                          __ /        <    /   )  \\\\___\",\n" +
                "             - ------===;;;'====------------------===;;;===----- -  -\",\n" +
                "                           \\\\/  ~\\\"~\\\"~\\\"~\\\"~\\\"~\\\\~\\\"~)~\\\"/\",\n" +
                "                           (_ (   \\\\  (     >    \\\\)\",\n" +
                "                            \\\\_( _ <         >_>'\",\n" +
                "                               ~ `-i' ::>|--\\\"\",\n" +
                "                                   I;|.|.|\",\n" +
                "                                  <|i::|i|`.\",\n" +
                "                                 (` ^'\\\"`-' \\\")\",\n" +
                "                                    B A N G !!!\n";
        System.out.println(bang);
    }

    public static void printDot(){
        for (int i = 3; i >0; i--) {
            System.out.print(".\n"); // "." 출력
            sleepMillis(800);
        }
    }

    public static void sleepMillis(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            return;
        }
    }
}

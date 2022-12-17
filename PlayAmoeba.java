package main;

import java.util.Random;
import java.util.Scanner;

public class PlayAmoeba {
    final static int HEIGHT = 11;
    final static int WIDTH = 25;
    final static String SPACE = " ";


    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {


            String[][] level = new String[HEIGHT][WIDTH];
            String pcPlayer = "O";
            String player = "X";
            boolean gameOver;
            boolean reMatch = true;
            int round = 0;

            wellcomeText();   //Üdvözlőszöveg
            while (reMatch) { // addig újrajátszuk, amíg akar a játékos játszani

                initLevel(level);  // pálya létrehozása
                draw(level, round);//pálya rajzolása
                round++;
                boolean playerIsFirst = playerIsFirst(scanner); //Ki lesz a kezdő játékos?

                if (playerIsFirst) { //ha a játékos kezd

                    do { //addig fut, amíg nincs vége a játéknak
                        player(level, scanner, player, pcPlayer); //játékos lépése
                        //pc(level, pcPlayer, player); //abban az esetbe, ha két gép játszik
                        draw(level, round);  //lépés megrajzolása
                        gameOver = control(level, player, pcPlayer, round); // ellenőrzése annak, hogy a játékos nyert-e
                        if (gameOver) {
                            break;
                        }
                        pc(level, player, pcPlayer); // gép lépése
                        draw(level, round);  //lépés megrajzolása
                        gameOver = control(level, player, pcPlayer, round); // ellenőrzése annak, hogy a gép nyert-e
                        round++;
                    } while (!gameOver);

                } else {  // ha a gép kezd

                    do { //addig fut, amíg nincs vége a játéknak
                        pc(level, player, pcPlayer); // gép lépése
                        draw(level, round); //lépés megrajzolása
                        gameOver = control(level, player, pcPlayer, round); // ellenőrzése annak, hogy a gép nyert-e
                        if (gameOver) {
                            break;
                        }
                        player(level, scanner, player, pcPlayer); //játékos lépése
                        draw(level, round); //lépés megrajzolása
                        gameOver = control(level, player, pcPlayer, round); // ellenőrzése annak, hogy a játékos nyert-e
                        round++;
                    } while (!gameOver);
                }

                if (gameOver) { //Akar a játékos következő menetet?
                    round = 0;
                    reMatch = askRematch(scanner);
                }
            }
        }
    }


    //Pálya létrehozása
    private static void initLevel(String[][] level) {

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                level[i][j] = SPACE;
            }
        }
    }

    //Üdvözlő szöveg
    private static void wellcomeText() {
        System.out.println();
        System.out.println("Kedves játékos!");
        System.out.println();
        System.out.println("Üdvözöllek az amőba alkalmazásban! A te karaktered az X, a számítógépé az O");
        System.out.println("A pálya " + WIDTH + " egység széles és " + HEIGHT + " egység magas. Kérlek ezen tartományon belüli értékeket");
        System.out.println("adj meg a játék során. A játékot az nyeri, akinek először sikerül leraknia egymás");
        System.out.println("alá, mellé vagy átlósan 5 ugyanolyan karaktert.");
        System.out.println();
    }

    private static boolean playerIsFirst(Scanner scanner) {
        boolean playerIsFirst = true;
        System.out.print("Szeretnéd a játékot kezdeni?  (i/n)  ");
        String yesOrNo = scanner.next();
        if (yesOrNo.equals("i")) {
            return playerIsFirst = true;
        } else if (yesOrNo.equals("n")) {
            return playerIsFirst = false;
        } else {
            System.out.println("Értelmezhetetlen választ adtál meg!");
            playerIsFirst(scanner);
        }

        return playerIsFirst;
    }

    private static boolean askRematch(Scanner scanner) {
        boolean reMatch = true;
        System.out.print("Szeretnéd a játékot megismételni?  (i/n)  ");
        String yesOrNo = scanner.next();
        if (yesOrNo.equals("i")) {
            System.out.println();
            System.out.println("Induljon hát a következő kör!");
            System.out.println();
            return reMatch = true;
        } else if (yesOrNo.equals("n")) {
            System.out.println();
            System.out.println("Viszlát!");
            return reMatch = false;


        } else {
            System.out.println("Értelmezhetetlen választ adtál meg!");
            askRematch(scanner);
        }

        return reMatch;
    }

    //Játékos lépése
    private static void player(String[][] level, Scanner scanner, String player, String pcPlayer) {
        System.out.println();
        int horizontal;
        int vertical;
        do {

            do {
                System.out.print("Kérem a lépés vízszintes koordinátáját: ");
                horizontal = scanner.nextInt();
                if (!(0 < horizontal && horizontal <= WIDTH)) {
                    System.out.println();
                    System.out.println("A választott vízszitnes koordináta nagyobb mint a pálya szélessége");
                    System.out.println("Kérem adjon meg egy megfelelő víszszintes koordinátát!");
                    System.out.println();
                }
            } while (!(0 < horizontal && horizontal <= WIDTH));

            do {
                System.out.print("Kérem a lépés fügőleges koordinátáját: ");
                vertical = scanner.nextInt();
                if (!(0 < vertical && vertical <= HEIGHT)) {
                    System.out.println();
                    System.out.println("A választott fügőleges koordináta nagyobb mint a pálya hossza");
                    System.out.println("Kérem adjon meg egy megfelelő függőleges koordinátát!");
                    System.out.println();
                }
            } while (!(0 < vertical && vertical <= HEIGHT));

            System.out.println();
            if (level[vertical - 1][horizontal - 1].equals(player) || level[vertical - 1][horizontal - 1].equals(pcPlayer)) {
                System.out.println("Ez a koordináta már létezik a játékban!");
                System.out.println("Adjon meg egy helyes koordinátát!");
            }
        } while (level[vertical - 1][horizontal - 1].equals(player) || level[vertical - 1][horizontal - 1].equals(pcPlayer));
        level[vertical - 1][horizontal - 1] = player;
    }

    //Pálya felrajzolása
    private static void draw(String[][] level, int round) {
        for (int i = 0; i < 10; i++) {
            System.out.print(" ");
            System.out.print(" " + (i+1) + " ");
        }
        for (int i = 10; i < WIDTH; i++) {
            System.out.print(" " + (i+1) + " ");
        }
        System.out.println();
        for (int i = 0; i < (WIDTH); i++) {
            System.out.print("+---");
        }
        System.out.println("+");
        for (int i = 0; i < HEIGHT; i++) {
            System.out.print("| ");
            for (int j = 0; j < WIDTH; j++) {


                System.out.print(level[i][j]);
                System.out.print(" | ");
            }
            System.out.print(" " + (i + 1));
            System.out.println();
            for (int j = 0; j < (WIDTH); j++) {
                System.out.print("+---");
            }

            System.out.println("+");
        }


        System.out.println();
        System.out.println("----------      " + (round) + ".     ----------");
        System.out.println();
    }

    //Annak ellenőrzése, hogy valaki nyert-e
    private static boolean control(String[][] level, String player, String pcPlayer, int round) {
        boolean gameOver = true;
        for (int i = 0; i < HEIGHT - 4; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j] && level[i][j] == level[i + 3][j] && level[i][j] == level[i + 4][j] && level[i][j].equals(player)) {
                    draw(level, round);
                    System.out.println("A játékos nyert!");
                    return gameOver = true;
                } else if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j] && level[i][j] == level[i + 3][j] && level[i][j] == level[i + 4][j] && level[i][j].equals(pcPlayer)) {
                    System.out.println("A számítógép nyert!");
                    return gameOver = true;
                } else {
                    gameOver = false;
                }
            }
        }

        for (int i = 0; i < HEIGHT - 4; i++) {
            for (int j = 0; j < WIDTH - 4; j++) {
                if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 2][j + 2] && level[i][j] == level[i + 3][j + 3] && level[i][j] == level[i + 4][j + 4] && level[i][j].equals(player)) {
                    draw(level, round);
                    System.out.println("A játékos nyert!");
                    return gameOver = true;
                } else if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 2][j + 2] && level[i][j] == level[i + 3][j + 3] && level[i][j] == level[i + 4][j + 4] && level[i][j].equals(pcPlayer)) {
                    System.out.println("A számítógép nyert!");
                    return gameOver = true;
                } else {
                    gameOver = false;
                }
            }
        }

        for (int i = 0; i < HEIGHT - 4; i++) {
            for (int j = 4; j < WIDTH; j++) {
                if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 2][j - 2] && level[i][j] == level[i + 3][j - 3] && level[i][j] == level[i + 4][j - 4] && level[i][j].equals(player)) {
                    draw(level, round);
                    System.out.println("A játékos nyert!");
                    return gameOver = true;
                } else if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 2][j - 2] && level[i][j] == level[i + 3][j - 3] && level[i][j] == level[i + 4][j - 4] && level[i][j].equals(pcPlayer)) {
                    System.out.println("A számítógép nyert!");
                    return gameOver = true;
                } else {
                    gameOver = false;
                }
            }
        }

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH - 4; j++) {
                if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 2] && level[i][j] == level[i][j + 3] && level[i][j] == level[i][j + 4] && level[i][j].equals(player)) {
                    draw(level, round);
                    System.out.println("A játékos nyert!");
                    return gameOver = true;
                } else if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 2] && level[i][j] == level[i][j + 3] && level[i][j] == level[i][j + 4] && level[i][j].equals(pcPlayer)) {
                    System.out.println("A számítógép nyert!");
                    return gameOver = true;
                } else {
                    gameOver = false;
                }
            }
        }
        return gameOver;
    }

    // számítógép lépése
    private static void pc(String[][] level, String player, String pcPlayer) {

        boolean pcHasNotSteppedYet = true;


        pcHasNotSteppedYet = fourSameCharacter(level, pcPlayer, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = fourSameCharacter(level, player, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = threeSameChareacterAndNotClosed(level, pcPlayer, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = threeSameCharacter(level, pcPlayer, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = threeSameChareacterAndNotClosed(level, player, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = twiceTwoSameCharacterDanger(level, pcPlayer, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = twiceTwoSameCharacterDanger(level, player, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = twoSameCharacterAndNotClosed(level, pcPlayer, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = threeSameCharacter(level, player, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = twoSameCharacter(level, pcPlayer, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = twoSameCharacterAndNotClosed(level, player, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = onlyOneCharacter(level, pcPlayer, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = twoSameCharacter(level, player, pcHasNotSteppedYet, player, pcPlayer);
        pcHasNotSteppedYet = onlyOneCharacter(level, player, pcHasNotSteppedYet, player, pcPlayer);
        if (pcHasNotSteppedYet) {
            randomstep(level, player, pcPlayer);
        }
    }


    private static boolean fourSameCharacter(String[][] level, String testingPlayer, boolean pcHasNotSteppedYet, String player, String pcPlayer) {

        if (pcHasNotSteppedYet) {
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {

                    //--------függőlegesen 4 egymás alatt
                    if (pcHasNotSteppedYet && i < HEIGHT - 4) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j] && level[i][j] == level[i + 3][j] && level[i][j].equals(testingPlayer) && level[i + 4][j].equals(SPACE)) {
                            level[i + 4][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < HEIGHT - 3 && 0 < i) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j] && level[i][j] == level[i + 3][j] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    // Függőlegesen 5 egymás alatt, de az egyik hiányzik
                    if (pcHasNotSteppedYet && i < HEIGHT - 5) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 3][j] && level[i][j].equals(testingPlayer) && level[i + 4][j] == level[i][j] && level[i + 2][j].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < HEIGHT - 5) {
                        if (level[i][j] == level[i + 2][j] && level[i][j] == level[i + 3][j] && level[i][j].equals(testingPlayer) && level[i + 4][j] == level[i][j] && level[i + 1][j].equals(SPACE)) {
                            level[i + 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < HEIGHT - 5) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j] && level[i][j].equals(testingPlayer) && level[i + 4][j] == level[i][j] && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //--------vízszintesen 4 egymás mellett
                    if (pcHasNotSteppedYet && j < WIDTH - 4) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 2] && level[i][j] == level[i][j + 3] && level[i][j].equals(testingPlayer) && level[i][j + 4].equals(SPACE)) {
                            level[i][j + 4] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && j < WIDTH - 3 && 0 < j) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 2] && level[i][j] == level[i][j + 3] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE)) {
                            level[i][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    // Vízszintesen 5 egymás mellett, de az egyik hiányzik
                    if (pcHasNotSteppedYet && j < WIDTH - 5) {
                        if (level[i][j] == level[i][j + 2] && level[i][j] == level[i][j + 3] && level[i][j].equals(testingPlayer) && level[i][j] == level[i][j + 4] && level[i][j + 1].equals(SPACE)) {
                            level[i][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && j < WIDTH - 5) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 3] && level[i][j].equals(testingPlayer) && level[i][j] == level[i][j + 4] && level[i][j + 2].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && j < WIDTH - 5) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 2] && level[i][j].equals(testingPlayer) && level[i][j] == level[i][j + 4] && level[i][j + 3].equals(SPACE)) {
                            level[i][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //--------Atlósan jobbra lefele 4 egymas mellett
                    if (pcHasNotSteppedYet && i < HEIGHT - 4 && j < WIDTH - 4) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 2][j + 2] && level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i + 4][j + 4].equals(SPACE)) {
                            level[i + 4][j + 4] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < i && 0 < j && i < HEIGHT - 3 && j < WIDTH - 3) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 2][j + 2] && level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i - 1][j - 1].equals(SPACE)) {
                            level[i - 1][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    // Atlósan jobbra 5 egymás alatt, de az egyik hiányzik
                    if (pcHasNotSteppedYet && i < HEIGHT - 5 && j < WIDTH - 5) {
                        if (level[i][j] == level[i + 2][j + 2] && level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i][j] == level[i + 4][j + 4] && level[i + 1][j + 1].equals(SPACE)) {
                            level[i + 1][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < HEIGHT - 5 && j < WIDTH - 5) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i][j] == level[i + 4][j + 4] && level[i + 2][j + 2].equals(SPACE)) {
                            level[i + 2][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < HEIGHT - 5 && j < WIDTH - 5) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 2][j + 2] && level[i][j].equals(testingPlayer) && level[i][j] == level[i + 4][j + 4] && level[i + 3][j + 3].equals(SPACE)) {
                            level[i + 3][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //-------Atlósan balra lefelé 4 egymás mellett
                    if (pcHasNotSteppedYet && i < HEIGHT - 4 && 3 < j) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 2][j - 2] && level[i][j] == level[i + 3][j - 3] && level[i][j].equals(testingPlayer) && level[i + 4][j - 4].equals(SPACE)) {
                            level[i + 4][j - 4] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 3 && 2 < j && j < WIDTH - 1) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 2][j - 2] && level[i][j] == level[i + 3][j - 3] && level[i][j].equals(testingPlayer) && level[i - 1][j + 1].equals(SPACE)) {
                            level[i - 1][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    // Atlósan balra 5 egymás alatt, de az egyik hiányzik
                    if (pcHasNotSteppedYet && i < HEIGHT - 5 && 4 < j) {
                        if (level[i][j] == level[i + 2][j - 2] && level[i][j] == level[i + 3][j - 3] && level[i][j].equals(testingPlayer) && level[i][j] == level[i + 4][j - 4] && level[i + 1][j - 1].equals(SPACE)) {
                            level[i + 1][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < HEIGHT - 5 && 4 < j) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 3][j - 3] && level[i][j].equals(testingPlayer) && level[i][j] == level[i + 4][j - 4] && level[i + 2][j - 2].equals(SPACE)) {
                            level[i + 2][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < HEIGHT - 5 && 4 < j) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 2][j - 2] && level[i][j].equals(testingPlayer) && level[i][j] == level[i + 4][j - 4] && level[i + 3][j - 3].equals(SPACE)) {
                            level[i + 3][j - 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                }
            }
            if (!pcHasNotSteppedYet && testingPlayer.equals(player)) {
                System.out.println("A gép 4 azonos karaktert talált egymás mellett, ezért lezárta azt.");
            } else if (!pcHasNotSteppedYet && testingPlayer.equals(pcPlayer)) {
                System.out.println("A gép 4 egymás melletti karakterét továbbépítette és ezzel nyert.");
            }
        }
        return pcHasNotSteppedYet;
    }

    private static boolean threeSameChareacterAndNotClosed(String[][] level, String testingPlayer, boolean pcHasNotSteppedYet, String player, String pcPlayer) {
        Random random = new Random();
        int nullOrOne;
        //--------függőlegesen 3 egymás alatt lezáratlanul
        if (pcHasNotSteppedYet) {
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    //--------függőlegesen 3 egymás alatt lezáratlanul
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 3) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j] && level[i][j].equals(testingPlayer) && level[i + 3][j].equals(SPACE) && level[i - 1][j].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i + 3][j] = pcPlayer;
                            } else {
                                level[i - 1][j] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //--------vízszintesen 3 egymás mellett lezáratlanul
                    if (pcHasNotSteppedYet && 0 < j && j < WIDTH - 3) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 2] && level[i][j].equals(testingPlayer) && level[i][j + 3].equals(SPACE) && level[i][j - 1].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i][j + 3] = pcPlayer;
                            } else {
                                level[i][j - 1] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //-----Atlósan jobbra lefelé 3 egymás mellett lezáratlanul
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 3 && 0 < j && j < WIDTH - 3) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 2][j + 2] && level[i][j].equals(testingPlayer) && level[i + 3][j + 3].equals(SPACE) && level[i - 1][j - 1].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i + 3][j + 3] = pcPlayer;
                            } else {
                                level[i - 1][j - 1] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //---Atlósan balra lefelé 3 egymás mellett lezáratlanul
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 3 && 2 < j && j < WIDTH - 1) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 2][j - 2] && level[i][j].equals(testingPlayer) && level[i + 3][j - 3].equals(SPACE) && level[i - 1][j + 1].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i + 3][j - 3] = pcPlayer;
                            } else {
                                level[i - 1][j + 1] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //egymás alatt négy, de köztük az egyik hiányzik és lezárva egyik vége sincs
                    if (pcHasNotSteppedYet && 0 < i && i < (HEIGHT - 4)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 3][j] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i - 1][j].equals(SPACE) && level[i + 4][j].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < i && i < (HEIGHT - 4)) {
                        if (level[i][j] == level[i + 2][j] && level[i][j] == level[i + 3][j] && level[i][j].equals(testingPlayer) && level[i + 1][j].equals(SPACE) && level[i - 1][j].equals(SPACE) && level[i + 4][j].equals(SPACE)) {
                            level[i + 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //egymás mellett négy, de köztük az egyik hiányzik és lezárva egyik vége sincs
                    if (pcHasNotSteppedYet && 0 < j && j < (WIDTH - 4)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 3] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i][j - 1].equals(SPACE) && level[i][j + 4].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < j && j < (WIDTH - 4)) {
                        if (level[i][j] == level[i][j + 2] && level[i][j] == level[i][j + 3] && level[i][j].equals(testingPlayer) && level[i][j + 1].equals(SPACE) && level[i][j - 1].equals(SPACE) && level[i][j + 4].equals(SPACE)) {
                            level[i][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //egymás alatt átlósan jobbra lefele négy, de köztük az egyik hiányzik és lezárva egyik vége sincs
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 4 && 0 < j && j < WIDTH - 4) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE) && level[i + 4][j + 4].equals(SPACE) && level[i - 1][j - 1].equals(SPACE)) {
                            level[i + 2][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 4 && 0 < j && j < WIDTH - 4) {
                        if (level[i][j] == level[i + 2][j + 2] && level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i + 1][j + 1].equals(SPACE) && level[i + 4][j + 4].equals(SPACE) && level[i - 1][j - 1].equals(SPACE)) {
                            level[i + 1][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //egymás alatt átlósan balra lefele négy, de köztük az egyik hiányzik és lezárva egyik vége sincs
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 4 && 3 < j && j < WIDTH - 1) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 3][j - 3] && level[i][j].equals(testingPlayer) && level[i + 2][j - 2].equals(SPACE) && level[i + 4][j - 4].equals(SPACE) && level[i - 1][j + 1].equals(SPACE)) {
                            level[i + 2][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 4 && 3 < j && j < WIDTH - 1) {
                        if (level[i][j] == level[i + 2][j - 2] && level[i][j] == level[i + 3][j - 3] && level[i][j].equals(testingPlayer) && level[i + 1][j - 1].equals(SPACE) && level[i + 4][j - 4].equals(SPACE) && level[i - 1][j + 1].equals(SPACE)) {
                            level[i + 1][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                }
            }
            if (!pcHasNotSteppedYet && testingPlayer.equals(player)) {
                System.out.println("A gép 3 azonos karaktert talált egymás mellett, ami egyik oldalról sincs lezárva ezért lezárta azt.");
            } else if (!pcHasNotSteppedYet && testingPlayer.equals(pcPlayer)) {
                System.out.println("A gép 3 egymás melletti egyik oldalról sem zárt karakterét továbbépítette.");
            }
        }
        return pcHasNotSteppedYet;
    }

    private static boolean threeSameCharacter(String[][] level, String testingPlayer, boolean pcHasNotSteppedYet, String player, String pcPlayer) {

        if (pcHasNotSteppedYet) {
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {

                    //--------függőlegesen 3 egymás alatt
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j] && level[i][j].equals(testingPlayer) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < i && i < (HEIGHT - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //egymás alatt négy, de köztük az egyik hiányzik
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 3][j] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3)) {
                        if (level[i][j] == level[i + 2][j] && level[i][j] == level[i + 3][j] && level[i][j].equals(testingPlayer) && level[i + 1][j].equals(SPACE)) {
                            level[i + 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    //--------vízszintesen 3 egymás mellett

                    if (pcHasNotSteppedYet && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 2] && level[i][j].equals(testingPlayer) && level[i][j + 3].equals(SPACE)) {
                            level[i][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < j && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 2] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE)) {
                            level[i][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    //egymás mellett négy, de köztük az egyik hiányzik
                    if (pcHasNotSteppedYet && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i][j + 3] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i][j + 2] && level[i][j] == level[i][j + 3] && level[i][j].equals(testingPlayer) && level[i][j + 1].equals(SPACE)) {
                            level[i][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //---------Atlósan jobbra lefelé 3 egymás mellett
                    if (pcHasNotSteppedYet && i < HEIGHT - 3 && j < WIDTH - 3) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 2][j + 2] && level[i][j].equals(testingPlayer) && level[i + 3][j + 3].equals(SPACE)) {
                            level[i + 3][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    if (pcHasNotSteppedYet && 0 < i && 0 < j && i < HEIGHT - 2 && j < WIDTH - 2) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 2][j + 2] && level[i][j].equals(testingPlayer) && level[i - 1][j - 1].equals(SPACE)) {
                            level[i - 1][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //egymás alatt átlósan jobbra lefele négy, de köztük az egyik hiányzik
                    if (pcHasNotSteppedYet && j < WIDTH - 3 && i < HEIGHT - 3) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE)) {
                            level[i + 2][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && j < WIDTH - 3 && i < HEIGHT - 3) {
                        if (level[i][j] == level[i + 2][j + 2] && level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i + 1][j + 1].equals(SPACE)) {
                            level[i + 1][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //-------Atlósan balra lefelé 3 egymás mellett
                    if (pcHasNotSteppedYet && i < HEIGHT - 3 && 2 < j) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 2][j - 2] && level[i][j].equals(testingPlayer) && level[i + 3][j - 3].equals(SPACE)) {
                            level[i + 3][j - 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 2 && 1 < j && j < WIDTH - 1) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 2][j - 2] && level[i][j].equals(testingPlayer) && level[i - 1][j + 1].equals(SPACE)) {
                            level[i - 1][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //egymás alatt átlósan balra lefele négy, de köztük az egyik hiányzik
                    if (pcHasNotSteppedYet && i < HEIGHT - 3 && 2 < j) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 3][j - 3] && level[i][j].equals(testingPlayer) && level[i + 2][j - 2].equals(SPACE)) {
                            level[i + 2][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < HEIGHT - 3 && 2 < j) {
                        if (level[i][j] == level[i + 2][j - 2] && level[i][j] == level[i + 3][j - 3] && level[i][j].equals(testingPlayer) && level[i + 1][j - 1].equals(SPACE)) {
                            level[i + 1][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                }
            }
            if (!pcHasNotSteppedYet && testingPlayer.equals(player)) {
                System.out.println("A gép 3 azonos karaktert talált egymás mellett, ezért lezárta azt.");
            } else if (!pcHasNotSteppedYet && testingPlayer.equals(pcPlayer)) {
                System.out.println("A gép 3 egymás melletti karakterét továbbépítette.");
            }
        }
        return pcHasNotSteppedYet;
    }

    private static boolean twoSameCharacterAndNotClosed(String[][] level, String testingPlayer, boolean pcHasNotSteppedYet, String player, String pcPlayer) {
        Random random = new Random();
        int nullOrOne;
        if (pcHasNotSteppedYet) {
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {

                    //--------függőlegesen 2 egymás alatt
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 2) {
                        if (level[i][j] == level[i + 1][j] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i - 1][j].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i + 2][j] = pcPlayer;
                            } else {
                                level[i - 1][j] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //--------vízszintesen 2 egymás mellett
                    if (pcHasNotSteppedYet && 0 < j && j < WIDTH - 2) {
                        if (level[i][j] == level[i][j + 1] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i][j - 1].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i][j + 2] = pcPlayer;
                            } else {
                                level[i][j - 1] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //-----Atlósan jobbra lefelé 2 egymás mellett
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 2 && 0 < j && j < WIDTH - 2) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j].equals(testingPlayer) && level[i - 1][j - 1].equals(SPACE) && level[i + 2][j + 2].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i - 1][j - 1] = pcPlayer;
                            } else {
                                level[i + 2][j + 2] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //----Atlósan balra lefelé 2 egymás mellett
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 2 && 1 < j && j < WIDTH - 2) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j].equals(testingPlayer) && level[i - 1][j + 1].equals(SPACE) && level[i + 2][j - 2].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i - 1][j + 1] = pcPlayer;
                            } else {
                                level[i + 2][j - 2] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //--------függőlegesen 3 egymás alatt és közülük az egyik hiányzik
                    if (pcHasNotSteppedYet && 0 < i && i < (HEIGHT - 3)) {
                        if (level[i + 1][j].equals(SPACE) && level[i][j] == level[i + 2][j] && level[i][j].equals(testingPlayer) && level[i + 3][j].equals(SPACE) && level[i - 1][j].equals(SPACE)) {
                            level[i + 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //--------vízszintesen 3 egymás mellett  és közülük az egyik hiányzik
                    if (pcHasNotSteppedYet && 0 < j && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i][j + 2] && level[i][j + 1].equals(SPACE) && level[i][j].equals(testingPlayer) && level[i][j + 3].equals(SPACE) && level[i][j - 1].equals(SPACE)) {
                            level[i][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    //---------Atlósan jobbra lefelé 3 egymás mellett  és közülük az egyik hiányzik
                    if (pcHasNotSteppedYet && 0 < i && 0 < j && i < HEIGHT - 3 && j < WIDTH - 3) {
                        if (level[i + 1][j + 1].equals(SPACE) && level[i][j] == level[i + 2][j + 2] && level[i][j].equals(testingPlayer) && level[i + 3][j + 3].equals(SPACE) && level[i - 1][j - 1].equals(SPACE)) {
                            level[i + 1][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //-------Atlósan balra lefelé 3 egymás mellett  és közülük az egyik hiányzik
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 3 && 2 < j && j < WIDTH - 1) {
                        if (level[i + 1][j - 1].equals(SPACE) && level[i][j] == level[i + 2][j - 2] && level[i][j].equals(testingPlayer) && level[i + 3][j - 3].equals(SPACE) && level[i - 1][j + 1].equals(SPACE)) {
                            level[i + 1][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    //egymás alatt négy, de köztük kettő hiányzik és lezárva egyik vége sincs
                    if (pcHasNotSteppedYet && 0 < i && i < (HEIGHT - 4)) {
                        if (level[i][j] == level[i + 3][j] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i - 1][j].equals(SPACE) && level[i + 4][j].equals(SPACE) && level[i + 1][j].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i + 2][j] = pcPlayer;
                            } else {
                                level[i + 1][j] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }


                    //egymás mellett négy, de köztük kettő hiányzik és lezárva egyik vége sincs
                    if (pcHasNotSteppedYet && 0 < j && j < (WIDTH - 4)) {
                        if (level[i][j] == level[i][j + 3] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i][j - 1].equals(SPACE) && level[i][j + 4].equals(SPACE) && level[i][j + 1].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i][j + 2] = pcPlayer;
                            } else {
                                level[i][j + 1] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }


                    //egymás alatt átlósan jobbra lefele négy, de köztük kettő hiányzik és lezárva egyik vége sincs
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 4 && 0 < j && j < WIDTH - 4) {
                        if (level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE) && level[i + 4][j + 4].equals(SPACE) && level[i - 1][j - 1].equals(SPACE) && level[i + 1][j + 1].equals(SPACE)) {
                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i + 2][j + 2] = pcPlayer;
                            } else {
                                level[i + 1][j + 1] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }


                    //egymás alatt átlósan balra lefele négy, de köztük kettő hiányzik és lezárva egyik vége sincs
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 4 && 3 < j && j < WIDTH - 1) {
                        if (level[i][j] == level[i + 3][j - 3] && level[i][j].equals(testingPlayer) && level[i + 2][j - 2].equals(SPACE) && level[i + 4][j - 4].equals(SPACE) && level[i - 1][j + 1].equals(SPACE) && level[i + 1][j - 1].equals(SPACE)) {

                            nullOrOne = random.nextInt(2);
                            if (nullOrOne == 0) {
                                level[i + 2][j - 2] = pcPlayer;
                            } else {
                                level[i + 1][j - 1] = pcPlayer;
                            }
                            pcHasNotSteppedYet = false;
                        }
                    }
                }
            }
            if (!pcHasNotSteppedYet && testingPlayer.equals(player)) {
                System.out.println("A gép lezáratlan kettest talált, amiből lezáratlan hármast lehetett volna csinálni, ezért lezárta azt.");
            } else if (!pcHasNotSteppedYet && testingPlayer.equals(pcPlayer)) {
                System.out.println("A gép a lezáratlan kettesét továbbépítette.");
            }
        }
        return pcHasNotSteppedYet;
    }

    private static boolean twoSameCharacter(String[][] level, String testingPlayer, boolean pcHasNotSteppedYet, String player, String pcPlayer) {

        if (pcHasNotSteppedYet) {
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {

                    //--------függőlegesen 2 egymás alatt
                    if (pcHasNotSteppedYet && i < HEIGHT - 2) {
                        if (level[i][j] == level[i + 1][j] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 1) {
                        if (level[i][j] == level[i + 1][j] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //--------vízszintesen 2 egymás mellett
                    if (pcHasNotSteppedYet && j < WIDTH - 2) {
                        if (level[i][j] == level[i][j + 1] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < j && j < WIDTH - 1) {
                        if (level[i][j] == level[i][j + 1] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE)) {
                            level[i][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //-----Atlósan jobbra lefelé 2 egymás mellett
                    if (pcHasNotSteppedYet && j < WIDTH - 2 && i < HEIGHT - 2) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE)) {
                            level[i + 2][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 1 && 0 < j && j < WIDTH - 1) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j].equals(testingPlayer) && level[i - 1][j - 1].equals(SPACE)) {
                            level[i - 1][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    //----Atlósan balra lefelé 2 egymás mellett
                    if (pcHasNotSteppedYet && i < HEIGHT - 2 && 1 < j) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j].equals(testingPlayer) && level[i + 2][j - 2].equals(SPACE)) {
                            level[i + 2][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 0 < i && i < HEIGHT - 1 && 0 < j && j < WIDTH - 1) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j].equals(testingPlayer) && level[i - 1][j + 1].equals(SPACE)) {
                            level[i - 1][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                }
            }
            if (!pcHasNotSteppedYet && testingPlayer.equals(player)) {
                System.out.println("A gép 2 azonos karaktert talált egymás mellett, ezért lezárta azt.");
            } else if (!pcHasNotSteppedYet && testingPlayer.equals(pcPlayer)) {
                System.out.println("A gép 2 egymás melletti karakterét továbbépítette.");
            }
        }
        return pcHasNotSteppedYet;
    }


    private static boolean onlyOneCharacter(String[][] level, String testingPlayer, boolean pcHasNotSteppedYet, String player, String pcPlayer) {
        Random random = new Random();
        int horizontal;
        int vertical;
        int numberOfAttempts = 0;

        if (pcHasNotSteppedYet) {
            outer:
            for (int i = 1; i < HEIGHT - 1; i++) {
                for (int j = 1; j < WIDTH - 1; j++) {
                    if (level[i][j].equals(testingPlayer)) {
                        do {
                            horizontal = random.nextInt(3) - 1;
                            vertical = random.nextInt(3) - 1;
                            numberOfAttempts++;
                            if (level[i + vertical][j + horizontal].equals(SPACE)) {
                                level[i + vertical][j + horizontal] = pcPlayer;
                                pcHasNotSteppedYet = false;
                                break outer;


                            }
                        } while (!(level[i + vertical][j + horizontal].equals(SPACE)) && numberOfAttempts < 5);
                        numberOfAttempts = 0;
                    }
                }
            }
            if (!pcHasNotSteppedYet && testingPlayer.equals(player)) {
                System.out.println("A gép a játékos melletti egyik mezőre lépett véletlenszerűen");
            } else if (!pcHasNotSteppedYet && testingPlayer.equals(pcPlayer)) {
                System.out.println("A gép a saját karaktere melletti egyik mezőre lépett véletlenszerűen");
            }
        }
        return pcHasNotSteppedYet;
    }

    private static void randomstep(String[][] level, String player, String pcPlayer) {
        Random random = new Random();
        int horizontal;
        int vertical;
        do {
            horizontal = random.nextInt(WIDTH - 8) + 4;
            vertical = random.nextInt(HEIGHT - 8) + 4;
            System.out.println();

        } while (level[vertical][horizontal].equals(player) || level[vertical][horizontal].equals(pcPlayer));
        level[vertical][horizontal] = pcPlayer;
        System.out.println("A gép véletlenszerűen lépett");
    }


    private static boolean twiceTwoSameCharacterDanger(String[][] level, String testingPlayer, boolean pcHasNotSteppedYet, String player, String pcPlayer) {
        if (pcHasNotSteppedYet) {
            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    // 2 függőleges és két vízszintes
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 1 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j - 1] && level[i][j] == level[i + 2][j - 2] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && j < WIDTH - 2) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 1][j + 2] && level[i][j] == level[i + 2][j + 2] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 0 < i && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 1][j + 1] && level[i][j] == level[i - 1][j + 2] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j + 1] && level[i][j] == level[i + 2][j + 2] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && 1 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 3][j - 1] && level[i][j] == level[i + 3][j - 2] && level[i][j].equals(testingPlayer) && level[i + 3][j].equals(SPACE) && level[i + 2][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && j < WIDTH - 2) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 2][j + 2] && level[i][j] == level[i + 3][j + 2] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i + 1][j + 2].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 1 < i && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 2][j + 1] && level[i][j] == level[i - 2][j + 2] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 2][j].equals(SPACE)) {
                            level[i - 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 3][j + 1] && level[i][j] == level[i + 3][j + 2] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 2 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j - 2] && level[i][j] == level[i + 2][j - 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 2][j - 1].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && j < WIDTH - 3) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 1][j + 3] && level[i][j] == level[i + 2][j + 3] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i][j + 3].equals(SPACE)) {
                            level[i][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 0 < i && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 1][j + 2] && level[i][j] == level[i - 1][j + 3] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 1][j + 1].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 2][j + 2] && level[i][j] == level[i + 2][j + 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 2][j + 1].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && 2 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 3][j - 2] && level[i][j] == level[i + 3][j - 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 3][j - 1].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && j < WIDTH - 3) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 2][j + 3] && level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i][j + 3].equals(SPACE) && level[i + 1][j + 3].equals(SPACE)) {
                            level[i][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 1 < i && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 2][j + 2] && level[i][j] == level[i - 2][j + 3] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 2][j + 1].equals(SPACE) && level[i - 2][j].equals(SPACE)) {
                            level[i - 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 3][j + 2] && level[i][j] == level[i + 3][j + 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 3][j + 1].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    // 2 függőleges és két átlós baljra lefelé
                    if (pcHasNotSteppedYet && i < (HEIGHT - 4) && j > 1) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 3][j - 1] && level[i][j] == level[i + 4][j - 2] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i][j + 2] && level[i][j] == level[i + 1][j + 1] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 0 < i && 1 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i][j - 1] && level[i][j] == level[i + 1][j - 2] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 2 < i && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 2][j + 1] && level[i][j] == level[i - 3][j + 2] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && i < (HEIGHT - 5) && 1 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 4][j - 1] && level[i][j] == level[i + 5][j - 2] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 1][j + 2] && level[i][j] == level[i + 2][j + 1] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 1 < i && 1 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 1][j - 1] && level[i][j] == level[i][j - 2] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 2][j].equals(SPACE)) {
                            level[i - 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 3 < i && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 3][j + 1] && level[i][j] == level[i - 4][j + 2] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 2][j].equals(SPACE)) {
                            level[i - 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && i < (HEIGHT - 5) && j > 2) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 4][j - 2] && level[i][j] == level[i + 5][j - 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 3][j - 1].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && j < (WIDTH - 3) && 0 < i) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i][j + 2] && level[i][j] == level[i - 1][j + 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 1][j + 1].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 0 < i && 2 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 1][j - 2] && level[i][j] == level[i + 2][j - 3] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i][j - 1].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 3 < i && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 3][j + 2] && level[i][j] == level[i - 4][j + 3] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 2][j + 1].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && i < (HEIGHT - 6) && j > 2) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 5][j - 2] && level[i][j] == level[i + 6][j - 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 4][j - 1].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && j < (WIDTH - 3) && 0 < i) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 1][j + 2] && level[i][j] == level[i][j + 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 2][j + 1].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 1 < i && 2 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i][j - 2] && level[i][j] == level[i + 1][j - 3] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 1][j - 1].equals(SPACE) && level[i - 2][j].equals(SPACE)) {
                            level[i - 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 4 < i && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 4][j + 2] && level[i][j] == level[i - 5][j + 3] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 3][j + 1].equals(SPACE) && level[i - 2][j].equals(SPACE)) {
                            level[i - 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    // 2 függőleges és két átlós jobra lefelé
                    if (pcHasNotSteppedYet && i < (HEIGHT - 4) && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 3][j + 1] && level[i][j] == level[i + 4][j + 2] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 1 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i][j - 2] && level[i][j] == level[i + 1][j - 1] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 0 < i && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i][j + 1] && level[i][j] == level[i + 1][j + 2] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 2 < i && 1 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 2][j - 1] && level[i][j] == level[i - 3][j - 2] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    if (pcHasNotSteppedYet && i < (HEIGHT - 5) && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 4][j + 1] && level[i][j] == level[i + 5][j + 2] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && 1 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 1][j - 2] && level[i][j] == level[i + 2][j - 1] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 1 < i && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 1][j + 1] && level[i][j] == level[i][j + 2] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 2][j].equals(SPACE)) {
                            level[i - 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 3 < i && 1 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 3][j - 1] && level[i][j] == level[i - 4][j - 2] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 2][j].equals(SPACE)) {
                            level[i - 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    if (pcHasNotSteppedYet && i < (HEIGHT - 5) && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 4][j + 2] && level[i][j] == level[i + 5][j + 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 3][j + 1].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 2 < j && 0 < i) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i][j - 2] && level[i][j] == level[i - 1][j - 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 1][j - 1].equals(SPACE)) {
                            level[i + 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 0 < i && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 1][j + 2] && level[i][j] == level[i + 2][j + 3] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i][j + 1].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 3 < i && 2 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 3][j - 2] && level[i][j] == level[i - 4][j - 3] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 2][j - 1].equals(SPACE)) {
                            level[i - 1][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    if (pcHasNotSteppedYet && i < (HEIGHT - 6) && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 5][j + 2] && level[i][j] == level[i + 6][j + 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 4][j + 1].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 2 < j && 0 < i) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i + 1][j - 2] && level[i][j] == level[i][j - 3] && level[i][j].equals(testingPlayer) && level[i + 2][j].equals(SPACE) && level[i + 2][j - 1].equals(SPACE) && level[i + 3][j].equals(SPACE)) {
                            level[i + 3][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 1 < i && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i][j + 2] && level[i][j] == level[i + 1][j + 3] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 1][j + 1].equals(SPACE) && level[i - 2][j].equals(SPACE)) {
                            level[i - 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 1) && 4 < i && 2 < j) {
                        if (level[i][j] == level[i + 1][j] && level[i][j] == level[i - 4][j - 2] && level[i][j] == level[i - 5][j - 3] && level[i][j].equals(testingPlayer) && level[i - 1][j].equals(SPACE) && level[i - 3][j - 1].equals(SPACE) && level[i - 2][j].equals(SPACE)) {
                            level[i - 2][j] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    // 2 víszintesen és két átlós balra lefelé
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 2 < j && (j < WIDTH - 1)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 1][j - 2] && level[i][j] == level[i + 2][j - 3] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE)) {
                            level[i][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 1 < i && j < (WIDTH - 1) && 1 < j) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i - 1][j] && level[i][j] == level[i - 2][j + 1] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE)) {
                            level[i][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && j < (WIDTH - 2)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 2][j] && level[i][j] == level[i + 1][j + 1] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 1 < i && j < (WIDTH - 4)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i - 1][j + 3] && level[i][j] == level[i - 2][j + 4] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }

                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 3 < j && (j < WIDTH - 1)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 1][j - 3] && level[i][j] == level[i + 2][j - 4] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE) && level[i][j - 2].equals(SPACE)) {
                            level[i][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 1 < i && j < (WIDTH - 1) && 2 < j) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i - 1][j - 1] && level[i][j] == level[i - 2][j] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE) && level[i][j - 2].equals(SPACE)) {
                            level[i][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 2][j + 1] && level[i][j] == level[i + 1][j + 2] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i][j + 3].equals(SPACE)) {
                            level[i][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 1 < i && j < (WIDTH - 5)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i - 1][j + 4] && level[i][j] == level[i - 2][j + 5] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i][j + 3].equals(SPACE)) {
                            level[i][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && 3 < j && (j < WIDTH - 1)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 2][j - 3] && level[i][j] == level[i + 3][j - 4] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE) && level[i + 1][j - 2].equals(SPACE)) {
                            level[i][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 2 < i && j < (WIDTH - 2) && 0 < j) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i - 2][j + 1] && level[i][j] == level[i - 3][j + 2] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE) && level[i - 1][j].equals(SPACE)) {
                            level[i][j - 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && j < (WIDTH - 2) && 0 < j) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 2][j] && level[i][j] == level[i + 3][j - 1] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i + 1][j + 1].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 2 < i && j < (WIDTH - 5)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i - 2][j + 4] && level[i][j] == level[i - 3][j + 5] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i - 1][j + 3].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && 4 < j && (j < WIDTH - 1)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 2][j - 4] && level[i][j] == level[i + 3][j - 5] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE) && level[i][j - 2].equals(SPACE) && level[i + 1][j - 3].equals(SPACE)) {
                            level[i][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 2 < i && j < (WIDTH - 1) && 2 < j) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i - 2][j] && level[i][j] == level[i - 3][j + 1] && level[i][j].equals(testingPlayer) && level[i][j - 1].equals(SPACE) && level[i][j - 2].equals(SPACE) && level[i - 1][j - 1].equals(SPACE)) {
                            level[i][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && j < (WIDTH - 3)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i + 3][j] && level[i][j] == level[i + 2][j + 1] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i][j + 3].equals(SPACE) && level[i + 1][j + 2].equals(SPACE)) {
                            level[i][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 2 < i && j < (WIDTH - 6)) {
                        if (level[i][j] == level[i][j + 1] && level[i][j] == level[i - 2][j + 5] && level[i][j] == level[i - 3][j + 6] && level[i][j].equals(testingPlayer) && level[i][j + 2].equals(SPACE) && level[i][j + 3].equals(SPACE) && level[i - 1][j + 4].equals(SPACE)) {
                            level[i][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    // 2 víszintesen és két átlós jobbra lefelé
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 0 < j && (j < WIDTH - 3)) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i + 1][j + 2] && level[i][j] == level[i + 2][j + 3] && level[i][j].equals(testingPlayer) && level[i][j + 1].equals(SPACE)) {
                            level[i][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 1 < i && j < (WIDTH - 1) && 0 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i - 1][j] && level[i][j] == level[i - 2][j - 1] && level[i][j].equals(testingPlayer) && level[i][j + 1].equals(SPACE)) {
                            level[i][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && j < (WIDTH - 1) && 0 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i + 2][j] && level[i][j] == level[i + 1][j - 1] && level[i][j].equals(testingPlayer) && level[i][j - 2].equals(SPACE)) {
                            level[i][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 1 < i && 3 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i - 1][j - 3] && level[i][j] == level[i - 2][j - 4] && level[i][j].equals(testingPlayer) && level[i][j - 2].equals(SPACE)) {
                            level[i][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 0 < j && (j < WIDTH - 4)) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i + 1][j + 3] && level[i][j] == level[i + 2][j + 4] && level[i][j].equals(testingPlayer) && level[i][j + 1].equals(SPACE) && level[i][j + 2].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 1 < i && j < (WIDTH - 2) && 0 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i - 1][j + 1] && level[i][j] == level[i - 2][j] && level[i][j].equals(testingPlayer) && level[i][j + 1].equals(SPACE) && level[i][j + 2].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 2) && 2 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i + 2][j - 1] && level[i][j] == level[i + 1][j - 2] && level[i][j].equals(testingPlayer) && level[i][j - 2].equals(SPACE) && level[i][j - 3].equals(SPACE)) {
                            level[i][j - 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 1 < i && 4 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i - 1][j - 4] && level[i][j] == level[i - 2][j - 5] && level[i][j].equals(testingPlayer) && level[i][j - 2].equals(SPACE) && level[i][j - 3].equals(SPACE)) {
                            level[i][j - 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && 0 < j && (j < WIDTH - 4)) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i + 2][j + 3] && level[i][j] == level[i + 3][j + 4] && level[i][j].equals(testingPlayer) && level[i][j + 1].equals(SPACE) && level[i + 1][j + 2].equals(SPACE)) {
                            level[i][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 2 < i && j < (WIDTH - 1) && 1 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i - 2][j - 1] && level[i][j] == level[i - 3][j - 2] && level[i][j].equals(testingPlayer) && level[i][j + 1].equals(SPACE) && level[i - 1][j].equals(SPACE)) {
                            level[i][j + 1] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && j < (WIDTH - 1) && 1 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i + 2][j] && level[i][j] == level[i + 3][j + 1] && level[i][j].equals(testingPlayer) && level[i][j - 2].equals(SPACE) && level[i + 1][j - 1].equals(SPACE)) {
                            level[i][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 2 < i && 4 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i - 2][j - 4] && level[i][j] == level[i - 3][j - 5] && level[i][j].equals(testingPlayer) && level[i][j - 2].equals(SPACE) && level[i - 1][j - 3].equals(SPACE)) {
                            level[i][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && 0 < j && (j < WIDTH - 5)) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i + 2][j + 4] && level[i][j] == level[i + 3][j + 5] && level[i][j].equals(testingPlayer) && level[i][j + 1].equals(SPACE) && level[i][j + 2].equals(SPACE) && level[i + 1][j + 3].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 2 < i && j < (WIDTH - 3) && 1 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i - 2][j] && level[i][j] == level[i - 3][j - 1] && level[i][j].equals(testingPlayer) && level[i][j + 1].equals(SPACE) && level[i][j + 2].equals(SPACE) && level[i - 1][j + 1].equals(SPACE)) {
                            level[i][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && i < (HEIGHT - 3) && 2 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i + 3][j] && level[i][j] == level[i + 2][j - 1] && level[i][j].equals(testingPlayer) && level[i][j - 2].equals(SPACE) && level[i][j - 3].equals(SPACE) && level[i + 1][j - 2].equals(SPACE)) {
                            level[i][j - 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && 2 < i && 5 < j) {
                        if (level[i][j] == level[i][j - 1] && level[i][j] == level[i - 2][j - 5] && level[i][j] == level[i - 3][j - 6] && level[i][j].equals(testingPlayer) && level[i][j - 2].equals(SPACE) && level[i][j - 3].equals(SPACE) && level[i - 1][j - 4].equals(SPACE)) {
                            level[i][j - 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    // két átlós
                    if (pcHasNotSteppedYet && 1 < i && (j < WIDTH - 4)) {
                        if (level[i][j] == level[i - 1][j + 1] && level[i][j] == level[i - 1][j + 3] && level[i][j] == level[i][j + 4] && level[i][j].equals(testingPlayer) && level[i - 2][j + 2].equals(SPACE)) {
                            level[i - 2][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 2) && (j < WIDTH - 4)) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 1][j + 3] && level[i][j] == level[i][j + 4] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE)) {
                            level[i + 2][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 4) && (j < WIDTH - 2)) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 4][j] && level[i][j] == level[i + 3][j + 1] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE)) {
                            level[i + 2][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 4) && 1 < j) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 4][j] && level[i][j] == level[i + 3][j - 1] && level[i][j].equals(testingPlayer) && level[i + 2][j - 2].equals(SPACE)) {
                            level[i + 2][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && 2 < i && (j < WIDTH - 5)) {
                        if (level[i][j] == level[i - 1][j + 1] && level[i][j] == level[i - 1][j + 5] && level[i][j] == level[i - 2][j + 4] && level[i][j].equals(testingPlayer) && level[i - 2][j + 2].equals(SPACE) && level[i - 3][j + 3].equals(SPACE)) {
                            level[i - 3][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 3) && (j < WIDTH - 5)) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 1][j + 5] && level[i][j] == level[i + 2][j + 4] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE) && level[i + 3][j + 3].equals(SPACE)) {
                            level[i + 3][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 5) && (j < WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 5][j + 1] && level[i][j] == level[i + 4][j + 2] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE) && level[i + 3][j + 3].equals(SPACE)) {
                            level[i + 3][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 5) && 2 < j) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 5][j - 1] && level[i][j] == level[i + 4][j - 2] && level[i][j].equals(testingPlayer) && level[i + 2][j - 2].equals(SPACE) && level[i + 3][j - 3].equals(SPACE)) {
                            level[i + 3][j - 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && 1 < i && i < (HEIGHT - 1) && (j < WIDTH - 5)) {
                        if (level[i][j] == level[i - 1][j + 1] && level[i][j] == level[i][j + 4] && level[i][j] == level[i + 1][j + 5] && level[i][j].equals(testingPlayer) && level[i - 2][j + 2].equals(SPACE) && level[i - 1][j + 3].equals(SPACE)) {
                            level[i - 2][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 2) && 0 < i && (j < WIDTH - 5)) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i][j + 4] && level[i][j] == level[i - 1][j + 5] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE) && level[i + 1][j + 3].equals(SPACE)) {
                            level[i + 2][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 5) && (j < WIDTH - 2) && 0 < j) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 4][j] && level[i][j] == level[i + 5][j - 1] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE) && level[i + 3][j + 1].equals(SPACE)) {
                            level[i + 2][j + 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 5) && 1 < j && (j < WIDTH - 1)) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 4][j] && level[i][j] == level[i + 5][j + 1] && level[i][j].equals(testingPlayer) && level[i + 2][j - 2].equals(SPACE) && level[i + 3][j - 1].equals(SPACE)) {
                            level[i + 2][j - 2] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }


                    if (pcHasNotSteppedYet && 2 < i && (j < WIDTH - 6)) {
                        if (level[i][j] == level[i - 1][j + 1] && level[i][j] == level[i - 1][j + 5] && level[i][j] == level[i][j + 6] && level[i][j].equals(testingPlayer) && level[i - 2][j + 2].equals(SPACE) && level[i - 3][j + 3].equals(SPACE) && level[i - 2][j + 4].equals(SPACE)) {
                            level[i - 3][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 3) && (j < WIDTH - 6)) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 1][j + 5] && level[i][j] == level[i][j + 6] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE) && level[i + 3][j + 3].equals(SPACE) && level[i + 2][j + 4].equals(SPACE)) {
                            level[i + 3][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 6) && (j < WIDTH - 3)) {
                        if (level[i][j] == level[i + 1][j + 1] && level[i][j] == level[i + 5][j + 1] && level[i][j] == level[i + 6][j] && level[i][j].equals(testingPlayer) && level[i + 2][j + 2].equals(SPACE) && level[i + 3][j + 3].equals(SPACE) && level[i + 4][j + 2].equals(SPACE)) {
                            level[i + 3][j + 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                    if (pcHasNotSteppedYet && (i < HEIGHT - 6) && 2 < j) {
                        if (level[i][j] == level[i + 1][j - 1] && level[i][j] == level[i + 5][j - 1] && level[i][j] == level[i + 6][j] && level[i][j].equals(testingPlayer) && level[i + 2][j - 2].equals(SPACE) && level[i + 3][j - 3].equals(SPACE) && level[i + 4][j - 2].equals(SPACE)) {
                            level[i + 3][j - 3] = pcPlayer;
                            pcHasNotSteppedYet = false;
                        }
                    }
                }
            }
            if (!pcHasNotSteppedYet && testingPlayer.equals(player)) {
                System.out.println("A gép 2 lezáratlan (vagy lezárt) hármas csinálási lehetőséget akadályozott meg");
            } else if (!pcHasNotSteppedYet && testingPlayer.equals(pcPlayer)) {
                System.out.println("A gép 2 hármast hozott léptre egy lépésben.");
            }
        }
        return pcHasNotSteppedYet;
    }


}


import java.util.ArrayList;
import java.util.Random;

public class Mutation {
    private final static int DELTA = 10;

    //Se altera un solo gen con una probabilidad Pm
    public static Player gene(Player player, ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, double pm){
        Random random = new Random();
        if(random.nextDouble() > pm){
            return player;
        }
        int i = random.nextInt(6);
        int j;
        switch (i){
            case 0:
                return new Player(1.3 + (2.0 - 1.3) * random.nextDouble(), player.getChest(), player.getGloves(), player.getHelmet(), player.getWeapon(), player.getBoots(), player.getType());
            case 1:
                j = chestList.indexOf(player.getChest()) + random.nextInt(DELTA) + 1;
                if(j > chestList.size()){
                    j -= chestList.size();
                }
                return new Player(player.getHeight(), chestList.get(j), player.getGloves(), player.getHelmet(), player.getWeapon(), player.getBoots(), player.getType());
            case 2:
                j = glovesList.indexOf(player.getGloves()) + random.nextInt(DELTA) + 1;
                if (j > glovesList.size()){
                    j -= glovesList.size();
                }
                return new Player(player.getHeight(), player.getChest(), glovesList.get(j), player.getHelmet(), player.getWeapon(), player.getBoots(), player.getType());
            case 3:
                j = helmetList.indexOf(player.getHelmet()) + random.nextInt(DELTA) + 1;
                if(j > helmetList.size()){
                    j -= helmetList.size();
                }
                return new Player(player.getHeight(), player.getChest(), player.getGloves(), helmetList.get(j), player.getWeapon(), player.getBoots(), player.getType());
            case 4:
                j = weaponsList.indexOf(player.getWeapon()) + random.nextInt(DELTA) + 1;
                if(j > weaponsList.size()){
                    j -= weaponsList.size();
                }
                return new Player(player.getHeight(), player.getChest(), player.getGloves(), player.getHelmet(), weaponsList.get(j), player.getBoots(), player.getType());
            case 5:
                j = bootList.indexOf(player.getBoots()) + random.nextInt(DELTA) + 1;
                if(j > bootList.size()){
                    j -= bootList.size();
                }
                return new Player(player.getHeight(), player.getChest(), player.getGloves(), player.getHelmet(), player.getWeapon(), bootList.get(j), player.getType());
        }
        return player;
    }

    //Se selecciona una cantidad m de genes para mutar, con probabilidad Pm
    public static Player limitedMultigene(Player player, ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, int m, double pm){
        if(m == 0){
            return player;
        }
        if(m > 6){
            m = 6;
        }
        Player playerAux = new Player(player.getHeight(), player.getChest(), player.getGloves(), player.getHelmet(), player.getWeapon(), player.getBoots(), player.getType());
        for (int i = 0 ; i < m ; i++){
            mutate(playerAux, bootList, weaponsList,helmetList,glovesList, chestList, pm, i);
        }
        return playerAux;
    }

    private static void mutate(Player player, ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, double pm, int i){
        Random random = new Random();
        if(random.nextDouble() > pm){
            return ;
        }
        int j;
        switch (i){
            case 0:
                player.setHeight(1.3 + (2.0 - 1.3) * random.nextDouble());
                break;
            case 1:
                j = chestList.indexOf(player.getChest()) + random.nextInt(DELTA) + 1;
                if(j > chestList.size()){
                    j -= chestList.size();
                }
                player.setChest(chestList.get(j));
                break;
            case 2:
                j = glovesList.indexOf(player.getGloves()) + random.nextInt(DELTA) + 1;
                if (j > glovesList.size()){
                    j -= glovesList.size();
                }
                player.setGloves(glovesList.get(j));
                break;
            case 3:
                j = helmetList.indexOf(player.getHelmet()) + random.nextInt(DELTA) + 1;
                if(j > helmetList.size()){
                    j -= helmetList.size();
                }
                player.setHelmet(helmetList.get(j));
                break;
            case 4:
                j = weaponsList.indexOf(player.getWeapon()) + random.nextInt(DELTA) + 1;
                if(j > weaponsList.size()){
                    j -= weaponsList.size();
                }
                player.setWeapon(weaponsList.get(j));
                break;
            case 5:
                j = bootList.indexOf(player.getBoots()) + random.nextInt(DELTA) + 1;
                if(j > bootList.size()){
                    j -= bootList.size();
                }
                player.setBoots(bootList.get(j));
        }
    }

    //Cada gen tiene una probabilidad Pm de ser mutado
    public static Player uniformMultigene(Player player, ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, double pm){
        Random random = new Random();
        double height = player.getHeight();
        int jc = chestList.indexOf(player.getChest());
        int jg = glovesList.indexOf(player.getGloves());
        int jh = helmetList.indexOf(player.getHelmet());
        int jw = weaponsList.indexOf(player.getWeapon());
        int jb = bootList.indexOf(player.getBoots());
        if(random.nextDouble() <= pm){
            height = 1.3 + (2.0 - 1.3) * random.nextDouble();
        }
        if(random.nextDouble() <= pm){
            jc = jc + random.nextInt(DELTA) + 1;
            if(jc > chestList.size()){
                jc = jc - chestList.size();
            }
        }
        if(random.nextDouble()<= pm){
            jg = jg + random.nextInt(DELTA) + 1;
            if (jg > glovesList.size()){
                jg = jg - glovesList.size();
            }
        }
        if(random.nextDouble()<= pm){
            jh = jh + random.nextInt(DELTA) + 1;
            if(jh > helmetList.size()){
                jh = jh - helmetList.size();
            }
        }
        if(random.nextDouble()<= pm){
            jw = jw + random.nextInt(DELTA) + 1;
            if(jw > weaponsList.size()){
                jw = jw - weaponsList.size();
            }
        }
        if(random.nextDouble()<= pm){
            jb = jb + random.nextInt(DELTA) + 1;
            if(jb > bootList.size()){
                jb = jw - bootList.size();
            }
        }
        return new Player(height, chestList.get(jc), glovesList.get(jg), helmetList.get(jh),weaponsList.get(jw), bootList.get(jb), player.getType());
    }

    //Con una probabilidad Pm se mutan todos los genes del individuo
    public static Player complete(Player player, ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, double pm){
        Random random = new Random();
        if(random.nextDouble() > pm){
            return player;
        }
        int jc = chestList.indexOf(player.getChest()) + random.nextInt(DELTA) + 1;
        if(jc > chestList.size()){
            jc -= chestList.size();
        }
        int jg = glovesList.indexOf(player.getGloves()) + random.nextInt(DELTA) + 1;
        if (jg > glovesList.size()){
            jg -= glovesList.size();
        }
        int jh = helmetList.indexOf(player.getHelmet()) + random.nextInt(DELTA) + 1;
        if(jh > helmetList.size()){
            jh -= helmetList.size();
        }
        int jw = weaponsList.indexOf(player.getWeapon()) + random.nextInt(DELTA) + 1;
        if(jw > weaponsList.size()){
            jw -= weaponsList.size();
        }
        int jb = bootList.indexOf(player.getBoots()) + random.nextInt(DELTA) + 1;
        if(jb > bootList.size()){
            jb -= bootList.size();
        }
        return new Player(1.3 + (2.0 - 1.3) * random.nextDouble(), chestList.get(jc), glovesList.get(jg), helmetList.get(jh), weaponsList.get(jw), bootList.get(jb), player.getType());
    }
}

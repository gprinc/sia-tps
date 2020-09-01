import java.util.ArrayList;
import java.util.Random;

public class Mutation {
    private Random random = new Random();
    private final static int DELTA = 2;

    //Se altera un solo gen con una probabilidad Pm
    public Player gene(Player player, ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, double pm){
        if(random.nextDouble() > pm){
            return player;
        }
        int i = random.nextInt(6);
        int j;
        switch (i){
            case 0:
                return new Player(1.3 + (2.0 - 1.3) * random.nextDouble(), player.getChest(), player.getGloves(), player.getHelmet(), player.getWeapon(), player.getBoots(), player.getType());
            case 1:
                j = chestList.indexOf(player.getChest()) + DELTA;
                if(j > chestList.size()){
                    j = 0;
                }
                return new Player(player.getHeight(), (Chest) chestList.get(j), player.getGloves(), player.getHelmet(), player.getWeapon(), player.getBoots(), player.getType());
            case 2:
                j = glovesList.indexOf(player.getGloves()) + DELTA;
                if (j > glovesList.size()){
                    j = 0;
                }
                return new Player(player.getHeight(), player.getChest(), (Gloves) glovesList.get(j), player.getHelmet(), player.getWeapon(), player.getBoots(), player.getType());
            case 3:
                j = helmetList.indexOf(player.getHelmet()) + DELTA;
                if(j > helmetList.size()){
                    j = 0;
                }
                return new Player(player.getHeight(), player.getChest(), player.getGloves(), (Helmet) helmetList.get(j), player.getWeapon(), player.getBoots(), player.getType());
            case 4:
                j = weaponsList.indexOf(player.getWeapon()) + DELTA;
                if(j > weaponsList.size()){
                    j = 0;
                }
                return new Player(player.getHeight(), player.getChest(), player.getGloves(), player.getHelmet(), (Weapon) weaponsList.get(j), player.getBoots(), player.getType());
            case 5:
                j = bootList.indexOf(player.getBoots()) + DELTA;
                if(j > bootList.size()){
                    j = 0;
                }
                return new Player(player.getHeight(), player.getChest(), player.getGloves(), player.getHelmet(), player.getWeapon(), (Boots) bootList.get(j), player.getType());
        }
        return player;
    }

    //Se selecciona una cantidad m de genes para mutar, con probabilidad Pm
    public Player limitedMultigene(Player player, ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, int m, double pm){
        if(m == 0){
            return player;
        }
        Player playerAux = new Player(player.getHeight(), player.getChest(), player.getGloves(), player.getHelmet(), player.getWeapon(), player.getBoots(), player.getType());
        for (int i = 0 ; i < m ; i++){
            mutate(playerAux, bootList, weaponsList,helmetList,glovesList, chestList, pm, i);
        }
        return playerAux;
    }

    private void mutate(Player player, ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, double pm, int i){
        if(random.nextDouble() > pm){
            return ;
        }
        int j;
        switch (i){
            case 0:
                player.setHeight(1.3 + (2.0 - 1.3) * random.nextDouble());
                break;
            case 1:
                j = chestList.indexOf(player.getChest()) + DELTA;
                if(j > chestList.size()){
                    j = 0;
                }
                player.setChest((Chest) chestList.get(j));
                break;
            case 2:
                j = glovesList.indexOf(player.getGloves()) + DELTA;
                if (j > glovesList.size()){
                    j = 0;
                }
                player.setGloves((Gloves) glovesList.get(j));
                break;
            case 3:
                j = helmetList.indexOf(player.getHelmet()) + DELTA;
                if(j > helmetList.size()){
                    j = 0;
                }
                player.setHelmet((Helmet) helmetList.get(j));
                break;
            case 4:
                j = weaponsList.indexOf(player.getWeapon()) + DELTA;
                if(j > weaponsList.size()){
                    j = 0;
                }
                player.setWeapon((Weapon) weaponsList.get(j));
                break;
            case 5:
                j = bootList.indexOf(player.getBoots()) + DELTA;
                if(j > bootList.size()){
                    j = 0;
                }
                player.setBoots((Boots) bootList.get(j));
        }
    }

    //Cada gen tiene una probabilidad Pm de ser mutado
    public Player uniformMultigene(Player player, ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, double pm){
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
            jc = jc + DELTA;
            if(jc > chestList.size()){
                jc = 0;
            }
        }
        if(random.nextDouble()<= pm){
            jg = jg + DELTA;
            if (jg > glovesList.size()){
                jg = 0;
            }
        }
        if(random.nextDouble()<= pm){
            jh = jh + DELTA;
            if(jh > helmetList.size()){
                jh = 0;
            }
        }
        if(random.nextDouble()<= pm){
            jw = jw + DELTA;
            if(jw > weaponsList.size()){
                jw = 0;
            }
        }
        if(random.nextDouble()<= pm){
            jb = jb + DELTA;
            if(jb > bootList.size()){
                jb = 0;
            }
        }
        return new Player(height, chestList.get(jc), glovesList.get(jg), helmetList.get(jh),weaponsList.get(jw), bootList.get(jb), player.getType());
    }

    //Con una probabilidad Pm se mutan todos los genes del individuo
    public Player complete(Player player, ArrayList<Item> bootList, ArrayList<Item> weaponsList, ArrayList<Item> helmetList, ArrayList<Item> glovesList, ArrayList<Item> chestList, double pm){
        if(random.nextDouble() > pm){
            return player;
        }
        int jc = chestList.indexOf(player.getChest()) + DELTA;
        if(jc > chestList.size()){
            jc = 0;
        }
        int jg = glovesList.indexOf(player.getGloves()) + DELTA;
        if (jg > glovesList.size()){
            jg = 0;
        }
        int jh = helmetList.indexOf(player.getHelmet()) + DELTA;
        if(jh > helmetList.size()){
            jh = 0;
        }
        int jw = weaponsList.indexOf(player.getWeapon()) + DELTA;
        if(jw > weaponsList.size()){
            jw = 0;
        }
        int jb = bootList.indexOf(player.getBoots()) + DELTA;
        if(jb > bootList.size()){
            jb = 0;
        }
        return new Player(1.3 + (2.0 - 1.3) * random.nextDouble(), chestList.get(jc), glovesList.get(jg), helmetList.get(jh), weaponsList.get(jw), bootList.get(jb), player.getType());
    }
}

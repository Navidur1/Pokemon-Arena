//Pokemon.java
//Navidur Rahman
//Pokemon class to create Pokemon objects and attack

import java.util.*;

public class Pokemon{
    private String name;
    private int hp;
    private int tothp;
    private int energy = 50;
    private String type;
    private String resist;
    private String weak;
    private int numAttacks;
    private ArrayList <Move> moves = new ArrayList<>();
    private boolean stun = false;
    private boolean disable = false;

    public Pokemon(String data){ //constructer method for creating Pokemon objects
        String[] dataList = data.split(",");
        this.name = dataList[0];
        this.hp = Integer.parseInt(dataList[1]);
        this.tothp = Integer.parseInt(dataList[1]);
        this.type = dataList[2];
        this.resist = dataList[3];
        this.weak = dataList[4];
        this.numAttacks = Integer.parseInt(dataList[5]);
        for(int i = 6; i < 6+(numAttacks*4); i+=4){ //calls move class to create moves
            Move m = new Move(dataList[i], Integer.parseInt(dataList[i+1]), Integer.parseInt(dataList[i+2]), dataList[i+3]);
            moves.add(m);
        }
    }

    public String getName(){ //getter for name
        return name;
    }

    public int getHP(){ //getter for curent hp
        return hp;
    }

    public int getTotHP(){ //getter for total hp
        return tothp;
    }

    public int getEnergy(){ //getter for energy
        return energy;
    }

    public ArrayList <Move> getMoves(){ //getter for moves ArrayList
        return moves;
    }

    public String getType(){ //getter for type
        return type;
    }

    public String getResist(){ //getter for resist
        return resist;
    }

    public String getWeak(){ //getter for weakness
        return weak;
    }

    public boolean getStun(){ //getter for stun boolean
        return stun;
    }

    public void Shuffle(){ //shuffles the moves of an enemy
        Collections.shuffle(moves);
    }

    public void addEnergy(int inc){ //adds energy at end of turn
        energy += inc;
        if(energy > 50){
            energy = 50;
        }
    }

    public void addHP(int inc){ //adds hp after killing an enemy
        hp += inc;
        if(hp > tothp){
            hp = tothp;
        }
    }

    public void unDisable(){ //undisbles the pokemon
        disable = false;
    }

    public void attack(Pokemon enemyPokemon, int choice){ //attack method takes in an enemy and move choice
        if(choice == moves.size()){ //if player chose to pass
            System.out.println(name + " has passed their turn!\n");
        }
        else if(stun){ //if Pokemon is stunned
            System.out.println(name + " was stunned and couldn't move!\n");
            stun = false;
        }
        else{
            Move move = moves.get(choice);
            energy -= move.getCost();
            int doDmg = 0;
            if(disable){ //if Pokemon is disabled
                doDmg -= 10;
                if(doDmg < 0){
                    doDmg = 0;
                }
            }
            boolean stunned = false;
            boolean disabled = false;
            boolean recharged = false;
            boolean superE = false;
            boolean notE = false;
            System.out.println(name + " used " + move.getName() + "!\n");

            if(type.equals(enemyPokemon.getResist())){ //if move is resisted
                notE = true;
                doDmg += (move.getDamage()/2);
            }
            else if(type.equals(enemyPokemon.getWeak())){ //if move is strong
                superE = true;
                doDmg += (move.getDamage()*2);
            }
            else{
                doDmg += move.getDamage();
            }
            //Specials
            Random rand = new Random();
            if(move.getSpecial().equals("wild storm")){
                int dmgNum = 0;
                while(true){
                    int n = rand.nextInt(2); //50/50 chance
                    if(n == 0){ //miss
                        break;
                    }
                    else{ //hit
                        dmgNum += 1;
                        enemyPokemon.hp -= doDmg;
                        System.out.println(enemyPokemon.getName() + " took " + doDmg + " damage!\n");
                        if(enemyPokemon.hp <= 0){
                            System.out.println(enemyPokemon.getName() + " has fainted!\n");
                            break;
                        }
                    }
                }
                System.out.println(move.getName() + " hit " + dmgNum + " time(s)!\n");
                if(enemyPokemon.hp > 0){
                    System.out.println(enemyPokemon.getName() + " has " + enemyPokemon.getHP() + " HP remaining!\n");
                }
            }
            else{
                if(move.getSpecial().equals("stun")){
                    int n = rand.nextInt(2);
                    if(n == 0){ //hit
                        stunned = true;
                    }
                }
                else if(move.getSpecial().equals("wild card")){
                    int n = rand.nextInt(2);
                    if(n == 0){ //miss
                        doDmg = 0;
                        System.out.println(name + " missed!");
                    }
                }
                else if(move.getSpecial().equals("disable")){
                    disabled = true;
                }
                else if(move.getSpecial().equals("recharge")){
                    recharged = true;
                    energy += 20;
                    if(energy > 50){
                        energy = 50;
                    }
                }
                enemyPokemon.hp -= doDmg;
                if(doDmg > 0){
                    System.out.println(enemyPokemon.getName() + " took " + doDmg + " damage!");
                    if(notE){
                        System.out.println("The move was not very effective!\n");
                    }
                    if(superE){
                        System.out.println("The move was super effective!\n");
                    }
                }
                if(enemyPokemon.hp <= 0){
                    System.out.println(enemyPokemon.getName() + " has fainted!\n");
                }
                else{
                    if(stunned){
                        System.out.println(enemyPokemon.getName() + " was stunned!\n");
                        enemyPokemon.stun = true;
                    }
                    else if(disabled){
                        System.out.println(enemyPokemon.getName() + " was disabled!\n");
                        enemyPokemon.disable = true;
                    }
                    else if(recharged){
                        System.out.println(name + " gained 20 energy!\n");
                    }
                    System.out.println(enemyPokemon.getName() + " has " + enemyPokemon.getHP() + " HP remaining!\n");
                }
            }
        }
    }
}


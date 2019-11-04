//PokemonTester.java

import java.io.*;
import java.util.*;

public class PokemonArena{
    static ArrayList <Pokemon> pokemons = new ArrayList<>();
    public static void main(String[]args){
        try{ //getting each pokemon with stats line by line
            Scanner inFile = new Scanner(new File("pokemon.txt"));
            int n = Integer.parseInt(inFile.nextLine());
            for(int i=0; i < n; i++){
                Pokemon test = new Pokemon(inFile.nextLine());
                pokemons.add(test);
            }
        }
        catch(IOException ex){
            System.out.println(ex);
        }
        //Choosing Pokemon
        System.out.println("Pick your 4 Pokemon!");
        for(int i = 0; i < 28; i++){
            System.out.println(i+1 + ". " + pokemons.get(i).getName());
        }
        System.out.print("\n");

        ArrayList <Pokemon> PlayerPokemons = new ArrayList<>(); //player's pokemon
        ArrayList <Integer> chosen = new ArrayList<>(); //chosen pokemon
        Scanner kb = new Scanner(System.in);

        for(int i = 0; i < 4; i++){
            int pos = kb.nextInt();
            if(!chosen.contains(pos) && pos > 0 && pos < 29){
                PlayerPokemons.add(pokemons.get(pos-1));
                chosen.add(pos);
                System.out.println("You chose " + pokemons.get(pos-1).getName() + "!\n");
            }
            else{
                System.out.println("Invalid Pokemon Chosen!");
                i--;
            }
        }
//Enemy Pokemon
        Pokemon currentEnemy;
        ArrayList <Pokemon> EnemyPokemons = new ArrayList<>();
        for(int i = 0; i < 28; i++){
            if(!PlayerPokemons.contains(pokemons.get(i))){ //if not already chosen
                EnemyPokemons.add(pokemons.get(i));
            }
        }
        Collections.shuffle(EnemyPokemons); //randomizing the enemies
        System.out.println("To be declared Trainer Supreme you must defeat the rest of the pokemon!\n");
        currentEnemy = EnemyPokemons.get(0);
        System.out.println("You must defeat " + currentEnemy.getName() + " <" + currentEnemy.getType() + "> to proceed!\n");
        Pokemon currentAlly;

//Choosing Who to Start With
        while(true){
            System.out.println("Who will you choose to fight with?\n");
            for(int i = 0; i < 4; i++){
                System.out.println(i+1 + ". " + PlayerPokemons.get(i).getName() + "(" + PlayerPokemons.get(i).getHP() + "/" + PlayerPokemons.get(i).getTotHP() + ") <" + PlayerPokemons.get(i).getType() + ">");
            }
            System.out.print("\n");
            int pos1 = kb.nextInt();
            if(pos1 > 0 && pos1 < 5){ //invalid input
                System.out.println(PlayerPokemons.get(pos1-1).getName() + ", I choose you!\n");
                currentAlly = PlayerPokemons.get(pos1-1);
                break;
            }
            System.out.println("Invalid input!\n");
        }
        boolean pPass;
        int movec;
        int act;

//Main Gameplay
        while(true){
            pPass = false;
//Choosing Action
            while(true){
                System.out.println("What will " + currentAlly.getName() + " do? (" + currentAlly.getHP() + "/" + currentAlly.getTotHP() + ") <" + currentAlly.getType() + ">");
                System.out.println("1.Attack\t 2.Switch\t 3.Pass\n");
                act = kb.nextInt();
                if(act < 1 || act >= 4){ //invalid input
                    System.out.println("Invalid selection!\n");
                }
                else{
                    break;
                }
            }
//Attack
            if(act == 1){
//Choosing Move
                System.out.println("Which move will you use? (Energy:" + currentAlly.getEnergy() + ")\n");
                while(true){ //displaying moves
                    for(int i = 0; i < currentAlly.getMoves().size(); i++){
                        System.out.println(i+1 + "." + currentAlly.getMoves().get(i).getName() + " <Cost:" + currentAlly.getMoves().get(i).getCost() + "> (Dmg:" + currentAlly.getMoves().get(i).getDamage() + ") [Special:" + currentAlly.getMoves().get(i).getSpecial() + "]");
                    }
                    System.out.println(currentAlly.getMoves().size()+1 + ".Pass\n");
                    movec = kb.nextInt();
//Passing
                    if(movec == currentAlly.getMoves().size()+1){
                        pPass = true;
                        break;
                    }
//Invalid Input
                    else if(movec <= 0 || movec > currentAlly.getMoves().size()+1){
                        System.out.println("Invalid selection!\n");
                    }
                    else{
                        if(currentAlly.getEnergy() < currentAlly.getMoves().get(movec-1).getCost()){
                            System.out.println("You do not have enough energy to use that move, please choose a different action!\n");
                        }
                        else{
                            break;
                        }
                    }
                }
                Random rand = new Random();
                int n = rand.nextInt(1); //choosing who goes first
//Ally First
                if(n == 0){
                    if(pPass == false){ //if attacking
                        currentAlly.attack(currentEnemy,movec-1);
                    }
                    else{
                        System.out.println("You have passed!\n");
                    }
//If Enemy Dies
                    if(currentEnemy.getHP() <= 0){
                        System.out.println("You have defeated " + currentEnemy.getName() + "!\n");
                        EnemyPokemons.remove(0);
                        if(EnemyPokemons.size() > 0){ //if enemies are left
                            currentEnemy = EnemyPokemons.get(0);
                        }
                        else{ //no enemies left
                            System.out.println("Congratulations! You have become Trainer Supreme!");
                            break;
                        }
                        System.out.println("All your active pokemon got healed by 20 HP!\n");
                        for(Pokemon p : PlayerPokemons){ //adding hp back and undisbling
                            p.addHP(20);
                            p.unDisable();
                        }
                        System.out.println("You must defeat " + currentEnemy.getName() + " <" + currentEnemy.getType() + "> to proceed!\n");
                        while(true){ //choosing who to battle with
                            System.out.println("Who will you battle with?\n");
                            for(int i = 0; i < PlayerPokemons.size(); i++){
                                System.out.println(i+1 + "." + PlayerPokemons.get(i).getName() + "(" + PlayerPokemons.get(i).getHP() + "/" + PlayerPokemons.get(i).getTotHP() + ") <" + PlayerPokemons.get(i).getType() + ">");
                            }
                            System.out.print("\n");
                            int p = kb.nextInt();
                            if(p > 0 && p <= PlayerPokemons.size()){
                                System.out.println(PlayerPokemons.get(p-1).getName() + ", I choose you!\n");
                                currentAlly = PlayerPokemons.get(p-1);
                                break;
                            }
                            System.out.println("Invalid selection!\n");
                        }
                    }//end if(enemy dies)
//If Enemy Lives
                    else{
                        currentEnemy.Shuffle();
                        for(int i = 0; i < currentEnemy.getMoves().size(); i++){ //choosing enemy move
                            if(i == currentEnemy.getMoves().size()-1 && currentEnemy.getEnergy() < currentEnemy.getMoves().get(i).getCost()){
                                System.out.println(currentEnemy.getName() + " has passed!\n");
                            }
                            if(currentEnemy.getEnergy() >= currentEnemy.getMoves().get(i).getCost()){
                                currentEnemy.attack(currentAlly,i);
                                break;
                            }
                        }
//If Ally Dies
                        if(currentAlly.getHP() <= 0){
                            PlayerPokemons.remove(currentAlly);
                            if(PlayerPokemons.size() > 0){
                                while(true){ //choosing new ally
                                    System.out.println("Who will you switch with?\n");
                                    for(int i = 0; i < PlayerPokemons.size(); i++){
                                        System.out.println(i+1 + "." + PlayerPokemons.get(i).getName() + "(" + PlayerPokemons.get(i).getHP() + "/" + PlayerPokemons.get(i).getTotHP() + ") <" + PlayerPokemons.get(i).getType() + ">");
                                    }
                                    System.out.print("\n");
                                    int poke = kb.nextInt();
                                    if(poke >= 1 && poke <= PlayerPokemons.size()){
                                        currentAlly = PlayerPokemons.get(poke-1);
                                        System.out.println(currentAlly.getName() + ", I choose you!\n");
                                        break;
                                    }
                                    System.out.println("Invalid input!\n");
                                }
                            }
                            else{ //if no player pokemons are left
                                System.out.println("You have no more usable Pokemon!");
                                System.out.println("You have been unsuccessful in becoming Trainer Supreme!");
                                System.out.println("You have blacked out!");
                                break;
                            }
                        }//end if(ally dies)
                    }//end if(enemy lives)
                }//end if(ally first)
//Enemy First
                else{
//Enemy Attack
                    currentEnemy.Shuffle();
                    for(int i = 0; i < currentEnemy.getMoves().size(); i++){ //choosing enemy move
                        if(i == currentEnemy.getMoves().size()-1 && currentEnemy.getEnergy() < currentEnemy.getMoves().get(i).getCost()){
                            System.out.println(currentEnemy.getName() + " has passed!\n");
                        }
                        if(currentEnemy.getEnergy() >= currentEnemy.getMoves().get(i).getCost()){
                            currentEnemy.attack(currentAlly,i);
                            break;
                        }
                    }
//If Ally Dies
                    if(currentAlly.getHP() <= 0){
                        PlayerPokemons.remove(currentAlly);
                        if(PlayerPokemons.size() > 0){
                            while(true){ //if allies are left
                                System.out.println("Who will you switch with?\n");
                                for(int i = 0; i < PlayerPokemons.size(); i++){
                                    System.out.println(i+1 + "." + PlayerPokemons.get(i).getName() + "(" + PlayerPokemons.get(i).getHP() + "/" + PlayerPokemons.get(i).getTotHP() + ") <" + PlayerPokemons.get(i).getType() + ">");
                                }
                                System.out.print("\n");
                                int poke = kb.nextInt();
                                if(poke >= 1 && poke <= PlayerPokemons.size()){
                                    currentAlly = PlayerPokemons.get(poke-1);
                                    System.out.println(currentAlly.getName() + ", I choose you!\n");
                                    break;
                                }
                                System.out.println("Invalid input!\n");
                            }
                        }
                        else{ //if no allies left
                            System.out.println("You have no more usable Pokemon!");
                            System.out.println("You have been unsuccessful in becoming Trainer Supreme!");
                            System.out.println("You have blacked out!");
                            break;
                        }
                    }//end if(ally dies)
//If Ally Lives
                    else{
                        if(pPass == false){
                            currentAlly.attack(currentEnemy,movec-1);
                        }
                        else{
                            System.out.println("You have passed!\n");
                        }
//If Enemy Dies
                        if(currentEnemy.getHP() <= 0){
                            System.out.println("You have defeated " + currentEnemy.getName() + "!\n");
                            EnemyPokemons.remove(0);
                            if(EnemyPokemons.size() > 0){
                                currentEnemy = EnemyPokemons.get(0);
                            }
                            else{ //no enemies left
                                System.out.println("Congratulations! You have become Trainer Supreme!");
                                break;
                            }
                            System.out.println("All your active pokemon got healed by 20 HP!\n");
                            for(Pokemon p : PlayerPokemons){
                                p.addHP(20);
                                p.unDisable();
                            }
                            System.out.println("You must defeat " + currentEnemy.getName() + " <" + currentEnemy.getType() + "> to proceed!\n");
                            while(true){ //choosing who to battle with
                                System.out.println("Who will you battle with?\n");
                                for(int i = 0; i < PlayerPokemons.size(); i++){
                                    System.out.println(i+1 + "." + PlayerPokemons.get(i).getName() + "(" + PlayerPokemons.get(i).getHP() + "/" + PlayerPokemons.get(i).getTotHP() + ") <" + PlayerPokemons.get(i).getType() + ">");
                                }
                                System.out.print("\n");
                                int p = kb.nextInt();
                                if(p > 0 && p <= PlayerPokemons.size()){
                                    System.out.println(PlayerPokemons.get(p-1).getName() + ", I choose you!\n");
                                    currentAlly = PlayerPokemons.get(p-1);
                                    break;
                                }
                                System.out.println("Invalid selection!\n");
                            }
                        }//end if(enemy dies)
                    }//end if(ally lives)
                }//end if(enemy first)
            }//end if(act == 1)
//Switch
            else if(act == 2){
//Switching
                if(PlayerPokemons.size() > 1){
                    while(true){
                        System.out.println("Who will you switch with?");
                        for(int i = 0; i < PlayerPokemons.size(); i++){
                            System.out.println(i+1 + "." + PlayerPokemons.get(i).getName() + "(" + PlayerPokemons.get(i).getHP() + "/" + PlayerPokemons.get(i).getTotHP() + ") <" + PlayerPokemons.get(i).getType() + ">");
                        }
                        System.out.print("\n");
                        int swap = kb.nextInt();
                        if(swap < 1 || swap > PlayerPokemons.size()){
                            System.out.println("Invalid input!");
                        }
                        else if(currentAlly == PlayerPokemons.get(swap-1)){
                            System.out.println(currentAlly.getName() + " is already battling!");
                        }
                        else{
                            if(currentAlly.getStun() == false){
                                currentAlly = PlayerPokemons.get(swap-1);
                                System.out.println(currentAlly.getName() + ", I choose you!");
                                break;
                            }
                            System.out.println(currentAlly.getName() + " was stunned and couldn't move!");
                            break;
                        }

                    }
//Enemy Attack
                    currentEnemy.Shuffle();
                    int m;
                    for(int i = 0; i < currentEnemy.getMoves().size(); i++){ //choosing enemy move
                        if(i == currentEnemy.getMoves().size()-1 && currentEnemy.getEnergy() < currentEnemy.getMoves().get(i).getCost()){
                            System.out.println(currentEnemy.getName() + " has passed!");
                        }
                        else if(currentEnemy.getEnergy() >= currentEnemy.getMoves().get(i).getCost()){
                            currentEnemy.attack(currentAlly,i);
                            break;
                        }
                    }
//Ally Dies
                    if(currentAlly.getHP() <= 0){
                        PlayerPokemons.remove(currentAlly);
                        if(PlayerPokemons.size() > 0){ //if allies left
                            while(true){
                                System.out.println("Who will you switch with?");
                                for(int i = 0; i < PlayerPokemons.size(); i++){
                                    System.out.println(i+1 + "." + PlayerPokemons.get(i).getName() + "(" + PlayerPokemons.get(i).getHP() + "/" + PlayerPokemons.get(i).getTotHP() + ") <" + PlayerPokemons.get(i).getType() + ">");
                                }
                                System.out.print("\n");
                                int poke = kb.nextInt();
                                if(poke >= 1 && poke <= PlayerPokemons.size()){
                                    currentAlly = PlayerPokemons.get(poke-1);
                                    System.out.println(currentAlly.getName() + ", I choose you!");
                                    break;
                                }
                                System.out.println("Invalid input!");
                            }
                        }
                        else{ //if no allies left
                            System.out.println("You have no more usable Pokemon!");
                            System.out.println("You have been unsuccessful in becoming Trainer Supreme!");
                            System.out.println("You have blacked out!");
                            break;
                        }
                    }//end if(ally dies)
                }//end switch
                else{
                    System.out.println("You have no other Pokemon to switch to!");
                    continue;
                }
            }

//Pass
            else{
                System.out.println("You have passed!");
//Enemy Attack
                currentEnemy.Shuffle();
                int m;
                for(int i = 0; i < currentEnemy.getMoves().size(); i++){
                    if(i == currentEnemy.getMoves().size()-1 && currentEnemy.getEnergy() < currentEnemy.getMoves().get(i).getCost()){
                        System.out.println(currentEnemy.getName() + " has passed!");
                    }
                    else if(currentEnemy.getEnergy() >= currentEnemy.getMoves().get(i).getCost()){
                        currentEnemy.attack(currentAlly,i);
                        break;
                    }
                }
//Ally Dies
                if(currentAlly.getHP() <= 0){
                    PlayerPokemons.remove(currentAlly);
                    if(PlayerPokemons.size() > 0){
                        while(true){
                            System.out.println("Who will you switch with?");
                            for(int i = 0; i < PlayerPokemons.size(); i++){
                                System.out.println(i+1 + "." + PlayerPokemons.get(i).getName() + "(" + PlayerPokemons.get(i).getHP() + "/" + PlayerPokemons.get(i).getTotHP() + ") <" + PlayerPokemons.get(i).getType() + ">");
                            }
                            System.out.print("\n");
                            int poke = kb.nextInt();
                            if(poke >= 1 && poke <= PlayerPokemons.size()){
                                currentAlly = PlayerPokemons.get(poke-1);
                                System.out.println(currentAlly.getName() + ", I choose you!");
                                break;
                            }
                            System.out.println("Invalid input!");
                        }
                    }
                    else{
                        System.out.println("You have no more usable Pokemon!");
                        System.out.println("You have been unsuccessful in becoming Trainer Supreme!");
                        System.out.println("You have blacked out!");
                        break;
                    }
                }//end if(ally dies)
            }//end pass
            System.out.println("All pokemon have regained 10 energy!");
            currentEnemy.addEnergy(10);
            for(Pokemon p : PlayerPokemons){
                p.addEnergy(10);
            }
        }//end while
    }
}


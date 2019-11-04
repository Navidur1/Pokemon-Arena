//Move.java
//Navidur Rahman
//This move class is used to create move objects for a Pokemon

class Move{
    String name;
    int cost;
    int dmg;
    String special;

    public Move(String name, int cost, int dmg, String special){ //constructer method for creating moves, takes in data from Pokemon
        this.name = name;
        this.cost = cost;
        this.dmg = dmg;
        if(special.equals(" ")){
            this.special = "none";
        }
        else{
            this.special = special;
        }
    }

    public String getName(){ //getter for name
        return name;
    }

    public int getCost(){ //getter for cost
        return cost;
    }

    public int getDamage(){ //getter for damage
        return dmg;
    }

    public String getSpecial(){ //getter for special
        return special;
    }
}


// Waldemar Schröder 11.11.2023

import java.util.Random;
import java.util.Scanner;

class PokeJavaAttack {
   String name;
   String type;
   int power;

   // constructor
   PokeJavaAttack(String name, String type, int power){
      this.name = name;
      this.type = type;
      this.power = power;
   }
}

class PokeJava {
   String name;
   String type;
   int hp;
   int offense;
   int defense;
   int speed;
   PokeJavaAttack[] attacks = new PokeJavaAttack[2];

   // possible PokeJavas
   public static String[] names = {"Normie","Firely", "Waterly", "Grassie"};

   // possible PokeJava types
   public static String[] types = {"normal","fire", "water", "grass"};
   
   public static int minHp=20;
   public static int maxHp=100;

   public static int minOffense=5;
   public static int maxOffense=50;

   public static int minDefense=5;
   public static int maxDefense=50;

   public static int minSpeed=1;
   public static int maxSpeed=50;

   static int getRandomNr (int min, int max) throws Exception {
      // Blueprint random number between min and max 
      if (min > max){
         throw new Exception("Error: min cannot be > max");
      }
      else {
         Random rand = new Random();
         // nextInt as provided by Random is exclusive of the top value so you need to add 1 
         return (rand.nextInt((max - min) + 1) + min);
      }  
   }

   // constructor
   PokeJava(String name){
      try {
         this.name = name;
         if (this.name == "Normie"){
            this.type = "normal";
            this.attacks[0] = new PokeJavaAttack("Tackle","normal",40);
            this.attacks[1] = new PokeJavaAttack("Bodycheck","normal",60);
         }
         if (this.name == "Firely"){
            this.type = "fire";
            this.attacks[0] = new PokeJavaAttack("Tackle","normal",40);
            this.attacks[1] = new PokeJavaAttack("Fire-Breath","fire",60);
         }
         if (this.name == "Waterly"){
            this.type = "water";
            this.attacks[0] = new PokeJavaAttack("Tackle","normal",40);
            this.attacks[1] = new PokeJavaAttack("Aqua-Gun","water",60);
         }
         if (this.name == "Grassie"){
            this.type = "grass";
            this.attacks[0] = new PokeJavaAttack("Tackle","normal",40);
            this.attacks[1] = new PokeJavaAttack("Plant-Power","grass",60);
         }
         //this.type = types[this.getRandomNr(0,types.length-1)];
         this.hp = this.getRandomNr(minHp,maxHp);
         this.offense = this.getRandomNr(minOffense,maxOffense);
         this.defense = this.getRandomNr(minDefense,maxDefense);
         this.speed = this.getRandomNr(minSpeed,maxSpeed);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   void printData (){
      System.out.println("Name: " + this.name);
      System.out.println("Type: " + this.type);
      System.out.println("HP: " + this.hp);
      System.out.println("Offense: " + this.offense);
      System.out.println("Defense: " + this.defense);
      System.out.println("Speed: " + this.speed);
      System.out.println("Attack Nr. 1: " + this.attacks[0].name);
      System.out.println("Attack Nr. 2: " + this.attacks[1].name);
      System.out.println("\n");
   }

   int effectiveIntCalc(PokeJavaAttack attack, PokeJava enemyPoke){
      int effectiveInt = 2;

      if (attack.type == "fire" && enemyPoke.type == "water"){
         effectiveInt = 1;
      }
      if (attack.type == "fire" && enemyPoke.type == "grass"){
         effectiveInt = 4;
      }
      if (attack.type == "water" && enemyPoke.type == "fire"){
         effectiveInt = 4;
      }
      if (attack.type == "water" && enemyPoke.type == "grass"){
         effectiveInt = 1;
      }
      if (attack.type == "grass" && enemyPoke.type == "fire"){
         effectiveInt = 1;
      }
      if (attack.type == "grass" && enemyPoke.type == "water"){
         effectiveInt = 4;
      }
      return effectiveInt;
   }
   
   int damageCalc (int effectiveInt, PokeJavaAttack attack, PokeJava enemyPoke) {
      int typeBonus = 1;
      if (attack.type == this.type){typeBonus = 2;}

      int damage = 1 + (attack.power*typeBonus*effectiveInt*this.offense / enemyPoke.defense) / 50;
      return damage;
   }
   
   void attackPoke (int effectiveInt, int damage, PokeJavaAttack attack, PokeJava enemyPoke){
      System.out.println(this.name + " used " + attack.name + " against " + enemyPoke.name);

      if (effectiveInt == 1){
         System.out.println("Attack was not effective");
      }
      if (effectiveInt == 2){
         System.out.println("Attack was normal effective");
      }
      if (effectiveInt == 4){
         System.out.println("Attack was very effective");
      }

      enemyPoke.hp -= damage;
      if (enemyPoke.hp < 0){enemyPoke.hp = 0;}
      System.out.println(enemyPoke.name + " HP: " + enemyPoke.hp + "\n");
   }  
}

public class Battle {
   public static void main(String[] args) {
      System.out.println("\nBattle begins\n");

      // This reads the input provided by user using keyboard
      Scanner scan = new Scanner(System.in);
      System.out.print("Which Mode do you choose?\n");
      System.out.print("Type 0 for autopilot\n");
      System.out.print("Type 1 for selfplaying\n");

      // This method reads the number provided using keyboard
      int mode = scan.nextInt();

      // My PokeJava init
      String userChoicePoke = "Normie";
      if (mode == 0) {
         //scan.close(); // multiple Battles dont work
         try {
            userChoicePoke = PokeJava.names[PokeJava.getRandomNr(0,PokeJava.names.length-1)];
         } catch (Exception e) {
            e.printStackTrace();
         }
      }

      if (mode == 1) {
      // This reads the input provided by user using keyboard
      System.out.print("Which PokeJava do you choose?\n");
      System.out.print("Type 0 for Normie\n");
      System.out.print("Type 1 for Firely\n");
      System.out.print("Type 2 for Waterly\n");
      System.out.print("Type 3 for Grassie\n");

      // This method reads the number provided using keyboard
      int userChoiceInt = scan.nextInt();
      userChoicePoke = PokeJava.names[userChoiceInt];
      }

      PokeJava myPoke = new PokeJava(userChoicePoke);
      System.out.println("My PokeJava");
      myPoke.printData();

      // Enemy's Pokejava init
      String enemyChoicePoke = "Normie";
      try {
         enemyChoicePoke = PokeJava.names[PokeJava.getRandomNr(0,PokeJava.names.length-1)];
      } catch (Exception e) {
         e.printStackTrace();
      }
      PokeJava enemyPoke = new PokeJava(enemyChoicePoke);
      System.out.println("Enemy's PokeJava");
      enemyPoke.printData();

      // battle loop
      int count = 1;
      while (myPoke.hp != 0 || enemyPoke.hp != 0){
         System.out.println("Round " + count);
         
         // calc myPoke attack stats
         int[] myPokeEffectiveInt = {
            myPoke.effectiveIntCalc(myPoke.attacks[0],enemyPoke),
            myPoke.effectiveIntCalc(myPoke.attacks[1],enemyPoke)
         };
      
         int[] myPokeDamages = {
            myPoke.damageCalc(myPokeEffectiveInt[0],myPoke.attacks[0],enemyPoke),
            myPoke.damageCalc(myPokeEffectiveInt[1],myPoke.attacks[1],enemyPoke)
         };

         int myMaxAt = 0;
         for (int i = 0; i < myPokeDamages.length; i++) {
            myMaxAt = myPokeDamages[i] > myPokeDamages[myMaxAt] ? i : myMaxAt;
         }

         // calc enemys best PokeAttack
         int[] enemyPokeEffectiveInt = {
            enemyPoke.effectiveIntCalc(enemyPoke.attacks[0],myPoke),
            enemyPoke.effectiveIntCalc(enemyPoke.attacks[1],myPoke)
         };
         
         int[] enemyPokeDamages = {
            enemyPoke.damageCalc(enemyPokeEffectiveInt[0],enemyPoke.attacks[0],myPoke),
            enemyPoke.damageCalc(enemyPokeEffectiveInt[1],enemyPoke.attacks[1],myPoke)
         };

         int enemyMaxAt = 0;
         for (int i = 0; i < enemyPokeDamages.length; i++) {
            enemyMaxAt = enemyPokeDamages[i] > enemyPokeDamages[enemyMaxAt] ? i : enemyMaxAt;
         }

         int userChoiceAtt = myMaxAt;
         //if (mode == 0){}
         if (mode == 1){
            System.out.print("Which Attack do you choose?\n");
            System.out.print("Type 0 for " + myPoke.attacks[0].name + "\n");
            System.out.print("Type 1 for " + myPoke.attacks[1].name + "\n");
            System.out.print("Type 2 for running away\n");

            // This method reads the number provided using keyboard
            userChoiceAtt = scan.nextInt();
            if (userChoiceAtt == 2) {
               System.out.println("You ran away");
               break;
            }
         }

         // battle begins
         if (myPoke.speed > enemyPoke.speed){
            myPoke.attackPoke(myPokeEffectiveInt[userChoiceAtt],myPokeDamages[userChoiceAtt],myPoke.attacks[userChoiceAtt], enemyPoke);
            if (myPoke.hp == 0 || enemyPoke.hp == 0){break;}
            enemyPoke.attackPoke(enemyPokeEffectiveInt[enemyMaxAt],enemyPokeDamages[enemyMaxAt],enemyPoke.attacks[enemyMaxAt], myPoke);
            if (myPoke.hp == 0 || enemyPoke.hp == 0){break;}
         } else {
            enemyPoke.attackPoke(enemyPokeEffectiveInt[enemyMaxAt],enemyPokeDamages[enemyMaxAt],enemyPoke.attacks[enemyMaxAt], myPoke);
            if (myPoke.hp == 0 || enemyPoke.hp == 0){break;}
            myPoke.attackPoke(myPokeEffectiveInt[userChoiceAtt],myPokeDamages[userChoiceAtt],myPoke.attacks[userChoiceAtt], enemyPoke);
            if (myPoke.hp == 0 || enemyPoke.hp == 0){break;}
         }
         
         count++;
      }
      if (myPoke.hp == 0){
         System.out.println("My " + myPoke.name + " was defeated");
      }
      if (enemyPoke.hp == 0){
         System.out.println("Enemy's " + enemyPoke.name + " was defeated");
      }
      //scan.close(); // multiple Battles dont work
   }
}
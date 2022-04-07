/* PROGRAM : Battleship
 * Programming Assignment
 * 
 * Class         : 1A
 * Authur        : Cheng Chi Kit
 * studentNumber : 190046352
 * 
 * 
 * Input   : ship and missle(Number)
 * Output  : result of the map,statistics of the game
 * Type    : game
 * Purpose : play this game with a friend or play with yourself
 * 
 * History : 
 *   26/10/2019 - new
 *   27/10/2019 - finished
 *   12/11/2019 - simplify the program
 *   21/11/2019 - added more Documentation and changed some name of the variable
 */
import java.util.*;
public class Battleship
{
    private static String choice = ""; //Human,CPU or exit
    private static int mapSize = 10; // a 10 x 10 map,change it to get a bigger map(no more than 99)
    
    private static String[][]map = new String [mapSize][mapSize]; // Before Player2 start
    private static String[][]mapNew = new String [mapSize][mapSize]; // After Player2 start and print a empty map
    
    static Scanner scanner = new Scanner (System.in); //Scanner
    
    private static String userInput = ""; //for user input things
    
    private static int xCoordinate = 0; //for the array map and mapNew
    private static int yCoordinate = 0; //for the array map and mapNew
    
    private static int count2 = 0; // for counting to print how many ship
    
    private static String horizontalOrVertical = ""; // ship print Horizontal Or Vertical
    
    private static boolean check = true; //mainly for playerChoice().Default = true for print the first "Battleship Player1 (0 - Human, 1 - CPU, x - Exit): "
    
    private static int hittedShip = 0; //how many missile has hitted the ships
    
    private static int launchedMissile = 0; //how many missile has launched
    
    private static String result = null;//for playerChoice(),setted in the class for looping purpose
    static Random random = new Random(); //Random
    public static void main (String [] args){ //main program
        choice = playerChoice(); //cpu or human or exit
        setDefaultMap(map); //set all the array map to " . "
        

        printMap(map); //print all the " . "
        if (choice.equals("0")){ //if user inputted 0,then player insert all the ship
            playerInputShip();
        }
        else{enterXYCpu();} //if user inputted 1,then cpu insert all the ship
        playerTwoStart(); //player two start,time to shoot all the ship!
        setDefaultMap(mapNew);//set all the array mapNew to " . "
        

        printMap(mapNew);//print all the " . "
        

        playerMissile();//main program for counting how many ship has been hitted and how many missile has launched.And finish the game
    }

    private static void playerTwoStart(){ //for clearing the screen using the way as the assignment provided
        userInput = "";
        System.out.print("Press Enter for Player2 to start ... ");
        userInput = scanner.nextLine();
        for(int i = 0; i < 100 ; i++){
            System.out.println();
        }
    }

    private static void checkText(){ //print the text below to get a new input
        System.out.print("Please input 0 or 1, x - Exit : ");
        userInput = scanner.nextLine();
        horizontalOrVertical = userInput;//get a new value in case of player inputted the wrong value
    }

    private static void playerInputShip(){ //from carrier to partrol boat,player need to input to the ships to correct place 
        for(int i = 0; i < 5;i++){
            if (i==0){
                shipType("Carrier",5,i);//i is for enterXY()
            }
            if (i==1){
                System.out.println("Set the ship: Battleship, ship size: 4 ");
                shipType("Battleship",4,i);//i is for enterXY()
            }
            if (i==2){
                System.out.println("Set the ship: Destroyer, ship size: 3 ");
                shipType("Destroyer",3,i);//i is for enterXY()
            }
            if (i==3){
                System.out.println("Set the ship: Submarine, ship size: 3 ");
                shipType("Submarine",3,i);//i is for enterXY()
            }
            if (i==4){
                System.out.println("Set the ship: Patrol Boat, ship size: 2 ");
                shipType("Patrol Boat",2,i);//i is for enterXY()
            }
        }
    }
    
        private static void shipType(String ship,int size,int i){//check if player had inputted the correct number or not
        System.out.println("Player1: Set the ship: "+ship+", ship size: "+size);
        System.out.print("Orientation (0 - horizontal, 1 - vertical), x - Exit: ");
        userInput = scanner.nextLine();
        horizontalOrVertical = userInput;//if player inputted the correct value at the first time, get the input
        while (true){
            if (userInput.equals("1") || userInput.equals("0") || userInput.equals("x")){
                xExit(userInput);
                enterXY(i);//call the enterXY() method
                printMap(map);//then print the map to show to the player
                break;
            }
            else {
                checkText();//print the checktext to get a new input
            }
        }
    }

    private static void setDefaultMap(String[][] array) {//set all the array to " . "
        for(int i = 0; i < mapSize;i++){
            for(int j = 0; j < mapSize;j++){
                array[i][j] = " . ";
            }
        }
    }

    private static void printMap(String[][] array) {//print the map
        System.out.print("    ");
        for(int i = 0; i < mapSize;i++){
            if(i>=9){
                System.out.print(i+" ");
            }
            else{
                System.out.print(i+"  ");
            }
        }
        System.out.print(" <--x");
        System.out.println();
        System.out.print("--+");
        for(int i = 0; i < mapSize;i++){
            System.out.print("---");
        }
        System.out.println();
        //below is to print all the y
        for(int i = 0; i < mapSize;i++){
            if(i>9){
                System.out.print(i+"|");
            }
            else{
                System.out.print(i+" |");
            }
            for(int j = 0; j < mapSize;j++){
                System.out.print(array[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("^\nY\n");
    }

    private static String playerChoice(){//check if the player had inputted the correct value or not and get the input if yes
        if (result == "0" || result == "1"){ //result default is null . therefore it won't work at the first time
            return result;
        }
        if(check == true){
            System.out.print("Battleship Player1 (0 - Human, 1 - CPU, x - Exit): ");
        }
        else{
            System.out.print("Please input 0 or 1, x - Exit : ");
        }
        result = scanner.nextLine();
        check = checkInput(result);
        while(check == false){ //loop until player input the correct input
            playerChoice();
        }
        xExit(result);
        return result;
    }

    private static void xExit(String x){ //Exit the program at any time
        if (x.length() == 1 && x.charAt(0)=='x'){ //if x then exit the program
            System.exit(0);
        }
    }

    private static boolean checkInput(String zero){ //check if user inputted 0,1 or x
        if (zero.length() != 1  || 
           (zero.charAt(0)!='0' && zero.charAt(0)!='1' && zero.charAt(0)!='x')){
            return false;}
        else{return true;}
    }

    private static void playerMissile(){//check if the player's inputted array valuse is the same as the ship or not
        int countx = 0;
        int county = 0;
        while (hittedShip != 17){
            System.out.print("Player2: Set your missile [XY], x - Exit:");
            userInput = scanner.nextLine();
            xExit(userInput);
            if (!userInput.matches("\\d{2}")){
                while(!userInput.matches("\\d{2}"))
                {
                    System.out.print("Error in [XY]! Please input again, x - Exit : ");
                    userInput = scanner.nextLine();
                }
            }
            char first = userInput.charAt(0);
            xCoordinate = Character.getNumericValue(first);
            char second = userInput.charAt(1);
            yCoordinate = Character.getNumericValue(second);
            countx = xCoordinate;
            county = yCoordinate;
            if ( mapNew[yCoordinate][xCoordinate].equals(" o ") || mapNew[yCoordinate][xCoordinate].equals(" # ") ){
                System.out.println("You have already fired this area. ");
                playerMissile();
            }
            launchedMissile++;
            if ( (map[yCoordinate][xCoordinate].equals(mapNew[yCoordinate][xCoordinate]))){
                mapNew[yCoordinate][xCoordinate] = " o ";
                System.out.println("Missed.");
                System.out.println("Launched:"+launchedMissile+", Hit: "+hittedShip);
                printMap(mapNew);
            }
            else{
                mapNew[yCoordinate][xCoordinate] = " # ";
                System.out.println("It's a hit!! ");
                hittedShip++;
                System.out.println("Launched:"+launchedMissile+", Hit: "+hittedShip);
                printMap(mapNew);
            }
        }
        System.out.println("You have hit all battleships!");
        System.exit(0);
    }

    private static void xplus(){ //user for allShipHuman() and allShipCpu()
        xCoordinate++; // BELOW XY ARE REVERSE!
        map[yCoordinate][xCoordinate] = " S "; //print ship in the array, print a String " S " in the array
        count2++;
    }

    private static void yplus(){ //user for allShipHuman() and allShipCpu()
        yCoordinate++; // BELOW XY ARE REVERSE!
        map[yCoordinate][xCoordinate] = " S "; //print ship in the array, print a String " S " in the array
        count2++;
    }

    private static void enterXY(int i){//carrier to patrol boat
        int count = i;
        if (count == 0){allShipHuman("Carrier",5);}
        if (count == 1){allShipHuman("Battleship",4);}
        if (count == 2){allShipHuman("Destroyer",3);}
        if (count == 3){allShipHuman("Submarine",3);}
        if (count == 4){allShipHuman("Patrol Boat",2);}
    }

    private static void allShipHuman(String ship,int size){//check if the player had inputted the correct value or not,(ship can't be over the boundary and overlap
        int countx = 0;
        int county = 0;
        System.out.print("Position of "+ship+" [XY]: ");
        userInput = scanner.nextLine();
        while (true){
            if (!userInput.matches("\\d{2}")){
                while(!userInput.matches("\\d{2}"))
                {
                    System.out.print("Error in [XY]! Please input again, x - Exit : ");
                    userInput = scanner.nextLine();
                    xExit(userInput);
                }
            }

            char first = userInput.charAt(0);
            xCoordinate = Character.getNumericValue(first);
            char second = userInput.charAt(1);
            yCoordinate = Character.getNumericValue(second);
            if (horizontalOrVertical.equals("0")){
                if ( (yCoordinate+size) > 10){
                    xCoordinate = 0;
                    yCoordinate = 0;
                    System.out.println("The ships cannot be over the boundary! ");
                    allShipHuman(ship,size);
                    break;
                }
            }
            else{
                if ( (xCoordinate+size) > 10){
                    xCoordinate = 0;
                    yCoordinate = 0;
                    System.out.println("The ships cannot be over the boundary! ");
                    allShipHuman(ship,size);
                    break;
                }
            }

            countx = xCoordinate;
            county = yCoordinate;
            if (!(horizontalOrVertical.equals("0"))){
                while(county<(yCoordinate+size)){
                    if(county < 0){countx++;}
                    else if(map[county][xCoordinate].equals(" S ")){
                        System.out.println("The ships cannot overlap!");
                        allShipHuman(ship,size);
                        break;
                    }

                    county++;
                }
            }
            else{
                while(countx<(xCoordinate+size)){
                    if(countx < 0){county++;}
                    else if(map[yCoordinate][countx].equals(" S ")){
                        System.out.println("The ships cannot overlap!");
                        allShipHuman(ship,size);
                        break;
                    }
                    countx++;
                }
            }
            //exit patrolBoat() to prevent printting " S " the second time.
            if(map[yCoordinate][xCoordinate].equals(" S ")){break;}
            //below will run when the above checking is done
            //-------------------------------------------------------
            //print 2 ship
            map[yCoordinate][xCoordinate] = " S "; // reversed xy 
            count2 = 0;
            if (horizontalOrVertical.equals("0")){
                while(count2 < size - 1){
                    xplus();
                }
                break;
            }
            else{
                while(count2 < size - 1){
                    yplus();
                }
                break;
            }
        }
    }
    //  BELOW IS FOR CPU~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    private static void enterXYCpu(){//carrier to patrol boat
        for(int i = 0 ; i<5 ; i++ ){
            if(i==0){allShipCpu(5);}
            if(i==1){allShipCpu(4);}
            if(i==2){allShipCpu(3);}
            if(i==3){allShipCpu(3);}
            if(i==4){allShipCpu(2);}
        }
    }

    private static void allShipCpu(int size){//random ship value
        int HorizontalOrVertical2 = random.nextInt(2);
        int countx = 0;
        int county = 0;
        xCoordinate = random.nextInt(10);
        xCoordinate = random.nextInt(10);
        while(true){
            if (HorizontalOrVertical2 == 0){
                if ( (xCoordinate+size) > 10){
                    xCoordinate = 0;
                    yCoordinate = 0;
                    allShipCpu(size);
                    break;
                }
            }
            else{
                if ( (yCoordinate+size) > 10){
                    xCoordinate = 0;
                    yCoordinate = 0;
                    allShipCpu(size);
                    break;
                }
            }
            countx = xCoordinate;
            county = yCoordinate;
            if (!(HorizontalOrVertical2==0)){
                while(county<(yCoordinate+size)){
                    if(county < 0){countx++;}
                    else if(map[county][xCoordinate].equals(" S ")){
                        allShipCpu(size);
                        break;
                    }
                    county++;
                }
            }
            else{
                while(countx<(xCoordinate+size)){
                    if(countx < 0){county++;}
                    else if(map[yCoordinate][countx].equals(" S ")){
                        allShipCpu(size);
                        break;
                    }
                    countx++;
                }
            }
            //exit cpuInputShipSubmarine() to prevent printting " S " the second time.
            if(map[yCoordinate][xCoordinate].equals(" S ")){break;}
            map[yCoordinate][xCoordinate] = " S "; // reversed xy 
            count2 = 0;
            if (HorizontalOrVertical2==0){
                while(count2 < size - 1){
                    xplus();
                }
                break;
            }
            else{
                while(count2 < size - 1){
                    yplus();
                }
                break;
            }
        }
    }

}


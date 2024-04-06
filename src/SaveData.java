import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class SaveData {
    String fileName;
    int highScores[] = new int[5];
    String listScore[] = new String[5];
    String DataPath = "src\\saveData";

    SaveData(int id){
        if(id == 1){
            fileName = "FlappySaveData";
        }
        else if(id == 2){
            fileName = "MineSweeperSaveData";
        }
        else if(id == 3){
            fileName = "TetrisSaveData";
        }
        else if(id == 4){
            fileName = "SnakeSaveData";
        }
        else if(id == 5){
            fileName = "FighterSaveData";
        }
        else if(id == 6){
            fileName = "ChessSaveData";
        }
    }

    public void createSaveData(double highScore){
        int score = (int)highScore;
        try{
            FileWriter output = new FileWriter(new File(DataPath, fileName));
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("1st Place: " + score + "\n2nd Place: 0\n3rd Place: 0\n4th Place: 0\n5th Place: 0");
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void createSaveDataFigherChess(){
        int winNum = 0;
        try{
            FileWriter output = new FileWriter(new File(DataPath, fileName));
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("Player 1 wins: " + winNum + "\nPlayer 2 wins: " + winNum);
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void saveDataFighterChess(int num, int id){
        try {
            File f = new File(DataPath, fileName);
            if(!f.isFile()){
                createSaveDataFigherChess();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            reader.close();
            String score = "";
            int scoreOne = 0;
            int scoreTwo = 0;
            for(int j = 15; j < line1.length(); j++){
                if(line1.charAt(j) != '.'){
                    score = score + line1.charAt(j);
                }
                else{
                    break;
                }
            }
            scoreOne = Integer.parseInt(score);

            score = "";
            for(int j = 15; j < line2.length(); j++){
                if(line2.charAt(j) != '.'){
                    score = score + line2.charAt(j);
                }
                else{
                    break;                   
                }
            }
            scoreTwo = Integer.parseInt(score);
            
            if(id == 1){
                scoreOne+=num;
            }
            else if(id == 2){
                scoreTwo+=num;
            }
            FileWriter output = new FileWriter(new File(DataPath, fileName));
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("Player 1 wins: " + scoreOne + "\nPlayer 2 wins: " + scoreTwo);
            writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public double loadHighScore(double highScore){
        try {
            File f = new File(DataPath, fileName);
            if(!f.isFile()){
                //System.out.println("new file");
                createSaveData(highScore);
            }
            //System.out.println("Loading data");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line = reader.readLine();
            String num = "";
            
            for(int j = 11; j < line.length(); j++){
                if(line.charAt(j) != '.'){
                    num = num + line.charAt(j);
                }
                else{
                    break;
                }
            }
            highScore = Double.parseDouble(num);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return highScore;
    }

    
    private void setHighScore(){
        FileWriter output = null;
        try {
            File f = new File(DataPath, fileName);
            output = new FileWriter(f);
            BufferedWriter writer = new BufferedWriter(output);
            
            writer.write(listScore[0]+"\n"+listScore[1]+"\n"+listScore[2]+"\n"+listScore[3]+"\n"+listScore[4]);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void sortHighScore(double score){

        String highScoreSet[] = new String[5];
        highScoreSet[0] = null;
        highScoreSet[1] = null;
        highScoreSet[2] = null;
        highScoreSet[3] = null;
        highScoreSet[4] = null;

        try {
            File f = new File(DataPath, fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            for(int i = 0; i < 5; i++){
                highScoreSet[i] = reader.readLine();
            }
            getIntFromString(highScoreSet);
            int newScore = (int) score;
            if(newScore > highScores[4]){
                highScores[4] = newScore;
            }
            arrangeScores();
            reader.close();
            setHighScore();
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getIntFromString(String highScoreSet[]){
        /*each score is arranged as
            1st_Place:_score[0]
            2nd_Place:_score[1]
            3rd_Place:_score[2]
            4th_Place:_score[3]
            5th_Place:_score[4]

            let _ be a space, and score[1] be the score.
            we know that in the string, which is an array of characters that indexes 0-10 are letters and spaces, but indexes 11+ are
            read numbers.
            thus, we can arrange our for loop to cycle from int j = 11; j < string.length; j++ to gather each int
            */
        for(int i = 0; i < highScoreSet.length; i++){
            String num = "";
            for(int j = 11; j < highScoreSet[i].length(); j++){
                if(highScoreSet[i].charAt(j) != '.'){
                    num = num + highScoreSet[i].charAt(j);
                }
                else{
                    break;
                }
            }
            //System.out.println(num); checked to see if string was valid
            highScores[i] = Integer.parseInt(num);
        }
    }

    public void arrangeScores(){
        int temp = 0;
        for(int i = 0; i < 5; i++){
            for(int j = i + 1; j < 5; j++){
                if(highScores[i] < highScores[j]){
                    temp = highScores[i];
                    highScores[i] = highScores[j];
                    highScores[j] = temp;
                }
            }
        }
        
        for(int i = 0; i < 5; i++){
            if(i == 0){
                listScore[i] = "1st Place: " + highScores[i];
            }
            else if(i == 1){
                listScore[i] = "2nd Place: " + highScores[i];
            }
            else if(i == 2){
                listScore[i] = "3rd Place: " + highScores[i];
            }
            else if(i == 3){
                listScore[i] = "4th Place: " + highScores[i];
            }
            else{
                listScore[i] = "5th Place: " + highScores[i];
            }
        }

        /* final print statement to verify the data is put in correctly
        for(int i = 0; i < 5; i++){
            System.out.println(listScore[i]);
        }
        */
    }
}

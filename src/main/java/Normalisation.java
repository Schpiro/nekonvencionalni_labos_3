import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Normalisation {
    public static final Integer input_size = 32;
    public static double[][] INPUT = new double[input_size][5];
    public static double[][] OUTPUT = new double[input_size][7];
    public static Double pocetno_min = INPUT[0][0];
    public static Double pocetno_max = INPUT[0][0];
    public static Double zadnje_min = INPUT[0][0];
    public static Double zadnje_max = INPUT[0][0];
    public static Double brojslogova_min = INPUT[0][0];
    public static  Double brojslogova_max = INPUT[0][0];
    public static Double length_min = INPUT[0][0];
    public static Double length_max = INPUT[0][0];
    public static Double brojsamoglasnika_min = INPUT[0][0];
    public static Double brojsamoglasnika_max = INPUT[0][0];


    public Normalisation(){
        try {
            File myObj = new File("input.csv");
            Scanner myReader = new Scanner(myObj);
            Integer counter = 0;
            double jedan = 1;
            double nula = 0;
            while (myReader.hasNextLine()) {
                String[] result = (myReader.nextLine()).split(",");
                INPUT[counter][0] = Double.parseDouble(result[0]);
                INPUT[counter][1] = Double.parseDouble(result[1]);
                INPUT[counter][2] = Double.parseDouble(result[2]);
                INPUT[counter][3] = Double.parseDouble(result[3]);
                INPUT[counter][4] = Double.parseDouble(result[4]);
                if(result[5].equals("omlet")) OUTPUT[counter] = new double[]{jedan, nula, nula, nula, nula, nula, nula};
                if(result[5].equals("palačinka")) OUTPUT[counter] = new double[]{nula, jedan, nula, nula, nula, nula, nula};
                if(result[5].equals("tost")) OUTPUT[counter] = new double[]{nula, nula, jedan, nula, nula, nula, nula};
                if(result[5].equals("pekmez")) OUTPUT[counter] = new double[]{nula, nula, nula, jedan, nula, nula, nula};
                if(result[5].equals("žitarice")) OUTPUT[counter] = new double[]{nula, nula, nula, nula, jedan, nula, nula};
                if(result[5].equals("jogurt")) OUTPUT[counter] = new double[]{nula, nula, nula, nula, nula, jedan, nula};
                if(result[5].equals("pita")) OUTPUT[counter] = new double[]{nula, nula, nula, nula, nula, nula, jedan};
                System.out.println(Arrays.toString(OUTPUT[counter]));
                counter++;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Normalisation(String[] lista_worda, String tip) {
        for(String word: lista_worda) {
            Double pocetno_slovo = ((double) (word.toUpperCase().toCharArray())[0]) - 65;
            Double zadnje_slovo = ((double) (word.toUpperCase().toCharArray())[word.length() - 1]) - 65;
            Double broj_slogova = syllablecount(word);
            Double word_length = Double.valueOf(word.length());
            Double broj_samoglanika = samoglasnikcount(word);
            String output = pocetno_slovo+","+zadnje_slovo+","+broj_slogova+","+word_length+","+broj_samoglanika+","+tip+"\n";
            try {
                Files.write(Paths.get("input.csv"), output.getBytes(), StandardOpenOption.APPEND);

            }catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
        }
    }

    public static double[] normalizeInput(String word){
        Double pocetno_slovo = ((double) (word.toUpperCase().toCharArray())[0]) - 65;
        pocetno_slovo = (pocetno_slovo - pocetno_min)/(pocetno_max-pocetno_min);
        Double zadnje_slovo = ((double) (word.toUpperCase().toCharArray())[word.length() - 1]) - 65;
        zadnje_slovo = (zadnje_slovo - zadnje_min)/(zadnje_max-zadnje_min);
        Double broj_slogova = syllablecount(word);
        broj_slogova = (broj_slogova - brojslogova_min)/(brojslogova_max-brojslogova_min);
        Double word_length = Double.valueOf(word.length());
        word_length = (word_length - length_min)/(length_max-length_min);
        Double broj_samoglanika = samoglasnikcount(word);
        broj_samoglanika = (broj_samoglanika - brojsamoglasnika_min)/(brojsamoglasnika_max-brojsamoglasnika_min);
        String output = pocetno_slovo+","+zadnje_slovo+","+broj_slogova+","+word_length+","+broj_samoglanika+","+"test"+"\n";
        /*try {
            Files.write(Paths.get("output.csv"), output.getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }*/
        return new double[]{pocetno_slovo, zadnje_slovo, broj_slogova, word_length, broj_samoglanika};
    }

    private static Double syllablecount(String word){
        Pattern p = Pattern.compile("[aiouy]|(?!^)e(?<!$)");
        Matcher m = p.matcher(word);

        int syllables = 0;
        while (m.find()){
            syllables++;
        }
        //System.out.println(syllables);
        return (double)syllables;
    }
    private static Double samoglasnikcount(String word){
        Pattern p = Pattern.compile("([aeiouAEIOU]{1})");
        Matcher m = p.matcher(word);

        int syllables = 0;
        while (m.find()){
            syllables++;
        }
        //System.out.println(syllables);
        return (double)syllables;
    }

    public static void normalize(){
        pocetno_min = INPUT[0][0];
        pocetno_max = INPUT[0][0];
        zadnje_min = INPUT[0][0];
        zadnje_max = INPUT[0][0];
        brojslogova_min = INPUT[0][0];
        brojslogova_max = INPUT[0][0];
        length_min = INPUT[0][0];
        length_max = INPUT[0][0];
        brojsamoglasnika_min = INPUT[0][0];
        brojsamoglasnika_max = INPUT[0][0];

        for(int i = 0; i < input_size ; i++){
            if(INPUT[i][0] < pocetno_min) pocetno_min = INPUT[i][0];
            if(INPUT[i][0] > pocetno_max) pocetno_max = INPUT[i][0];
            if(INPUT[i][1] < zadnje_min) zadnje_min = INPUT[i][1];
            if(INPUT[i][1] > zadnje_max) zadnje_max = INPUT[i][1];
            if(INPUT[i][2] < brojslogova_min) brojslogova_min = INPUT[i][2];
            if(INPUT[i][2] > brojslogova_max) brojslogova_max = INPUT[i][2];
            if(INPUT[i][3] < length_min) length_min = INPUT[i][3];
            if(INPUT[i][3] > length_max) length_max = INPUT[i][3];
            if(INPUT[i][4] < brojsamoglasnika_min) brojsamoglasnika_min = INPUT[i][4];
            if(INPUT[i][4] > brojsamoglasnika_max) brojsamoglasnika_max = INPUT[i][4];
        }
        for(int i = 0; i < input_size ; i++){
            INPUT[i][0] = (INPUT[i][0] - pocetno_min)/(pocetno_max-pocetno_min);
            INPUT[i][1] = (INPUT[i][1] - zadnje_min)/(zadnje_max-zadnje_min);
            INPUT[i][2] = (INPUT[i][2] - brojslogova_min)/(brojslogova_max-brojslogova_min);
            INPUT[i][3] = (INPUT[i][3] - length_min)/(length_max-length_min);
            INPUT[i][4] = (INPUT[i][4] - brojsamoglasnika_min)/(brojsamoglasnika_max-brojsamoglasnika_min);
        }
    }
}

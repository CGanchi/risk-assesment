// STUDENT VERSION

// Risk Assessment.java
// Name: Camron Ganchi
// Date:
// Adapted from Jillian Cardamon 6/6/18

/* Idea (from article): each inmate is assigned a true probability of 
   reoffending based on normal distribution mean and std dev from the 
   Ohio data - weighted avg = 21.79, std dev = 13.85 then the computer 
   uses those probabilities to decide whether each dot will actually 
   reoffend a dot with a 75% risk will on average reoffend 3/4 times no 
   one knows the true chance of reoffending so risk assessment tries to 
   estimate it normal distribution with mean set at true risk and std 
   dev 0.15 can use random.gauss(mu, sigma) for normal distribution
*/

import java.util.*;

public class Risk_Assessment{
    public static double lowCutOff = 0;
    public static double mediumCutOff = 0;
    public static double highCutOff = 43;

    public static Defendant[] defendants;
    public static ArrayList<Defendant> awardedParole;
    public static ArrayList<Defendant> deniedParole;
    
    public static void printDefendantList(Defendant[] l){
	// given an array of defendants, print out each defendant
	// to help debugging

	for (Defendant d: l)
	    System.out.println(d);
    }

    public static Defendant[] createDefendants(){
    	awardedParole = new ArrayList<>();
        deniedParole = new ArrayList<>();
    	defendants = new Defendant[100];
        Random rand = new Random();
        double mean = 21.79;
        double stdDeviation = 13.85;

        
        //calculate recidivism rates
        for (int i = 0; i < defendants.length; i++) {
            double trueRisk = rand.nextGaussian() * stdDeviation + mean;
            trueRisk = Math.max(0, Math.min(trueRisk, 43)); // Ensuring the value is within 0 and 43

            double reoffendProbability = rand.nextDouble() * 43;
            boolean willReoffend = reoffendProbability <= trueRisk;

            defendants[i] = new Defendant(trueRisk, willReoffend);
        }

        return defendants;
    }

    public static void chooseCutOffs(){
    	
    	//getting cutoffs from user
    	 Scanner scanner = new Scanner(System.in);
    	    System.out.println("Enter cutoff for low risk (1-99): ");
    	    int num = scanner.nextInt();
    	    lowCutOff = num * 43 / 100.0;
    	    
    	    System.out.print("Enter cutoff for medium risk (");
    	    System.out.println(num +1 + "-99): ");
    	    mediumCutOff = scanner.nextInt() * 43 / 100.0;

	
    }

    public static void assessDefendants(){
    	
    	// keep track of which defendants were granted and denied parole and move them 
    	// to their respective arrayLists.
    	for (Defendant defendant : defendants) {
            defendant.assess(lowCutOff, mediumCutOff, highCutOff);
            defendant.decideParole();
            if (defendant.isGivenParole()) {
                awardedParole.add(defendant);
            } else {
                deniedParole.add(defendant);
            }
        }
    }
    

    public static void paroleStats(){
    	
    	//count up and display information to user.
    	int totalReoffend = 0;
        int wrongfullyDenied = 0;
        for (Defendant d : defendants) {
            if (d.willOffendAgain() && d.isGivenParole()) {
                totalReoffend++;
            }
            if (!d.willOffendAgain() && !d.isGivenParole()) {
                wrongfullyDenied++;
            }
        }
        System.out.println("Total defendants: " + defendants.length);
        System.out.println("Defendants granted parole: " + awardedParole.size());
        System.out.println("Defendants denied parole: " + deniedParole.size());
        double percreoff = ((double) totalReoffend/awardedParole.size())*100;
        double wrongdeni = ((double) wrongfullyDenied/deniedParole.size())*100;
        System.out.println("Reoffended after parole: " + percreoff + "%");
        System.out.println("Wrongfully denied parole: " + wrongdeni + "%");
    }
        
    public static void main(String[] args){
    	   // Step 1: Create defendants
        defendants = createDefendants();

        // Step 2: Choose cutoffs for risk categories
        chooseCutOffs();

        // Step 3: Assess defendants and decide on parole
        assessDefendants();

        // Step 4: Display parole statistics
        paroleStats();
    }
    
	    
	
    
	



}
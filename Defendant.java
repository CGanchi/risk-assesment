// STUDENT VERSION

// Defendant.java
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

public class Defendant {
    private double trueRisk;
    private boolean willReoffend;
    private double assessmentScore;
    private String riskCategory;
    private boolean givenParole;

    Defendant(double risk, boolean reoffend) {
        trueRisk = risk;
        willReoffend = reoffend;
        assessmentScore = 0;
        riskCategory = null;
        givenParole = false;
    }

    public String toString() {
        return "Assessment Score: " + this.assessmentScore + '\n' +
               "Risk Category: " + this.riskCategory + '\n' +
               "Will Reoffend: " + this.willReoffend + '\n' +
               "Given Parole: " + this.givenParole;
    }

    // Accessor methods
    public double getTrueRisk() { return trueRisk; }
    public boolean willOffendAgain() { return willReoffend; }
    public double getAssessmentScore() { return assessmentScore; }
    public String getRiskCategory() { return riskCategory; }
    public boolean isGivenParole() { return givenParole; }

    // Mutator methods
    public void assess(double low, double med, double high) {
        // Simulate assessment score
        Random random = new Random();
        assessmentScore = trueRisk + (0.15 * random.nextGaussian());


        // Assign risk category based on assessmentScore
        if (assessmentScore <= low) {
            riskCategory = "low";
        } else if (assessmentScore <= med) {
            riskCategory = "medium";
        } else {
            riskCategory = "high";
        }
    }

    public void decideParole() {
        Random random = new Random();
        if (riskCategory.equals("low")) {
            givenParole = true;
        } else if (riskCategory.equals("high")) {
            givenParole = false;
        } else {
            // Medium risk has a 50-50 chance of parole
            givenParole = random.nextBoolean();
        }
    }
}
		
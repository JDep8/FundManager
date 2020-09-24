// COSC2452: Assignment 3 / Jacob Depares | S3851480

// Have broken the funds information and values into its own class. This stops the need
// for multiple arrays and allows us to control what other classes have access to. We 
// do have an array for the seasonValues in this class, we could have made this its own
// class however as the values are tied to the fund it’s more appropriate to keep them 
// as one class. 

public class Fund {

	// Using a string for the fund name, no other variable will be adequate here
	// thus a string is appropriate. Naming the variable after what it stores, the
	// funds name.

	private String fundName;

	// Using a char to track the status for each fund. We could use an int and set 1
	// for active and 0 for inactive however we have chosen char as we want to
	// display “Y” or “N” for the user to know the status. This will save extra code
	// in the program. Naming the variable after the information it tracks.

	private char fundStatus;

	// For the current and max number of seasons the most appropriate data type is
	// an int, we will never need to store decimals etc here thus the data type
	// choice. The variables track the current season for the fund and the max
	// Number of seasons the seasonValue array can store thus they are named as
	// there functions.

	private int currentSeason;
	private int maxNoSeason;

	// Using an array to store the season Values, as mentioned above this could be
	// its own class however as it is directly tied to the fund it is unnecessary.
	// Using an array as we need to account for indefinite number of seasons. The
	// array is of int data type and not float / double as this will appear in
	// scientific notation on a list which we do not want. For the values we are
	// dealing with dropping the cents is not an issue. Naming the array again after
	// the data it stores, the value for the season.

	private int[] seasonValue;

	public Fund(String fundName, char fundStatus, int maxNoSeason) {

		// Using the method to obtain all variable information except the currentSeason,
		// this will always be zero when creating a new fund thus it would be irrelevant
		// to ask for this.

		this.fundName = fundName;
		this.fundStatus = fundStatus;

		this.maxNoSeason = maxNoSeason;
		this.seasonValue = new int[maxNoSeason];
		this.currentSeason = 0;

	}

	public String getFundName() {
		return fundName;
	}

	public char getFundStatus() {
		return this.fundStatus;
	}

	public void setFundStatus(char fundStatus) {
		this.fundStatus = fundStatus;
	}

	public int getSeasonValue(int season) {
		return this.seasonValue[season];
	}

	public void setSeasonValue(int season, int value) {

		// IF statement here to check if the array size needs to be expanded, as
		// we are only doing one check a loop would be unnecessary.

		if (season == this.maxNoSeason) {

			// We haven’t created another method for this block of code as this is the only
			// time we would need to expand the array.

			// We have expanded the array by creating a copy of it in temporary arrays
			// with the new desired size, then resizing the original array and copying it
			// back. We could have used araylist to simplify this process however this would
			// have caused limitation in other aspects of out program.

			int[] temporarySeasonValue = new int[this.maxNoSeason + 12];

			// For the transfer of data from one array to the temporary as this is a
			// duplication of code, we could create a separate method for this and put it in
			// a loop to account for all the arrays we need to expand. However, as the
			// duplication is only once the creation of another method is not justified.

			int seasonCount = 0;

			while (seasonCount < this.maxNoSeason) {
				temporarySeasonValue[seasonCount] = this.seasonValue[seasonCount];
				seasonCount += 1;
			}

			seasonCount = 0;

			this.seasonValue = new int[this.maxNoSeason + 12];

			while (seasonCount < this.maxNoSeason) {

				this.seasonValue[seasonCount] = temporarySeasonValue[seasonCount];
				seasonCount += 1;
			}

			this.maxNoSeason += 12;

		}

		this.seasonValue[season] = value;
	}

	public int getCurrentSeason() {
		return this.currentSeason;
	}

	public void setCurrentSeason(int currentSeason) {
		this.currentSeason = currentSeason;
	}

	public int getMaxNoSeason() {
		return this.maxNoSeason;
	}

}

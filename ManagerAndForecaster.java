// COSC2452: Assignment 3 / Jacob Depares | S3851480

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ManagerAndForecaster {
	// CODE REQUIRMENTS: All code requirements have been complete.

	private GTerm gt;

	// We are keeping track of both the max number of funds and seasons and the
	// current number of funds. We have used an int value here as we don’t need to
	// account for decimals. We also don’t need an array of sorts here. Naming the
	// variables here to indicate what they represent to ensure the program is as
	// readable as possible.

	private int maxNumFunds;
	private int currentNumberOfFunds;

	// Using an int array to keep track of if the list in the program have been
	// populated. We could create a variable for every list however this would begin
	// to become convoluted to manage. Choosing an int variable here as the default
	// initialization values will be 0 which is ideal, we could use almost any data
	// type such as char however that would mean we would need to add code to
	// initialize the default values. Again we have named the variable to match what
	// it does, keeping track if the list is populated or not. We don’t want to be
	// making this array its own class as it is only relevant to this class.

	private int listPopulated[];

	// Creating an array to store the number of seasons in each average for use in
	// the analytic and projection methods. We want to use an int value here as the
	// number needs to be a full number. We could use three int variables however
	// this would create unnecessary variables and mean if we would like to expand
	// the number of elements we are required to hardcode this through the rest of
	// the code. Sticking with the method off naming by describing what the variable
	// does, it keeps track of the number of seasons in the average calculations
	// thus the name. We don’t want to be making this array its own class as it is
	// only relevant to this class.

	private int[] noOfSeasonsInAvg;

	// The final array to keep track of the fund information, we have used the data
	// type Fund created through the second class. This will encapsulate all the
	// funds information and its results. An alternative would be to create arrays
	// for all the variables referenced in the Fund class however this begins to
	// become convoluted fast. Naming the array fund as that is what it is keeping
	// track off.

	private Fund[] fund;

	public void ObjfundManagementAndForecaster() {

		// Hard codding the below arrays lengths here, we could take these inputs from
		// the method however this would be problematic. If we allow the length off the
		// array to be changed here through the method anything other then3 and 5
		// respectively could crash the program.

		this.noOfSeasonsInAvg = new int[3];
		this.listPopulated = new int[5];
		this.currentNumberOfFunds = 0;

		// The value of maxNumFunds and the elements in the array noOfSeasonsInAvg are
		// stored in the config file. We are loading the values here before populating
		// the gterm. We want to do this so the fields referencing the noOfSeasonsInAvg
		// are with the information the user last entered. Below we will be populating
		// all the saved funds information, thus we have taken the maxNumFunds from the
		// config file to avoid an unnecessary expansion of the array.

		BufferedReader inFile = null;
		try {
			inFile = new BufferedReader(new FileReader("config.txt"));

			String currLine = inFile.readLine();
			String[] configValues = currLine.split(",");

			this.maxNumFunds = Integer.parseInt(configValues[0]);
			this.noOfSeasonsInAvg[0] = Integer.parseInt(configValues[1]);
			this.noOfSeasonsInAvg[1] = Integer.parseInt(configValues[2]);
			this.noOfSeasonsInAvg[2] = Integer.parseInt(configValues[3]);

			currLine = inFile.readLine();

			inFile.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		this.fund = new Fund[this.maxNumFunds];

		this.gt = new GTerm(1900, 1000);

		this.gt.setBackgroundColor(2, 15, 16);
		this.gt.setFontColor(7, 212, 225);
		this.gt.setFontStyle(Font.ROMAN_BASELINE);

		this.gt.setFontSize(45);
		this.gt.setXY(580, 120);
		this.gt.println("Fund Management & Forecaster");

		this.gt.setXY(890, 200);
		this.gt.setFontSize(20);
		this.gt.setFontColor(Color.BLACK);
		this.gt.addButton("Welcome", this, "welcome");

		// Reference to image [1]

		this.gt.setXY(0, 300);
		this.gt.addImageIcon("welcomeImage.png");

	}

	public void welcome() {

		// When adding the elements to the page for the fund we have added them in
		// groups for each section. This has meant we have had to change the font color
		// and size a few more times than necessary however as we are interacting with
		// the text areas and list so frequently throughout the program it allows the
		// index numbers to be in the most logical order in the way they display on the
		// screen to help with the programming.

		this.gt.clear();

		this.gt.setFontColor(7, 212, 225);
		this.gt.setFontSize(18);

		this.gt.setXY(0, 0);
		this.gt.println("Fund Operations:");
		this.gt.setXY(300, 0);
		this.gt.println("Fund Status:");
		this.gt.setXY(560, 0);
		this.gt.println("Amend Result:");
		this.gt.setXY(1335, 0);
		this.gt.println("No. of seasons in avg:");
		this.gt.setXY(1700, 0);
		this.gt.println("Help:");
		this.gt.setXY(10, 967);
		this.gt.println("Fund Management & Forecasting V3.0");

		this.gt.setXY(1335, 25);
		this.gt.println("A:");
		this.gt.setXY(1415, 25);
		this.gt.println("B:");
		this.gt.setXY(1475, 25);
		this.gt.println("C:");

		this.gt.setFontColor(Color.BLACK);
		this.gt.setFontSize(12);
		this.gt.setXY(1380, 25);
		this.gt.addTextArea(Integer.toString(this.noOfSeasonsInAvg[0]), 30, 30);
		this.gt.setXY(1440, 25);
		this.gt.addTextArea(Integer.toString(this.noOfSeasonsInAvg[1]), 30, 30);
		this.gt.setXY(1500, 25);
		this.gt.addTextArea(Integer.toString(this.noOfSeasonsInAvg[2]), 30, 30);

		this.gt.setFontSize(15);
		this.gt.setXY(0, 25);
		this.gt.addButton("Add Fund", this, "addFund");
		this.gt.addButton("Add Result", this, "addResult");

		this.gt.setXY(300, 25);
		this.gt.addButton("Active", this, "statusActive");
		this.gt.addButton("Inactive", this, "statusInactive");

		this.gt.setXY(560, 25);
		this.gt.addButton("Change Result", this, "changeResult");
		this.gt.addButton("Remove Result", this, "removeResult");

		this.gt.setXY(1545, 25);
		this.gt.addButton("Update", this, "updateNoOfSeasonsInAvg");

		this.gt.setXY(1700, 25);
		this.gt.addButton("Getting Started", this, "gettingStarted");

		this.gt.setXY(0, 575);
		this.gt.setFontSize(18);
		this.gt.setFontColor(7, 212, 225);

		this.gt.println("Combined analytics, forecasting and comparisons:");

		this.gt.setXY(0, 600);
		this.gt.setFontSize(15);
		this.gt.println("Combined Analytics:");

		this.gt.setFontColor(Color.BLACK);
		this.gt.setFontSize(14);

		this.gt.setXY(0, 645);
		this.gt.addTextArea("Active Fund Value", 160, 25);
		this.gt.addTextArea("", 90, 25);

		this.gt.setXY(0, 675);
		this.gt.addTextArea("Profit", 160, 25);
		this.gt.addTextArea("", 90, 25);

		this.gt.setXY(0, 700);
		this.gt.addTextArea("Growth", 160, 25);
		this.gt.addTextArea("", 90, 25);

		this.gt.setXY(0, 730);
		this.gt.addTextArea("Last " + Integer.toString(this.noOfSeasonsInAvg[0]) + " Season Avg", 160, 25);
		this.gt.addTextArea("", 90, 25);

		this.gt.setXY(0, 755);
		this.gt.addTextArea("Last " + Integer.toString(this.noOfSeasonsInAvg[2]) + " Season Avg", 160, 25);
		this.gt.addTextArea("", 90, 25);

		this.gt.setXY(0, 780);
		this.gt.addTextArea("Last " + Integer.toString(this.noOfSeasonsInAvg[2]) + " Season Avg", 160, 25);
		this.gt.addTextArea("", 90, 25);

		this.gt.setXY(0, 810);
		this.gt.addTextArea("Max Drawdown", 160, 25);
		this.gt.addTextArea("", 90, 25);

		this.gt.setXY(300, 600);
		this.gt.setFontSize(15);
		this.gt.setFontColor(7, 212, 225);
		this.gt.println("Forecasting:");

		this.gt.setFontColor(Color.BLACK);
		this.gt.setFontSize(14);

		this.gt.setXY(370, 645);
		this.gt.addTextArea(Integer.toString(this.noOfSeasonsInAvg[0]) + " Season Avg Forecast", 200, 25);
		this.gt.addTextArea(Integer.toString(this.noOfSeasonsInAvg[1]) + " Season Avg Forecast", 200, 25);
		this.gt.addTextArea(Integer.toString(this.noOfSeasonsInAvg[2]) + " Season Avg Forecast", 200, 25);

		this.gt.setXY(300, 670);
		this.gt.addList(70, 275, null, null);
		this.gt.addList(200, 275, null, null);
		this.gt.addList(200, 275, null, null);
		this.gt.addList(200, 275, null, null);

		this.gt.setXY(1020, 600);
		this.gt.setFontColor(7, 212, 225);
		this.gt.setFontSize(15);
		this.gt.println("Fund Comparison       | Profit/Down Ratios: |");

		this.gt.setXY(1020, 620);
		this.gt.println("Fund Name:            |Avg $$     Count     |Exposure");

		this.gt.setFontSize(14);
		this.gt.setXY(1020, 645);
		this.gt.setFontColor(Color.BLACK);
		this.gt.addList(200, 300, null, null);
		this.gt.addList(100, 300, null, null);
		this.gt.addList(100, 300, null, null);
		this.gt.addList(100, 300, null, null);

		this.gt.setXY(10, 845);
		this.gt.addImageIcon("JDLogo.png");

		// Once we have the main elements of the program loaded, we will read though the
		// Fund.txt file to check if there are any funds. We will then populate any fund
		// information by calling the appropriate methods. We have used a while loop to
		// read through every line in the file. Then within there is another while loop
		// to cycle through the results ensuring both the fund information and its
		// results are populated. We have had to use a loop as we have an indefinite
		// number of values to account for. An alternative would be to segregate the
		// entirety of the below block of code into a method however this would be the
		// only time we are reading from the file thus it will only be referenced once
		// creating unnecessary code.

		BufferedReader inFile = null;
		try {
			inFile = new BufferedReader(new FileReader("Fund.txt"));
			String currLine = inFile.readLine();
			int fundNumber = 0;

			while (currLine != null) {

				String[] fundValues = currLine.split(",");

				newFund(fundValues[0], fundValues[1].charAt(0), Integer.parseInt(fundValues[3]), 'Y');
				currLine = inFile.readLine();

				int season = 0;
				while (season < Integer.parseInt(fundValues[2])) {
					newResult(fundNumber, Integer.parseInt(fundValues[season + 4]), 'Y');
					season += 1;
				}
				fundNumber += 1;
			}
			inFile.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void addFund() {

		// This Method simple calls the newFund Method. The reason we have done it this
		// way and not just used the button to call the newFund method direct is we need
		// to account for the fact when the program first opens it will be adding the
		// funds to the program. In this case we already have the fund information and
		// we don’t want to be asking the user for it as it is populating. How we
		// managed this is explained in the newFund method.

		newFund(null, 'Y', 12, 'N');
	}

	public void newFund(String fundName, char fundStatus, int maxNoSeason, char fromFile) {

		// The reasoning behind the 4 variables in the method: if the method is being
		// called at the beginning of the program populating the information through the
		// Fund. Txt file we already have the fund name, status and its max number of
		// seasons to use. The fromFile variable will be used to tell the method to not
		// ask for user inputs if we are populating from the fund file. We could create
		// two methods to do this however this would be a lot more code in comparison.

		// Performing user input validation here to ensure the user doesn’t input
		// nothing for the fund name. A simple while loop cycling through the attempts,
		// we could use a few if statements here however this would be a lot of
		// unnecessary code.

		int userInputAttempts = 0;
		if (fromFile == 'N') {
			fundName = this.gt.getInputString("Please enter the fund name?");

			while (userInputAttempts < 3 && fundName.equals("")) {
				fundName = this.gt.getInputString("Error: Name can not be empty!\n\n Please enter the fund name?");
				userInputAttempts += 1;
			}

		}

		if (userInputAttempts < 3) {

			// We need to calculate the spacing on the X axis to add all our page elements
			// to ensure they don’t overlap with previous funds and the layout is
			// consistent. Also needing to use an IF statement to check if this is our first
			// fund as we don’t want to apply the arithmetic in this case. We could as an
			// alternative use a global variable to account for this which would also negate
			// the need for the IF statement however this would be an unnecessary global
			// variable which is not used anywhere else in the program.

			int location = 0;
			if (this.currentNumberOfFunds != 0) {
				location = (this.currentNumberOfFunds * 300);
			}

			// When adding the elements to the page for the fund we have added them in
			// groups for each section. This has meant we have had to change the font color
			// and size a few more times than necessary however as we are interacting with
			// the text areas and list so frequently throughout the program it allows the
			// index numbers to be in the most logical order in the way they display on the
			// screen to help with reading the code.

			this.gt.setFontStyle(Font.ROMAN_BASELINE);

			this.gt.setXY(location, 60);
			this.gt.setFontColor(Color.BLACK);
			this.gt.setFontSize(14);
			this.gt.addTextArea(String.valueOf(fundStatus), 15, 25);

			this.gt.setFontColor(7, 212, 225);
			this.gt.setFontStyle(Font.BOLD);
			this.gt.setFontSize(18);
			this.gt.println(" " + (this.currentNumberOfFunds + 1) + " : " + fundName);

			this.gt.setFontSize(15);
			this.gt.setXY(location, 80);

			this.gt.setFontStyle(Font.PLAIN);
			this.gt.println("Season:  Value:");

			this.gt.setFontColor(Color.BLACK);
			this.gt.setXY(location, 105);
			this.gt.addList(270, 300, null, null);

			this.gt.setFontColor(7, 212, 225);
			this.gt.setFontStyle(Font.ROMAN_BASELINE);

			this.gt.setXY(location, 410);
			this.gt.setFontSize(18);
			this.gt.println("Fund Analytics:");

			this.gt.setFontColor(Color.BLACK);
			this.gt.setFontSize(14);

			this.gt.setXY(location, 435);
			this.gt.addTextArea("Fund Profit", 160, 25);
			this.gt.addTextArea("", 90, 25);

			this.gt.setXY(location, 460);
			this.gt.addTextArea("Fund Growth", 160, 25);
			this.gt.addTextArea("", 90, 25);

			this.gt.setXY(location, 490);
			this.gt.addTextArea("Last " + this.noOfSeasonsInAvg[0] + " Season Avg", 160, 25);
			this.gt.addTextArea("", 90, 25);

			this.gt.setXY(location, 515);
			this.gt.addTextArea("Last " + this.noOfSeasonsInAvg[1] + " Season Avg", 160, 25);
			this.gt.addTextArea("", 90, 25);

			this.gt.setXY(location, 540);
			this.gt.addTextArea("Last " + this.noOfSeasonsInAvg[2] + " Season Avg", 160, 25);
			this.gt.addTextArea("", 90, 25);

			this.fund[this.currentNumberOfFunds] = new Fund(fundName, fundStatus, maxNoSeason);
			this.currentNumberOfFunds += 1;

			// IF statement here to check if the array size needs to be expanded, as
			// we are only doing one check a loop would be unnecessary.

			if (this.currentNumberOfFunds == this.maxNumFunds) {
				int maxNumFunds = this.maxNumFunds + 5;

				// We haven’t created another method for this block of code as this is the only
				// time we would need to expand the array.

				// We have expanded the array by creating a copy of it in temporary arrays
				// with the new desired size, then resizing the original array and copying it
				// back. We could have used araylist to simplify this process however this would
				// have caused limitation in other aspects of out program.

				Fund[] temporaryFund = new Fund[maxNumFunds];

				// For the transfer of data from one array to the temporary as this is a
				// duplication of code, we could create a separate method for this and put it in
				// a loop to account for all the arrays we need to expand. However, as the
				// duplication is only once the creation of another method is not justified.

				int fundNumber = 0;

				while (fundNumber < this.currentNumberOfFunds) {
					temporaryFund[fundNumber] = this.fund[fundNumber];
					fundNumber += 1;
				}

				fundNumber = 0;

				this.fund = new Fund[maxNumFunds + 5];

				while (fundNumber < this.currentNumberOfFunds) {

					this.fund[fundNumber] = temporaryFund[fundNumber];
					fundNumber += 1;
				}

				this.maxNumFunds = maxNumFunds;
				writeConfigFile();

			}

			// Another IF statement to check if the fund is being added through the file or
			// through user input, we don’t want to write the same information back to the
			// file. Only if this is a new fund we will then proceed to update the relevant
			// file.

			if (fromFile == 'N') {
				writeFundFile();
			}
		} else {
			this.gt.showMessageDialog("Unable to add fund.\nPlease try again!");
		}
	}

	public void addResult() {

		// This Method simple calls the newResult Method. The reason we have done it
		// this way and not just used the button to call the newResult method direct is
		// we need to account for the fact when the program first opens it will be
		// adding the funds and there results to the program. In this case we already
		// have the result information and we don’t want to be asking the user for it as
		// it is populating. How we managed this is explained in the newResult method.

		newResult(0, 0, 'N');
	}

	public void newResult(int fundNumber, int valueOfSeason, char fromFile) {

		// The reasoning behind the 3 variables in the method: if the method is being
		// called at the beginning of the program populating the information through the
		// Fund.Txt file we already have the season values stored. We will use the fund
		// number to ensure the values are allocated to the correct season for the fund.
		// The fromFile variable will be used to tell the method to not ask for user
		// inputs if we are populating from the fund file. We could create two methods
		// to do this however this would be a lot more code in comparison

		// Using an If statement to check if there any funds added. This is simple to
		// make the user experience better. We could in theory let the user continue
		// without the check it wouldn’t crash the code however this would lead the user
		// to completing unnecessary steps that will have no action.

		if (currentNumberOfFunds == 0) {
			gt.showMessageDialog("Please add a fund before proceeding.");
		} else {
			int userInputAttempts = 0;
			if (fromFile == 'N') {
				fundNumber = Integer.parseInt(this.gt.getInputString("Which fund would we like to add a result to? \n\n"
						+ "Fund number to the left off the fund name."));
				// BUG: If the user doesn’t input anything or inputs anything but an int value
				// the program will crash.

				// We are using a while loop with an int variable to limit the number of
				// attempts the user can have at inputting a valid value. We are limiting the
				// number of user attempts to account for the fact the user may have accidently
				// clicked the button or continuously enters the wrong value. Checking the input
				// allows us to ensure it is valid and won’t crash the code. An alternative to
				// the loop method would be an IF statement however this would only allow us to
				// check once or we could not check at all which could lead the code to crash.

				while ((fundNumber > this.currentNumberOfFunds || fundNumber < 0) && userInputAttempts < 3) {
					fundNumber = Integer.parseInt(this.gt.getInputString("Error: Unable to find relevant fund number.\n"
							+ "Which fund would we like to add a result to? \n\n"
							+ "Fund number to the left off the fund name."));
					userInputAttempts += 1;
				}
				// BUG: If the user doesn’t input anything or inputs anything but an int value
				// the program will crash.

				fundNumber -= 1;

				// Using two if statements, firstly to check if the user attempts have exceed
				// the limit then checking if the status is active for the fund before
				// proceeding. We need to check the limit first as if the user never enters a
				// valid input, when we try and check the fund status the code will crash. An
				// alternative to this would be to reset the fundNumber to zero if the user
				// never inputs a valid entry however this would cause the dialogue box to
				// display if the first fund is indeed inactive which we don’t want.

				if (userInputAttempts != 3) {
					if (this.fund[fundNumber].getFundStatus() == 'N') {
						this.gt.showMessageDialog("Fund currently inactive, please activate to add result!\n"
								+ "You can check the fund status to the left off the fund number:\n\nY = Active / N = Inactive");
						userInputAttempts = 10;
					}
				}
			}

			if (userInputAttempts < 3) {

				// As we are taking a user input, we want to account for invalid entries
				// occurring from things such as mistyping or copy and paste from an excel for
				// example. Alternatively, we could not do this however this does have the
				// possibility to crash the program.

				// Also we are converting decimal values to int, the reason for this is that
				// when we get into larger numbers it moves into scientific notation on the
				// list. This does not look good and something to avoid. It is a trade off
				// though as we will loose the cent values from the user input however this is
				// not an issue as we are dealing with large values and the cent will have
				// almost no impact throughout the program.

				String value;
				if (fromFile == 'N') {
					value = this.gt
							.getInputString("Result for season " + (this.fund[fundNumber].getCurrentSeason() + 1));

				} else {
					value = Integer.toString(valueOfSeason);
				}

				// performing input validation that once we remove all but the numerical values
				// we have a valid input. We do not want to proceed if the input is not valid as
				// it will crash the code. We will then add the value to the relevant fund
				// season and list. We are using a calculation to determine the list spacing. .
				// We are using a calculation to determine the list spacing to ensure it is
				// consistent depending on the number of did gets in the season. We could have
				// two lists for this to avoid this however there isn’t a way to have the list
				// side by side scroll as one.

				if (!value.replaceAll("[^0-9\\.]", "").equals("") && !value.replaceAll("[^0-9\\.]", "").equals(".")) {

					double dvalue = Double.valueOf(value.replaceAll("[^0-9\\.]", ""));
					valueOfSeason = (int) Math.round(dvalue);

					this.fund[fundNumber].setSeasonValue(this.fund[fundNumber].getCurrentSeason(), valueOfSeason);

					String listSpacing = "        ";
					String season = Integer.toString(this.fund[fundNumber].getCurrentSeason() + 1);
					listSpacing = listSpacing.substring(0, listSpacing.length() - season.length());

					this.gt.addElementToList((fundNumber) + 8, season + listSpacing + "$ "
							+ this.fund[fundNumber].getSeasonValue(this.fund[fundNumber].getCurrentSeason()));

					this.fund[fundNumber].setCurrentSeason(this.fund[fundNumber].getCurrentSeason() + 1);

					// Another IF statement to check if the fund is being added through the file or
					// through user input, we don’t want to write the same information back to the
					// file. Only if this is a new fund we will then proceed to update the relevant
					// file.

					if (fromFile == 'N') {
						writeFundFile();
					}

					fundAnalytics(fundNumber);
					combinedForcastignAndProjections();

				} else {
					this.gt.showMessageDialog("Unable to add result.\nPlease try again!");
				}
			} else {
				this.gt.showMessageDialog("Unable to add result.\nPlease try again!");
			}

		}
	}

	public void statusActive() {

		// Using an If statement to check if there any funds added. This is simple to
		// make the user experience better. We could in theory let the user continue
		// without the check it wouldn’t crash the code however this would lead the user
		// to completing unnecessary steps that will have no action.

		if (this.currentNumberOfFunds == 0) {
			this.gt.showMessageDialog("Please add a fund before proceeding.");
		} else {

			int fundNumber = Integer.parseInt(this.gt.getInputString(
					"Which fund would you like to set to active? \n\n" + "Fund number to the left off the fund name."));
			// BUG: If the user doesn’t input anything or inputs anything but an int value
			// the program will crash.

			// We are using a while loop with an int variable to limit the number of
			// attempts the user can have at inputting a valid value. We are limiting the
			// number of user attempts to account for the fact the user may have accidently
			// clicked the button or continuously enters the wrong value. Checking the input
			// allows us to ensure it is valid and won’t crash the code. An alternative to
			// the loop method would be an IF statement however this would only allow us to
			// check once or we could not check at all which could lead the code to crash.

			int userInputAttempts = 0;
			while ((fundNumber > this.currentNumberOfFunds || fundNumber < 0) && userInputAttempts < 3) {
				fundNumber = Integer.parseInt(this.gt.getInputString("Error: Unable to find relevant fund number.\n"
						+ "Which fund would you like to set to active? \n\n"
						+ "Fund number to the left off the fund name."));
				userInputAttempts += 1;
			}
			// BUG: If the user doesn’t input anything or inputs anything but an int value
			// the program will crash.

			fundNumber -= 1;

			// Using an if / else if / else statement here as we have three possible
			// outcomes based on the user input. We could use entrenched if statements
			// however this would result in unnecessarily code. Not to mention the if / else
			// if / else layout is much more readable for other programs and shows clear
			// outcomes based on the user input.

			if (this.fund[fundNumber].getFundStatus() == 'Y') {
				this.gt.showMessageDialog(
						"Fund already active.\nYou can check the fund status to the left off the fund number:\n\nY = Active / N = Inactive");
			}

			else if (userInputAttempts < 3) {

				this.fund[fundNumber].setFundStatus('Y');
				this.gt.setTextInEntry(((fundNumber) * 11 + 20), "Y");
				writeFundFile();
				combinedForcastignAndProjections();

			} else {
				this.gt.showMessageDialog("Unable to process request.\nPlease try again!");
			}

		}
	}

	public void statusInactive() {

		// Using an If statement to check if there any funds added. This is simple to
		// make the user experience better. We could in theory let the user continue
		// without the check it wouldn’t crash the code however this would lead the user
		// to completing unnecessary steps that will have no action.

		if (this.currentNumberOfFunds == 0) {
			this.gt.showMessageDialog("Please add a fund before proceeding.");
		} else {

			int fundNumber = Integer
					.parseInt(this.gt.getInputString("Which fund would you like to set to inactive? \n\n"
							+ "Fund number to the left off the fund name."));
			// BUG: If the user doesn’t input anything or inputs anything but an int value
			// the program will crash.

			// We are using a while loop with an int variable to limit the number of
			// attempts the user can have at inputting a valid value. We are limiting the
			// number of user attempts to account for the fact the user may have accidently
			// clicked the button or continuously enters the wrong value. Checking the input
			// allows us to ensure it is valid and won’t crash the code. An alternative to
			// the loop method would be an IF statement however this would only allow us to
			// check once or we could not check at all which could lead the code to crash.

			int userInputAttempts = 0;
			while ((fundNumber > this.currentNumberOfFunds || fundNumber < 0) && userInputAttempts < 3) {
				fundNumber = Integer.parseInt(this.gt.getInputString("Error: Unable to find relevant fund number.\n"
						+ "Which fund would you like to set to inactive? \n\n"
						+ "Fund number to the left off the fund name."));
				userInputAttempts += 1;
			}
			// BUG: If the user doesn’t input anything or inputs anything but an int value
			// the program will crash.

			fundNumber -= 1;

			// Using an if / else if / else statement here as we have three possible
			// outcomes based on the user input. We could use entrenched if statements
			// however this would result in unnecessarily code. Not to mention the if / else
			// if / else layout is much more readable for other programs and shows clear
			// outcomes based on the user input.

			if (this.fund[fundNumber].getFundStatus() == 'N') {
				this.gt.showMessageDialog(
						"Fund already inactive.\nYou can check the fund status to the left off the fund number:\n\nY = Active / N = Inactive");
			}

			else if (userInputAttempts < 3) {

				this.fund[fundNumber].setFundStatus('N');
				this.gt.setTextInEntry(((fundNumber) * 11 + 20), "N");
				writeFundFile();
				combinedForcastignAndProjections();

			} else {
				this.gt.showMessageDialog("Unable to process request.\nPlease try again!");
			}

		}
	}

	public void changeResult() {

		// Using an If statement to check if there any funds added. This is simple to
		// make the user experience better. We could in theory let the user continue
		// without the check it wouldn’t crash the code however this would lead the user
		// to completing unnecessary steps that will have no action.

		if (this.currentNumberOfFunds == 0) {
			this.gt.showMessageDialog("Please add a fund before proceeding.");
		} else {

			int fundNumber = Integer
					.parseInt(this.gt.getInputString("Which fund would we like to change the result off? \n\n"
							+ "Fund number to the left off the fund name."));
			// BUG: If the user doesn’t input anything or inputs anything but an int value
			// the program will crash.

			// We are using a while loop with an int variable to limit the number of
			// attempts the user can have at inputting a valid value. We are limiting the
			// number of user attempts to account for the fact the user may have accidently
			// clicked the button or continuously enters the wrong value. Checking the input
			// allows us to ensure it is valid and won’t crash the code. An alternative to
			// the loop method would be an IF statement however this would only allow us to
			// check once or we could not check at all which could lead the code to crash.

			int userInputAttempts = 0;
			while ((fundNumber > this.currentNumberOfFunds || fundNumber < 0) && userInputAttempts < 3) {
				fundNumber = Integer.parseInt(this.gt.getInputString("Error: Unable to find relevant fund number.\n"
						+ "Which fund would we like to change the result off? \n\n"
						+ "Fund number to the left off the fund name."));
				userInputAttempts += 1;
			}
			// BUG: If the user doesn’t input anything or inputs anything but an int value
			// the program will crash.

			fundNumber -= 1;

			// Using an if / else if statement here as we have three possible
			// outcomes based on the user input. We could use entrenched if statements
			// however this would result in unnecessarily code. Not to mention the if / else
			// if / else layout is much more readable for other programs and shows clear
			// outcomes based on the user input.

			if (userInputAttempts != 3) {
				if (this.fund[fundNumber].getFundStatus() == 'N') {
					this.gt.showMessageDialog("Fund currently inactive, please activate to add result!\n"
							+ "You can check the fund status to the left off the fund number:\n\nY = Active / N = Inactive");
					userInputAttempts = 5;
				}
			} else if (userInputAttempts < 3 && this.fund[fundNumber].getCurrentSeason() == 0) {
				this.gt.showMessageDialog("Fund currently has zero results posted against it!");
				userInputAttempts = 5;
			}

			// Again we are doing user input validation with a while loop for the season
			// number, we want to ensure the user inputs a valid season for the respective
			// fund before proceeding. An alternative would be to just pass the value
			// through however this has the potential to crash the code. We are also using
			// another if statement below to check with a counter if we can proceed, we
			// haven’t used an else statement at the bottom of the code block as we don’t
			// want to show the user two error messages.

			int season = 0;
			if (userInputAttempts < 3) {
				season = Integer.parseInt(this.gt.getInputString("For fund " + this.fund[fundNumber].getFundName()
						+ ",\nwhich season result do you intend to change?"));
				// BUG: If the user doesn’t input anything or inputs anything but an int value
				// the program will crash.

				userInputAttempts = 0;
				while ((season > this.fund[fundNumber].getCurrentSeason() || season < 1) && userInputAttempts < 3) {

					this.gt.getInputString("Error: Invalid fund season!\n\n"
							+ "Please reference the season number to the left of the value in the list.\n" + "For fund "
							+ this.fund[fundNumber].getFundName() + ",\nwhich season result do you intend to change?");
					userInputAttempts += 1;
				}
				// BUG: If the user doesn’t input anything or inputs anything but an int value
				// the program will crash.

			}

			if (userInputAttempts < 3) {

				// As we are taking a user input, we want to account for invalid entries
				// occurring from things such as mistyping or copy and paste from an excel for
				// example. Alternatively, we could not do this however this does have the
				// possibility to crash the program.

				// Also we are converting decimal values to int, the reason for this is that
				// when we get into larger numbers it moves into scientific notation on the
				// list. This does not look good and something to avoid. It is a trade off
				// though as we will loose the cent values from the user input however this is
				// not an issue as we are dealing with large values and the cent will have
				// almost no impact throughout the program.

				String value = this.gt.getInputString("Value for season " + (season));

				// performing input validation that once we remove all but the numerical values
				// we have a valid input. We do not want to proceed if the input is not valid as
				// it will crash the code.

				if (!value.replaceAll("[^0-9\\.]", "").equals("") && !value.replaceAll("[^0-9\\.]", "").equals(".")) {

					double dvalue = Double.valueOf(value.replaceAll("[^0-9\\.]", ""));
					int valueOfSeason = (int) Math.round(dvalue);

					this.fund[fundNumber].setSeasonValue(season - 1, valueOfSeason);
					int seasonCount = 0;

					// As we are amending a list value, we will need to clear the list before
					// repopulating. We are using a calculation to determine the list spacing to
					// ensure it is consistent depending on the number of did gets in the season. We
					// could have two lists for this to avoid this however there isn’t a way to have
					// the list side by side scroll as one.

					while (seasonCount < this.fund[fundNumber].getCurrentSeason()) {
						this.gt.removeElementFromList(fundNumber + 8, 0);
						seasonCount += 1;

					}

					seasonCount = 0;
					while (seasonCount < this.fund[fundNumber].getCurrentSeason()) {

						String listSpacing = "        ";
						listSpacing = listSpacing.substring(0,
								listSpacing.length() - String.valueOf(seasonCount + 1).length());

						this.gt.addElementToList((fundNumber) + 8, (seasonCount + 1) + listSpacing + "$ "
								+ this.fund[fundNumber].getSeasonValue(seasonCount));
						seasonCount += 1;
					}

					writeFundFile();
					fundAnalytics(fundNumber);
					combinedForcastignAndProjections();

				} else {
					this.gt.showMessageDialog("Unable to add result.\nPlease try again!");
				}
			} else if (userInputAttempts == 3) {
				this.gt.showMessageDialog("Unable to Change result.\nPlease try again!");
			}

		}
	}

	public void removeResult() {

		// Using an If statement to check if there any funds added. This is simple to
		// make the user experience better. We could in theory let the user continue
		// without the check it wouldn’t crash the code however this would lead the user
		// to completing unnecessary steps that will have no action.

		if (this.currentNumberOfFunds == 0) {
			this.gt.showMessageDialog("Please add a fund before proceeding.");
		} else {

			int fundNumber = Integer
					.parseInt(this.gt.getInputString("Which fund would we like to remove the result off? \n\n"
							+ "Fund number to the left off the fund name."));
			// BUG: If the user doesn’t input anything or inputs anything but an int value
			// the program will crash.

			// We are using a while loop with an int variable to limit the number of
			// attempts the user can have at inputting a valid value. We are limiting the
			// number of user attempts to account for the fact the user may have accidently
			// clicked the button or continuously enters the wrong value. Checking the input
			// allows us to ensure it is valid and won’t crash the code. An alternative to
			// the loop method would be an IF statement however this would only allow us to
			// check once or we could not check at all which could lead the code to crash.

			int userInputAttempts = 0;
			while ((fundNumber > this.currentNumberOfFunds || fundNumber < 0) && userInputAttempts < 3) {
				fundNumber = Integer.parseInt(this.gt.getInputString("Error: Unable to find relevant fund number.\n"
						+ "Which fund would we like to change the result off? \n\n"
						+ "Fund number to the left off the fund name."));
				userInputAttempts += 1;
			}
			// BUG: If the user doesn’t input anything or inputs anything but an int value
			// the program will crash.

			fundNumber -= 1;

			// Using an if / else if statement here as we have three possible
			// outcomes based on the user input. We could use entrenched if statements
			// however this would result in unnecessarily code. Not to mention the if / else
			// if / else layout is much more readable for other programs and shows clear
			// outcomes based on the user input.
			if (userInputAttempts != 3) {
				if (this.fund[fundNumber].getFundStatus() == 'N') {
					this.gt.showMessageDialog("Fund currently inactive, please activate to add result!\n"
							+ "You can check the fund status to the left off the fund number:\n\nY = Active / N = Inactive");
					userInputAttempts = 5;
				}
			}

			else if (userInputAttempts < 3 && this.fund[fundNumber].getCurrentSeason() == 0) {
				this.gt.showMessageDialog("Fund currently has zero results posted against it!");
				userInputAttempts = 5;
			}

			// Again we are doing user input validation with a while loop for the season
			// number, we want to ensure the user inputs a valid season for the respective
			// fund before proceeding. An alternative would be to just pass the value
			// through however this has the potential to crash the code. We are also using
			// another if statement below to check with a counter if we can proceed, we
			// haven’t used an else statement at the bottom of the code block as we don’t
			// want to show the user two error messages.

			int season = 0;
			if (userInputAttempts < 3) {
				season = Integer.parseInt(this.gt.getInputString("For fund " + this.fund[fundNumber].getFundName()
						+ ",\nwhich season result do you intend to remove?"));
				// BUG: If the user doesn’t input anything or inputs anything but an int value
				// the program will crash.

				userInputAttempts = 0;
				while ((season > this.fund[fundNumber].getCurrentSeason() || season < 1) && userInputAttempts < 3) {

					this.gt.getInputString("Error: Invalid fund season!\n\n"
							+ "Please reference the season number to the left of the value in the list.\n" + "For fund "
							+ this.fund[fundNumber].getFundName() + ",\nwhich season result do you intend to remove?");
					userInputAttempts += 1;
				}
				// BUG: If the user doesn’t input anything or inputs anything but an int value
				// the program will crash.

			}

			if (userInputAttempts < 3) {

				season -= 1;

				// Here we are removing the selected result from the array. To do this we simply
				// need to move the required result to the last active element in the array and
				// set its value to zero. As we are not dealing with string arrays, we don’t
				// have to worry about clearing and re-populating the array to avoid null
				// errors.

				while (season < this.fund[fundNumber].getCurrentSeason() - 1) {

					int swapInt = this.fund[fundNumber].getSeasonValue(season);
					this.fund[fundNumber].setSeasonValue(season, this.fund[fundNumber].getSeasonValue(season + 1));
					this.fund[fundNumber].setSeasonValue(season + 1, swapInt);
					season += 1;
				}

				this.fund[fundNumber].setCurrentSeason(this.fund[fundNumber].getCurrentSeason() - 1);
				this.fund[fundNumber].setSeasonValue(this.fund[fundNumber].getCurrentSeason(), 0);

				// Two while loops to clear the appropriate list and re-populate. We need to do
				// this in the respective order to ensure the list are updated accurate with the
				// new information. Using a calculation to determine the list spacing to ensure
				// it is consistent depending on the number of did gets in the season. We could
				// have two lists for this to avoid this however there isn’t a way to have the
				// list side by side scroll as one.

				season = 0;
				while (season < this.fund[fundNumber].getCurrentSeason() + 1) {

					this.gt.removeElementFromList(fundNumber + 8, 0);
					season += 1;

				}

				season = 0;
				while (season < this.fund[fundNumber].getCurrentSeason()) {

					String listSpacing = "        ";
					listSpacing = listSpacing.substring(0, listSpacing.length() - String.valueOf(season + 1).length());

					this.gt.addElementToList((fundNumber) + 8,
							(season + 1) + listSpacing + "$ " + this.fund[fundNumber].getSeasonValue(season));
					season += 1;
				}

				writeFundFile();
				fundAnalytics(fundNumber);
				combinedForcastignAndProjections();

			} else if (userInputAttempts == 3) {
				this.gt.showMessageDialog("Unable to Change result.\nPlease try again!");
			}

		}
	}

	public void updateNoOfSeasonsInAvg() {

		// Here we are taking user inputs for the noOfSeasonsInAvg to be used
		// through the
		// program for analytics. We have checked the data and cleared out any unwanted
		// characters or symbols. We have also accounted for decimal values by rounding
		// them up. Whilst an alternative would be not to clean the data, we want to
		// make our program as user friendly as possible.

		// As there are only three values, we could just replicate the code three times.
		// However, we don’t want to do this as it is unnecessary code and if we ever
		// expand the code in the future that would mean we would have to complete
		// duplicating the block. For the same reason as this we haven’t hardcode “3” in
		// the comparison of the loop but rather measured the length of the array to
		// make future expansion if wanted easier.

		int seasonCount = 0;
		while (seasonCount < this.noOfSeasonsInAvg.length) {

			if (this.gt.getTextFromEntry(seasonCount).replaceAll("[^0-9\\.]", "").equals("")
					|| this.gt.getTextFromEntry(seasonCount).replaceAll("[^0-9\\.]", "").equals(".")) {
				this.gt.showMessageDialog("Season values can not be empty");

			} else if (Double.valueOf(this.gt.getTextFromEntry(seasonCount).replaceAll("[^0-9\\.]", "")) < 2.0) {
				this.gt.showMessageDialog("Season values can not be less than 2");

			}

			else {
				double noOfSeasonsInAvgValue = Double
						.valueOf(this.gt.getTextFromEntry(seasonCount).replaceAll("[^0-9\\.]", ""));
				this.noOfSeasonsInAvg[seasonCount] = (int) Math.round(noOfSeasonsInAvgValue);
			}
			seasonCount += 1;
		}

		// Using a simple bubble sort here to sort the array before using two loops to
		// update both the text are and each funds value based on the user input. We
		// could have used more sophisticated sorting techniques insertion sort however
		// as this array is limited to a small size this is not necessary.

		int loopCount = 0;
		seasonCount = 0;
		while (loopCount < this.noOfSeasonsInAvg.length) {
			while (seasonCount < (this.noOfSeasonsInAvg.length - 1)) {
				if ((new Integer(this.noOfSeasonsInAvg[seasonCount])
						.compareTo(this.noOfSeasonsInAvg[seasonCount + 1])) > 0) {

					int swapNoOfSeasonsInAvg = this.noOfSeasonsInAvg[seasonCount];
					this.noOfSeasonsInAvg[seasonCount] = this.noOfSeasonsInAvg[seasonCount + 1];
					this.noOfSeasonsInAvg[seasonCount + 1] = swapNoOfSeasonsInAvg;
					seasonCount = 0;
				}
				seasonCount += 1;
			}
			seasonCount = 0;
			loopCount += 1;
		}

		seasonCount = 0;
		while (seasonCount < this.noOfSeasonsInAvg.length) {
			this.gt.setTextInEntry(seasonCount, String.valueOf(this.noOfSeasonsInAvg[seasonCount]));
			seasonCount += 1;
		}

		int fundNumber = 0;
		while (fundNumber < this.currentNumberOfFunds) {
			fundAnalytics(fundNumber);
			fundNumber += 1;
		}

		writeConfigFile();
		combinedForcastignAndProjections();

	}

	public void fundAnalytics(int fundNumber) {

		// The reason we have created a separate method here is as this code is called
		// multiple times, we don’t want to be duplicating it. This will allow the
		// function to be called as needed.

		// As the user may change the Season Values, we need to clear the relevant
		// fields before proceeding as they may not be updated due to conditions not
		// being met below. We could use a loop to cycle through the small number of
		// areas to clear however this would require three loops as the text area index
		// is not in a consistent pattern. As there is only a small number of areas to
		// clear we have hard coded this however if we were to expand this in the
		// future, we could look to change this.

		this.gt.setTextInEntry(fundNumber * 11 + 25, "Last " + this.noOfSeasonsInAvg[0] + " Season Avg");
		this.gt.setTextInEntry(fundNumber * 11 + 26, "");
		this.gt.setTextInEntry(fundNumber * 11 + 27, "Last " + this.noOfSeasonsInAvg[1] + " Season Avg");
		this.gt.setTextInEntry(fundNumber * 11 + 28, "");
		this.gt.setTextInEntry(fundNumber * 11 + 29, "Last " + this.noOfSeasonsInAvg[2] + " Season Avg");
		this.gt.setTextInEntry(fundNumber * 11 + 30, "");

		// Using Four if statements to check the conditions are true to populate the
		// relevant individual analytics fields. We don’t want to be using
		// loops here as we just want to check if the condition is true before
		// populating. We also don’t want to use “else if” statements as there will be
		// instances where multiple statements are true and we need to populate multiple
		// analytic fields.

		if (this.fund[fundNumber].getCurrentSeason() >= this.noOfSeasonsInAvg[0]) {
			double growth = fundGrowth(fundNumber, this.noOfSeasonsInAvg[0]);
			this.gt.setTextInEntry((fundNumber * 11 + 26), growth + "%");

		}

		if (this.fund[fundNumber].getCurrentSeason() >= this.noOfSeasonsInAvg[1]) {
			double growth = this.fundGrowth(fundNumber, this.noOfSeasonsInAvg[1]);
			this.gt.setTextInEntry((fundNumber * 11 + 28), growth + "%");

		}

		if (this.fund[fundNumber].getCurrentSeason() >= this.noOfSeasonsInAvg[2]) {
			double growth = this.fundGrowth(fundNumber, this.noOfSeasonsInAvg[2]);
			this.gt.setTextInEntry((fundNumber * 11 + 30), growth + "%");

		}

		if (this.fund[fundNumber].getCurrentSeason() >= 2) {

			int profit = (this.fund[fundNumber].getSeasonValue(this.fund[fundNumber].getCurrentSeason() - 1)
					- this.fund[fundNumber].getSeasonValue(0));

			double growth = fundGrowth(fundNumber, 0);
			this.gt.setTextInEntry((fundNumber * 11 + 22), "$ " + profit);
			this.gt.setTextInEntry((fundNumber * 11 + 24), growth + "%");
		}

	}

	public void combinedForcastignAndProjections() {

		// The reason we have created a separate method here is as this code is called
		// multiple times, we don’t want to be duplicating it. This will allow the
		// function to be called as needed.

		// Using a variable here to limit the number of years displayed in
		// forecasting, we could hardcode this however this would mean if we ever wanted
		// to change the value in the future this is a lot easier.

		int forcastNumYears = 12;

		// As the user may change the Season Values, we need to clear the relevant
		// fields before proceeding as they may not be updated due to conditions not
		// being met below. We could use a loop to cycle through the small number of
		// areas to clear however this would require three loops as the text area index
		// is not in a consistent pattern. As there is only a small number of areas to
		// clear we have hard coded this however if we were to expand this in the
		// future, we could look to change this.

		this.gt.setFontSize(14);
		this.gt.setFontColor(Color.BLACK);

		this.gt.setTextInEntry(4, "");
		this.gt.setTextInEntry(6, "");
		this.gt.setTextInEntry(8, "");

		this.gt.setTextInEntry(9, "Last " + this.noOfSeasonsInAvg[0] + " Season Avg");
		this.gt.setTextInEntry(10, "");
		this.gt.setTextInEntry(11, "Last " + this.noOfSeasonsInAvg[1] + " Season Avg");
		this.gt.setTextInEntry(12, "");
		this.gt.setTextInEntry(13, "Last " + this.noOfSeasonsInAvg[2] + " Season Avg");
		this.gt.setTextInEntry(14, "");

		this.gt.setTextInEntry(16, "");

		this.gt.setTextInEntry(17, this.noOfSeasonsInAvg[0] + " Season Avg Forecast");
		this.gt.setTextInEntry(18, this.noOfSeasonsInAvg[1] + " Season Avg Forecast");
		this.gt.setTextInEntry(19, this.noOfSeasonsInAvg[2] + " Season Avg Forecast");

		// Using a combination of loops and IF statements here to check the current
		// lists before we proceed. If the list has any values in it, we want to clear
		// them before attempting to re-populate. We could perform this operation in
		// segments below in the IF statements, however this would resolve in
		// duplications of code. Also if the user where to increase the value of the
		// number of season In averages it would then not be called.

		int year = 0;
		int list = 0;

		while (list < 4) {
			if (this.listPopulated[list] == 1) {
				year = 0;
				while (year < forcastNumYears) {
					this.gt.removeElementFromList(list, 0);
					year += 1;
				}
				this.listPopulated[list] = 0;

			}
			list += 1;
		}

		while (this.listPopulated[4] != 0) {
			this.gt.removeElementFromList(4, 0);
			this.gt.removeElementFromList(5, 0);
			this.gt.removeElementFromList(6, 0);
			this.gt.removeElementFromList(7, 0);
			this.listPopulated[4] -= 1;

		}

		if (this.currentNumberOfFunds != 0) {

			// Below we need to determine the index number (Fund Number) of the fund with
			// the largest number off seasons posted against it that is still active. We
			// need to do this as below we will be looking to populate analytic fields and
			// we cannot go beyond the largest number off seasons. We have used a while loop
			// to cycle through all the funds and an IF statement to make the comparison. A
			// viable alternative would be to sort the array from largest to smallest then
			// use the index of zero however this would mean we need to sort all arrays in
			// parallel and account for the new ordering in other sections of code.

			int fundNumber = 0;

			int greatestNumberOfFundSeasons = 0;
			int indexOfFund = 0;

			while (fundNumber < this.currentNumberOfFunds) {

				if ((this.fund[fundNumber].getCurrentSeason() > greatestNumberOfFundSeasons)
						&& (this.fund[fundNumber].getFundStatus() == 'Y')) {
					greatestNumberOfFundSeasons = this.fund[fundNumber].getCurrentSeason();
					indexOfFund = fundNumber;

				}
				fundNumber += 1;
			}

			fundNumber = indexOfFund;

			// Using an IF statement to check if the fund is still active, whilst we did do
			// this above before altering the value of the fundNumber, we need to consider
			// if the user has set all funds to inactive. An alternative to this would be to
			// include the below block off code in the loops entrenched IF statement however
			// this would cause the code to unnecessarily trigger multiple times if multiple
			// funds are active.

			if (this.fund[fundNumber].getFundStatus() == 'Y') {

				// Below we are using four IF statements to check the conditions for each season
				// analytic we need to populate. Whilst we could use a loop here with an
				// entrenched IF statement as an alternative to cycle through all four text
				// areas that need to be updated. This would mean we need to keep track of a
				// multitude of variables for the noOfSeasonsInAvg Index, Text area index
				// and we also would need to account for the hard-coded comparison of value of
				// 2.

				if (this.fund[fundNumber].getCurrentSeason() >= this.noOfSeasonsInAvg[0]) {

					double avergeGrowthForPeriod = combinedFundGrowth(this.noOfSeasonsInAvg[0]);
					this.gt.setTextInEntry(10, combinedFundGrowth(this.noOfSeasonsInAvg[0]) + "%");

					year = 0;
					while (year < forcastNumYears) {
						year += 1;
						int forcast = combinedForcasting(avergeGrowthForPeriod, year);

						this.gt.addElementToList(0, "Year " + year);
						this.gt.addElementToList(1, "$ " + forcast);

					}

					this.listPopulated[0] = 1;
					this.listPopulated[1] = 1;
				}

				if (this.fund[fundNumber].getCurrentSeason() >= this.noOfSeasonsInAvg[1]) {

					double avergeGrowthForPeriod = combinedFundGrowth(this.noOfSeasonsInAvg[1]);
					this.gt.setTextInEntry(12, avergeGrowthForPeriod + "%");

					year = 0;
					while (year < forcastNumYears) {
						year += 1;
						int forcast = combinedForcasting(avergeGrowthForPeriod, year);

						this.gt.addElementToList(2, "$ " + forcast);

					}
					this.listPopulated[2] = 1;

				}

				if (this.fund[fundNumber].getCurrentSeason() >= this.noOfSeasonsInAvg[2]) {

					double avergeGrowthForPeriod = combinedFundGrowth(this.noOfSeasonsInAvg[2]);
					this.gt.setTextInEntry(14, avergeGrowthForPeriod + "%");

					year = 0;
					while (year < forcastNumYears) {
						year += 1;
						int forcast = combinedForcasting(avergeGrowthForPeriod, year);

						this.gt.addElementToList(3, "$ " + forcast);

					}
					this.listPopulated[3] = 1;
				}

				if (this.fund[fundNumber].getCurrentSeason() >= 2) {

					// Below there are multiple operations performed if any fund has more then two
					// seasons posted against it. Whilst this is a large amount of code for one
					// method we don’t repeat this anywhere else thus the reason we have broken it
					// into any other methods of its own.

					// Calculating the combined profit off all active funds by using a while loop
					// with an entrenched IF statement to ensure the conditions are valid. We have
					// also used a char variable to check if there any valid entries. We could use
					// almost any other variable to do this however we went with the char to keep
					// our programming style consistent and readable as we have done this status
					// variable throughout the program for other reasons.

					fundNumber = 0;
					int combinedOriginal = 0;
					int combinedCurrent = 0;
					char validEntires = 'N';

					while (fundNumber < this.currentNumberOfFunds) {
						if (this.fund[fundNumber].getCurrentSeason() > 1
								&& this.fund[fundNumber].getFundStatus() == 'Y') {
							combinedOriginal += this.fund[fundNumber].getSeasonValue(0);
							combinedCurrent += this.fund[fundNumber]
									.getSeasonValue(this.fund[fundNumber].getCurrentSeason() - 1);
							validEntires = 'Y';
						}
						fundNumber += 1;
					}

					if (validEntires == 'Y') {
						int combinedProfit = combinedCurrent - combinedOriginal;
						this.gt.setTextInEntry(4, "$ " + combinedCurrent);
						this.gt.setTextInEntry(6, "$ " + combinedProfit);
					}

					double avergeGrowthForPeriod = combinedFundGrowth(0);
					this.gt.setTextInEntry(8, avergeGrowthForPeriod + "%");

					// calculating the max draw percentage across all accounts from one season to
					// the next. We have used a two loop entrenched with IF statements. We need to
					// account for each fund and all of its season values hence the two loops. As an
					// alternative we could have stored this information as an array throughout the
					// program and just sort to the lowest value however this would mean another
					// array to manage throughout the program.

					fundNumber = 0;
					double maxDD = 0.00;
					while (fundNumber < this.currentNumberOfFunds) {
						if (this.fund[fundNumber].getCurrentSeason() > 1
								&& this.fund[fundNumber].getFundStatus() == 'Y') {
							int season = 1;
							while (season < this.fund[fundNumber].getCurrentSeason()) {
								int original = this.fund[fundNumber].getSeasonValue(season - 1);
								int current = this.fund[fundNumber].getSeasonValue(season);

								DecimalFormat df = new DecimalFormat("#.##");
								df.setRoundingMode(RoundingMode.CEILING);

								double value = (((Double.valueOf(current) - Double.valueOf(original))
										/ Double.valueOf(original)) * 100);
								value = Double.valueOf(df.format(value));

								if (value < maxDD) {
									maxDD = value;

								}
								season += 1;
							}
						}
						fundNumber += 1;
					}

					this.gt.setTextInEntry(16, maxDD + "%");

					// The below block of code utilizes an IF statement entrenched in a while loop
					// to confirm the fund has meets the condition for comparison before adding
					// their name to the list. Whilst we could have just added the fund name to the
					// list when the user added it to the program which would have negated the need
					// for both storing the values and the below block of code. This would have
					// caused issue below when trying to make calculations and also would mean when
					// the user deactivates the fund the comparison would still be there.

					fundNumber = 0;
					while (fundNumber < this.currentNumberOfFunds) {
						if (this.fund[fundNumber].getCurrentSeason() > 1
								&& this.fund[fundNumber].getFundStatus() == 'Y') {
							this.gt.addElementToList(4, this.fund[fundNumber].getFundName());
							this.listPopulated[4] += 1;
						}
						fundNumber += 1;
					}

					// Below there is quite an extensive amount of operations, cycling through each
					// fund to perform the relevant calculations for comparison. We have combined
					// all three comparisons calculations here, whilst a valid alternative would be
					// to break each section down and perform the calculations independently this
					// would involve an extensive amount of repetitious code.

					fundNumber = 0;
					while (fundNumber < this.currentNumberOfFunds) {

						double original = 0.0;
						double current = 0.0;
						double profit = 0.0;
						int profitCount = 0;
						double drawDown = 0.0;
						int drawDownCount = 0;
						int season = 1;

						while (season < this.fund[fundNumber].getCurrentSeason()
								&& this.fund[fundNumber].getFundStatus() == 'Y') {

							original = (double) this.fund[fundNumber].getSeasonValue(season - 1);
							current = (double) this.fund[fundNumber].getSeasonValue(season);
							if (current - original > 0) {
								profit += current - original;
								profitCount += 1;
							}

							else if (current - original < 0) {
								drawDown += current - original;
								drawDownCount += 1;
							}

							season += 1;
						}

						if (this.fund[fundNumber].getCurrentSeason() > 1
								&& this.fund[fundNumber].getFundStatus() == 'Y') {
							if (profitCount == 0) {
								profit = 1.0;
								profitCount = 1;
							} else {
								profit = profit / profitCount;
							}

							if (drawDown == 0) {
								drawDown = 1.0;
								drawDownCount = 1;
							} else {
								drawDown = drawDown / drawDownCount;
								drawDown = drawDown * (-1.0);
							}

							DecimalFormat df = new DecimalFormat("#.##");
							df.setRoundingMode(RoundingMode.CEILING);

							double eposure = (current / combinedCurrent) * 100;

							this.gt.addElementToList(5, Double.valueOf(df.format(profit / drawDown)));
							this.gt.addElementToList(6, Double.valueOf(df.format(profitCount / drawDownCount)));
							this.gt.addElementToList(7, Double.valueOf(df.format(eposure)) + "%");

						}
						fundNumber += 1;

					}

				}
			}

		}

	}

	public double fundGrowth(int fundNumber, int seasonGrowthPeriod) {

		// The reason we have created a separate method here is as this code is called
		// multiple times, we don’t want to be duplicating it. This will allow the
		// function to be called as needed.

		// For the growth calculation we need to convert the int variables to a double
		// to account for the valueOfSeasonForFund of the division. If we don’t do this
		// we will be left with only whole number due to the limitations of int meaning
		// we can only report every 100% growth cycle.

		int current = this.fund[fundNumber].getSeasonValue(this.fund[fundNumber].getCurrentSeason() - 1);
		int original = 0;

		// Performing a check here to see if the variable seasonGrowthPeriod = 0.
		// We are doing this as we would never want to compare the growth of a season
		// against itself. Thus, we will take the opportunity to utilize the zero value
		// to account for the full fund’s growth. We could make another method just for
		// this however this would result in another method to manage and it would be
		// unnecessary.

		if (seasonGrowthPeriod == 0) {
			original = this.fund[fundNumber].getSeasonValue(0);

		} else {
			original = this.fund[fundNumber]
					.getSeasonValue(this.fund[fundNumber].getCurrentSeason() - seasonGrowthPeriod);
		}

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);

		double growth = (((Double.valueOf(current) - Double.valueOf(original)) / Double.valueOf(original)) * 100);

		if (seasonGrowthPeriod != 0) {
			growth = growth / seasonGrowthPeriod;

		}

		return Double.valueOf(df.format(growth));

	}

	public double combinedFundGrowth(int seasonGrowthPeriod) {

		// Creating a method here as this block of code is called multiple times
		// throughout the program. we are using a while loop to cycle through all the
		// funds and an IF statement to check the conditions. An alternative would be
		// throughout the program to just call the below “fundGrowth” method and have a
		// loop to cycle through all the funds. However this would result in the
		// unnecessary calling of a method potentially many times.

		double sumOfFunds = 0.00;
		int numberOfFundsWithValidSeasons = 0;

		int fundNumber = 0;
		while (fundNumber < this.currentNumberOfFunds) {

			if (this.fund[fundNumber].getCurrentSeason() != 0
					&& this.fund[fundNumber].getCurrentSeason() >= seasonGrowthPeriod
					&& this.fund[fundNumber].getFundStatus() == 'Y') {

				sumOfFunds += this.fundGrowth(fundNumber, seasonGrowthPeriod);
				numberOfFundsWithValidSeasons += 1;

			}
			fundNumber += 1;
		}

		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.CEILING);

		double avergeGrowthForPeriod = (sumOfFunds / numberOfFundsWithValidSeasons);
		return Double.valueOf(df.format(avergeGrowthForPeriod));
	}

	public int combinedForcasting(double avergeGrowthForPeriod, int year) {

		// The reason we have created a separate method here is as this code is called
		// multiple times, we don’t want to be duplicating it. This will allow the
		// function to be called as needed.

		// Calculating the sum of the current value of all active funds with an If
		// statement entrenched in a while loop. An alternative to this would be to have
		// it as a method variable, requiring for this block of code to be repeated many
		// times. Also including an IF statement below to check the output of the
		// calculation. This isn’t required however it would leave the user with a
		// negative balance on their account, it would make more sense within the
		// function of the program to stop at zero.

		int fundNumber = 0;
		int combinedCurrent = 0;

		while (fundNumber < this.currentNumberOfFunds) {
			if (this.fund[fundNumber].getCurrentSeason() != 0 && this.fund[fundNumber].getFundStatus() == 'Y') {
				combinedCurrent += this.fund[fundNumber].getSeasonValue(this.fund[fundNumber].getCurrentSeason() - 1);
			}
			fundNumber += 1;
		}

		double rate = 1 + (avergeGrowthForPeriod / 12);
		rate = Math.pow(rate, year);
		int forcast = (int) Math.round(combinedCurrent * rate);

		if (forcast < 0) {
			forcast = 0;
		}

		return forcast;

	}

	public void writeFundFile() {

		// The reason we have created a separate method here is as this code is called
		// multiple times, we don’t want to be duplicating it. This will allow the
		// function to be called as needed.

		String fileName = "Fund.txt";

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

			int fundNumber = 0;
			int season = 0;
			while (fundNumber < this.currentNumberOfFunds) {
				bw.write(this.fund[fundNumber].getFundName() + "," + this.fund[fundNumber].getFundStatus() + ","
						+ this.fund[fundNumber].getCurrentSeason() + "," + this.fund[fundNumber].getMaxNoSeason());

				// Using a while loop to cycle through the season results and add them to the
				// file, as we have an indefinite number of values a loop is required.

				season = 0;
				while (season < this.fund[fundNumber].getCurrentSeason()) {

					bw.write("," + Integer.toString(this.fund[fundNumber].getSeasonValue(season)));
					season += 1;
				}
				bw.write("\n");

				fundNumber += 1;
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void writeConfigFile() {

		// The reason we have created a separate method here is as this code is called
		// multiple times, we don’t want to be duplicating it. This will allow the
		// function to be called as needed.

		String fileName = "Config.txt";

		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

			bw.write(Integer.toString(this.maxNumFunds));

			// Using a while loop here to add the noOfSeasonsInAvg, we could have just as
			// easily coded each value without the loop however this makes it easier to
			// expand the noOfSeasonsInAvg in the future.

			int seasonAvg = 0;
			while (seasonAvg < 3) {

				if (seasonAvg < 3) {
					bw.write(",");
				}
				bw.write(Integer.toString(this.noOfSeasonsInAvg[seasonAvg]));

				seasonAvg += 1;
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void gettingStarted() {

		// Using a message dialog box here to display the getting started / help
		// information. We could go down the path of creating another gterm window just
		// for this however this would be over complicating a very simple function. With
		// the message dialog box we don’t need to consider formatting etc…

		this.gt.showMessageDialog(
				"Welcome to the fund Management and Forecaster tool, the purpose of this program is to manage,\n"
						+ "forecast and compare your current investment options such as managed funds, hedge funds, super,\n"
						+ "self-managed and so forth. After the you has created a fund and populated the results, individual\n"
						+ "fund analytics, combined analytics, forecast and comparisons will be calculated to help you manage\n"
						+ " your positions and build a fundamentally sound projection and comparison.\n\n"

						+ "FUND OPERATIONS:\n"
						+ "Adding a fund: To add a result for the desired fund, select “add Result” You will be prompted to enter\n"
						+ "a Fund number which is the number to the left off the fund name, then the result for the next season.\n"
						+ "Please do note that whilst you can enter cents (ie $10,900.56) the value will be rounded to the nearest \n"
						+ "dollar (ie $10,901).\n\n"

						+ "Adding Result: this will be used to add a result to your fund. Once you have added a fund to the left\n"
						+ "of the name there will be a fund number, please use this valuewhen first prompted to add a result.\n"
						+ "Then please add the dollar value for this season.\n\n"

						+ "FUND STATUS:\n"
						+ "Active / Inactive: The Active / Inactive buttons will enable you disable and enable a desired fund. \n"
						+ "If a fund is no longer active you can disable said fund and this will remove it from all combined analytics,\n"
						+ "forecasting and comparisons. Whilst the opposite will happen if you activate a currently inactive fund.\n"
						+ "To change the status of the fund, select Active / Inactive depending on what is relevant then input the\n"
						+ "fund number when prompted\n\n"

						+ "AMEND RESULTS:\n"
						+ "Change Results: To change the result of a season for a fund, select “change Result’, you will then be\n"
						+ "prompted to enter the fund number, then the season number and then finally the new result for the\n"
						+ "respective funds season.\n\n"

						+ "Remove result: To remove the result, select remove result the you will then be prompted to input the\n"
						+ "fund number than the season number. This will remove the result for the relevant funds season.\n\n"

						+ "NUMBER OF SEASONS IN AVERAGES:\n"
						+ "Change Number of seasons in averages: To change the values used in calculating the independent fund\n"
						+ "analytics as well as the Combined analytics and forecasting  you will need to input the new values as\n"
						+ "numbers in the relevant fields and select update. ");

	}

	public static void main(String[] args) {

		ManagerAndForecaster objName;
		objName = new ManagerAndForecaster();

		objName.ObjfundManagementAndForecaster();

	}
}

//Reference List:

//[1] Designer G. “Stock market or forex trading graph in graphic concept, Vector illustrator” pintrest.com. https://www.pinterest.com.au/pin/485896247296672918/ (accessed Apr. 29, 2020).